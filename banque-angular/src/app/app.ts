import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { BanqueService, Compte, Consommateur, Operation } from './banque.service';
import { Icon } from './icon';

@Component({
  selector: 'app-root',
  imports: [FormsModule, Icon],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private api = inject(BanqueService);
  private changeDetector = inject(ChangeDetectorRef);
  @ViewChild('operationPanel') operationPanel!: ElementRef<HTMLElement>;

  consommateurs: { id: Consommateur; name: string }[] = [
    { id: 'java', name: 'Java' },
    { id: 'python', name: 'Python' },
    { id: 'php', name: 'PHP' },
    { id: 'js', name: 'JavaScript' }
  ];
  selected: Consommateur = 'java';
  statuses: Record<Consommateur, string> = {
    java: 'checking', python: 'checking', php: 'checking', js: 'checking'
  };

  comptes: Compte[] = [];
  loading = true;
  busy = false;
  connectionError = '';
  search = '';
  page = 1;
  pageSize = 6;
  operation: Operation = 'creerCompte';
  form = { code: null as number | null, destination: null as number | null, montant: 0 as number | null };
  confirmDelete = false;
  formError = '';
  success = '';
  result: Compte | number | null = null;
  private requestNumber = 0;

  operations: { id: Operation; label: string; description: string; button: string }[] = [
    { id: 'creerCompte', label: 'Créer un compte', description: 'Indiquez le solde de départ. Il peut être nul.', button: 'Créer le compte' },
    { id: 'verser', label: 'Verser de l’argent', description: 'Choisissez un compte et le montant à ajouter.', button: 'Confirmer le versement' },
    { id: 'retirer', label: 'Retirer de l’argent', description: 'Le montant doit être inférieur ou égal au solde.', button: 'Confirmer le retrait' },
    { id: 'virement', label: 'Faire un virement', description: 'Choisissez les comptes source et destination.', button: 'Confirmer le virement' },
    { id: 'getCompte', label: 'Consulter un compte', description: 'Affichez les détails du compte sélectionné.', button: 'Consulter le compte' },
    { id: 'conversion', label: 'Convertir un montant', description: 'Le service multiplie le montant par 3,5.', button: 'Convertir' },
    { id: 'supprimerCompte', label: 'Supprimer un compte', description: 'Seuls les comptes avec un solde nul peuvent être supprimés.', button: 'Supprimer le compte' }
  ];

  ngOnInit() {
    this.refreshConnections();
    this.loadAccounts();
  }

  get consumer() { return this.consommateurs.find(c => c.id === this.selected)!; }
  get info() { return this.operations.find(op => op.id === this.operation)!; }
  get total() { return this.comptes.reduce((sum, compte) => sum + compte.solde, 0); }
  get filtered() { return this.comptes.filter(c => String(c.numCompte).includes(this.search.trim())); }
  get pageCount() { return Math.max(1, Math.ceil(this.filtered.length / this.pageSize)); }
  get visibleAccounts() { return this.filtered.slice((this.page - 1) * this.pageSize, this.page * this.pageSize); }
  get accountResult() { return this.result && typeof this.result === 'object' ? this.result : null; }
  get numberResult() { return typeof this.result === 'number' ? this.result : null; }
  get selectedAccount() { return this.comptes.find(c => c.numCompte === this.form.code); }

  money(value: number) {
    return new Intl.NumberFormat('fr-FR', { minimumFractionDigits: 2, maximumFractionDigits: 2 }).format(value);
  }

  date(value: string) {
    const date = new Date(value);
    return Number.isNaN(date.getTime()) ? value : new Intl.DateTimeFormat('fr-FR').format(date);
  }

  async refreshConnections() {
    await Promise.all(this.consommateurs.map(async consumer => {
      this.statuses[consumer.id] = 'checking';
      try { await this.api.health(consumer.id); this.statuses[consumer.id] = 'online'; }
      catch { this.statuses[consumer.id] = 'offline'; }
    }));
  }

  async loadAccounts() {
    const request = ++this.requestNumber;
    const consumer = this.selected;
    this.loading = true;
    this.connectionError = '';
    try {
      const comptes = await this.api.appeler<Compte[]>(consumer, 'listComptes');
      if (request !== this.requestNumber) return;
      this.comptes = comptes || [];
      this.statuses[consumer] = 'online';
      this.page = Math.min(this.page, this.pageCount);
      if (!this.comptes.some(c => c.numCompte === this.form.code)) this.form.code = null;
      if (!this.comptes.some(c => c.numCompte === this.form.destination)) this.form.destination = null;
    } catch (err) {
      if (request !== this.requestNumber) return;
      this.comptes = [];
      this.statuses[consumer] = 'offline';
      this.connectionError = this.errorMessage(err);
    } finally {
      if (request === this.requestNumber) this.loading = false;
    }
  }

  selectConsumer(id: Consommateur) {
    if (this.busy || this.selected === id) return;
    this.selected = id;
    this.clearFeedback();
    this.loadAccounts();
  }

  clearFeedback() {
    this.formError = '';
    this.success = '';
    this.result = null;
    this.confirmDelete = false;
  }

  changeOperation() {
    this.clearFeedback();
    this.form.montant = this.operation === 'creerCompte' ? 0 : null;
    this.form.destination = null;
  }

  open(operation: Operation, code?: number) {
    this.operation = operation;
    this.changeOperation();
    this.form.code = code ?? null;
    this.changeDetector.detectChanges();
    this.operationPanel.nativeElement.scrollIntoView({ block: 'nearest' });
    this.operationPanel.nativeElement.querySelector<HTMLElement>('.first-field')?.focus({ preventScroll: true });
  }

  async submit() {
    if (this.busy || this.loading) return;
    this.formError = '';
    this.success = '';
    this.result = null;
    const { code, destination, montant } = this.form;
    const operation = this.operation;
    let params: object;
    if (operation === 'creerCompte') params = { soldeInitial: montant };
    else if (operation === 'conversion') params = { montant };
    else if (operation === 'getCompte' || operation === 'supprimerCompte') {
      if (operation === 'supprimerCompte' && !this.confirmDelete) return;
      params = { code };
    } else if (operation === 'virement') {
      if (code === destination) { this.formError = 'Choisissez deux comptes différents.'; return; }
      params = { codeSource: code, codeDestination: destination, montant };
    } else params = { code, montant };

    this.busy = true;
    try {
      const data = await this.api.appeler<Compte | number | string | null>(this.selected, operation, params);
      if (operation === 'getCompte') {
        if (data) this.result = data as Compte;
        else this.formError = 'Ce compte est introuvable.';
      } else if (operation === 'conversion') {
        this.result = data as number;
      } else {
        if (operation === 'creerCompte') {
          const compte = data as Compte;
          this.success = `Le compte n° ${compte.numCompte} a été créé.`;
          this.result = compte;
          this.form.code = compte.numCompte;
        } else {
          this.success = String(data);
        }
        this.confirmDelete = false;
        this.form.montant = operation === 'creerCompte' ? 0 : null;
        await this.loadAccounts();
      }
    } catch (err) { this.formError = this.errorMessage(err); }
    finally { this.busy = false; }
  }

  errorMessage(err: unknown) {
    if (err instanceof HttpErrorResponse) {
      if (typeof err.error?.message === 'string') return err.error.message.replace(/^S:Server:\s*/, '');
      return `Connexion à ${this.consumer.name} impossible. Vérifiez que son API et le serveur SOAP sont démarrés.`;
    }
    if (err instanceof Error && err.name === 'TimeoutError') return 'Le service ne répond pas. Réessayez dans un instant.';
    return err instanceof Error ? err.message : 'Une erreur est survenue.';
  }
}

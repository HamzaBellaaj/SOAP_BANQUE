import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, timeout } from 'rxjs';

export interface Compte {
  numCompte: number;
  solde: number;
  dateCreation: string;
}

export type Consommateur = 'java' | 'python' | 'php' | 'js';
export type Operation = 'creerCompte' | 'verser' | 'retirer' | 'virement'
  | 'supprimerCompte' | 'getCompte' | 'conversion';

interface Reponse<T> {
  success: boolean;
  consumer: string;
  data: T;
  message?: string;
}

@Injectable({ providedIn: 'root' })
export class BanqueService {
  private http = inject(HttpClient);

  health(consommateur: Consommateur) {
    return firstValueFrom(this.http.get(`/api/${consommateur}/health`).pipe(timeout(12000)));
  }

  async appeler<T>(consommateur: Consommateur, operation: string, params: object = {}): Promise<T> {
    const reponse = await firstValueFrom(this.http.post<Reponse<T>>(
      `/api/${consommateur}/execute`, { operation, params }
    ).pipe(timeout(15000)));
    if (!reponse.success) throw new Error(reponse.message || 'Opération refusée.');
    return reponse.data;
  }
}

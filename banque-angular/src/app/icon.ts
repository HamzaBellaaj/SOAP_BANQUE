import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-icon',
  template: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6"
    stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
    <path [attr.d]="paths[name] || paths['arrow']" />
  </svg>`,
  styles: [`:host { display: inline-flex; width: 20px; height: 20px; flex-shrink: 0; } svg { width: 100%; height: 100%; }`]
})
export class Icon {
  @Input() name = 'arrow';
  paths: Record<string, string> = {
    grid: 'M3 3h7v7H3z M14 3h7v7h-7z M3 14h7v7H3z M14 14h7v7h-7z',
    wallet: 'M20 7V5a2 2 0 0 0-2-2H5a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h15V7H5a2 2 0 0 1 0-4 M20 11h-6v6h6 M16 14h.01',
    transfer: 'M3 7h17 M16 3l4 4-4 4 M21 17H4 M8 13l-4 4 4 4',
    down: 'M12 3v14 M6 11l6 6 6-6 M4 20h16',
    up: 'M12 17V3 M6 9l6-6 6 6 M4 20h16',
    arrow: 'M5 12h14 M13 6l6 6-6 6',
    diagonal: 'M6 18L18 6 M6 6h12v12',
    plus: 'M12 5v14 M5 12h14',
    search: 'M21 21l-5-5 M18 10a8 8 0 1 1-16 0 8 8 0 0 1 16 0',
    refresh: 'M20 7V3l-3 3a8 8 0 1 0 3 10 M20 3h-5',
    chevron: 'M6 9l6 6 6-6',
    code: 'M8 5l-7 7 7 7 M16 5l7 7-7 7 M14 3l-4 18',
    shield: 'M12 2l9 4v6c0 5-9 10-9 10S3 17 3 12V6z M8 12l3 3 5-6',
    clock: 'M22 12a10 10 0 1 1-20 0 10 10 0 0 1 20 0 M12 6v6l4 2',
    close: 'M6 6l12 12 M18 6L6 18',
    check: 'M5 12l4 4L19 6',
    trash: 'M3 6h18 M9 6V3h6v3 M5 6l1 15h12l1-15 M10 10v7 M14 10v7',
    eye: 'M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12 M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0',
    export: 'M12 3v12 M7 10l5 5 5-5 M4 16v5h16v-5',
    help: 'M22 12a10 10 0 1 1-20 0 10 10 0 0 1 20 0 M9 8a3 3 0 0 1 6 1c0 2-3 2-3 5 M12 17h.01',
    bank: 'M3 9h18L12 3z M5 10v8 M10 10v8 M14 10v8 M19 10v8 M3 21h18',
    alert: 'M12 8v5 M12 17h.01 M10 3L1 19h22L14 3z',
    sun: 'M12 2v2 M12 20v2 M2 12h2 M20 12h2 M5 5l1 1 M18 18l1 1 M5 19l1-1 M18 6l1-1 M17 12a5 5 0 1 1-10 0 5 5 0 0 1 10 0',
    menu: 'M3 6h18 M3 12h18 M3 18h18'
  };
}

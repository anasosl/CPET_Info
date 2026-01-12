import { Injectable } from '@angular/core';

export interface Tarefa { id: number; titulo: string; concluida: boolean; }

@Injectable({ providedIn: 'root' })
export class TaskService {
  public tarefas: Tarefa[] = [
    { id: 1, titulo: 'Estudar TypeScript', concluida: true },
    { id: 2, titulo: 'Fazer mockup', concluida: false },
    { id: 3, titulo: 'Praticar Ionic', concluida: false }
  ]; 

  getPorcentagem() {
    const total = this.tarefas.length;
    if (total === 0) return 0;
    const concluidas = this.tarefas.filter(t => t.concluida).length;
    return concluidas / total;
  }
}
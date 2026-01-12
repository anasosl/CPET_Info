import { Component } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../services/task';
import { HighlightDirective } from '../directives/highlight';
import { addIcons } from 'ionicons'; 
import { add } from 'ionicons/icons';

@Component({
  selector: 'app-tarefas',
  templateUrl: './tarefas.page.html',
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, HighlightDirective]
})
export class TarefasPage {
  constructor(public taskService: TaskService) {
    addIcons({ add });
  }

  adicionarTarefa() {
    const novaTarefa = {
      id: Date.now(),
      titulo: 'Nova Tarefa ' + (this.taskService.tarefas.length + 1),
      concluida: false
    };
    this.taskService.tarefas.push(novaTarefa);
  }
}
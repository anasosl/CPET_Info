import { Component } from '@angular/core';
import { IonicModule, NavController } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { TaskService } from '../services/task';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  standalone: true,
  imports: [IonicModule, CommonModule]
})
export class HomePage {
  constructor(public taskService: TaskService, private navCtrl: NavController) {}

  irParaTarefas() {
    this.navCtrl.navigateForward('/tarefas');
  }
}
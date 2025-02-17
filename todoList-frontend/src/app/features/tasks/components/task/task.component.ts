import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpClientModule, HttpHeaders} from '@angular/common/http';
import { Task } from '../../models/task';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-task',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    CommonModule,
    HttpClientModule
  ],
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss'],
})
export class TaskComponent implements OnInit {///
  tasks: Task[] = [];
  // @ts-ignore
  newTask: Task = { title: '', description: '', status: 'PENDING' };
  selectedTask: Task | null = null;

  private apiUrl = 'http://localhost:8085/api/tasks';

  constructor(private http: HttpClient) {
    console.log('HttpClient in TaskComponent:', this.http);
  }

  ngOnInit(): void {
    this.loadTasks();
  }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  }

  loadTasks(): void {
    this.http.get<Task[]>(this.apiUrl, { headers: this.getHeaders() }).subscribe({
      next: (tasks) => (this.tasks = tasks),
      error: (err) => console.error('Erreur lors du chargement des tâches :', err),
    });
  }

  addTask(): void {
    this.http.post<Task>(this.apiUrl, this.newTask, { headers: this.getHeaders() }).subscribe({
      next: (task) => {
        this.tasks.push(task);
        // @ts-ignore
        this.newTask = { title: '', description: '', status: 'PENDING' }; // Réinitialiser le formulaire
      },
      error: (err) => console.error('Erreur lors de l\'ajout de la tâche :', err),
    });
  }

  deleteTask(id: number): void {
    this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() }).subscribe({
      next: () => (this.tasks = this.tasks.filter((task) => task.id !== id)),
      error: (err) => console.error('Erreur lors de la suppression de la tâche :', err),
    });
  }

  markAsCompleted(id: number): void {
    this.http.patch<Task>(`${this.apiUrl}/${id}/complete`, {}, { headers: this.getHeaders() }).subscribe({
      next: (updatedTask) => {
        const taskIndex = this.tasks.findIndex((task) => task.id === updatedTask.id);
        if (taskIndex !== -1) this.tasks[taskIndex] = updatedTask;
      },
      error: (err) => console.error('Erreur lors de la mise à jour de la tâche :', err),
    });
  }

  editTask(task: Task): void {
    this.selectedTask = { ...task }; // Copier la tâche sélectionnée pour modification
  }

  updateTask(selectedTask: Task): void {
    if (this.selectedTask) {
      this.http.put<Task>(`${this.apiUrl}/${this.selectedTask.id}`, this.selectedTask, { headers: this.getHeaders() }).subscribe({
        next: (updatedTask) => {
          const taskIndex = this.tasks.findIndex((t) => t.id === updatedTask.id);
          if (taskIndex !== -1) this.tasks[taskIndex] = updatedTask;
          this.selectedTask = null; // Réinitialiser la tâche sélectionnée
        },
        error: (err) => console.error('Erreur lors de la modification de la tâche :', err),
      });
    }
  }
}

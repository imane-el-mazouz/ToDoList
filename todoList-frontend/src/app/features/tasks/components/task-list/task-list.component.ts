import { Component } from '@angular/core';
import { Task } from "../../models/task";
import { TaskService } from "../../services/task.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import {RouterLink} from "@angular/router"; // Import HttpClientModule

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    CommonModule,
  HttpClientModule
  ],
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.scss'
})
export class TaskListComponent {
  tasks: Task[] = [];
  // @ts-ignore
  newTask: Task = { title: '', description: '', status: 'PENDING' };

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.taskService.getAllTasks().subscribe(tasks => {
      this.tasks = tasks;
    });
  }

  addTask(): void {
    this.taskService.addTask(this.newTask).subscribe(task => {
      this.tasks.push(task);
      // @ts-ignore
      this.newTask = { title: '', description: '', status: 'PENDING' };
    });
  }

  deleteTask(id: number): void {
    this.taskService.deleteTask(id).subscribe(() => {
      this.tasks = this.tasks.filter(task => task.id !== id);
    });
  }

  markAsCompleted(id: number): void {
    this.taskService.markAsCompleted(id).subscribe(updatedTask => {
      const taskIndex = this.tasks.findIndex(task => task.id === updatedTask.id);
      if (taskIndex !== -1) {
        this.tasks[taskIndex] = updatedTask;
      }
    });
  }

  updateTask(task: Task): void {
    this.taskService.updateTask(task.id!, task).subscribe(updatedTask => {
      const taskIndex = this.tasks.findIndex(t => t.id === updatedTask.id);
      if (taskIndex !== -1) {
        this.tasks[taskIndex] = updatedTask;
      }
    });
  }
}

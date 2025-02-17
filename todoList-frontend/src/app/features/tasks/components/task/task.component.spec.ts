import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TaskComponent } from './task.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Task } from '../../models/task';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import {Status} from "../../enums/status";

describe('TaskComponent', () => {
  let component: TaskComponent;
  let fixture: ComponentFixture<TaskComponent>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        TaskComponent,
        HttpClientTestingModule,
        ReactiveFormsModule,
        FormsModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(TaskComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load tasks on init', () => {

    const mockTasks: Task[] = [
      { id: 1, title: 'Task 1', description: 'Description 1', status: Status.PENDING },
      { id: 2, title: 'Task 2', description: 'Description 2', status: Status.PENDING }
    ];

    component.loadTasks();

    const req = httpMock.expectOne('http://localhost:8085/api/tasks');
    expect(req.request.method).toBe('GET');
    req.flush(mockTasks);

    expect(component.tasks.length).toBe(2);
    expect(component.tasks).toEqual(mockTasks);
  });

  it('should add a new task', () => {
    const newTask: Task = { id: 3, title: 'Task 3', description: 'Description 3', status: Status.PENDING };
    component.newTask = { title: 'Task 3', description: 'Description 3', status: Status.PENDING };

    component.addTask();

    const req = httpMock.expectOne('http://localhost:8085/api/tasks');
    expect(req.request.method).toBe('POST');
    req.flush(newTask);

    expect(component.tasks.length).toBe(1);
    expect(component.tasks[0]).toEqual(newTask);
  });

  it('should delete a task', () => {
    const mockTask: Task = { id: 1, title: 'Task 1', description: 'Description 1', status: Status.PENDING };
    component.tasks = [mockTask];

    component.deleteTask(1);

    const req = httpMock.expectOne('http://localhost:8085/api/tasks/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});

    expect(component.tasks.length).toBe(0);
  });

  afterEach(() => {
    httpMock.verify();
  });
});

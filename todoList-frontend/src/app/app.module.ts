import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgForOf, NgIf } from '@angular/common';

import { AppComponent } from './app.component';
import { TaskComponent } from './features/tasks/components/task/task.component';
import { TaskListComponent } from './features/tasks/components/task-list/task-list.component';
import { AuthInterceptor } from './features/tasks/services/auth_interceptor/auth-interceptor-service.service';
import { TaskService } from './features/tasks/services/task.service';
import { routes } from './app.routes';

@NgModule({
  declarations: [

  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    NgForOf,
    NgIf,
    AppComponent,
    TaskListComponent,
    FormsModule
  ],
  providers: [
    TaskService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [], // Utilisez AppComponent comme point d'entrée
})
export class AppModule {
  constructor() {
    console.log('AppModule loaded');
  }
}

import { Routes } from '@angular/router';
import {TaskListComponent} from "./features/tasks/components/task-list/task-list.component";
import {LoginComponent} from "./features/tasks/components/login/login.component";
import {SignupComponent} from "./features/tasks/components/singup/singup.component";


export const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  { path: 'login', component: LoginComponent },
  { path : 'signup' , component: SignupComponent},

  {
    path: 'tasks',
    component: TaskListComponent,
    // canActivate: []
  },
  {
    path: '**',
    redirectTo: '/login'
  }
];

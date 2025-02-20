import { Routes } from '@angular/router';
import {LoginComponent} from "./features/tasks/components/login/login.component";
import {SignupComponent} from "./features/tasks/components/singup/singup.component";
import {TaskComponent} from "./features/tasks/components/task/task.component";
import {AdminComponent} from "./shared/admin-dash/admin.component";


export const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  { path: 'login', component: LoginComponent },
  { path : 'signup' , component: SignupComponent},
  { path : 'task-list' , component: TaskComponent},
  { path: 'dashboard', component: AdminComponent },

  {
    path: 'dashboard',
    component: AdminComponent,
    children: [
      { path: 'dashboard', component: AdminComponent },
      { path : 'task-list' , component: TaskComponent},
    ]
  },
  {
    path: '**',
    redirectTo: '/login'
  }
];

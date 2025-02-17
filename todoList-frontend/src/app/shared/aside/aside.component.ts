import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";


@Component({
  selector: 'app-aside',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,

  ],
  templateUrl: './aside.component.html',
  styleUrl: './aside.component.scss'
})
export class AsideComponent {
  showSaveUserForm = false;
  showTechForm = false;
  constructor() { }


}

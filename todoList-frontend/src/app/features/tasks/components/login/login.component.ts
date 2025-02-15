import { Component } from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { CommonModule } from '@angular/common';
import {AuthService} from "../../services/auth_service/auth-service.service";

// import {NavBarComponent} from "../shared/nav-bar/nav-bar.component";
// import {FooterComponent} from "../shared/footer/footer.component";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    HttpClientModule
    // DividerModule,
    // ButtonDirective,
    // NavBarComponent,
    // FooterComponent
  ],
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login(): void {
    const { email, password } = this.loginForm.value;

    if (this.loginForm.invalid) {
      return;
    }

    this.http.post<{ accessToken: string, person: { role: string } }>('http://localhost:8085/api/auth/login', { email, password })
      .subscribe(
        response => {
          this.authService.setToken(response.accessToken);

          if (response.person.role === 'USER') {
            this.router.navigate(['/tasks']);
          } else if (response.person.role === 'ADMIN') {
            this.router.navigate(['/tasks']);
          }else {
            this.errorMessage = 'role undefined: ' + response.person.role;
          }
        },
        error => {
          console.error('Error during login', error);
          this.errorMessage = 'Login failed. Please check your credentials and try again.';
        }
      );
  }
}

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  isRegisterMode = false;
  isLoading = false;
  errorMessage = '';

  loginData = { email: '', password: '' };
  registerData = { name: '', email: '', password: '' };

  constructor(private authService: AuthService, private router: Router) {
    if (this.authService.isLoggedIn()) this.router.navigate(['/home']);
  }

  onLogin(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.authService.login(this.loginData).subscribe({
      next: () => this.router.navigate(['/home']),
      error: (err) => {
        this.errorMessage = err.error?.message || 'Invalid email or password';
        this.isLoading = false;
      }
    });
  }

  onRegister(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.authService.register(this.registerData).subscribe({
      next: () => {
        this.isRegisterMode = false;
        this.loginData.email = this.registerData.email;
        this.isLoading = false;
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Registration failed';
        this.isLoading = false;
      }
    });
  }

  toggleMode(): void {
    this.isRegisterMode = !this.isRegisterMode;
    this.errorMessage = '';
  }

  onSubmit(): void {
    if (this.isRegisterMode) {
      this.onRegister();
    } else {
      this.onLogin();
    }
  }
}

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: { name: string; email: string; role: string } | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
  }

  logout(): void { this.authService.logout(); }
  navigateTo(path: string): void { this.router.navigate([path]); }
}

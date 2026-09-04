import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  userName = '';
  question = '';
  isListening = false;
  speechRecognition: any;

  topics = [
    { icon: 'fas fa-chalkboard-teacher', title: 'Induction Programs', description: 'Learn about the onboarding induction process and schedule' },
    { icon: 'fas fa-book-open', title: 'Mandatory Courses', description: 'Find and complete required training courses' },
    { icon: 'fas fa-download', title: 'Software Installation', description: 'Get help with required software setup' },
    { icon: 'fas fa-clipboard-check', title: 'Assessments', description: 'Understand assessment criteria and passing scores' },
    { icon: 'fas fa-file-alt', title: 'Document Verification', description: 'Know which documents are required' },
    { icon: 'fas fa-laptop', title: 'Learning Portal', description: 'Access your learning portal and courses' }
  ];

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    const user = this.authService.getCurrentUser();
    this.userName = user?.name || 'there';
    this.initSpeechRecognition();
  }

  initSpeechRecognition(): void {
    const SpeechRecognition = (window as any).SpeechRecognition || (window as any).webkitSpeechRecognition;
    if (SpeechRecognition) {
      this.speechRecognition = new SpeechRecognition();
      this.speechRecognition.continuous = false;
      this.speechRecognition.interimResults = false;
      this.speechRecognition.lang = 'en-US';
      this.speechRecognition.onresult = (event: any) => {
        this.question = event.results[0][0].transcript;
        this.isListening = false;
      };
      this.speechRecognition.onerror = () => { this.isListening = false; };
      this.speechRecognition.onend = () => { this.isListening = false; };
    }
  }

  toggleVoice(): void {
    if (!this.speechRecognition) { alert('Speech recognition not supported in this browser.'); return; }
    if (this.isListening) { this.speechRecognition.stop(); this.isListening = false; }
    else { this.speechRecognition.start(); this.isListening = true; }
  }

  askQuestion(): void {
    if (!this.question.trim()) return;
    this.router.navigate(['/chat'], { state: { question: this.question } });
  }

  onKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Enter') this.askQuestion();
  }

  logout(): void { this.authService.logout(); }

  navigateTo(path: string): void { this.router.navigate([path]); }

  onTopicClick(topic: any): void {
    this.question = `Tell me about ${topic.title.toLowerCase()}`;
    this.askQuestion();
  }
}

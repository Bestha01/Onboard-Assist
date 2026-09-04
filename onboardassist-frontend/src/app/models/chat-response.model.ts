export interface ChatResponse {
  answer: string;
  sessionId: string;
}

export interface ChatMessage {
  type: 'user' | 'ai';
  content: string;
  timestamp: Date;
}

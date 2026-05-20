import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, map, shareReplay } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TranslationService {
  private translations: Record<string, string> = {};
  private loaded = false;

  constructor(private http: HttpClient) {
    this.loadTranslations();
  }

  private async loadTranslations() {
    try {
      this.translations = await firstValueFrom(
        this.http.get<Record<string, string>>('assets/i18n/error-messages.json')
      );
      this.loaded = true;
    } catch (error) {
      console.error('Failed to load translations:', error);
      // Fallback translations
      this.translations = {
          "USER_NOT_FOUND": "Nie znaleziono użytkownika",
          "TASK_NOT_FOUND": "Nie znaleziono zadania",
          "INVALID_CREDENTIALS": "Nieprawidłowe dane logowania",
          "BAD_REQUEST": "Nieprawidłowe żądanie",
          "USER_ALREADY_EXISTS": "Użytkownik już istnieje",
          "INTERNAL_ERROR": "Wystąpił błąd serwera. Spróbuj ponownie później",
          "PASSWORD_NOT_SAME": "Hasła nie sa takie same",
          "PASSWORD_SAME_AS_OLD": "Nowe hasło nie może być takie samo jak stare"
};
      this.loaded = true;
    }
  }

  getErrorMessage(message: string): string {
    if (!this.loaded) {
      return 'Wystąpił błąd';
    }
    return this.translations[message] || 'Wystąpił błąd';
  }

  getErrorMessages(): Record<string, string> {
    return this.translations;
  }
}
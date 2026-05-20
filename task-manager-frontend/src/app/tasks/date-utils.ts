import { formatDate } from '@angular/common';

export function formatDateForApi(date: Date | string | null): string | null {
  if (!date) {
    return null;
  }

  const parsedDate = typeof date === 'string' ? new Date(date) : date;
  if (isNaN(parsedDate.getTime())) {
    return null;
  }

  return formatDate(parsedDate, 'yyyy-MM-dd', 'en-US');
}

import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function futureOrPresentValidator(
  control: AbstractControl,
): ValidationErrors | null {
  if (!control.value) return null;

  const today = new Date();
  const selected = new Date(control.value);

  today.setHours(0, 0, 0, 0);
  selected.setHours(0, 0, 0, 0);

  return selected < today ? { pastDate: true } : null;
}

export function futureOrPresentUnlessOriginalValidator(
  originalDate: Date | string | null,
): ValidatorFn {
  const original = originalDate ? new Date(originalDate) : null;
  if (original && !isNaN(original.getTime())) {
    original.setHours(0, 0, 0, 0);
  }

  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) return null;

    const today = new Date();
    const selected = new Date(control.value);

    today.setHours(0, 0, 0, 0);
    selected.setHours(0, 0, 0, 0);

    if (selected < today) {
      if (original && selected.getTime() === original.getTime()) {
        return null;
      }
      return { pastDate: true };
    }

    return null;
  };
}

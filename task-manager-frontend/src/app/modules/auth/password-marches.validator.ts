import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";


export const passwordMatchValidator: ValidatorFn = (
    control: AbstractControl
): ValidationErrors | null =>{
    
    const password = control.get('newPassword')?.value;
    const confirm = control.get('confirmPassword')?.value;

    if(!password || !confirm){
        return null;
    }

    return password == confirm ? null : { passwordMissmatch: true };
}
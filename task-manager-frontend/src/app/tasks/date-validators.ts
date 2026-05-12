import { AbstractControl, ValidationErrors } from "@angular/forms";

export function futureOrPresentValidator(control: AbstractControl):ValidationErrors | null{
    if(!control.value) return null;

    const today = new Date();
    const selected = new Date(control.value);

    

    today.setHours(0,0,0,0);
    selected.setHours(0,0,0,0);

    return selected < today ? {pastDate: true}: null;
}
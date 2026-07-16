import { Component, Inject } from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogRef,
  MatDialogActions,
  MatDialogModule,
} from '@angular/material/dialog';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatRadioModule } from '@angular/material/radio';

@Component({
  selector: 'app-role-dialog',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatButtonModule,
    MatRadioModule,
    MatDialogActions,
    MatDialogModule,
  ],
  templateUrl: './role-dialog.component.html',
})
export class RoleDialogComponent {
  form;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<RoleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public currentRole: string,
  ) {
    this.form = this.fb.nonNullable.group({
      role: [currentRole],
    });
  }

  save() {
    this.dialogRef.close(this.form.getRawValue().role);
  }
}

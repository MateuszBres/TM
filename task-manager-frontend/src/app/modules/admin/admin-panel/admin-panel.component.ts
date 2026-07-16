import { Component, OnInit } from '@angular/core';
import { AdminService, userResponse } from '../admin.service';
import {
  MatCellDef,
  MatHeaderCellDef,
  MatHeaderRowDef,
  MatRowDef,
  MatTableModule,
} from '@angular/material/table';
import { MatCard, MatCardTitle } from '@angular/material/card';
import { MatButton } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { RoleDialogComponent } from '../role-dialog/role-dialog.component';
import { SnackbarService } from '../../../core/snackbar.service';
import { successResponse } from '../../../core/successResponse';
@Component({
  selector: 'app-admin-panel',
  imports: [
    MatTableModule,
    MatCellDef,
    MatHeaderCellDef,
    MatHeaderRowDef,
    MatRowDef,
    MatCard,
    MatCardTitle,
    MatButton,
  ],
  templateUrl: './admin-panel.component.html',
  styleUrl: './admin-panel.component.css',
})
export class AdminPanelComponent implements OnInit {
  users: userResponse[] = [];
  displayedColumns: string[] = [
    'id',
    'email',
    'role',
    'createdAt',
    'edit',
    'delete',
  ];

  constructor(
    private service: AdminService,
    private dialog: MatDialog,
    private snack: SnackbarService,
  ) {}

  ngOnInit() {
    this.service.getAllUsers().subscribe((users) => (this.users = users));
  }

  editRole(user: userResponse) {
    this.dialog
      .open(RoleDialogComponent, {
        width: '300px',
        height: '250px',
        data: user.role,
      })
      .afterClosed()
      .subscribe((role) => {
        if (!role) return;

        const newRole = user.role === 'USER' ? 'ADMIN' : 'USER';
        this.service.updateUserRole(user.id, newRole).subscribe(() => {
          user.role = newRole;
          this.snack.success(
            `Rola użytkownika ${user.email} została zmieniona na ${newRole}`,
          );
        });
      });
  }
  deleteUser(user: userResponse) {
    this.service.deleteUser(user.id).subscribe(() => {
      this.users = this.users.filter((u) => u.id !== user.id);
      this.snack.info(`Usunięto użytkownika ${user.email}`);
    });
  }
}

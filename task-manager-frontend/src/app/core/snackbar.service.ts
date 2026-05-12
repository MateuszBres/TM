import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';


@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  constructor(private snackBar: MatSnackBar) { }

  success(message: string): void{
    this.snackBar.open(message, 'OK',{
      duration:3000,
      panelClass: ['snackbar-success'],
      horizontalPosition: 'center',
      verticalPosition:'bottom'
    });
  }
    error(message: string): void{
      this.snackBar.open(message,'Ok',{
        duration: 3000,
        panelClass: ['snackbar-error'],
        horizontalPosition:'center',
        verticalPosition:'bottom'
      });
    }

    info(message: string):void{
      this.snackBar.open(message,'OK',{
        duration:3000,
        panelClass:['snackbar-info'],
        horizontalPosition:'center',
        verticalPosition:'bottom'
      })
    }

  
}

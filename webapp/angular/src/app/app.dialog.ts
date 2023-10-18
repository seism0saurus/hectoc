import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {HectocDialogDto} from './dto/hectoc.dialog';

@Component({
  selector: 'app-dialog',
  templateUrl: './app.dialog.html',
  styleUrls: ['./app.dialog.css']
})

export class AppDialog {

  constructor(
    public dialogRef: MatDialogRef<AppDialog>,
    @Inject(MAT_DIALOG_DATA) public data: HectocDialogDto,
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

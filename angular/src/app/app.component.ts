import { Component } from '@angular/core';
import { HectocService } from './hectoc.service';
import { MatDialog } from '@angular/material/dialog';
import { AppDialog } from './app.dialog';
import { HectocDialogDto } from './dto/hectoc.dialog';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  hectoc: String = '123456';
  solution: String = '';
  getFailed: Boolean = false;
  errorGet: any = null;
  solveFailed: Boolean = false;
  errorSolve: any = null;
  isSolving: Boolean = false;

  constructor(
    private service: HectocService,
    public dialog: MatDialog
    ){
    this.newHectoc();
  }
  
  newHectoc() {
    this.service.getHectoc().subscribe(
      {
        next: (data) => {
          this.hectoc = data.challenge;
          this.getFailed = false;
          this.errorGet = null;
        },
        error: (error) => {
          console.error(error);
          this.getFailed = true;
          this.errorGet = error.error;
        }
      }
    );
  }

  solve() {
    if (!this.isSolving){
      this.isSolving = true;
      let solutionDto = { solution: this.solution.trim()};
      this.solveFailed = false;
      this.errorSolve = null;
      this.service.solveHectoc(this.hectoc, solutionDto).subscribe(
        {
          next: (data) => {
            this.isSolving = false;
            let dialogDTO = {
              challenge: this.hectoc,
              solution: this.solution,
              valid: data.valid,
              result: data.result
            }
            this.openDialog(dialogDTO);
          },
          error: (error) => {
            console.error(error);
            this.solveFailed = true;
            this.errorSolve = error.error;
            let dialogDTO = {
              challenge: this.hectoc,
              solution: this.solution,
              valid: false,
              error: error.error
            }
            this.openDialog(dialogDTO);
            this.isSolving = false;
          }
        }
      );
    }
  }

  openDialog(data: HectocDialogDto): void {
    const dialogRef = this.dialog.open(AppDialog, {
      data: data,
    });
  }
}
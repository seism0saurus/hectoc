import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {HectocService} from "../hectoc-service/hectoc.service";
import {MatDialog} from "@angular/material/dialog";
import {HectocDialogDto} from "../hectoc-service/dto/hectoc.dialog";
import {AppDialog} from "./dialog/app.dialog";

@Component({
  selector: 'app-challenger',
  standalone: true,
  imports: [
    FormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule
  ],
  templateUrl: './challenger.component.html',
  styleUrl: './challenger.component.css'
})
export class ChallengerComponent {
  hectoc: String = '123456';
  solution: String = '';
  getFailed: Boolean = false;
  errorGet: any = null;
  solveFailed: Boolean = false;
  errorSolve: any = null;
  isSolving: Boolean = false;
  startTime: DOMHighResTimeStamp;
  endTime: DOMHighResTimeStamp;

  constructor(
    private service: HectocService,
    public dialog: MatDialog
  ) {
    this.startTime = performance.now();
    this.endTime = this.startTime;
    this.newHectoc();
  }

  newHectoc() {
    this.service.getHectoc().subscribe(
      {
        next: (data) => {
          this.hectoc = data.challenge;
          this.solution = '';
          this.getFailed = false;
          this.errorGet = null;
          this.startTime = performance.now()
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
    if (!this.isSolving) {
      this.isSolving = true;
      this.endTime = performance.now();
      let solutionDto = {solution: this.solution.trim()};
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
              result: data.result,
              time: (this.endTime - this.startTime) / 1000
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
              error: error.error,
              time: (this.endTime - this.startTime) / 1000
            }
            this.openDialog(dialogDTO);
            this.isSolving = false;
          }
        }
      );
    }
  }

  openDialog(data: HectocDialogDto): void {
    this.dialog.open(AppDialog, {
      data: data,
    });
  }
}

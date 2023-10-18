import {Injectable} from "@angular/core";
import {HttpClient} from '@angular/common/http'
import {Observable} from "rxjs";
import {HectocChallengeDto} from "./dto/hectoc.challenge";
import {HectocResultDto} from "./dto/hectoc.result";
import {HectocSolutionDto} from "./dto/hectoc.solution";
import {environment} from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HectocService {

  constructor(private http: HttpClient) {
  }

  getHectoc(): Observable<HectocChallengeDto> {
    let url = environment.API_URL;
    return this.http.get<HectocChallengeDto>(url);
  }

  solveHectoc(hectoc: String, solution: HectocSolutionDto): Observable<HectocResultDto> {
    let url = environment.API_URL + '/' + hectoc + '/solution';
    return this.http.post<HectocResultDto>(url, solution);
  }
}

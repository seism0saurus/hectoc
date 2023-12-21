import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {ChallengerComponent} from "./challenger/challenger.component";
import {LegalNoticeComponent} from "./legal-notice/legal-notice.component";
import {DataProtectionComponent} from "./data-protection/data-protection.component";
import {BotComponent} from "./bot/bot.component";
import {HowtoComponent} from "./howto/howto.component";
import {ExplanationComponent} from "./explanation/explanation.component";
import {BugComponent} from "./bug/bug.component";
import {ParticipationComponent} from "./participation/participation.component";

const routes: Routes = [
  {path: '', component: ExplanationComponent},
  {path: 'home', component: ExplanationComponent},
  {path: 'howto', component: HowtoComponent},
  {path: 'challenger', component: ChallengerComponent},
  {path: 'bot', component: BotComponent},
  {path: 'bug', component: BugComponent},
  {path: 'participate', component: ParticipationComponent},
  {path: 'legal-notice', component: LegalNoticeComponent},
  {path: 'data-protection', component: DataProtectionComponent},
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

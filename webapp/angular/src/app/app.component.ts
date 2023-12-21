import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatIconRegistry} from "@angular/material/icon";
import {DomSanitizer} from "@angular/platform-browser";
import {BreakpointObserver} from "@angular/cdk/layout";
import {MatSidenav} from "@angular/material/sidenav";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit {

  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;

  constructor(
    iconRegistry: MatIconRegistry,
    sanitizer: DomSanitizer,
    private observer: BreakpointObserver
  ) {
    iconRegistry.addSvgIcon('mastodon', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/mastodon.svg'));
    iconRegistry.addSvgIcon('numbers', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/numbers.svg'));
    iconRegistry.addSvgIcon('rocket', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/rocket.svg'));
    iconRegistry.addSvgIcon('cookie', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/cookie.svg'));
    iconRegistry.addSvgIcon('law', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/law.svg'));
    iconRegistry.addSvgIcon('bug', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/bug.svg'));
    iconRegistry.addSvgIcon('participate', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/participate.svg'));
    iconRegistry.addSvgIcon('challenge', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/challenge.svg'));
  }

  ngAfterViewInit() {
    this.observer
      .observe(["(max-width: 900px)"])
      .subscribe((res) => {
        if (res.matches) {
          this.sidenav.mode = "over";
          this.sidenav.close();
        } else {
          this.sidenav.mode = "side";
          this.sidenav.open();
        }
      });
  }
}

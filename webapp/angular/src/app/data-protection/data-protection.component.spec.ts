import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DataProtectionComponent} from './data-protection.component';

describe('DatenschutzComponent', () => {
  let component: DataProtectionComponent;
  let fixture: ComponentFixture<DataProtectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DataProtectionComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DataProtectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

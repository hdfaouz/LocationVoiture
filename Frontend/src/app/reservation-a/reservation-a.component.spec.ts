import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationAComponent } from './reservation-a.component';

describe('ReservationAComponent', () => {
  let component: ReservationAComponent;
  let fixture: ComponentFixture<ReservationAComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservationAComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReservationAComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

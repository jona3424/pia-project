import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestauranGuestComponent } from './restauran-guest.component';

describe('RestauranGuestComponent', () => {
  let component: RestauranGuestComponent;
  let fixture: ComponentFixture<RestauranGuestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RestauranGuestComponent]
    });
    fixture = TestBed.createComponent(RestauranGuestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

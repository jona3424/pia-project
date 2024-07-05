import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAddRestaurantComponent } from './admin-add-restaurant.component';

describe('AdminAddRestaurantComponent', () => {
  let component: AdminAddRestaurantComponent;
  let fixture: ComponentFixture<AdminAddRestaurantComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminAddRestaurantComponent]
    });
    fixture = TestBed.createComponent(AdminAddRestaurantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

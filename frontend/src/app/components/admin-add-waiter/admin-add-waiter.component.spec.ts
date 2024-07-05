import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAddWaiterComponent } from './admin-add-waiter.component';

describe('AdminAddWaiterComponent', () => {
  let component: AdminAddWaiterComponent;
  let fixture: ComponentFixture<AdminAddWaiterComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminAddWaiterComponent]
    });
    fixture = TestBed.createComponent(AdminAddWaiterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

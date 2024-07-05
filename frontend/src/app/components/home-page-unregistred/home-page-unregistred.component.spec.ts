import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomePageUnregistredComponent } from './home-page-unregistred.component';

describe('HomePageUnregistredComponent', () => {
  let component: HomePageUnregistredComponent;
  let fixture: ComponentFixture<HomePageUnregistredComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomePageUnregistredComponent]
    });
    fixture = TestBed.createComponent(HomePageUnregistredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckPayment } from './check-payment';

describe('CheckPayment', () => {
  let component: CheckPayment;
  let fixture: ComponentFixture<CheckPayment>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CheckPayment]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CheckPayment);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

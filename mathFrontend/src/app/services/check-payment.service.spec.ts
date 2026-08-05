import { TestBed } from '@angular/core/testing';

import { CheckPaymentService } from './check-payment.service';

describe('CheckPaymentService', () => {
  let service: CheckPaymentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CheckPaymentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

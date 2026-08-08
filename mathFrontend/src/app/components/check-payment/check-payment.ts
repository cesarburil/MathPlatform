import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CheckPaymentService } from '../../services/check-payment.service';

@Component({
  selector: 'app-check-payment',
  imports: [],
  templateUrl: './check-payment.html',
  styleUrl: './check-payment.scss',
})
export class CheckPayment {
  constructor(private router: Router, private service: CheckPaymentService){}

  checkPayment():any{
    this.service.check().subscribe(result =>
      console.log(result)
    )
  }
}

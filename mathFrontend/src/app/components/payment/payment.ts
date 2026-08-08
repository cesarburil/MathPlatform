import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PaymentService } from '../../services/payment.service';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';
import { SseService } from '../../services/sse.service';
declare var PagSeguro: any;

@Component({
  selector: 'app-payment',
  imports: [ReactiveFormsModule],
  templateUrl: './payment.html',
  styleUrl: './payment.scss',
})

export class Payment {

  constructor(private paymentService: PaymentService, private router: Router, private sseService: SseService) { };

  paymentForm = new FormGroup({
    holder: new FormControl("", [Validators.required]),
    number: new FormControl("", [Validators.required]),
    expMonth: new FormControl("", [Validators.required]),
    expYear: new FormControl("", [Validators.required]),
    securityCode: new FormControl("", [Validators.required]),
  })

  encrypt(): string {
    const card = PagSeguro.encryptCard({
      publicKey: environment.pagbank_publicKey,
      holder: this.paymentForm.value.holder,
      number: this.paymentForm.value.number,
      expMonth: this.paymentForm.value.expMonth,
      expYear: this.paymentForm.value.expYear,
      securityCode: this.paymentForm.value.securityCode,
    });

    console.log(card.hasErrors);
    console.log(card.errors);

    return card.encryptedCard;
  }

  pay() {
    this.sseService.connect(`${environment.apiUrl}/sse`).subscribe(data => {
      console.log("Notificação webhook: ")
      console.log(data);
    });
    const encryptedCard = this.encrypt();

  
    this.paymentService.pay(encryptedCard).subscribe({next: result => {
      console.log("Retorno pagamento: ");
      console.log(JSON.parse(result)["charges"][0]["status"]);

    }, error: e => {
      console.error(e)
    }});

  }


}

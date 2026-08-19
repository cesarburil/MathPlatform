import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PaymentService } from '../../services/payment.service';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';
import { SseService } from '../../services/sse.service';
import { ToastService } from '../../services/toast.service';
import { LoginService } from '../../services/login.service';
import { ACCESS_DURATION_LABEL, ACCESS_PRICE_LABEL } from '../../catalog';
declare var PagSeguro: any;

@Component({
  selector: 'app-payment',
  imports: [ReactiveFormsModule],
  templateUrl: './payment.html',
  styleUrl: './payment.scss',
})

export class Payment {
  readonly price = ACCESS_PRICE_LABEL;
  readonly duration = ACCESS_DURATION_LABEL;

  constructor(
    private paymentService: PaymentService,
    private router: Router,
    private sseService: SseService,
    private toasts: ToastService,
    private login: LoginService,
  ) { };

  paymentForm = new FormGroup({
    holder: new FormControl("", [Validators.required]),
    number: new FormControl("", [Validators.required]),
    expMonth: new FormControl("", [Validators.required]),
    expYear: new FormControl("", [Validators.required]),
    securityCode: new FormControl("", [Validators.required]),
  })

  encrypt(): string | null {
    const card = PagSeguro.encryptCard({
      publicKey: environment.pagbank_publicKey,
      holder: this.paymentForm.value.holder,
      number: this.paymentForm.value.number,
      expMonth: this.paymentForm.value.expMonth,
      expYear: this.paymentForm.value.expYear,
      securityCode: this.paymentForm.value.securityCode,
    });

    if (card.hasErrors || !card.encryptedCard) {
      this.toasts.error('Confira os dados do cartão e tente de novo.');
      return null;
    }

    return card.encryptedCard;
  }

  pay() {
    if (!this.paymentForm.valid) {
      this.toasts.error('Preencha todos os campos do cartão.');
      return;
    }

    const encryptedCard = this.encrypt();
    if (!encryptedCard) {
      return;
    }

    this.sseService.connect(`${environment.apiUrl}/sse`).subscribe({
      next: (data) => {
        const status = JSON.parse(data)["charges"][0]["status"];
        if (status === "PAID") {
          this.login.markPremium();
          this.toasts.ok('Pagamento confirmado.');
          this.router.navigate(['/categories']);
        } else {
          this.router.navigate(['/pay/error']);
        }
      },
      error: () => {
        this.toasts.error('Não foi possível acompanhar o pagamento. Tente de novo.');
      },
    });

    this.paymentService.pay(encryptedCard).subscribe();
  }
}

package main.businessLayer.ExternalServices;

import resources.PaymentMethod;

public interface PaymentService {

    public boolean pay(PaymentMethod paymentMethod);
    public boolean cancelPayment(PaymentMethod paymentMethod);
}

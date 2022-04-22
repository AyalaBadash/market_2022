package main.businessLayer.ExternalServices;

import main.resources.PaymentMethod;

public interface PaymentService {

    public String pay(PaymentMethod paymentMethod);
    public boolean cancelPayment(PaymentMethod paymentMethod);
}

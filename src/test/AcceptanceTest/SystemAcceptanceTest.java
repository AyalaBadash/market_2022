package test.AcceptanceTest;

import main.PaymentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SystemAcceptanceTest extends AcceptanceTest {

    @BeforeEach

    @Test
    @DisplayName("Initialize Market System - C`orrect Values")
    public void marketInitialize(){
        Assertions.assertTrue(false);
    }
    @Test
    @DisplayName("Initialize Market System - Null values")
    public void marketInitializeNullPaymentServ(){
        Assertions.assertTrue(false);
    }
}

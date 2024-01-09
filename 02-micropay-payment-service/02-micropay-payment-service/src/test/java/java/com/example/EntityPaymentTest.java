package java.com.example;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void defaultConstructorTest() {
        Payment payment = new Payment();
        assertNotNull(payment);
    }

    @Test
    void parameterizedConstructorTest() {
        Payment payment = new Payment(101L, 50.0);
        assertEquals(101L, payment.getUserId());
        assertEquals(50.0, payment.getAmount());
    }

    @Test
    void setterGetterMethodsTest() {
        Payment payment = new Payment();
        payment.setUserId(102L);
        payment.setAmount(75.0);
        assertEquals(102L, payment.getUserId());
        assertEquals(75.0, payment.getAmount());
    }

    @Test
    void idGenerationTest() {
        Payment payment = new Payment();
        assertNotNull(payment.getPaymentId());
    }

    @Test
    void equalityCheckTest() {
        Payment payment1 = new Payment(103L, 60.0);
        Payment payment2 = new Payment(103L, 60.0);
        assertEquals(payment1, payment2);
    }

    @Test
    void consistencyHashCodeTest() {
        Payment payment = new Payment(104L, 70.0);
        int hashCode1 = payment.hashCode();
        int hashCode2 = payment.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void toStringMethodTest() {
        Payment payment = new Payment(105L, 80.0);
        assertNotNull(payment.toString());
    }

    @Test
    void validationNegativeAmountTest() {
        assertThrows(IllegalArgumentException.class, () -> new Payment(106L, -90.0));
    }

    @Test
    void validationNullUserIdTest() {
        assertThrows(IllegalArgumentException.class, () -> new Payment(null, 100.0));
    }

    @Test
    void validationNullAmountTest() {
        assertThrows(IllegalArgumentException.class, () -> new Payment(107L, null));
    }

    @Test
    void jpaAnnotationsTest() {
        Payment payment = new Payment();
        assertNotNull(payment.getClass().getAnnotation(Entity.class));
        assertNotNull(payment.getClass().getAnnotation(Table.class));

        assertNotNull(payment.getClass().getDeclaredField("paymentId").getAnnotation(Id.class));
        assertNotNull(payment.getClass().getDeclaredField("paymentId").getAnnotation(GeneratedValue.class));
        assertNotNull(payment.getClass().getDeclaredField("userId").getAnnotation(Column.class));
        assertNotNull(payment.getClass().getDeclaredField("amount").getAnnotation(Column.class));
    }
}

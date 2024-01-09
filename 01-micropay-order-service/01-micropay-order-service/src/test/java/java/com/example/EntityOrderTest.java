package java.com.example;

import static org.junit.jupiter.api.Assertions.*;

class EntityOrderTest {

    @Test
    void defaultConstructorTest() {
        Order order = new Order();
        assertNotNull(order);
    }

    @Test
    void parameterizedConstructorTest() {
        Order order = new Order(101L, 50.0);
        assertEquals(101L, order.getUserId());
        assertEquals(50.0, order.getAmount());
    }

    @Test
    void setterGetterMethodsTest() {
        Order order = new Order();
        order.setUserId(102L);
        order.setAmount(75.0);
        assertEquals(102L, order.getUserId());
        assertEquals(75.0, order.getAmount());
    }

    @Test
    void idGenerationTest() {
        Order order = new Order();
        assertNotNull(order.getOrderId());
    }

    @Test
    void equalityCheckTest() {
        Order order1 = new Order(103L, 60.0);
        Order order2 = new Order(103L, 60.0);
        assertEquals(order1, order2);
    }

    @Test
    void consistencyHashCodeTest() {
        Order order = new Order(104L, 70.0);
        int hashCode1 = order.hashCode();
        int hashCode2 = order.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void toStringMethodTest() {
        Order order = new Order(105L, 80.0);
        assertNotNull(order.toString());
    }

    @Test
    void validationNegativeAmountTest() {
        assertThrows(IllegalArgumentException.class, () -> new Order(106L, -90.0));
    }

    @Test
    void validationNullUserIdTest() {
        assertThrows(IllegalArgumentException.class, () -> new Order(null, 100.0));
    }

    @Test
    void validationNullAmountTest() {
        assertThrows(IllegalArgumentException.class, () -> new Order(107L, null));
    }

    @Test
    void jpaAnnotationsTest() {
        Order order = new Order();
        assertNotNull(order.getClass().getAnnotation(Entity.class));
        assertNotNull(order.getClass().getAnnotation(Table.class));

        assertNotNull(order.getClass().getDeclaredField("orderId").getAnnotation(Id.class));
        assertNotNull(order.getClass().getDeclaredField("orderId").getAnnotation(GeneratedValue.class));
        assertNotNull(order.getClass().getDeclaredField("userId").getAnnotation(Column.class));
        assertNotNull(order.getClass().getDeclaredField("amount").getAnnotation(Column.class));
    }
}

package seedu.loyaltylift.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.loyaltylift.storage.JsonAdaptedOrder.CUSTOMER_ID_MESSAGE_FIELD;
import static seedu.loyaltylift.storage.JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.loyaltylift.testutil.Assert.assertThrows;
import static seedu.loyaltylift.testutil.TypicalOrders.ORDER_A;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import seedu.loyaltylift.commons.exceptions.IllegalValueException;
import seedu.loyaltylift.model.AddressBook;
import seedu.loyaltylift.model.ReadOnlyAddressBook;
import seedu.loyaltylift.model.attribute.Address;
import seedu.loyaltylift.model.attribute.Name;
import seedu.loyaltylift.model.customer.Customer;
import seedu.loyaltylift.model.order.CreatedDate;
import seedu.loyaltylift.model.order.Order;
import seedu.loyaltylift.model.order.Quantity;
import seedu.loyaltylift.model.order.Status;
import seedu.loyaltylift.testutil.TypicalCustomers;
import seedu.loyaltylift.testutil.TypicalOrders;

import java.util.Collection;

public class JsonAdaptedOrderTest {

    private static final String INVALID_NAME = "T@rts";
    private static final Integer INVALID_QUANTITY = -1;
    private static final String INVALID_STATUS = "something";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_CREATED_DATE = "2020-05-02";

    private static final String VALID_CUSTOMER_UID = ORDER_A.getCustomer().getUID();
    private static final String VALID_NAME = ORDER_A.getName().fullName;
    private static final Integer VALID_QUANTITY = ORDER_A.getQuantity().value;
    private static final String VALID_STATUS = ORDER_A.getStatus().toString().toUpperCase();
    private static final String VALID_ADDRESS = ORDER_A.getAddress().toString();
    private static final String VALID_CREATED_DATE = ORDER_A.getCreatedDate().toString();

    private static final AddressBookStub ADDRESS_BOOK_STUB = new AddressBookStub(
            TypicalCustomers.getTypicalCustomers(),
            TypicalOrders.getTypicalOrders());

    private static final AddressBook ADDRESS_BOOK = new AddressBook(ADDRESS_BOOK_STUB);

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws Exception {
        JsonAdaptedOrder order = new JsonAdaptedOrder(ORDER_A);
        assertEquals(ORDER_A, order.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_nullCustomerAssociation_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(null, VALID_NAME, VALID_QUANTITY, VALID_STATUS, VALID_ADDRESS, VALID_CREATED_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CUSTOMER_ID_MESSAGE_FIELD);
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_CUSTOMER_UID, INVALID_NAME, VALID_QUANTITY, VALID_STATUS, VALID_ADDRESS, VALID_CREATED_DATE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_CUSTOMER_UID, null, VALID_QUANTITY, VALID_STATUS, VALID_ADDRESS, VALID_CREATED_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_CUSTOMER_UID, VALID_NAME, INVALID_QUANTITY, VALID_STATUS, VALID_ADDRESS, VALID_CREATED_DATE);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_CUSTOMER_UID, VALID_NAME, null, VALID_STATUS, VALID_ADDRESS, VALID_CREATED_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_CUSTOMER_UID, VALID_NAME, VALID_QUANTITY, INVALID_STATUS, VALID_ADDRESS, VALID_CREATED_DATE);
        assertThrows(IllegalArgumentException.class, () -> order.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_CUSTOMER_UID, VALID_NAME, VALID_QUANTITY, null, VALID_ADDRESS, VALID_CREATED_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_CUSTOMER_UID, VALID_NAME, VALID_QUANTITY, VALID_STATUS, INVALID_ADDRESS, VALID_CREATED_DATE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_CUSTOMER_UID, VALID_NAME, VALID_QUANTITY, VALID_STATUS, null, VALID_CREATED_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_invalidCreatedDate_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_CUSTOMER_UID, VALID_NAME, VALID_QUANTITY, VALID_STATUS, VALID_ADDRESS, INVALID_CREATED_DATE);
        String expectedMessage = CreatedDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_nullCreatedDate_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_CUSTOMER_UID, VALID_NAME, VALID_QUANTITY, VALID_STATUS, VALID_ADDRESS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CreatedDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(ADDRESS_BOOK));
    }

    /**
     * A stub ReadOnlyAddressBook whose customers list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Customer> customers = FXCollections.observableArrayList();
        private final ObservableList<Order> orders = FXCollections.observableArrayList();

        AddressBookStub(Collection<Customer> customers, Collection<Order> orders) {
            this.customers.setAll(customers);
            this.orders.setAll(orders);
        }

        @Override
        public ObservableList<Customer> getCustomerList() {
            return customers;
        }

        @Override
        public ObservableList<Order> getOrderList() {
            return orders;
        }
    }
}

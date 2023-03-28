package seedu.loyaltylift.model.order;

import static seedu.loyaltylift.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

import seedu.loyaltylift.model.attribute.Address;
import seedu.loyaltylift.model.attribute.Name;
import seedu.loyaltylift.model.attribute.Note;
import seedu.loyaltylift.model.customer.Customer;

/**
 * Represents an Order in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {

    public static final Comparator<Order> SORT_CREATED_DATE = Comparator.comparing(Order::getCreatedDate);

    private final Customer customer;
    private final Name name;
    private final Quantity quantity;
    private final Address address;

    // Optional fields
    private final Status status;
    private final CreatedDate createdDate;
    private final Note note;

    /**
     * Order constructor with default values.
     */
    public Order(Customer customer, Name name, Quantity quantity, Address address) {
        this(customer, name, quantity, address, new Status(), new CreatedDate(LocalDate.now()), new Note(""));
    }

    /**
     * Order constructor with optional fields.
     */
    public Order(Customer customer, Name name, Quantity quantity, Address address,
            Status status, CreatedDate createdDate, Note note) {
        requireAllNonNull(customer, name, quantity, status, address, createdDate);
        this.customer = customer;
        this.name = name;
        this.quantity = quantity;
        this.address = address;
        this.status = status;
        this.createdDate = createdDate;
        this.note = note;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Name getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Address getAddress() {
        return address;
    }

    public Status getStatus() {
        return status;
    }

    public CreatedDate getCreatedDate() {
        return createdDate;
    }

    public Note getNote() {
        return note;
    }

    /**
     * Returns a new {@code Order} with its customer association to the specified customer.
     * @param customer The new customer associated to.
     * @return A new Order instance.
     */
    public Order newOrderWithCustomer(Customer customer) {
        return new Order(customer, getName(), getQuantity(), getAddress(), getStatus(), getCreatedDate(), getNote());
    }

    /**
     * Returns true if both orders have the same name.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        return equals(otherOrder);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return otherOrder.getCustomer().equals(getCustomer())
                && otherOrder.getName().equals(getName())
                && otherOrder.getQuantity().equals(getQuantity())
                && otherOrder.getAddress().equals(getAddress())
                && otherOrder.getStatus().equals(getStatus())
                && otherOrder.getCreatedDate().equals(getCreatedDate())
                && otherOrder.getNote().equals(getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, name, quantity, address, status, createdDate, note);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("[%s]", customer.getName()))
                .append(getName())
                .append("; Quantity: ")
                .append(getQuantity())
                .append("; Address: ")
                .append(getAddress())
                .append("; Status: ")
                .append(getStatus())
                .append("; CreatedDate: ")
                .append(getCreatedDate());
        return builder.toString();
    }

}

package seedu.loyaltylift.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.loyaltylift.model.AddressBook;
import seedu.loyaltylift.model.ReadOnlyAddressBook;
import seedu.loyaltylift.model.attribute.Address;
import seedu.loyaltylift.model.attribute.Name;
import seedu.loyaltylift.model.customer.Customer;
import seedu.loyaltylift.model.customer.CustomerType;
import seedu.loyaltylift.model.customer.Email;
import seedu.loyaltylift.model.customer.Phone;
import seedu.loyaltylift.model.order.CreatedDate;
import seedu.loyaltylift.model.order.Quantity;
import seedu.loyaltylift.model.order.Status;
import seedu.loyaltylift.model.order.StatusUpdate;
import seedu.loyaltylift.model.order.StatusValue;
import seedu.loyaltylift.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Customer[] getSampleCustomers() {
        return new Customer[] {
            new Customer(CustomerType.INDIVIDUAL, new Name("Alex Yeoh"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends")),
            new Customer(CustomerType.INDIVIDUAL, new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
            new Customer(CustomerType.INDIVIDUAL, new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours")),
            new Customer(CustomerType.INDIVIDUAL, new Name("David Li"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family")),
            new Customer(CustomerType.INDIVIDUAL, new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates")),
            new Customer(CustomerType.INDIVIDUAL, new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Customer sampleCustomer : getSampleCustomers()) {
            sampleAb.addCustomer(sampleCustomer);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a Quantity from a given string.
     */
    public static Quantity getQuantity(String quantity) {
        return new Quantity(Integer.parseInt(quantity));
    }

    /**
     * Returns a Status from a given string.
     */
    public static Status getStatus(String status, LocalDate date) {
        StatusValue statusValue = StatusValue.fromString(status);
        return new Status(List.of(
                new StatusUpdate(statusValue, date)
        ));
    }

    /**
     * Returns a CreatedDate from a given string.
     */
    public static CreatedDate getCreatedDate(String createdDate) {
        return new CreatedDate(LocalDate.parse(createdDate, CreatedDate.DATE_FORMATTER));
    }

}

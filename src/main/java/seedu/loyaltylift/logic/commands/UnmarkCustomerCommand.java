package seedu.loyaltylift.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.loyaltylift.commons.core.Messages;
import seedu.loyaltylift.commons.core.index.Index;
import seedu.loyaltylift.logic.commands.exceptions.CommandException;
import seedu.loyaltylift.model.Model;
import seedu.loyaltylift.model.customer.Address;
import seedu.loyaltylift.model.customer.Customer;
import seedu.loyaltylift.model.customer.Email;
import seedu.loyaltylift.model.customer.Name;
import seedu.loyaltylift.model.customer.Phone;
import seedu.loyaltylift.model.tag.Tag;

/**
 * Un-bookmarks an existing customer in the address book.
 */
public class UnmarkCustomerCommand extends Command {
    public static final String COMMAND_WORD = "unmarkc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Un-bookmarks the customer identified by the index number used in the displayed customer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_CUSTOMER_SUCCESS = "Un-bookmarked Customer: %1$s";

    private final Index index;

    /**
     * @param index of the customer in the filtered customer list to un-bookmark.
     */
    public UnmarkCustomerCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Customer> lastShownList = model.getFilteredCustomerList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        Customer customerToUnmark = lastShownList.get(index.getZeroBased());
        Customer unmarkedCustomer = createUnmarkedCustomer(customerToUnmark);
        model.setCustomer(customerToUnmark, unmarkedCustomer);
        return new CommandResult(String.format(MESSAGE_MARK_CUSTOMER_SUCCESS, unmarkedCustomer));
    }

    /**
     * Creates and returns a {@code Customer} with the same details as {@code customerToMark}
     * but with the boolean {@code marked} set to false.
     */
    private static Customer createUnmarkedCustomer(Customer customerToUnmark) {
        assert customerToUnmark != null;

        Name name = customerToUnmark.getName();
        Phone phone = customerToUnmark.getPhone();
        Email email = customerToUnmark.getEmail();
        Address address = customerToUnmark.getAddress();
        Set<Tag> tags = customerToUnmark.getTags();

        return new Customer(name, phone, email, address, tags, false);
    }
}

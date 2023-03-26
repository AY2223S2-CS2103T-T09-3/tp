package seedu.loyaltylift.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.loyaltylift.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.loyaltylift.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import java.util.Comparator;

import seedu.loyaltylift.model.Model;
import seedu.loyaltylift.model.customer.Customer;

/**
 * Lists all customers in the address book to the user.
 */
public class ListCustomerCommand extends Command {

    public static final String COMMAND_WORD = "listc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all customers with an optional sort option "
            + "(name by default) and displays them as a list with index numbers.\n"
            + "Parameters: [" + PREFIX_SORT + "{name|points}]\n"
            + "Example: " + COMMAND_WORD + " s/points";

    public static final String MESSAGE_SUCCESS = "Listed all customers";
    public static final String MESSAGE_INVALID_SORT = "Unrecognized sort option";

    private final Comparator<Customer> comparator;

    public ListCustomerCommand(Comparator<Customer> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortFilteredCustomerList(comparator);
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCustomerCommand // instanceof handles nulls
                && comparator.equals(((ListCustomerCommand) other).comparator)); // state check
    }
}

package seedu.loyaltylift.logic.commands;

import static seedu.loyaltylift.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.loyaltylift.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.List;

import seedu.loyaltylift.commons.core.Messages;
import seedu.loyaltylift.commons.core.index.Index;
import seedu.loyaltylift.logic.commands.exceptions.CommandException;
import seedu.loyaltylift.model.Model;
import seedu.loyaltylift.model.attribute.Address;
import seedu.loyaltylift.model.attribute.Name;
import seedu.loyaltylift.model.attribute.Note;
import seedu.loyaltylift.model.customer.Customer;
import seedu.loyaltylift.model.order.CreatedDate;
import seedu.loyaltylift.model.order.Order;
import seedu.loyaltylift.model.order.Quantity;
import seedu.loyaltylift.model.order.Status;

/**
 * Reverts the status of the order
 */
public class RevertOrderStatusCommand extends Command {

    public static final String COMMAND_WORD = "revo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": reverts the order status \n"
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD
            + " 1 ";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$s";

    public static final String MESSAGE_REVERT_STATUS_SUCCESS = "Reverted status for order: %1$s \n"
            + "New status is: %2$s";
    public static final String MESSAGE_INVALID_COMMAND = "This order status is already at its earliest stage";

    private final Index index;

    /**
     * @param index of the order in the filtered order list to advance status
     */
    public RevertOrderStatusCommand(Index index) {
        requireAllNonNull(index);

        this.index = index;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Order> lastShownList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToRevert = lastShownList.get(index.getZeroBased());
        Order revertedOrder = createRevertedOrder(orderToRevert);

        model.setOrder(orderToRevert, revertedOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);

        return new CommandResult(generateSuccessMessage(revertedOrder));
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToRevert}
     */
    private Order createRevertedOrder(Order orderToRevert) throws CommandException {
        assert orderToRevert != null;
        Status newStatus;
        Name name = orderToRevert.getName();
        Customer customer = orderToRevert.getCustomer();
        Address address = orderToRevert.getAddress();
        Quantity quantity = orderToRevert.getQuantity();
        Status status = orderToRevert.getStatus();
        CreatedDate createdDate = orderToRevert.getCreatedDate();
        Note note = orderToRevert.getNote();

        try {
            newStatus = status.newStatusWithRemoveLatest();
        } catch (IllegalStateException e) {
            throw new CommandException(MESSAGE_INVALID_COMMAND);
        }

        return new Order(customer, name, quantity, address, newStatus,
                createdDate, note);
    }



    /**
     * Generates a command execution success message based on whether
     * the points are added
     */
    private String generateSuccessMessage(Order revertedOrder) {
        String message = MESSAGE_REVERT_STATUS_SUCCESS;
        return String.format(message, revertedOrder, revertedOrder.getStatus());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RevertOrderStatusCommand)) {
            return false;
        }

        // state check
        RevertOrderStatusCommand e = (RevertOrderStatusCommand) other;
        return index.equals(e.index);
    }
}


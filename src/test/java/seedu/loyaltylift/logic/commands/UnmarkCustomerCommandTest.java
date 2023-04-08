package seedu.loyaltylift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.loyaltylift.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.loyaltylift.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.loyaltylift.logic.commands.CommandTestUtil.showCustomerAtIndex;
import static seedu.loyaltylift.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.loyaltylift.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.loyaltylift.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.loyaltylift.commons.core.Messages;
import seedu.loyaltylift.commons.core.index.Index;
import seedu.loyaltylift.model.Model;
import seedu.loyaltylift.model.ModelManager;
import seedu.loyaltylift.model.UserPrefs;
import seedu.loyaltylift.model.attribute.Address;
import seedu.loyaltylift.model.attribute.Name;
import seedu.loyaltylift.model.attribute.Note;
import seedu.loyaltylift.model.customer.Customer;
import seedu.loyaltylift.model.customer.CustomerType;
import seedu.loyaltylift.model.customer.Email;
import seedu.loyaltylift.model.customer.Marked;
import seedu.loyaltylift.model.customer.Phone;
import seedu.loyaltylift.model.customer.Points;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkCustomerCommand}.
 */
public class UnmarkCustomerCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Customer customerToUnmark = model.getFilteredCustomerList().get(INDEX_FIRST.getZeroBased());
        CustomerType customerType = customerToUnmark.getCustomerType();
        Name name = customerToUnmark.getName();
        Phone phone = customerToUnmark.getPhone();
        Email email = customerToUnmark.getEmail();
        Address address = customerToUnmark.getAddress();
        Points points = customerToUnmark.getPoints();
        Note note = customerToUnmark.getNote();
        Customer unmarkedCustomer = new Customer(customerType, name, phone, email, address, points,
                new Marked(false), note);
        UnmarkCustomerCommand unmarkCustomerCommand = new UnmarkCustomerCommand(INDEX_FIRST);

        String expectedMessage = String.format(UnmarkCustomerCommand.MESSAGE_UNMARK_CUSTOMER_SUCCESS, unmarkedCustomer);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setCustomer(model.getFilteredCustomerList().get(0), unmarkedCustomer);

        assertCommandSuccess(unmarkCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        UnmarkCustomerCommand unmarkCustomerCommand = new UnmarkCustomerCommand(outOfBoundIndex);

        assertCommandFailure(unmarkCustomerCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        model.sortFilteredCustomerList(Customer.SORT_POINTS);

        Customer customerToUnmark = model.getFilteredCustomerList().get(INDEX_FIRST.getZeroBased());
        CustomerType customerType = customerToUnmark.getCustomerType();
        Name name = customerToUnmark.getName();
        Phone phone = customerToUnmark.getPhone();
        Email email = customerToUnmark.getEmail();
        Address address = customerToUnmark.getAddress();
        Points points = customerToUnmark.getPoints();
        Note note = customerToUnmark.getNote();
        Customer unmarkedCustomer = new Customer(customerType, name, phone, email, address, points,
                new Marked(false), note);
        UnmarkCustomerCommand unmarkCustomerCommand = new UnmarkCustomerCommand(INDEX_FIRST);

        String expectedMessage = String.format(UnmarkCustomerCommand.MESSAGE_UNMARK_CUSTOMER_SUCCESS, unmarkedCustomer);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setCustomer(model.getFilteredCustomerList().get(0), unmarkedCustomer);

        assertCommandSuccess(unmarkCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCustomerAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCustomerList().size());

        UnmarkCustomerCommand unmarkCustomerCommand = new UnmarkCustomerCommand(outOfBoundIndex);

        assertCommandFailure(unmarkCustomerCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnmarkCustomerCommand unmarkFirstCommand = new UnmarkCustomerCommand(INDEX_FIRST);
        UnmarkCustomerCommand unmarkSecondCommand = new UnmarkCustomerCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkCustomerCommand unmarkFirstCommandCopy = new UnmarkCustomerCommand(INDEX_FIRST);
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different customer -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }
}

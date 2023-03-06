package seedu.loyaltylift.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.loyaltylift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.loyaltylift.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.loyaltylift.testutil.Assert.assertThrows;
import static seedu.loyaltylift.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.loyaltylift.logic.commands.AddCustomerCommand;
import seedu.loyaltylift.logic.commands.ClearCommand;
import seedu.loyaltylift.logic.commands.DeleteCustomerCommand;
import seedu.loyaltylift.logic.commands.EditCustomerCommand;
import seedu.loyaltylift.logic.commands.EditCustomerCommand.EditCustomerDescriptor;
import seedu.loyaltylift.logic.commands.ExitCommand;
import seedu.loyaltylift.logic.commands.FindCustomerCommand;
import seedu.loyaltylift.logic.commands.HelpCommand;
import seedu.loyaltylift.logic.commands.ListCustomerCommand;
import seedu.loyaltylift.logic.parser.exceptions.ParseException;
import seedu.loyaltylift.model.customer.Customer;
import seedu.loyaltylift.model.customer.NameContainsKeywordsPredicate;
import seedu.loyaltylift.testutil.CustomerBuilder;
import seedu.loyaltylift.testutil.CustomerUtil;
import seedu.loyaltylift.testutil.EditCustomerDescriptorBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Customer customer = new CustomerBuilder().build();
        AddCustomerCommand command = (AddCustomerCommand) parser.parseCommand(CustomerUtil.getAddCommand(customer));
        assertEquals(new AddCustomerCommand(customer), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCustomerCommand command = (DeleteCustomerCommand) parser.parseCommand(
                DeleteCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased());
        assertEquals(new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Customer customer = new CustomerBuilder().build();
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(customer).build();
        EditCustomerCommand command = (EditCustomerCommand) parser.parseCommand(EditCustomerCommand.COMMAND_WORD + " "
                + INDEX_FIRST_CUSTOMER.getOneBased() + " " + CustomerUtil.getEditCustomerDescriptorDetails(descriptor));
        assertEquals(new EditCustomerCommand(INDEX_FIRST_CUSTOMER, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCustomerCommand command = (FindCustomerCommand) parser.parseCommand(
                FindCustomerCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCustomerCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCustomerCommand.COMMAND_WORD) instanceof ListCustomerCommand);
        assertTrue(parser.parseCommand(ListCustomerCommand.COMMAND_WORD + " 3") instanceof ListCustomerCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}

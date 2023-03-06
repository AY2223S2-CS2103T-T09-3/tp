package seedu.loyaltylift.logic.parser;

import static seedu.loyaltylift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.loyaltylift.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.loyaltylift.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.loyaltylift.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.loyaltylift.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.loyaltylift.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.loyaltylift.logic.commands.AddCommand;
import seedu.loyaltylift.logic.parser.exceptions.ParseException;
import seedu.loyaltylift.model.person.Address;
import seedu.loyaltylift.model.person.Email;
import seedu.loyaltylift.model.person.Name;
import seedu.loyaltylift.model.person.Person;
import seedu.loyaltylift.model.person.Phone;
import seedu.loyaltylift.model.person.Remark;
import seedu.loyaltylift.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Remark remark = new Remark("");
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, remark, address, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

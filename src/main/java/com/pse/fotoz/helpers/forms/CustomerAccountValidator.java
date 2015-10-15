package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.CustomerAccount;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates a Customer entity. Validator checks whether:
 * <ol>
 * <li>
 * The shop doesn't already exist
 * </li>
 * <li>
 * The password and login are not empty
 * </li>
 * <li>
 * \@Issue lies The password is at least 8 characters long, only alphanumeric
 * character.
 * </li>
 * </ol>
 *
 * Does NOT check whether the photographer exists
 *
 * @author Robert
 */
public class CustomerAccountValidator implements InputValidator<CustomerAccount> {

    private final Map<String, String> properties;

    /**
     * Creates a validator with properties containing used error messages.
     *
     * @param properties properties containing used error messages
     */
    public CustomerAccountValidator(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public ValidationResult validate(CustomerAccount acc) {
        final List<String> errors = new ArrayList<>();

        if (HibernateEntityHelper.find(CustomerAccount.class, "login", acc.getLogin()).
                stream().findAny().isPresent()) {
            errors.add(properties.
                    get("ERROR_PRODUCER_NEWSHOP_LOGINALREADYEXISTS"));
        }

        if (isEmpty(acc.getLogin())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"),
                    properties.get("customer_login_loginname_label")));
        }

        if (isEmpty(acc.getPasswordHash())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"),
                    properties.get("customer_login_password_label")));
        } else if (!SimpleRegex.matches("[a-zA-Z0-9]*", acc.getPasswordHash())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_INVALIDFIELD"),
                    properties.get("customer_login_password_label")));
        }

        return new ValidationResult() {

            @Override
            public ValidationStatus status() {
                return errors.isEmpty() ? ValidationStatus.OK
                        : ValidationStatus.NOK;
            }

            @Override
            public List<String> errors() {
                return errors;
            }
        };
    }

}

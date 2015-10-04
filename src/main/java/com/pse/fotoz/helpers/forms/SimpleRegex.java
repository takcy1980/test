package com.pse.fotoz.helpers.forms;

import java.util.regex.Pattern;

/**
 *
 * @author Robert
 */
public class SimpleRegex {
    public static boolean matches(String regex, String subject) {
        return Pattern.compile(regex).matcher(subject).find();
    }
}

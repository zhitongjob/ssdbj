package com.lovver.ssdbj.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class GT {
	private final static GT _gt = new GT();
    private final static Object noargs[] = new Object[0];

    public final static String tr(String message) {
        return _gt.translate(message, null);
    }

    public final static String tr(String message, Object arg) {
        return _gt.translate(message, new Object[]{arg});
    }

    public final static String tr(String message, Object args[]) {
        return _gt.translate(message, args);
    }


    private ResourceBundle _bundle;

    private GT() {
        try
        {
            _bundle = ResourceBundle.getBundle("com.lovver.ssdb.messages");
        }
        catch (MissingResourceException mre)
        {
            // translation files have not been installed
            _bundle = null;
        }
    }

    private final String translate(String message, Object args[])
    {
        if (_bundle != null && message != null)
        {
            try
            {
                message = _bundle.getString(message);
            }
            catch (MissingResourceException mre)
            {
                // If we can't find a translation, just
                // use the untranslated message.
            }
        }

        // If we don't have any parameters we still need to run
        // this through the MessageFormat(ter) to allow the same
        // quoting and escaping rules to be used for all messages.
        //
        if (args == null) {
            args = noargs;
        }

        // Replace placeholders with arguments
        //
        if (message != null)
        {
            message = MessageFormat.format(message, args);
        }

        return message;
    }
}

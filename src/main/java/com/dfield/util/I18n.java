package com.dfield.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private static ResourceBundle bundle;
    private static Locale currentLocale;

    static {
        Locale sys = Locale.getDefault();
        // Se o sistema estiver em Português (pt, pt_BR, pt_PT), usa pt_BR. Caso contrário, inglês.
        if (sys.getLanguage().equalsIgnoreCase("pt")) {
            setLocale(new Locale("pt", "BR"));
        } else {
            setLocale(Locale.ENGLISH);
        }
    }

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("i18n.messages", currentLocale);
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return "!" + key + "!";
        }
    }

    public static String format(String key, Object... args) {
        try {
            return MessageFormat.format(bundle.getString(key), args);
        } catch (Exception e) {
            return "!" + key + "!";
        }
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }
}

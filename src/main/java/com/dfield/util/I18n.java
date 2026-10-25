package com.dfield.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private static ResourceBundle bundle;
    private static Locale currentLocale;

    static {
        Locale sys = Locale.getDefault();
        String lang = sys.getLanguage().toLowerCase();
        
        // Mapeamento dinâmico de idiomas suportados
        if (lang.equals("pt")) {
            setLocale(new Locale("pt", "BR"));
        } else if (lang.equals("es")) {
            setLocale(new Locale("es", "ES"));
        } else if (lang.equals("fr")) {
            setLocale(new Locale("fr", "FR")); // Preparando terreno para o pplaneX
        } else {
            setLocale(Locale.ENGLISH); // Fallback padrão
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
            String pattern = bundle.getString(key);
            if (pattern.contains("%")) {
                return String.format(currentLocale, pattern, args);
            }
            return MessageFormat.format(pattern, args);
        } catch (Exception e) {
            return "!" + key + "!";
        }
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }
}

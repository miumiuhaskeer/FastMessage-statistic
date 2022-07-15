package com.miumiuhaskeer.fastmessage.statistic.properties.bundle;

/**
 * Class copied from FastMessage project
 */
public class ErrorBundle {

    private static final SimpleBundle bundle = new SimpleBundle("static/error");

    /** {@inheritDoc} */
    public static String get(String key) {
        return bundle.get(key);
    }

    /** {@inheritDoc} */
    public static String getFromText(String text) {
        return bundle.getFromText(text);
    }
}
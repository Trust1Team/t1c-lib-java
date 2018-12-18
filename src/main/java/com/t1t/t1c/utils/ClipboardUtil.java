package com.t1t.t1c.utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public final class ClipboardUtil {

    private ClipboardUtil() {}

    /**
     * Saves a String to the clipboard, and returns the content that was replaced
     * @param text the String to save
     * @return the previous content
     */
    public static Transferable saveStringToClipboard(final String text) {
        final StringSelection selection = new StringSelection(text);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        final Transferable currentContents = clipboard.getContents(null);
        clipboard.setContents(selection, selection);
        return currentContents;
    }

    /**
     * Set the contents of the clipboard
     * @param content the content to set
     */
    public static void setClipboarContents(final Transferable content) {
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(content, new ClipboardOwner() {
            @Override
            public void lostOwnership(final Clipboard clipboard, final Transferable contents) {
                // empty
            }
        });
    }

}
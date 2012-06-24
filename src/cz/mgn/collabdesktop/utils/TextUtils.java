/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *         @author indy
 */
public class TextUtils {

    /**
     *         znaky, ktere rozdeluji slova
     */
    public static final String wordSeparaters = " \n";

    /**
     *         vrati slovo ktere v retezci zasahuje do dane pozice (muze vratit i
     * prazdny retezec pokud zadne)
     *
     *         @param text text ve kterem se hleda
     *         @param pos pozice na ktere se hleda slovo
     */
    public static String getWord(String text, int pos) {
        String be = text.substring(0, pos);
        String af = text.substring(pos);
        int s = getMaxIndexOfChars(be, wordSeparaters);
        if (s == -1) {
            s = 0;
        } else {
            s++;
        }
        int e = getMinIndexOfChars(af, wordSeparaters);
        if (e == -1) {
            e = text.length();
        } else {
            e += be.length();
        }
        if (s > e) {
            s = e;
        }
        return text.substring(s, e);
    }

    /**
     *         najde nejmensi index znaku ktery je v seznamu znaku (pozice toho ktery je
     * nejblize zacatku predaneho retezce)
     *
     *         @param string retezec ktery se prohledava
     *         @param chars seznam znaku
     */
    public static int getMinIndexOfChars(String string, String chars) {
        int s = - 1;
        for (int i = 0; i < wordSeparaters.length(); i++) {
            int k = string.indexOf(wordSeparaters.charAt(i));
            if ((k < s || s == -1) && k != -1) {
                s = k;
            }
        }
        return s;
    }

    /**
     *         najde nejvetsi index znaku ktery je v seznamu znaku (pozice toho ktery je
     * nejblize konce predaneho retezce)
     *
     *         @param string retezec ktery se prohledava
     *         @param chars seznam znaku
     */
    public static int getMaxIndexOfChars(String string, String chars) {
        int s = - 1;
        for (int i = 0; i < wordSeparaters.length(); i++) {
            int k = string.lastIndexOf(wordSeparaters.charAt(i));
            if (k > s) {
                s = k;
            }
        }
        return s;
    }

    /**
     *         vrati seznam zacatku a koncu odkazu v textu (vzdy dve cisla po sobe
     * zacatek, konec)
     */
    public static ArrayList<Integer> getLinkPositions(String text) {
        ArrayList<Integer> links = new ArrayList<Integer>();
        String toEnd = text;
        while (toEnd.length() > 0) {
            int s = 1;
            if (toEnd.length() == text.length()) {
                s = 0;
            }
            if (s > toEnd.length()) {
                s = toEnd.length();
            }
            s += text.length() - toEnd.length();
            int e = getMinIndexOfChars(text.substring(s), wordSeparaters);
            if (e == -1) {
                e = text.length();
            } else {
                e += s;
            }
            if (s > e) {
                e = s;
            }
            String w = text.substring(s, e);
            if (w.length() > 0 && isLink(w)) {
                links.add(s);
                links.add(e);
            }
            toEnd = text.substring(e);
        }
        return links;
    }

    /**
     *         zjisti jestli je zadane slovo odkaz
     */
    public static boolean isLink(String word) {
        return (word.startsWith("http://") || word.startsWith("https://"));
    }

    public static String generateRGBColor(Color color) {
        return "rgb (" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")";
    }

    public static String generateHexolor(Color color) {
        String r = Integer.toHexString(color.getRed());
        if (r.length() < 2) {
            r = "0" + r;
        }
        String g = Integer.toHexString(color.getGreen());
        if (g.length() < 2) {
            g = "0" + g;
        }
        String b = Integer.toHexString(color.getBlue());
        if (b.length() < 2) {
            b = "0" + b;
        }
        return "#" + r + g + b;
    }

    public static Color parseRGBColor(String rgb) {
        Pattern c = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");
        Matcher m = c.matcher(rgb);
        if (m.matches()) {
            int r = Integer.valueOf(m.group(1));
            int g = Integer.valueOf(m.group(2));
            int b = Integer.valueOf(m.group(3));
            if (r > 255 || g > 255 || b > 255) {
                return null;
            }
            return new Color(r, g, b);
        }

        return null;
    }

    public static Color parseHexColor(String hex) {
        if (hex.length() == 7) {
            Pattern c = Pattern.compile("^#([A-Fa-f0-9]{6})$");
            Matcher m = c.matcher(hex);
            if (m.matches()) {
                int color = Integer.valueOf(m.group(1), 16);
                return new Color(color);
            }
        }
        return null;
    }
}

/*
 * Collab desktop - Software for shared drawing via internet in real-time
 * Copyright (C) 2012 Martin Indra <aktive@seznam.cz>
 *
 * This file is part of Collab desktop.
 *
 * Collab desktop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Collab desktop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Collab desktop.  If not, see <http://www.gnu.org/licenses/>.
 */

package cz.mgn.collabdesktop.utils.textgraphicsutil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 *      @author Martin Indra <aktive@seznam.cz>
 */
public class TextGraphicsUtil {

    protected static final Font BASIC_FONT = new Font("serif", Font.PLAIN, 12);
    protected static Font[] fonts;
    protected static String[] fontsName;

    static {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fonts = ge.getAllFonts();
        fontsName = new String[fonts.length];
        for (int i = 0; i < fonts.length; i++) {
            fontsName[i] = fonts[i].getFontName();
        }
    }

    protected static BufferedImage renderText(String text, Color color, Font font) {
        String[] lines = text.split("\\n");
        int w = 1;
        int h = 1;
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) result.getGraphics();
        FontMetrics fm = g.getFontMetrics(font);
        g.dispose();
        h = lines.length * fm.getHeight();
        for (String line : lines) {
            w = Math.max(w, fm.stringWidth(line));
        }
        result = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        g = (Graphics2D) result.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(color);
        g.setFont(font);
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            int x = 0;
            int y = i * fm.getHeight() + fm.getAscent();
            g.drawString(line, x, y);
        }
        g.dispose();
        return result;
    }

    public static BufferedImage renderText(String text, Color color, int fontSize) {
        return renderText(text, color, BASIC_FONT.deriveFont((float) fontSize));
    }

    public static BufferedImage renderText(String text, Color color) {
        return renderText(text, color, BASIC_FONT);
    }

    public static BufferedImage renderText(String text, Color color, int fontSize, String fontName) {
        Font font = BASIC_FONT;
        for (int i = 0; i < fonts.length && font == BASIC_FONT; i++) {
            if (fonts[i].getFontName().equals(fontName)) {
                font = fonts[i];
            }
        }
        return renderText(text, color, font.deriveFont((float) fontSize));
    }

    public static String[] getAvailibleFonts() {
        return fontsName;
    }
}

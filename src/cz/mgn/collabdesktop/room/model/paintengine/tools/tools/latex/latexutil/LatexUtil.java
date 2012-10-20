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

package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.latex.latexutil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

/**
 *
 *  @author Martin Indra <aktive@seznam.cz>
 */
public class LatexUtil implements Runnable {

    protected LatexInterface latexInterface;
    protected String tex = "";
    protected float fontSize = 12f;
    protected Color fontColor = Color.BLACK;

    public static void generateMath(LatexInterface latexInterface, String tex, float fontSize, Color fontColor) {
        new LatexUtil(latexInterface, tex, fontSize, fontColor);
    }

    public static void generateMath(LatexInterface latexInterface, String tex) {
        new LatexUtil(latexInterface, tex);
    }

    protected LatexUtil(LatexInterface latexInterface, String tex) {
        this.latexInterface = latexInterface;
        this.tex = tex;
        new Thread(this).start();
    }

    protected LatexUtil(LatexInterface latexInterface, String tex, float fontSize, Color fontColor) {
        this.latexInterface = latexInterface;
        this.tex = tex;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        new Thread(this).start();
    }

    protected BufferedImage generateMath(String source, float fontSize, Color fontColor) {
        TeXFormula fomule = new TeXFormula(source);
        TeXIcon ti = fomule.createTeXIcon(TeXConstants.STYLE_DISPLAY, fontSize * 2, TeXConstants.TYPE_ORDINARY, fontColor);
        BufferedImage render = new BufferedImage(Math.max(1, ti.getIconWidth()), Math.max(1, ti.getIconHeight()), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) render.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ti.paintIcon(new JLabel(), g, 0, 0);
        g.dispose();
        return render;
    }

    @Override
    public void run() {
        BufferedImage render = generateMath(tex, fontSize, fontColor);
        latexInterface.latechRendered(render);
    }
}

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

package cz.mgn.collabdesktop.menu.frames.about;

import cz.mgn.collabdesktop.menu.MenuFrame;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 *    @author Martin Indra <aktive@seznam.cz>
 */
public class Authors extends MenuFrame {

    public Authors() {
        super();
    }

    @Override
    protected String getSectionName() {
        return "authors";
    }

    @Override
    protected void initComponents() {
        AuthorsPanel authorsPanel = new AuthorsPanel();
        Insets insets = getInsets();
        Dimension size = new Dimension(authorsPanel.getPreferredSize());
        size.width += insets.left + insets.right;
        size.height += insets.bottom + insets.top;
        setPreferredSize(size);
        setSize(getPreferredSize());
        setMaximumSize(new Dimension(size));
        setMinimumSize(new Dimension(size.width / 2, size.height / 2));
        setLayout(new BorderLayout());
        add(authorsPanel);
    }

    protected class AuthorsPanel extends JPanel {

        protected BufferedImage authors = null;

        public AuthorsPanel() {
            init();
        }

        protected void init() {
            authors = ImageUtil.loadImageFromResources("/resources/images/authors.png");
            setPreferredSize(new Dimension(authors.getWidth() + 10, authors.getHeight() + 30));
        }

        @Override
        public void paintComponent(Graphics g2) {
            Graphics2D g = (Graphics2D) g2;
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            int w = authors.getWidth();
            int h = authors.getHeight();
            if (w > getWidth() || h > getHeight()) {
                float scale = Math.min((float) getWidth() / (float) w, (float) getHeight() / (float) h);
                w *= scale;
                h *= scale;
            }
            int x = (getWidth() - w) / 2;
            int y = (getHeight() - h) / 2;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(authors, x, y, w, h, null);
        }
    }
}

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

package cz.mgn.collabdesktop.utils.gui.iconComponent;

import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.JCheckBox;

/**
 *
 *   @author Martin Indra <aktive@seznam.cz>
 */
public class IconCheckBox extends JCheckBox implements MouseListener, ItemListener {

    protected BufferedImage df = null;
    protected BufferedImage selected = null;
    protected BufferedImage hover = null;
    protected BufferedImage show = null;

    public IconCheckBox(String source, Icon icon) {
        super(icon);
        init(source);
    }

    protected void init(String source) {
        setBorder(null);
        setOpaque(false);
        loadImages(source);
        addMouseListener(this);
        addItemListener(this);
        show = df;
        setPreferredSize(new Dimension(df.getWidth(), df.getHeight()));
    }

    protected void loadImages(String source) {
        selected = ImageUtil.loadImageFromResources("/resources/images/buttons/" + source + "/selected.png");
        hover = ImageUtil.loadImageFromResources("/resources/images/buttons/" + source + "/hover.png");
        df = ImageUtil.loadImageFromResources("/resources/images/buttons/" + source + "/default.png");
    }

    @Override
    public void paintComponent(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        g.clearRect(0, 0, getWidth(), getHeight());
        if (show != null) {
            g.drawImage(show, 0, 0, null);
        }
        Icon icon = getIcon();
        if (icon != null) {
            icon.paintIcon(this, g2, (getWidth() - icon.getIconWidth()) / 2, (getHeight() - icon.getIconHeight()) / 2);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        show = isSelected() ? selected : hover;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        show = isSelected() ? selected : hover;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        show = isSelected() ? selected : df;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        show = isSelected() ? selected : hover;
    }
}

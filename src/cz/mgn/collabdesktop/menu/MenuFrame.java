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
package cz.mgn.collabdesktop.menu;

import cz.mgn.collabdesktop.utils.gui.GUIUtils;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public abstract class MenuFrame extends JFrame {

    public MenuFrame() {
        setTitle("Collab - " + getSectionName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image icon = Toolkit.getDefaultToolkit().getImage(MenuFrame.class.getResource("/resources/images/icon-32.png"));
        setIconImage(icon);
        initComponents();
    }

    protected abstract String getSectionName();

    protected abstract void initComponents();

    protected void centerWindow() {
        GUIUtils.centerWindow(this);
    }

    public void setWindowCenterLocation(int cX, int cY) {
        int x = Math.max(0, cX - (getWidth() / 2));
        int y = Math.max(0, cY - (getHeight() / 2));
        setLocation(x, y);
    }

    protected void goTo(MenuFrame menuFrame, boolean dialog) {
        int cX = getLocationOnScreen().x + (getWidth() / 2);
        int cY = getLocationOnScreen().y + (getHeight() / 2);
        if (dialog) {
            menuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else {
            dispose();
        }
        menuFrame.setWindowCenterLocation(cX, cY);
        menuFrame.setVisible(true);
    }
}

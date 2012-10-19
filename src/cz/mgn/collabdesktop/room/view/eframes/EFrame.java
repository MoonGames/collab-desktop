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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.view.eframes;

import cz.mgn.collabdesktop.menu.MenuFrame;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 *   @author indy
 */
public abstract class EFrame extends JDialog implements WindowListener {

    public EFrame() {
        addWindowListener(this);
        setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        setTitle("Collab - " + getSectionName());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        Image icon = Toolkit.getDefaultToolkit().getImage(MenuFrame.class.getResource("/resources/images/icon-32.png"));
        setIconImage(icon);
        initComponents();
    }

    public void showOnCenter(JFrame over) {
        Point position = over.getLocationOnScreen();
        position.x += over.getWidth() / 2;
        position.y += over.getHeight() / 2;
        position.x -= getWidth() / 2;
        position.y -= getHeight() / 2;
        position.x = Math.max(0, position.x);
        position.y = Math.max(0, position.y);
        setLocation(position);
        setVisible(true);
    }

    protected abstract String getSectionName();

    protected abstract void initComponents();

    public abstract void windowClosed();

    @Override
    public void windowClosing(WindowEvent e) {
        windowClosed();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}

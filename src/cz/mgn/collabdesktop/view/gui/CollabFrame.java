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
package cz.mgn.collabdesktop.view.gui;

import cz.mgn.collabdesktop.controller.interfaces.ModelInterface;
import cz.mgn.collabdesktop.view.gui.sections.GUISection;
import cz.mgn.collabdesktop.view.gui.sections.connectserver.ConnectServerGUI;
import cz.mgn.collabdesktop.view.interfaces.View;
import cz.mgn.collabdesktop.view.interfaces.views.sections.chooseroom.SectionChooseRoom;
import cz.mgn.collabdesktop.view.interfaces.views.sections.connectserver.SectionConnectServer;
import cz.mgn.collabdesktop.view.interfaces.views.sections.room.SectionRoom;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class CollabFrame extends JFrame implements View, CollabFrameInterface {

    protected ModelInterface controller;
    protected GUISection current = null;

    public CollabFrame(ModelInterface controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        getContentPane().setLayout(new BorderLayout());
    }

    protected void switchTo(GUISection section) {
        if (current != null) {
            getContentPane().remove(current);
            current.dismiss();
        }
        current = section;
        if (section != null) {
            // Do not change order of next four lines
            section.setController(controller);
            section.setFrameInterface(this);
            getContentPane().add(section, BorderLayout.CENTER);
            section.initComponents();
        }
    }

    @Override
    public void showDialog(JDialog dialog) {
        //TODO:
    }

    @Override
    public SectionConnectServer switchToSectionConnectServer() {
        ConnectServerGUI inter = new ConnectServerGUI();
        switchTo(inter);
        return inter;
    }

    @Override
    public SectionChooseRoom switchToSectionChooseRoom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SectionRoom switchToSectionRoom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

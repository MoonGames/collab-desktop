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
package cz.mgn.collabdesktop.room.view.panels.downpanel.subpanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 *   @author indy
 */
public class UsersPanel extends JPanel {

    protected JList usersList = null;
    protected JScrollPane usersListScrollPane = null;

    public UsersPanel() {
        initComponents();
    }

    protected void initComponents() {
        setLayout(new GridLayout(1, 1));

        usersList = new JList(new DefaultListModel());
        usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersListScrollPane = new JScrollPane(usersList);
        add(usersListScrollPane);
    }

    public void showUsers(String[] users) {
        DefaultListModel model = (DefaultListModel) usersList.getModel();
        model.clear();
        for(int i = 0; i < users.length; i++) {
            model.addElement(users[i]);
        }
    }
}

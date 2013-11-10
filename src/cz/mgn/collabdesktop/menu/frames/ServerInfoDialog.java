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

package cz.mgn.collabdesktop.menu.frames;

import cz.mgn.collabdesktop.utils.gui.FormUtility;
import cz.mgn.collabdesktop.utils.lobbyutil.ServerLobby;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 *  @author Martin Indra <aktive@seznam.cz>
 */
public class ServerInfoDialog extends JDialog {

    protected ServerLobby server;

    public ServerInfoDialog(JFrame owner, ServerLobby server) {
        super(owner);
        this.server = server;
        initComponents();
    }

    protected void initComponents() {
        setSize(400, 400);
        setMinimumSize(new Dimension(400, 200));
        setTitle("Server info (" + server.getName() + ")");

        getContentPane().setLayout(new BorderLayout());
        FormUtility formUtility = new FormUtility(new Insets(2, 2, 2, 2));
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(8, 5, 8, 5));
        getContentPane().add(form, BorderLayout.NORTH);

        formUtility.addLabel("Name: ", form);
        JTextField name = new JTextField(server.getName());
        name.setEditable(false);
        name.setOpaque(false);
        formUtility.addLastField(name, form);

        formUtility.addLabel("Address: ", form);
        JTextField address = new JTextField(server.getAddress());
        address.setEditable(false);
        formUtility.addLastField(address, form);

        formUtility.addLabel("Description: ", form);
        JTextArea description = new JTextArea(server.getDescription());
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setOpaque(false);
        formUtility.addLastField(description, form);
    }
}

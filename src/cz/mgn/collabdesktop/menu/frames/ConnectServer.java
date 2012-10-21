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

import cz.mgn.collabdesktop.menu.MenuFrame;
import cz.mgn.collabdesktop.utils.gui.FormUtility;
import cz.mgn.collabdesktop.utils.settings.Settings;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 *  @author Martin Indra <aktive@seznam.cz>
 */
public class ConnectServer extends MenuFrame implements ActionListener {

    protected JTextField serverOption;
    protected JTextField nickOption;

    public ConnectServer() {
        super();
        centerWindow();
    }

    @Override
    protected String getSectionName() {
        return "connect server";
    }

    @Override
    protected void initComponents() {
        initMenuBar();
        
        nickOption = new JTextField(Settings.defaultNick);
        nickOption.addActionListener(this);
        serverOption = new JTextField(Settings.defaultServer);
        serverOption.addActionListener(this);
        JButton buttonConnect = new JButton("Connect");
        buttonConnect.addActionListener(this);
        
        
        getContentPane().setLayout(new BorderLayout());
        FormUtility formUtility = new FormUtility(new Insets(2, 2, 2, 2));
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(8, 5, 8, 5));
        getContentPane().add(form, BorderLayout.NORTH);
        

        formUtility.addLabel("Nick: ", form);
        formUtility.addLastField(nickOption, form);
        
        formUtility.addLabel("Server: ", form);
        formUtility.addLastField(serverOption, form);
        
        formUtility.addLabel("", form);
        formUtility.addLastField(buttonConnect, form);

        Insets in = getInsets();
        Dimension size = new Dimension(400, 135);
        size.width += in.left + in.right;
        size.height += in.top + in.bottom;
        setPreferredSize(size);
        setSize(getPreferredSize());
        setResizable(false);
    }

    protected void connect() {
        String address = serverOption.getText();
        String nick = nickOption.getText();
        if (!address.isEmpty() && !nick.isEmpty()) {
            goTo(new ConnectingProgress(nick, address), false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        connect();
    }
}

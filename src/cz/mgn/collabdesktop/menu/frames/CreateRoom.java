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
import cz.mgn.collabdesktop.utils.settings.Settings;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class CreateRoom extends MenuFrame implements ActionListener {

    protected ChooseRoom chooseRoom;
    protected JButton create;
    protected JButton cancel;
    protected JTextField name;
    protected JSpinner width;
    protected JSpinner height;
    protected JPasswordField password;

    public CreateRoom(ChooseRoom chooseRoom) {
        super();
        this.chooseRoom = chooseRoom;
    }

    @Override
    protected String getSectionName() {
        return "create room";
    }

    @Override
    protected void initComponents() {
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        JLabel labelName = new JLabel("Name:");
        c.weightx = 0f;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(labelName, c);

        name = new JTextField(Settings.defaultRoomName);
        name.addActionListener(this);
        c.weightx = 1f;
        c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(name, c);

        JLabel labelResolution = new JLabel("Resolution:");
        c.weightx = 0f;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(labelResolution, c);
        
        width = new JSpinner(new SpinnerNumberModel(Math.min(Settings.defaultRoomWidth, Settings.maximalRoomWidth), 1, Settings.maximalRoomWidth, 50));
        height = new JSpinner(new SpinnerNumberModel(Math.min(Settings.defaultRoomHeight, Settings.maximalRoomHeight), 1, Settings.maximalRoomHeight, 50));

        c.weightx = 0.5f;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(width, c);

        JLabel labelX = new JLabel("x");
        c.weightx = 0f;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 1;
        pane.add(labelX, c);

        c.weightx = 0.5f;
        c.gridwidth = 1;
        c.gridx = 3;
        c.gridy = 1;
        pane.add(height, c);

        JLabel labelPassword = new JLabel("Password:");
        c.weightx = 0f;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(labelPassword, c);

        password = new JPasswordField();
        password.addActionListener(this);
        c.weightx = 1f;
        c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 2;
        pane.add(password, c);

        JLabel labelNoPassword = new JLabel("(empty means no password)");
        c.weightx = 1f;
        c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 3;
        pane.add(labelNoPassword, c);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 5, 0));
        create = new JButton("create");
        create.addActionListener(this);
        buttons.add(create);
        cancel = new JButton("cancel");
        cancel.addActionListener(this);
        buttons.add(cancel);
        c.weightx = 1f;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 4;
        pane.add(buttons, c);

        Insets in = getInsets();
        Dimension size = new Dimension(400, 190);
        size.width += in.left + in.right;
        size.height += in.top + in.bottom;
        setPreferredSize(size);
        setSize(getPreferredSize());
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != cancel) {
            String name = this.name.getText();
            if (!name.isEmpty()) {
                chooseRoom.createRoom(name, new String(password.getPassword()), (Integer) width.getValue(), (Integer) height.getValue());
            }
        }
        dispose();
    }
}

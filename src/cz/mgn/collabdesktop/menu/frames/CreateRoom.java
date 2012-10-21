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
        name = new JTextField(Settings.defaultRoomName);
        name.addActionListener(this);
        width = new JSpinner(new SpinnerNumberModel(Math.min(Settings.defaultRoomWidth, Settings.maximalRoomWidth), 1, Settings.maximalRoomWidth, 50));
        height = new JSpinner(new SpinnerNumberModel(Math.min(Settings.defaultRoomHeight, Settings.maximalRoomHeight), 1, Settings.maximalRoomHeight, 50));
        password = new JPasswordField();
        password.addActionListener(this);
        create = new JButton("create");
        create.addActionListener(this);
        cancel = new JButton("cancel");
        cancel.addActionListener(this);

        getContentPane().setLayout(new BorderLayout());
        FormUtility formUtility = new FormUtility(new Insets(2, 2, 2, 2));
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(8, 5, 8, 5));
        getContentPane().add(form, BorderLayout.NORTH);

        formUtility.addLabel("Name: ", form);
        formUtility.addLastField(name, form);

        formUtility.addLabel("Resolution: ", form);
        JPanel resolutionPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        resolutionPanel.add(width);
        resolutionPanel.add(height);
        formUtility.addLastField(resolutionPanel, form);

        formUtility.addLabel("Password: ", form);
        formUtility.addLastField(password, form);
        formUtility.addLabel("", form);
        JLabel pwInfo = new JLabel("(empty means no password)");
        pwInfo.setFont(pwInfo.getFont().deriveFont(Font.PLAIN));
        formUtility.addLastField(pwInfo, form);

        formUtility.addLabel("", form);
        formUtility.addMiddleField(create, form);
        formUtility.addLastField(cancel, form);

        Insets in = getInsets();
        Dimension size = new Dimension(400, 160);
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

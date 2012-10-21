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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class JoinRoom extends MenuFrame implements ActionListener {

    protected ChooseRoom chooseRoom;
    protected int roomID;
    protected JPasswordField password;
    protected JButton join;
    protected JButton cancel;

    public JoinRoom(ChooseRoom chooseRoom, int roomID) {
        super();
        this.chooseRoom = chooseRoom;
        this.roomID = roomID;
    }

    @Override
    protected String getSectionName() {
        return "join room";
    }

    @Override
    protected void initComponents() {
        password = new JPasswordField();
        password.addActionListener(this);
        join = new JButton("join");
        join.addActionListener(this);
        cancel = new JButton("cancel");
        cancel.addActionListener(this);

        getContentPane().setLayout(new BorderLayout());
        FormUtility formUtility = new FormUtility(new Insets(2, 2, 2, 2));
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(8, 5, 8, 5));
        getContentPane().add(form, BorderLayout.NORTH);
        
        formUtility.addLabel("Password: ", form);
        formUtility.addLastField(password, form);
        
        formUtility.addLabel("", form);
        formUtility.addMiddleField(join, form);
        formUtility.addLastField(cancel, form);

        Insets in = getInsets();
        Dimension size = new Dimension(400, 95);
        size.width += in.left + in.right;
        size.height += in.top + in.bottom;
        setPreferredSize(size);
        setSize(getPreferredSize());
        setResizable(false);
    }

    protected void join() {
        String pw = new String(password.getPassword());
        if (!pw.isEmpty()) {
            chooseRoom.joinRoom(roomID, pw);
            dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != cancel) {
            join();
        }
        dispose();
    }
}

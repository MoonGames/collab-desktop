/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.menu.frames;

import cz.mgn.collabdesktop.gui.menu.MenuFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 *
 *      @author indy
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
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        JLabel labelPassword = new JLabel("Password:");
        c.weightx = 0f;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(labelPassword, c);

        password = new JPasswordField();
        password.addActionListener(this);
        c.weightx = 1f;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(password, c);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 5, 0));
        join = new JButton("join");
        join.addActionListener(this);
        buttons.add(join);
        cancel = new JButton("cancel");
        cancel.addActionListener(this);
        buttons.add(cancel);
        c.weightx = 1f;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(buttons, c);

        Insets in = getInsets();
        Dimension size = new Dimension(400, 100);
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

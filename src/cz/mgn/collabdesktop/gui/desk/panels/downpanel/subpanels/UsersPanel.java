/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.downpanel.subpanels;

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

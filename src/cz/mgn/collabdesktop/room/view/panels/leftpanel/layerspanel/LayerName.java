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

package cz.mgn.collabdesktop.room.view.panels.leftpanel.layerspanel;

import cz.mgn.collabdesktop.room.view.eframes.EFrame;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class LayerName extends EFrame implements ActionListener {

    protected LayerNameInterface layerNameInterface = null;
    //
    protected JTextField name;
    protected JButton done;

    public LayerName(LayerNameInterface layerNameInterface) {
        super();
        this.layerNameInterface = layerNameInterface;
    }

    public void setDefaultName(String name) {
        this.name.setText(name);
    }

    @Override
    protected String getSectionName() {
        return "layer name";
    }

    @Override
    protected void initComponents() {
        setPreferredSize(new Dimension(250, 70));
        setSize(getPreferredSize());
        setResizable(false);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelName = new JLabel("Name:");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0f;
        c.weighty = 1f;
        c.insets = new Insets(0, 0, 0, 5);
        add(labelName, c);

        name = new JTextField();
        name.addActionListener(this);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1f;
        c.weighty = 1f;
        c.insets = new Insets(0, 0, 0, 0);
        add(name, c);

        done = new JButton("Done");
        done.addActionListener(this);
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0f;
        c.weighty = 1f;
        c.insets = new Insets(0, 5, 0, 0);
        add(done, c);
    }

    @Override
    public void windowClosed() {
        layerNameInterface.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = this.name.getText();
        if (name.isEmpty()) {
            layerNameInterface.close();
        } else {
            layerNameInterface.done(name);
        }
        dispose();
    }
}

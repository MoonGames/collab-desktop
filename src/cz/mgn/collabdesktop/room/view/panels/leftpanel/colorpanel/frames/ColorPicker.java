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

package cz.mgn.collabdesktop.room.view.panels.leftpanel.colorpanel.frames;

import cz.mgn.collabdesktop.room.view.eframes.EFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class ColorPicker extends EFrame implements ActionListener {

    protected ColorPickerInterface colorPickerInterface;
    //
    protected JColorChooser colorChooser;
    protected JButton select;
    protected JButton cancel;

    public ColorPicker(ColorPickerInterface colorPickerInterface, Color startColor) {
        super();
        this.colorPickerInterface = colorPickerInterface;
        colorChooser.setColor(startColor);
    }

    @Override
    protected String getSectionName() {
        return "color picker";
    }

    @Override
    protected void initComponents() {
        setPreferredSize(new Dimension(480, 400));
        setSize(getPreferredSize());
        setLayout(new BorderLayout(0, 5));

        colorChooser = new JColorChooser();
        add(colorChooser, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 5, 0));
        select = new JButton("Select");
        select.addActionListener(this);
        buttons.add(select);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        buttons.add(cancel);
        add(buttons, BorderLayout.SOUTH);
    }

    @Override
    public void windowClosed() {
        colorPickerInterface.closed();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            colorPickerInterface.closed();
        } else {
            colorPickerInterface.colorPicked(colorChooser.getColor());
        }
        dispose();
    }
}

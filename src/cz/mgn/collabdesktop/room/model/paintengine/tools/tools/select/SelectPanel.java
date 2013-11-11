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
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.select;

import cz.mgn.collabcanvas.interfaces.selectionable.SelectionUpdate;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class SelectPanel extends JPanel implements ActionListener {

    public static final int SHAPE_RECTANGLE = 0;
    public static final int SHAPE_OVAL = 1;
    public static final int SHAPE_COLOR = 2;
    protected SelectPanelInterface selectPanelInterface;
    protected JComboBox mode;
    protected JComboBox shape;

    protected JButton invert;

    public SelectPanel(SelectPanelInterface selectPanelInterface) {
        this.selectPanelInterface = selectPanelInterface;
        initComponents();
    }

    protected void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.NORTHWEST;

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        add(new JLabel("Mode: "), c);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        mode = new JComboBox(new ComboItem[]{
            new ComboItem(ComboItem.MODE_REPLACE),
            new ComboItem(ComboItem.MODE_UNION),
            new ComboItem(ComboItem.MODE_INTERSECTION),
            new ComboItem(ComboItem.MODE_REMOVE)
        });
        add(mode, c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        add(new JLabel("Shape: "), c);

        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        shape = new JComboBox(new ComboItem[]{
            new ComboItem(ComboItem.SHAPE_RECTANGLE),
            new ComboItem(ComboItem.SHAPE_OVAL)
            //TODO: new ComboItem(ComboItem.SHAPE_COLOR)
        });
        add(shape, c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        invert = new JButton("invert selection");
        invert.addActionListener(this);
        add(invert, c);
    }

    public int getSelectionType() {
        ComboItem mode = (ComboItem) this.mode.getSelectedItem();

        if (mode.getMode() == ComboItem.MODE_UNION) {
            return SelectionUpdate.MODE_UNIOIN;
        } else if (mode.getMode() == ComboItem.MODE_REMOVE) {
            return SelectionUpdate.MODE_REMOVE;
        } else if (mode.getMode() == ComboItem.MODE_INTERSECTION) {
            return SelectionUpdate.MODE_INTERSECTION;
        }
        return SelectionUpdate.MODE_REPLACE;
    }

    public int getSelectionShape() {
        ComboItem shape = (ComboItem) this.shape.getSelectedItem();

        if (shape.getMode() == ComboItem.SHAPE_OVAL) {
            return SHAPE_OVAL;
        } else if (shape.getMode() == ComboItem.SHAPE_COLOR) {
            return SHAPE_COLOR;
        }
        return SHAPE_RECTANGLE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == invert) {
            selectPanelInterface.invertSelection();
        }
    }

    public class ComboItem {

        public static final int MODE_REPLACE = 0;
        public static final int MODE_REMOVE = 1;
        public static final int MODE_INTERSECTION = 2;
        public static final int MODE_UNION = 3;
        public static final int SHAPE_RECTANGLE = 4;
        public static final int SHAPE_OVAL = 5;
        public static final int SHAPE_COLOR = 6;

        protected String title;
        protected int mode;

        public ComboItem(int mode) {
            this.mode = mode;

            switch (mode) {
                case MODE_INTERSECTION:
                    title = "Intersection";
                    break;
                case MODE_REMOVE:
                    title = "Subtraction";
                    break;
                case MODE_REPLACE:
                    title = "Replace";
                    break;
                case MODE_UNION:
                    title = "Union";
                    break;
                case SHAPE_RECTANGLE:
                    title = "Rectangle";
                    break;
                case SHAPE_OVAL:
                    title = "Oval";
                    break;
                case SHAPE_COLOR:
                    title = "Color";
                    break;
            }
        }

        public int getMode() {
            return mode;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}

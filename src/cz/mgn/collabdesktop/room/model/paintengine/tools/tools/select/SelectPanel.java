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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.select;

import cz.mgn.collabcanvas.interfaces.selectionable.SelectionUpdate;
import cz.mgn.collabdesktop.room.view.panels.leftpanel.layerspanel.LayersPanel;
import cz.mgn.collabdesktop.utils.gui.iconComponent.IconCheckBox;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class SelectPanel extends JPanel implements ItemListener, ActionListener {

    public static final int SHAPE_RECTANGLE = 0;
    public static final int SHAPE_OVAL = 1;
    protected SelectPanelInterface selectPanelInterface;
    protected JCheckBox newselection;
    protected JCheckBox add;
    protected JCheckBox substract;
    protected JCheckBox intersection;
    protected JCheckBox oval;
    protected JCheckBox rectangle;
    protected JButton invert;

    public SelectPanel(SelectPanelInterface selectPanelInterface) {
        this.selectPanelInterface = selectPanelInterface;
        initComponents();
    }

    protected void initComponents() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(LayersPanel.class.getResource("/resources/images/selection_new.png")));
        newselection = new IconCheckBox("button_16", icon);
        newselection.setToolTipText("new selection");
        newselection.addItemListener(this);
        add(newselection);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(LayersPanel.class.getResource("/resources/images/selection_add.png")));
        add = new IconCheckBox("button_16", icon);
        add.setToolTipText("add to current selection");
        add.addItemListener(this);
        add(add);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(LayersPanel.class.getResource("/resources/images/selection_substract.png")));
        substract = new IconCheckBox("button_16", icon);
        substract.setToolTipText("substract from current selection");
        substract.addItemListener(this);
        add(substract);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(LayersPanel.class.getResource("/resources/images/selection_intersection.png")));
        intersection = new IconCheckBox("button_16", icon);
        intersection.setToolTipText("intersection with current selection");
        intersection.addItemListener(this);
        add(intersection);

        newselection.setSelected(true);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(LayersPanel.class.getResource("/resources/images/selection_oval.png")));
        oval = new IconCheckBox("button_16", icon);
        oval.setToolTipText("oval selection");
        oval.addItemListener(this);
        add(oval);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(LayersPanel.class.getResource("/resources/images/selection_rectangle.png")));
        rectangle = new IconCheckBox("button_16", icon);
        rectangle.setToolTipText("rectangle selection");
        rectangle.addItemListener(this);
        add(rectangle);

        invert = new JButton("invert");
        invert.addActionListener(this);
        add(invert);
    }

    public int getSelectionType() {
        if (add.isSelected()) {
            return SelectionUpdate.MODE_UNIOIN;
        } else if (substract.isSelected()) {
            return SelectionUpdate.MODE_REMOVE;
        } else if (intersection.isSelected()) {
            return SelectionUpdate.MODE_INTERSECTION;
        }
        return SelectionUpdate.MODE_REPLACE;
    }

    public int getSelectionShape() {
        if (oval.isSelected()) {
            return SHAPE_OVAL;
        }
        return SHAPE_RECTANGLE;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            Object source = e.getSource();
            if (source == newselection || source == add || source == substract || source == intersection) {
                if (source != newselection) {
                    newselection.setSelected(false);
                }
                if (source != add) {
                    add.setSelected(false);
                }
                if (source != substract) {
                    substract.setSelected(false);
                }
                if (source != intersection) {
                    intersection.setSelected(false);
                }
            } else if (source == oval || source == rectangle) {
                if (source != oval) {
                    oval.setSelected(false);
                }
                if (source != rectangle) {
                    rectangle.setSelected(false);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == invert) {
            selectPanelInterface.invertSelection();
        }
    }
}

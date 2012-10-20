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

package cz.mgn.collabdesktop.room.view.panels.leftpanel.toolspanel;

import cz.mgn.collabdesktop.room.model.paintengine.PaintEngine;
import cz.mgn.collabdesktop.room.model.paintengine.ToolInfoInterface;
import cz.mgn.collabdesktop.room.view.panels.leftpanel.ToolOptionsPaneInterface;
import cz.mgn.collabdesktop.utils.gui.iconComponent.IconButton;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class ToolsPanel extends JPanel implements ActionListener {

    protected PaintEngine paintEngine;
    protected ToolOptionsPaneInterface toolOptions;
    //
    protected JPanel toolsInsdePanel = null;

    public ToolsPanel(PaintEngine paintEngine, ToolOptionsPaneInterface toolOptions) {
        this.paintEngine = paintEngine;
        this.toolOptions = toolOptions;
        initComponents();
    }

    protected void initComponents() {
        setPreferredSize(new Dimension(100, 30 + 2 * 32));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 1;

        toolsInsdePanel = new JPanel();
        int n = 4;
        int wGap = 8;
        toolsInsdePanel.setPreferredSize(new Dimension((n + 1) * wGap + n * 32, 20));
        toolsInsdePanel.setLayout(new FlowLayout(FlowLayout.CENTER, wGap, 10));
        add(toolsInsdePanel, c);
    }

    public void initTools() {
        ArrayList<ToolInfoInterface> tools = paintEngine.getToolsList();
        for (ToolInfoInterface tool : tools) {
            addTool(tool);
        }
        if (tools.size() > 0) {
            setTool(tools.get(0));
        }
    }

    protected void addTool(ToolInfoInterface tool) {
        ToolButton button = new ToolButton(tool);
        button.addActionListener(this);
        toolsInsdePanel.add(button);
    }

    protected void setTool(ToolInfoInterface tool) {
        paintEngine.selectTool(tool);
        toolOptions.setToolOptionsPanel(tool.getToolPanel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof ToolButton) {
            ToolButton btt = (ToolButton) source;
            setTool(btt.getTool());
        }
    }

    protected class ToolButton extends IconButton {

        protected ToolInfoInterface tool = null;

        public ToolButton(ToolInfoInterface tool) {
            super("button_32", new ImageIcon(tool.getToolIcon()));
            this.tool = tool;
            setToolTipText(tool.getToolName());
        }

        public ToolInfoInterface getTool() {
            return tool;
        }
    }
}

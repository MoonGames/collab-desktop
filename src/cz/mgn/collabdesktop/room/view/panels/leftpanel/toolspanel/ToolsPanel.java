/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author indy
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
        setPreferredSize(new Dimension(100, 30 + 3 * 32));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 1;

        toolsInsdePanel = new JPanel();
        int n = 5;
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

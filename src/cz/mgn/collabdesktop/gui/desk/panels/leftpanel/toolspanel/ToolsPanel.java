/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.leftpanel.toolspanel;

import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.tools.text.TextTool;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.tools.latex.LatexTool;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.tools.paintbucket.PaintBucketTool;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.ForToolInterface;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.PaintingInterface;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.ToolOptionsPaneInterface;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.toolspanel.extra.brushs.BrushPanel;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.Tool;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.tools.*;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.tools.brushable.BrushTool;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.tools.brushable.GeometryTool;
import cz.mgn.collabdesktop.utils.gui.button.IconButton;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 *   @author indy
 */
public class ToolsPanel extends JPanel implements ActionListener {

    protected PaintingInterface painting;
    protected ToolOptionsPaneInterface toolOptions;
    protected ForToolInterface forToolInterface;
    //
    protected JPanel toolsInsdePanel = null;

    public ToolsPanel(PaintingInterface painting, ToolOptionsPaneInterface toolOptions, ForToolInterface forToolInterface) {
        this.painting = painting;
        this.toolOptions = toolOptions;
        this.forToolInterface = forToolInterface;
        initComponents();
    }

    protected void initComponents() {
        setPreferredSize(new Dimension(100, 84));
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
        BrushPanel brushPanel = new BrushPanel();

        Tool brush = new BrushTool(forToolInterface, brushPanel);
        setTool(brush);
        addTool(brush);
        addTool(new GeometryTool(forToolInterface, brushPanel));
        addTool(new ColorPicker(forToolInterface));
        addTool(new ClearTool(forToolInterface));
        addTool(new PaintBucketTool(forToolInterface));
        addTool(new SelectTool(forToolInterface));
        addTool(new PictureTool(forToolInterface));
        addTool(new TextTool(forToolInterface));
        addTool(new LatexTool(forToolInterface));
        addTool(new DiagramTool(forToolInterface));
    }

    protected void addTool(Tool tool) {
        ToolButton button = new ToolButton(tool);
        button.addActionListener(this);
        toolsInsdePanel.add(button);
    }

    protected void setTool(Tool tool) {
        painting.setTool(tool);
        toolOptions.setToolOptionsPanel(tool.getToolOptionsPanel());
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

        protected Tool tool = null;

        public ToolButton(Tool tool) {
            super("button_32", new ImageIcon(tool.getToolIcon()));
            this.tool = tool;
            setToolTipText(tool.getToolName());
        }

        public Tool getTool() {
            return tool;
        }
    }
}

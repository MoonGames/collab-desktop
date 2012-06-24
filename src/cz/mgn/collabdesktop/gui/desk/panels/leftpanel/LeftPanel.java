/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.leftpanel;

import cz.mgn.collabdesktop.gui.desk.DeskInterface;
import cz.mgn.collabdesktop.gui.desk.executor.CommandExecutor;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.colorpanel.ColorPanel;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.layerspanel.LayersPanel;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.tooloptionspanel.ToolOptionsPanel;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.toolspanel.ToolsPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 *  @author indy
 */
public class LeftPanel extends JPanel implements ComponentListener, ToolOptionsPaneInterface, ForToolInterface {

    protected CommandExecutor executor = null;
    protected PaintingInterface paintingInterface = null;
    protected DeskInterface desk = null;
    //
    protected ColorPanel colorPanel = null;
    protected ToolsPanel toolsPanel = null;
    protected ToolOptionsPanel toolOptionsPanel = null;
    protected LayersPanel layersPanel = null;

    public LeftPanel(CommandExecutor executor, PaintingInterface paintingInterface, DeskInterface desk) {
        this.executor = executor;
        this.paintingInterface = paintingInterface;
        this.desk = desk;
        initComponents();
    }

    protected void initComponents() {
        //setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1f;
        c.gridx = 0;
        c.gridy = 0;

        c.insets = new Insets(10, 10, 10, 0);
        colorPanel = new ColorPanel(paintingInterface, desk);
        c.weighty = 0f;
        add(colorPanel, c);

        c.insets = new Insets(0, 10, 10, 0);

        toolsPanel = new ToolsPanel(paintingInterface, this, this);
        c.weighty = 0f;
        c.gridy++;
        add(toolsPanel, c);
        toolOptionsPanel = new ToolOptionsPanel();
        c.weighty = 0.6f;
        c.gridy++;
        add(toolOptionsPanel, c);
        layersPanel = new LayersPanel(executor, paintingInterface, desk);
        c.weighty = 0.4f;
        c.gridy++;
        add(layersPanel, c);

        toolsPanel.initTools();
    }

    protected void recountSizes() {
        int width = getWidth() - 20;
        colorPanel.setPreferredSize(new Dimension(width, colorPanel.getPreferredSize().height));
        toolsPanel.setPreferredSize(new Dimension(width, toolsPanel.getPreferredSize().height));
        toolOptionsPanel.setPreferredSize(new Dimension(width, toolOptionsPanel.getPreferredSize().height));
        layersPanel.setPreferredSize(new Dimension(width, layersPanel.getPreferredSize().height));
    }

    @Override
    public void componentResized(ComponentEvent e) {
        //recountSizes();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void setToolOptionsPanel(JPanel panel) {
        toolOptionsPanel.setTollOptionsPanel(panel);
    }

    @Override
    public int pickColor(int x, int y) {
        return paintingInterface.pickColor(x, y);
    }

    @Override
    public void setColor(int color) {
        colorPanel.setColor(new Color(color));
    }

    @Override
    public int getPaintingWidth() {
        return paintingInterface.getPaintingWidth();
    }

    @Override
    public int getPaintingHeight() {
        return paintingInterface.getPaintingHeight();
    }

    @Override
    public BufferedImage getPaintingImage() {
        return paintingInterface.getPaintingImage();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel;

import cz.mgn.collabdesktop.gui.desk.DeskInterface;
import cz.mgn.collabdesktop.gui.desk.executor.CommandExecutor;
import cz.mgn.collabdesktop.gui.desk.panels.downpanel.interfaces.PaintingSystemInterface;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.PaintingInterface;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.infopanel.InfoPanel;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintingpanel.PaintingPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 *
 * @author indy
 */
public class MiddlePanel extends JPanel implements ComponentListener, ScrollInterface, MouseListener, MouseMotionListener, MouseWheelListener {

    protected PaintingPanel paintingPanel = null;
    protected JScrollPane paintingPanelScrollPane = null;
    protected Point cursor = null;
    protected int mouseLastButton = 0;
    protected int showXCoord = 0;
    protected int showYCoord = 0;

    public MiddlePanel(CommandExecutor executor, DeskInterface desk) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BorderLayout());

        paintingPanel = new PaintingPanel(executor, desk);
        paintingPanelScrollPane = new JScrollPane(paintingPanel);
        paintingPanelScrollPane.setBorder(null);
        paintingPanelScrollPane.setWheelScrollingEnabled(false);
        add(paintingPanelScrollPane, BorderLayout.CENTER);
        paintingPanel.addComponentListener(this);

        paintingPanel.addMouseListener(this);
        paintingPanel.addMouseMotionListener(this);
        paintingPanel.addMouseWheelListener(this);

        InfoPanel infoPanel = new InfoPanel();
        paintingPanel.setInfoInterface(infoPanel);
        add(infoPanel, BorderLayout.SOUTH);

    }

    public PaintingInterface getPaintingInterface() {
        return paintingPanel;
    }

    public PaintingSystemInterface getPaintingSavableInterface() {
        return paintingPanel;
    }

    public void dismiss() {
        paintingPanel.dismiss();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        JScrollBar bar = paintingPanelScrollPane.getHorizontalScrollBar();
        int showXCoordL = showXCoord;
        if (showXCoord < 0) {
            showXCoordL = paintingPanel.getWidth() / 2;
        }
        int valueX = -paintingPanelScrollPane.getViewport().getWidth() / 2;
        valueX += showXCoordL;
        valueX = Math.min(bar.getMaximum(), valueX);
        valueX = Math.max(bar.getMinimum(), valueX);
        bar.setValue(valueX);

        bar = paintingPanelScrollPane.getVerticalScrollBar();
        int showYCoordL = showYCoord;
        if (showYCoord < 0) {
            showYCoordL = paintingPanel.getHeight() / 2;
        }
        int valueY = -paintingPanelScrollPane.getViewport().getHeight() / 2;
        valueY += showYCoordL;
        valueY = Math.min(bar.getMaximum(), valueY);
        valueY = Math.max(bar.getMinimum(), valueY);
        bar.setValue(valueY);
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
    public void resetScrollPoint() {
        showXCoord = -1;
        showYCoord = -1;
    }

    @Override
    public void setScrollPoint(int x, int y) {
        showXCoord = x;
        showYCoord = y;
    }

    @Override
    public void dragScroll(int x, int y) {
        JScrollBar bar = paintingPanelScrollPane.getHorizontalScrollBar();
        int valueX = bar.getValue() + x;
        valueX = Math.min(bar.getMaximum(), valueX);
        valueX = Math.max(bar.getMinimum(), valueX);
        bar.setValue(valueX);

        bar = paintingPanelScrollPane.getVerticalScrollBar();
        int valueY = bar.getValue() + y;
        valueY = Math.min(bar.getMaximum(), valueY);
        valueY = Math.max(bar.getMinimum(), valueY);
        bar.setValue(valueY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseLastButton = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseLastButton = e.getButton();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        resetScrollPoint();
        cursor = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setScrollPoint(e.getXOnScreen() - paintingPanel.getLocationOnScreen().x, e.getYOnScreen() - paintingPanel.getLocationOnScreen().y);
        if (mouseLastButton == MouseEvent.BUTTON2 && cursor != null) {
            int dx = cursor.x - e.getXOnScreen();
            int dy = cursor.y - e.getYOnScreen();
            dragScroll(dx, dy);
        }
        cursor = e.getLocationOnScreen();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        setScrollPoint(e.getXOnScreen() - paintingPanel.getLocationOnScreen().x, e.getYOnScreen() - paintingPanel.getLocationOnScreen().y);
        cursor = e.getLocationOnScreen();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown()) {
            float factor = 0.25f * e.getWheelRotation();
            paintingPanel.zoom(factor);
        }
    }
}

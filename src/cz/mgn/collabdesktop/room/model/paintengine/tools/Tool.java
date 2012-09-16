/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabdesktop.room.model.paintengine.Canvas;
import cz.mgn.collabdesktop.room.model.paintengine.PaintEngineInterface;
import cz.mgn.collabdesktop.room.model.paintengine.ToolInfoInterface;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public abstract class Tool implements ToolInfoInterface {

    /**
     * set color what this tool should use
     * 
     * @param color ARGB color
     */
    public abstract void setColor(int color);

    /**
     * set painting canvas
     * 
     * @param canvas painting canvas, can be null
     */
    public abstract void setCanvas(Canvas canvas);
    
    /**
     * set paint engine interface for commanding paint engine
     */
    public abstract void setPaintEngineInterface(PaintEngineInterface paintEngineInterface);

    /**
     * inform tool about mouse event (on canvas)
     */
    public abstract void mouseEvent(CollabPanelMouseEvent e);

    /**
     * inform tool about key event (on canvas)
     */
    public abstract void keyEvent(CollabPanelKeyEvent e);

    @Override
    public abstract BufferedImage getToolIcon();

    @Override
    public abstract BufferedImage getToolIcon(int width, int height);

    @Override
    public abstract String getToolName();

    @Override
    public abstract String getToolDescription();

    @Override
    public abstract JPanel getToolPanel();
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.leftpanel;

import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.Tool;
import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public interface PaintingInterface {

    public void setColor(int color);

    public void setTool(Tool tool);

    public void setPaintingLayer(int layer);

    public int pickColor(int x, int y);

    public int getPaintingWidth();

    public int getPaintingHeight();
    
    public BufferedImage getPaintingImage();
}

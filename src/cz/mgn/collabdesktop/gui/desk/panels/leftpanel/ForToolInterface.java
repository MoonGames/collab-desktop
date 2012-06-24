/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.leftpanel;

import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public interface ForToolInterface {

    public int pickColor(int x, int y);
    
    public BufferedImage getPaintingImage();

    public void setColor(int color);

    public int getPaintingWidth();

    public int getPaintingHeight();
}

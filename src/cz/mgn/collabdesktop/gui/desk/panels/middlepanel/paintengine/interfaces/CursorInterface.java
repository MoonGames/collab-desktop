/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces;

import java.awt.image.BufferedImage;

/**
 *
 *    @author indy
 */
public interface CursorInterface {

    public void setToolCursorIcon(BufferedImage toolCursorIcon, String name);
    
    public void setBrushCursor(BufferedImage brushCursor);
}

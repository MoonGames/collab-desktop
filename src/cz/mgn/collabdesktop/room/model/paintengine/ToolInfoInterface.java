/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine;

import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public interface ToolInfoInterface {

    /**
     * returns standart tool icon
     */
    public BufferedImage getToolIcon();

    /**
     * returns tool icon with selected size
     *
     * if icon in this size doesnt exist returns scaled icon
     */
    public BufferedImage getToolIcon(int width, int height);

    /**
     * returns tool name
     */
    public String getToolName();

    /**
     * returns tool description
     */
    public String getToolDescription();

    /**
     * returns tool panel (with tool properties)
     */
    public JPanel getToolPanel();
}

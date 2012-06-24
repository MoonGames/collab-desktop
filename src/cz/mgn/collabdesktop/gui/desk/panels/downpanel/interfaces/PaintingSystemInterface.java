/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.downpanel.interfaces;

import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public interface PaintingSystemInterface {

    public BufferedImage getSavableImage();

    public float zoom(float zoom);

    public float zoomTo100();
}

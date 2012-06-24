/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.executor;

import java.awt.image.BufferedImage;

/**
 *
 *    @author indy
 */
public interface Paintable {

    public void paint(BufferedImage change, int identificator, int layerID, int x, int y);

    public void remove(BufferedImage change, int identificator, int layerID, int x, int y);

    public void setLayersOrder(int[] layersOrderIDs);

    public void setResolution(int width, int height);
}

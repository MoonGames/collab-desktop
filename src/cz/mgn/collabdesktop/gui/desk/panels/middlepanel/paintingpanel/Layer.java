/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintingpanel;

import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.utils.ImageProcessor;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 *    @author indy
 */
public class Layer {

    protected int id = 0;
    protected BufferedImage image = null;

    public Layer(int id, int width, int height) {
        this.id = id;
        image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
    }

    public int getID() {
        return id;
    }

    public void resetID(int id) {
        this.id = id;
    }

    public void setResolution(int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) newImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        image = newImage;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void addChange(BufferedImage change, int x, int y) {
        ImageProcessor.addToImage(image, change, x, y, x, y, change.getWidth(), change.getHeight());
    }

    public void removeChange(BufferedImage change, int x, int y) {
        ImageProcessor.removeFromImage(image, change, x, y, x, y, change.getWidth(), change.getHeight());
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintingpanel;

import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.Painting;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces.Update;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author indy
 */
public class Cache implements Update {

    protected Painting painting = null;
    protected UpdateCacheInterface updateInterface = null;
    protected ArrayList<Layer> layers = new ArrayList<Layer>();
    protected BufferedImage image = null;
    protected UpdateRect updatePaint = new UpdateRect();
    protected UpdateRect updateNetwork = new UpdateRect();
    protected int width = 1;
    protected int height = 1;

    public Cache(UpdateCacheInterface updateInterface) {
        this.updateInterface = updateInterface;
    }

    public void init(Painting painting) {
        this.painting = painting;
    }

    protected void repaintCache() {
        int[] rect1 = updatePaint.getRect();
        updatePaint.reset();;
        int[] rect2 = updateNetwork.getRect();
        updateNetwork.reset();
        if (rect1 != null || rect2 != null) {
            Graphics2D g = (Graphics2D) image.getGraphics();
            CacheUpdate cu = new CacheUpdate();
            if (rect1 != null) {
                cu.getUpdateRect1().add(rect1[0], rect1[1], rect1[2], rect1[3]);
                paintLayers(g, rect1[0], rect1[1], rect1[2], rect1[3]);
            }
            if (rect2 != null) {
                cu.getUpdateRect2().add(rect2[0], rect2[1], rect2[2], rect2[3]);
                paintLayers(g, rect2[0], rect2[1], rect2[2], rect2[3]);
            }
            g.dispose();
            cu.setImage(image);
            updateInterface.updateCache(cu);
        }
    }

    protected void paintLayers(Graphics2D g, int x, int y, int width, int height) {
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(x, y, width, height);
        g.setComposite(AlphaComposite.SrcOver);
        for (Layer layer : layers) {
            BufferedImage layerImage = layer.getImage();
            if (layer.getID() == painting.getLayerID()) {
                layerImage = painting.addLocalChanges(layerImage, x, y, width, height);
            }
            g.drawImage(layerImage, x, y, width + x, height + y, x, y, width + x, height + y, null);
        }
    }

    protected void testIfPaintingExists() {
        boolean any = false;
        for (int i = 0; i < layers.size(); i++) {
            if (layers.get(i).getID() == painting.getLayerID()) {
                any = true;
            }
        }
        if (!any) {
            painting.noLayer();
        }
    }

    protected int getLayerIndexByID(int layerID) {
        for (int i = 0; i < layers.size(); i++) {
            if (layers.get(i).getID() == layerID) {
                return i;
            }
        }
        return -1;
    }

    protected Layer getLayerByID(int layerID) {
        int index = getLayerIndexByID(layerID);
        if (index == -1) {
            return null;
        }
        return layers.get(index);
    }

    public void paint(BufferedImage change, int identificator, int layerID, int x, int y) {
        if (layerID == painting.getLayerID()) {
            painting.addReceived(identificator);
        }
        Layer layer = getLayerByID(layerID);
        if (layer != null) {
            layer.addChange(change, x, y);
        }
        updateNetwork.add(x, y, change.getWidth(), change.getHeight());
        repaintCache();
    }

    public void remove(BufferedImage change, int identificator, int layerID, int x, int y) {
        if (layerID == painting.getLayerID()) {
            painting.removeReceived(identificator);
        }
        Layer layer = getLayerByID(layerID);
        if (layer != null) {
            layer.removeChange(change, x, y);
        }
        updateNetwork.add(x, y, change.getWidth(), change.getHeight());
        repaintCache();
    }

    public void setLayersOrder(int[] layersOrderIDs) {
        ArrayList<Layer> layersNew = new ArrayList<Layer>();
        for (int i = 0; i < layersOrderIDs.length; i++) {
            Layer add = getLayerByID(layersOrderIDs[i]);
            if (add == null) {
                add = new Layer(layersOrderIDs[i], width, height);
            }
            layersNew.add(add);
        }
        layers = layersNew;
        updateNetwork.add(0, 0, width, height);
        testIfPaintingExists();
        repaintCache();
    }

    public void setPaintingLayer(int layer) {
        if (layer >= 0) {
            painting.setLayer(layer, width, height);
        } else {
            painting.noLayer();
        }
        updatePaint.add(0, 0, width, height);
        repaintCache();
    }

    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        for (Layer layer : layers) {
            layer.setResolution(width, height);
        }
        if (painting.getLayerID() >= 0) {
            painting.setLayer(painting.getLayerID(), width, height);
        }
        updateNetwork.add(0, 0, width, height);
        repaintCache();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void update(int x, int y, int width, int height) {
        updatePaint.add(x, y, width, height);
    }

    @Override
    public void repaint() {
        repaintCache();
    }

    public class CacheUpdate {

        protected BufferedImage image = null;
        protected UpdateRect updateRect1 = null;
        protected UpdateRect updateRect2 = null;

        public CacheUpdate() {
            updateRect1 = new UpdateRect();
            updateRect2 = new UpdateRect();
        }

        public void setImage(BufferedImage image) {
            this.image = image;
        }

        public BufferedImage getImage() {
            return image;
        }

        public UpdateRect getUpdateRect1() {
            return updateRect1;
        }

        public UpdateRect getUpdateRect2() {
            return updateRect2;
        }
    }

    public interface UpdateCacheInterface {

        public void updateCache(CacheUpdate cacheUpdate);
    }
}

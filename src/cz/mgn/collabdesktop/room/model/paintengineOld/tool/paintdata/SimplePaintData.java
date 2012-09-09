/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengineOld.tool.paintdata;

import cz.mgn.collabcanvas.interfaces.paintable.PaintData;
import cz.mgn.collabcanvas.interfaces.paintable.PaintImage;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author indy
 */
public class SimplePaintData implements PaintData, PaintImage {

    protected ArrayList<PaintImage> paintImages = new ArrayList<PaintImage>();
    protected ArrayList<Point> paintImagePoints;
    protected BufferedImage paintImageImage;
    protected boolean paintImageIsAdditional;

    public SimplePaintData(ArrayList<Point> paintImagePoints, BufferedImage paintImageImage, boolean paintImageIsAdditional) {
        paintImages.add(this);
        this.paintImagePoints = paintImagePoints;
        this.paintImageImage = paintImageImage;
        this.paintImageIsAdditional = paintImageIsAdditional;
    }

    @Override
    public ArrayList<PaintImage> getPaintImages() {
        return paintImages;
    }

    @Override
    public ArrayList<Point> getApplyPoints() {
        return paintImagePoints;
    }

    @Override
    public BufferedImage getImage() {
        return paintImageImage;
    }

    @Override
    public boolean isAddChange() {
        return paintImageIsAdditional;
    }
}

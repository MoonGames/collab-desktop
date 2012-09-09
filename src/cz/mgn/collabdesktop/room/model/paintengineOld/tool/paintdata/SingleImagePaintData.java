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
public class SingleImagePaintData implements PaintData, PaintImage {

    protected ArrayList<PaintImage> paintImages = new ArrayList<PaintImage>();
    protected ArrayList<Point> paintImagePoints = new ArrayList<Point>();
    protected BufferedImage paintImageImage;
    protected boolean paintImageIsAdditional;

    public SingleImagePaintData(Point paintImagePoint, BufferedImage paintImageImage, boolean paintImageIsAdditional) {
        paintImages.add(this);
        paintImagePoints.add(paintImagePoint);
        this.paintImageImage = paintImageImage;
        this.paintImageIsAdditional = paintImageIsAdditional;
    }

    public SingleImagePaintData(BufferedImage paintImageImage, boolean paintImageIsAdditional) {
        this(new Point(0, 0), paintImageImage, paintImageIsAdditional);
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

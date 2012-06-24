/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author indy
 */
public interface Paint {

    public void paint(PaintData paintData);

    public void setCursor(BufferedImage cursor);

    public void repaintToolImage();

    public class PaintData {

        protected ArrayList<PaintImage> paintImages = new ArrayList<PaintImage>();

        public PaintData() {
        }

        public PaintData(PaintImage paintImage) {
            paintImages.add(paintImage);
        }

        public PaintData(ArrayList<PaintImage> paintImages) {
            paintImages.addAll(paintImages);
        }

        public void addPaintImage(PaintImage paintImage) {
            paintImages.add(paintImage);
        }

        public ArrayList<PaintImage> getPaintImages() {
            return paintImages;
        }
    }

    public class PaintImage {

        protected boolean add = true;
        protected BufferedImage paintImage;
        protected ArrayList<Point> points = new ArrayList<Point>();

        public PaintImage(boolean add, BufferedImage paintImage, Point point) {
            this.add = add;
            this.paintImage = paintImage;
            this.points.add(point);
        }

        public PaintImage(boolean add, BufferedImage paintImage, ArrayList<Point> points) {
            this.add = add;
            this.paintImage = paintImage;
            this.points.addAll(points);
        }

        public boolean isAdd() {
            return add;
        }

        public BufferedImage getPaintImage() {
            return paintImage;
        }

        public ArrayList<Point> getPoints() {
            return points;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintingpanel;

import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.Painting;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 *   @author indy
 */
public class Controller implements MouseListener, MouseMotionListener {

    protected Painting painting = null;
    protected int shiftX = 0;
    protected int shiftY = 0;
    protected float zoom = 1f;
    protected int mouseLastButton = 0;

    public Controller(Painting painting) {
        this.painting = painting;
    }

    public void setZoom(float zoom) {
        synchronized (this) {
            this.zoom = zoom;
        }
    }

    public void setShift(int shiftX, int shiftY) {
        synchronized (this) {
            this.shiftX = shiftX;
            this.shiftY = shiftY;
        }
    }

    protected Point shiftPoint(int x, int y) {
        return new Point(x - shiftX, y - shiftY);
    }

    protected Point zoomPoint(int x, int y) {
        return new Point((int) (x / zoom), (int) (y / zoom));
    }

    protected Point transformPoint(int x, int y) {
        Point result = shiftPoint(x, y);
        result = zoomPoint(result.x, result.y);
        return result;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        synchronized (this) {
            if (mouseLastButton != MouseEvent.BUTTON2) {
                Point point = transformPoint(e.getX(), e.getY());
                painting.mouseDragged(point.x, point.y, e.isShiftDown(), e.isControlDown());
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        synchronized (this) {
            Point point = transformPoint(e.getX(), e.getY());
            painting.mouseMoved(point.x, point.y, e.isShiftDown(), e.isControlDown());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        synchronized (this) {
            mouseLastButton = e.getButton();
            if (mouseLastButton != MouseEvent.BUTTON2) {
                Point point = transformPoint(e.getX(), e.getY());
                painting.mousePressed(point.x, point.y, e.isShiftDown(), e.isControlDown());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        synchronized (this) {
            mouseLastButton = e.getButton();
            if (mouseLastButton == MouseEvent.BUTTON1) {
                Point point = transformPoint(e.getX(), e.getY());
                painting.mouseReleased(point.x, point.y, e.isShiftDown(), e.isControlDown());
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

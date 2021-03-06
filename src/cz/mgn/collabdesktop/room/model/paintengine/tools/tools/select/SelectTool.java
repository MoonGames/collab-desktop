/*
 * Collab desktop - Software for shared drawing via internet in real-time
 * Copyright (C) 2012 Martin Indra <aktive@seznam.cz>
 *
 * This file is part of Collab desktop.
 *
 * Collab desktop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Collab desktop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Collab desktop.  If not, see <http://www.gnu.org/licenses/>.
 */

package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.select;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.selectionable.SelectionUpdate;
import cz.mgn.collabdesktop.room.model.paintengine.Canvas;
import cz.mgn.collabdesktop.room.model.paintengine.PaintEngineInterface;
import cz.mgn.collabdesktop.room.model.paintengine.tools.Tool;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.ToolsUtils;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class SelectTool extends Tool implements SelectPanelInterface {

    protected SelectPanel toolPanel;
    protected BufferedImage toolIcon;
    protected Canvas canvas = null;
    //
    protected int x1 = -1;
    protected int y1 = -1;
    protected int x2 = -1;
    protected int y2 = -1;

    public SelectTool() {
        toolPanel = new SelectPanel(this);
        //TODO: new SimpleMouseCursor(ImageUtil.loadImageFromResources("/resources/tools/select-cursor.gif"));
        toolIcon = ImageUtil.loadImageFromResources("/resources/tools/select-icon.png");
        toolPanel = new SelectPanel(this);
    }

    protected BufferedImage generateSelectionImage() {
        Rectangle r = countRect();
        BufferedImage selection = new BufferedImage(r.width, r.height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = selection.getGraphics();
        g.setColor(Color.BLACK);
        if (toolPanel.getSelectionShape() == SelectPanel.SHAPE_OVAL) {
            g.fillOval(0, 0, selection.getWidth(), selection.getHeight());
        } else {
            g.fillRect(0, 0, selection.getWidth(), selection.getHeight());
        }
        g.dispose();
        return selection;
    }

    protected void mousePressed(int x, int y, boolean control) {
        x1 = x;
        y1 = y;
        set(x, y);
    }

    protected void mouseDragged(int x, int y) {
        set(x, y);
    }

    protected void mouseReleased(int x, int y) {
        if (x1 >= 0) {
            Rectangle r = countRect();
            ArrayList<Point> points = new ArrayList<Point>();
            points.add(new Point(r.x, r.y));
            if (canvas != null) {
                canvas.getSelectionable().select(new SimpleSelectionUpdate(toolPanel.getSelectionType(), new Point(r.x, r.y), 1f, generateSelectionImage()));
            }
        }
        reset();
    }

    protected Rectangle countRect() {
        if (canvas != null) {
            int xx1 = Math.min(x1, x2);
            int xx2 = Math.max(x1, x2);
            if (xx1 == xx2) {
                xx2++;
            }
            int yy1 = Math.min(y1, y2);
            int yy2 = Math.max(y1, y2);
            if (yy1 == yy2) {
                yy2++;
            }
            return new Rectangle(xx1, yy1, xx2 - xx1, yy2 - yy1);
        }
        return new Rectangle();
    }

    protected void set(int x, int y) {
        if (canvas != null) {
            x2 = x;
            y2 = y;
            canvas.getVisible().setToolImage(getToolImage());
        } else {
            reset();
        }
    }

    protected void reset() {
        x1 = -1;
        x2 = -1;
        y1 = -1;
        y2 = -1;
        if (canvas != null) {
            canvas.getVisible().setToolImage(getToolImage());
        }
    }

    protected SelectToolImage getToolImage() {
        if (x1 >= 0) {
            Rectangle r = countRect();
            return new SelectToolImage(toolPanel.getSelectionShape(), r.width, r.height, x1 < x2, y1 < y2);
        }
        return null;
    }

    @Override
    public void setColor(int color) {
    }

    @Override
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mouseEvent(CollabPanelMouseEvent e) {
        int x = e.getEventCoordinates().x;
        int y = e.getEventCoordinates().y;
        switch (e.getEventType()) {
            case CollabPanelMouseEvent.TYPE_PRESS:
                mousePressed(x, y, e.isControlDown());
                break;
            case CollabPanelMouseEvent.TYPE_RELEASE:
                mouseReleased(x, y);
                break;
            case CollabPanelMouseEvent.TYPE_DRAG:
                mouseDragged(x, y);
                break;
        }
    }

    @Override
    public void keyEvent(CollabPanelKeyEvent e) {
    }

    @Override
    public BufferedImage getToolIcon() {
        return toolIcon;
    }

    @Override
    public BufferedImage getToolIcon(int width, int height) {
        return ToolsUtils.transformToolIcon(toolIcon, width, height);
    }

    @Override
    public String getToolName() {
        return "Select";
    }

    @Override
    public String getToolDescription() {
        return "Press CTRL+A to select all.";
    }

    @Override
    public JPanel getToolPanel() {
        return toolPanel;
    }

    @Override
    public void invertSelection() {
        if (canvas != null) {
            BufferedImage selection = new BufferedImage(canvas.getPaintable().getWidth(),
                    canvas.getPaintable().getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = selection.getGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, selection.getWidth(), selection.getHeight());
            canvas.getSelectionable().select(new SimpleSelectionUpdate(SelectionUpdate.MODE_XOR,
                    1f, selection));
        }
    }

    @Override
    public void setPaintEngineInterface(PaintEngineInterface paintEngineInterface) {
    }
}

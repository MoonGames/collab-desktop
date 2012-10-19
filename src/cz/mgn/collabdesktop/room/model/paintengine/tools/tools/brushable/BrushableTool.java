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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabdesktop.room.model.paintengine.Canvas;
import cz.mgn.collabdesktop.room.model.paintengine.tools.Tool;
import cz.mgn.collabdesktop.room.model.paintengine.tools.paintdata.SimplePaintData;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.brush.Brush;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.panel.BrushPanel;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public abstract class BrushableTool extends Tool implements BrushEventsListener {

    protected Canvas canvas = null;
    protected Brush selectedBrush = null;
    protected int color = 0xff000000;
    protected JPanel toolPanel;

    public BrushableTool() {
        init();
    }

    private void init() {
        toolPanel = new JPanel();
        toolPanel.setLayout(new BorderLayout());
        toolPanel.add(new BrushPanel(this));
    }

    /**
     * paint (or earse) line
     *
     * @param x1 start X position
     * @param y1 start Y position
     * @param x2 end X position
     * @param y2 end Y position
     * @param add if true paint line if false earse line
     */
    protected void paintLine(int x1, int y1, int x2, int y2, boolean add) {
        if (selectedBrush != null && canvas != null) {
            Brush.PaintBrush paintBrush = selectedBrush.paintLine(x1, y1, x2, y2);
            SimplePaintData data = new SimplePaintData(paintBrush.getPoints(), paintBrush.paint, add);
            canvas.getPaintable().paint(data);
        }
    }

    @Override
    public void setColor(int color) {
        this.color = color;
        if (selectedBrush != null) {
            selectedBrush.setColor(color);
        }
    }

    @Override
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        if (canvas != null) {
            if (selectedBrush != null) {
                canvas.getVisible().setToolCursor(new BrushToolCursor(selectedBrush));
            }
        }
    }

    @Override
    public JPanel getToolPanel() {
        return toolPanel;
    }

    @Override
    public void brushSelected(Brush brush) {
        selectedBrush = brush;
        if (selectedBrush != null) {
            selectedBrush.setColor(color);
            if (canvas != null) {
                canvas.getVisible().setToolCursor(new BrushToolCursor(selectedBrush));
            }
        } else {
            if (canvas != null) {
                canvas.getVisible().setToolCursor(null);
            }
        }
    }

    @Override
    public void brushScaled() {
        if (selectedBrush != null && canvas != null) {
            canvas.getVisible().setToolCursor(new BrushToolCursor(selectedBrush));
        }
    }

    @Override
    public abstract void mouseEvent(CollabPanelMouseEvent e);

    @Override
    public abstract void keyEvent(CollabPanelKeyEvent e);

    @Override
    public abstract BufferedImage getToolIcon();

    @Override
    public abstract BufferedImage getToolIcon(int width, int height);

    @Override
    public abstract String getToolName();

    @Override
    public abstract String getToolDescription();
}

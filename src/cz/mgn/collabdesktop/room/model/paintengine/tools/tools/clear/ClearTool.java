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

package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.clear;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.paintable.PaintData;
import cz.mgn.collabdesktop.room.model.paintengine.Canvas;
import cz.mgn.collabdesktop.room.model.paintengine.PaintEngineInterface;
import cz.mgn.collabdesktop.room.model.paintengine.tools.Tool;
import cz.mgn.collabdesktop.room.model.paintengine.tools.paintdata.SingleImagePaintData;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.ToolsUtils;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class ClearTool extends Tool {

    protected BufferedImage toolIcon;
    protected int color = 0xff000000;
    protected Canvas canvas = null;

    public ClearTool() {
        toolIcon = ImageUtil.loadImageFromResources("/resources/tools/clear-icon.png");
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void setPaintEngineInterface(PaintEngineInterface paintEngineInterface) {
    }

    @Override
    public void mouseEvent(CollabPanelMouseEvent e) {
        if (e.getEventType() == CollabPanelMouseEvent.TYPE_PRESS) {
            if (canvas != null) {
                int w = canvas.getPaintable().getWidth();
                int h = canvas.getPaintable().getHeight();
                Color c = null;
                if (!e.isControlDown()) {
                    c = new Color(color);
                }
                canvas.getPaintable().paint(generateFillData(c, 0, 0, w, h));
            }
        }
    }

    /**
     * Generate single color paint data or earse data. If color isn't specified
     * data is in earse mode otherwise in paint mode.
     *
     * @param color color of data, null mean 100% earse
     * @param x X coordinate of data
     * @param y Y coordinate of data
     * @param width width of data
     * @param height height of data
     */
    public static PaintData generateFillData(Color color, int x, int y,
            int width, int height) {
        BufferedImage fill = new BufferedImage(width, height,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = fill.getGraphics();
        if (color == null) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(color);
        }
        g.fillRect(0, 0, fill.getWidth(), fill.getHeight());
        g.dispose();
        return new SingleImagePaintData(fill, color != null);
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
        return "Clearer";
    }

    @Override
    public String getToolDescription() {
        return "Press CTRL for earsing.";
    }

    @Override
    public JPanel getToolPanel() {
        return null;
    }
}

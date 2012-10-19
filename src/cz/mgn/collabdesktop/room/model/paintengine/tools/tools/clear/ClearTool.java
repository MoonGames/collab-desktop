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
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.clear;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
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
 * @author indy
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
                BufferedImage fill = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics g = fill.getGraphics();
                if (e.isControlDown()) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(new Color(color));
                }
                g.fillRect(0, 0, fill.getWidth(), fill.getHeight());
                g.dispose();
                canvas.getPaintable().paint(new SingleImagePaintData(fill, !e.isControlDown()));
            }
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

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
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.latex;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabdesktop.room.model.paintengine.Canvas;
import cz.mgn.collabdesktop.room.model.paintengine.PaintEngineInterface;
import cz.mgn.collabdesktop.room.model.paintengine.tools.Tool;
import cz.mgn.collabdesktop.room.model.paintengine.tools.paintdata.SingleImagePaintData;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.ToolsUtils;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class LatexTool extends Tool implements LatexImageListener {

    protected BufferedImage toolIcon;
    protected BufferedImage source = null;
    protected BufferedImage toolApplyImage = null;
    protected LatexPanel toolPanel;
    protected int color = 0xFF000000;
    protected Canvas canvas = null;
    protected LatexToolImage toolImage = null;

    public LatexTool() {
        toolIcon = ImageUtil.loadImageFromResources("/resources/tools/latex-icon.png");
        toolPanel = new LatexPanel(this);
    }

    protected void generateToolApplyImage() {
        if (source == null) {
            toolApplyImage = null;
        } else {
            toolApplyImage = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            for (int x = 0; x < toolApplyImage.getWidth(); x++) {
                for (int y = 0; y < toolApplyImage.getHeight(); y++) {
                    int c = source.getAlphaRaster().getPixel(x, y, new int[1])[0] << 24;
                    c += (color & 0x00ffffff);
                    toolApplyImage.setRGB(x, y, c);
                }
            }
        }
    }

    @Override
    public void setColor(int color) {
        this.color = color;
        generateToolApplyImage();
    }

    @Override
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        if (toolImage != null && canvas != null) {
            canvas.getVisible().setToolImage(toolImage);
        }
    }

    @Override
    public void setPaintEngineInterface(PaintEngineInterface paintEngineInterface) {
    }

    @Override
    public void mouseEvent(CollabPanelMouseEvent e) {
        if (e.getEventType() == CollabPanelMouseEvent.TYPE_PRESS) {
            if (toolApplyImage != null && canvas != null) {
                int x = e.getEventCoordinates().x - (toolApplyImage.getWidth() / 2);
                int y = e.getEventCoordinates().y - (toolApplyImage.getHeight() / 2);
                SingleImagePaintData paintData = new SingleImagePaintData(new Point(x, y), toolApplyImage, !e.isControlDown());
                canvas.getPaintable().paint(paintData);
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
        return "LaTeX math";
    }

    @Override
    public String getToolDescription() {
        return "Click to canvas for draw rendered math.";
    }

    @Override
    public JPanel getToolPanel() {
        return toolPanel;
    }

    @Override
    public void setLatexImage(BufferedImage image) {
        this.source = image;
        generateToolApplyImage();
        toolImage = new LatexToolImage(source);
        if (canvas != null) {
            canvas.getVisible().setToolImage(toolImage);
        }
    }
}

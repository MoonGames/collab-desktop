/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine;

import cz.mgn.collabcanvas.canvas.CollabCanvas;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.CanvasInterface;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.Tool;
import java.awt.Color;

/**
 *
 * @author indy
 */
public class PaintEngine {

    protected Color color = Color.BLACK;
    protected Tool tool;
    protected CollabCanvas canvas;

    public PaintEngine(CollabCanvas canvas) {
        this.canvas = canvas;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(int color) {
        this.color = new Color(color);
    }

    public void setTool(Tool tool) {
        if (this.tool != null) {
            this.tool.setPaint(null);
        }
        this.tool = tool;
        if (tool != null) {
            tool.setPaint(new CanvasInterface(canvas.getVisible(), canvas.getPaintable(), canvas.getSelectionable()));
            tool.setColor(color.getRGB());
        }
    }

    public void setCanvas(CollabCanvas canvas) {
        this.canvas = canvas;
        if (tool != null) {
            tool.setPaint(new CanvasInterface(canvas.getVisible(), canvas.getPaintable(), canvas.getSelectionable()));
        }
    }

    public CollabCanvas getCanvas() {
        return canvas;
    }
}

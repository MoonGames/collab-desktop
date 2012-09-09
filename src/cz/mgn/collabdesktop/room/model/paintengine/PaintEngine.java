/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine;

import cz.mgn.collabcanvas.canvas.CollabCanvas;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelListener;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabdesktop.room.model.paintengine.tools.Tool;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.BrushTool;
import java.util.ArrayList;

/**
 *
 * @author indy
 */
public class PaintEngine implements CollabPanelListener {

    protected CollabCanvas canvas;
    protected ArrayList<PaintEngineListener> listeners = new ArrayList<PaintEngineListener>();
    /**
     * instances of available tools
     */
    protected ArrayList<Tool> tools = new ArrayList<Tool>();
    /**
     * currently selected tool
     */
    protected Tool selectedTool = null;

    public PaintEngine() {
        init();
    }

    protected void init() {
        //TODO: fill list of tools
        tools.add(new BrushTool());
    }

    public void addListener(PaintEngineListener listener) {
        synchronized (listeners) {
            if (listeners.contains(listener)) {
                throw new IllegalStateException("This listeners is already set!");
            }
            listeners.add(listener);
        }
    }

    public void removeListener(PaintEngineListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    /**
     * returns list available tools
     */
    public ArrayList<ToolInfoInterface> getToolsList() {
        ArrayList<ToolInfoInterface> toolsI = new ArrayList<ToolInfoInterface>();
        for (Tool tool : tools) {
            toolsI.add((ToolInfoInterface) tool);
        }
        return toolsI;
    }

    /**
     * set which tool is currently used
     *
     * @param tool tool, can be null
     */
    public void selectTool(ToolInfoInterface tool) {
        selectTool((Tool) tool);
    }

    protected void selectTool(Tool tool) {
        if (tool == this.selectedTool) {
            return;
        }
        if (tool != null && !tools.contains(tool)) {
            throw new IllegalStateException("This tool is not available!");
        }
        selectedTool = tool;
        synchronized (listeners) {
            for (PaintEngineListener listener : listeners) {
                listener.selectedToolChanged(selectedTool);
            }
        }
    }

    /**
     * set color which engine show preferentially use
     *
     * @param color ARGB color
     */
    public void setColor(int color) {
        for (Tool tool : tools) {
            tool.setColor(color);
        }
    }

    /**
     * set painting canvas
     *
     * @param canvas painting canvas, can be null
     */
    public void setCanvas(CollabCanvas canvas) {
        if (this.canvas != null) {
            this.canvas.getListenable().removeListener(this);
        }
        Canvas cnv = null;
        if (canvas != null) {
            cnv = new Canvas(canvas.getVisible(), canvas.getPaintable(), canvas.getSelectionable());
        }
        for (Tool tool : tools) {
            tool.setCanvas(cnv);
        }
        canvas.getListenable().addListener(this);
    }

    /**
     * call if this paint engine is not usable any more
     */
    public void destroy() {
        if (this.canvas != null) {
            this.canvas.getListenable().removeListener(this);
        }
    }

    @Override
    public void keyEvent(CollabPanelKeyEvent e) {
        if (selectedTool != null) {
            selectedTool.keyEvent(e);
        }
    }

    @Override
    public void mouseEvent(CollabPanelMouseEvent e) {
        if (selectedTool != null) {
            selectedTool.mouseEvent(e);
        }
    }
}

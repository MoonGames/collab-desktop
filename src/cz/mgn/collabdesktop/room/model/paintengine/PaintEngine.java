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
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.clear.ClearTool;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.colorpicker.ColorPickerTool;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.latex.LatexTool;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.paintbucket.PaintBucketTool;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.select.SelectTool;
import java.util.ArrayList;

/**
 *
 * @author indy
 */
public class PaintEngine implements CollabPanelListener, PaintEngineInterface {

    protected CollabCanvas canvas;
    protected Canvas cnv;
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
        tools.add(new ColorPickerTool());
        tools.add(new SelectTool());
        tools.add(new ClearTool());
        tools.add(new PaintBucketTool());
        tools.add(new LatexTool());
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

        if (canvas != null) {
            canvas.getVisible().setMouseCursor(null);
            canvas.getVisible().setToolImage(null);
            canvas.getVisible().setToolCursor(null);
        }

        if (selectedTool != null) {
            selectedTool.setCanvas(null);
            selectedTool.setPaintEngineInterface(null);
        }
        selectedTool = tool;
        if (tool != null) {
            tool.setCanvas(cnv);
            tool.setPaintEngineInterface(this);
        }
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
    @Override
    public void setColor(int color) {
        for (Tool tool : tools) {
            tool.setColor(color);
        }
        synchronized (listeners) {
            for (PaintEngineListener listener : listeners) {
                listener.colorChanged(color);
            }
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
        this.canvas = canvas;
        cnv = null;
        if (canvas != null) {
            cnv = new Canvas(canvas.getVisible(), canvas.getPaintable(), canvas.getSelectionable());
        }
        if (selectedTool != null) {
            selectedTool.setCanvas(cnv);
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

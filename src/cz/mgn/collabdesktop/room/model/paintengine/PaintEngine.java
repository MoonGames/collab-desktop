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
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.text.TextTool;
import cz.mgn.collabdesktop.room.model.paintengine.userevents.KeyCombination;
import java.util.ArrayList;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class PaintEngine implements CollabPanelListener, PaintEngineInterface {

    protected static KeyCombination KEY_COMBINATION_SELECT_ALL;
    protected static KeyCombination KEY_COMBINATION_DELETE_SELECTION;

    static {
        ArrayList<CollabPanelKeyEvent.KeyCode> combination =
                new ArrayList<CollabPanelKeyEvent.KeyCode>();

        combination.add(CollabPanelKeyEvent.KeyCode.CODE_CONTROL);
        combination.add(CollabPanelKeyEvent.KeyCode.CODE_A);
        KEY_COMBINATION_SELECT_ALL = new KeyCombination(
                (ArrayList<CollabPanelKeyEvent.KeyCode>) combination.clone());

        combination.clear();
        combination.add(CollabPanelKeyEvent.KeyCode.CODE_DELETE);
        KEY_COMBINATION_DELETE_SELECTION = new KeyCombination(
                (ArrayList<CollabPanelKeyEvent.KeyCode>) combination.clone());

    }
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
    /**
     * tool to switch back after key shortcut ends
     */
    protected Tool backUpTool = null;
    protected Tool colorPicker;

    public PaintEngine() {
        init();
    }

    protected void init() {
        //TODO: fill list of tools
        tools.add(new BrushTool());
        tools.add(colorPicker = new ColorPickerTool());
        tools.add(new SelectTool());
        tools.add(new ClearTool());
        tools.add(new PaintBucketTool());
        tools.add(new TextTool());
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

    protected void processKeyEventLocally(CollabPanelKeyEvent e) {
        testForColorPicker(e);

        if (e.getEventType() == CollabPanelKeyEvent.EVENT_TYPE_PRESSED) {
            if (KEY_COMBINATION_SELECT_ALL.test(e)) {
                if (canvas != null) {
                    canvas.getSelectionable().selectAll();
                }
            } else if (KEY_COMBINATION_DELETE_SELECTION.test(e)) {
                if (canvas != null && (!canvas.getSelectionable().
                        isSelectedAll() || selectedTool instanceof SelectTool)) {
                    canvas.getPaintable().paint(ClearTool.generateFillData(null,
                            0, 0, canvas.getPaintable().getWidth(), canvas.
                            getPaintable().getHeight()));
                }
            }
        }
    }

    /**
     * Test if key event should occur switch between color picker and origin
     * tool
     *
     * @param e event
     */
    protected void testForColorPicker(CollabPanelKeyEvent e) {
        if (e.getKeyCode() == CollabPanelKeyEvent.KeyCode.CODE_SHIFT) {
            if (e.getEventType() == CollabPanelKeyEvent.EVENT_TYPE_PRESSED) {
                backUpTool = selectedTool;
                selectTool(colorPicker);
            } else if (e.getEventType()
                    == CollabPanelKeyEvent.EVENT_TYPE_RELEASED) {
                if (backUpTool != null) {
                    selectTool(backUpTool);
                }
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
        processKeyEventLocally(e);
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

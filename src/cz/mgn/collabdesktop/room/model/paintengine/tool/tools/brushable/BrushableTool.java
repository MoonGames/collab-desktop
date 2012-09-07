/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tool.tools.brushable;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabdesktop.room.model.paintengine.tool.Tool;
import cz.mgn.collabdesktop.room.model.paintengine.tool.tools.brushable.brush.Brush;
import cz.mgn.collabdesktop.room.model.paintengine.tool.tools.brushable.brush.BrushListener;
import cz.mgn.collabdesktop.room.view.panels.leftpanel.toolspanel.extra.brushs.BrushPanel;
import cz.mgn.collabdesktop.room.view.panels.leftpanel.toolspanel.extra.brushs.BrushSelectionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public abstract class BrushableTool extends Tool implements BrushSelectionListener, BrushListener {

    protected Brush brush = null;
    protected int color = Color.BLACK.getRGB();
    protected JPanel toolPanel = null;
    protected BrushPanel brushPanel;

    public BrushableTool(BrushPanel brushPanel) {
        super();
        brush = brushPanel.getSelectedBrush();
        brushPanel.addBrushSelectionListener(this);
        brushPanel.addBrushListener(this);
        this.brushPanel = brushPanel;
        toolPanel = new JPanel(new BorderLayout());
        JPanel aditional = getAditionalPanel();
        if (aditional != null) {
            toolPanel.add(aditional, BorderLayout.SOUTH);
        }
    }

    public abstract JPanel getAditionalPanel();

    @Override
    public void mouseEvent(CollabPanelMouseEvent e) {
        if (canvasInterface != null && e.getEventType() == CollabPanelMouseEvent.TYPE_WHEEL) {
            if (e.isShiftDown()) {
                float scale = brush.getScale();
                float factor = (float) Math.pow(1.1, e.getWheelAmount());
                scale *= factor;
                scale = (float) Math.min(10, Math.max(0.1, scale));
                brush.setScale(scale);
            }
        }
    }

    @Override
    public void setColor(int color) {
        this.color = color;
        if (brush != null) {
            brush.setColor(color);
        }
    }

    public void setBrush(Brush brush) {
        this.brush = brush;
        if (canvasInterface != null) {
            brush.setColor(color);
            canvasInterface.getVisible().setToolCursor(new BrushToolCursor(brush));
        }
    }

    @Override
    public void canvasInterfaceSeted() {
        if (brush != null) {
            canvasInterface.getVisible().setToolCursor(new BrushToolCursor(brush));
        }
    }

    @Override
    public void canvasInterfaceUnset() {
    }

    @Override
    public JPanel getToolOptionsPanel() {
        toolPanel.add(brushPanel, BorderLayout.CENTER);
        return toolPanel;
    }

    @Override
    public void brushSelected(Brush brush) {
        setBrush(brush);
    }

    @Override
    public void brushScaled(float scale) {
        //FIXME: 
    }

    @Override
    public void brusheJitter(float jitter) {
    }

    @Override
    public void brushStep(float step) {
    }
}

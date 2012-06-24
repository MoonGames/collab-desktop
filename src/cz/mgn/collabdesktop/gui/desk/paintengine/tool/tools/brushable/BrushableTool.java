/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools.brushable;

import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.ForToolInterface;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.toolspanel.extra.brushs.BrushPanel;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.toolspanel.extra.brushs.BrushSelectionListener;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.Tool;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools.brushable.brush.Brush;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools.brushable.brush.BrushListener;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 *   @author indy
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
    public void mouseWheeled(int amount, boolean shift, boolean control) {
        if (paintable != null) {
            if (shift) {
                float scale = brush.getScale();
                float factor = (float) Math.pow(1.1, amount);
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
        if (paint != null) {
            p.setColor(color);
                paint.setCursor(brush.getCuror());
        }
    }

    @Override
    public void paintSeted() {
        if (brush != null) {
            brush.setColor(color);
            paint.setCursor(brush.getCuror());
        }
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
        if (paint != null) {
            paint.setCursor(brush.getCuror());
        }
    }

    @Override
    public void brusheJitter(float jitter) {
    }

    @Override
    public void brushStep(float step) {
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools.brushable.brush;

/**
 *
 * @author indy
 */
public interface BrushListener {

    public void brushScaled(float scale);

    public void brusheJitter(float jitter);

    public void brushStep(float step);
}

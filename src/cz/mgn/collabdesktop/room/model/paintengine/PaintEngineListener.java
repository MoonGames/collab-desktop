/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine;

/**
 *
 * @author indy
 */
public interface PaintEngineListener {

    /**
     * another tool is selected
     */
    public void selectedToolChanged(ToolInfoInterface selectedTool);
}

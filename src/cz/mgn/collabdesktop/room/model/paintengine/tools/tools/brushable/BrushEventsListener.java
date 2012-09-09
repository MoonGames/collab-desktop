/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable;

import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.brush.Brush;

/**
 *
 * @author indy
 */
public interface BrushEventsListener {
    
    /**
     * brush selected
     * 
     * @param brush can be null
     */
    public void brushSelected(Brush brush);
    
    /**
     * selected brush was scaled
     */
    public void brushScaled();
}

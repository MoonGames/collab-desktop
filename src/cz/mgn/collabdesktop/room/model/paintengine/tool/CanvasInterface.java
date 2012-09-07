/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tool;

import cz.mgn.collabcanvas.interfaces.paintable.Paintable;
import cz.mgn.collabcanvas.interfaces.selectionable.Selectionable;
import cz.mgn.collabcanvas.interfaces.visible.Visible;

/**
 *
 * @author indy
 */
public class CanvasInterface {

    protected Visible visible;
    protected Paintable paintable;
    protected Selectionable selectable;

    public CanvasInterface(Visible visible, Paintable paintable, Selectionable selectable) {
        this.visible = visible;
        this.paintable = paintable;
        this.selectable = selectable;
    }

    public Visible getVisible() {
        return visible;
    }

    public Paintable getPaintable() {
        return paintable;
    }

    public Selectionable getSelectionable() {
        return selectable;
    }
}

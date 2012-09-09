/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine;

import cz.mgn.collabcanvas.interfaces.paintable.Paintable;
import cz.mgn.collabcanvas.interfaces.selectionable.Selectionable;
import cz.mgn.collabcanvas.interfaces.visible.Visible;

/**
 *
 * @author indy
 */
public class Canvas {

    protected Visible visible;
    protected Paintable paintable;
    protected Selectionable selectionable;

    public Canvas(Visible visible, Paintable paintable, Selectionable selectionable) {
        this.visible = visible;
        this.paintable = paintable;
        this.selectionable = selectionable;
    }

    public Visible getVisible() {
        return visible;
    }

    public Paintable getPaintable() {
        return paintable;
    }

    public Selectionable getSelectionable() {
        return selectionable;
    }
}

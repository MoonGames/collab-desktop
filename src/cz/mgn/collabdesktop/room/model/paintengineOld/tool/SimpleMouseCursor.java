/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengineOld.tool;

import cz.mgn.collabcanvas.interfaces.visible.MouseCursor;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public class SimpleMouseCursor implements MouseCursor {

    protected BufferedImage cursor;

    public SimpleMouseCursor(BufferedImage cursor) {
        this.cursor = cursor;
    }

    @Override
    public Image getCursorImage() {
        return cursor;
    }
}

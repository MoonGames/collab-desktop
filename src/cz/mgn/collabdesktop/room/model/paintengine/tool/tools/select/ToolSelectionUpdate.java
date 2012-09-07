/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tool.tools.select;

import cz.mgn.collabcanvas.interfaces.selectionable.SelectionUpdate;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public class ToolSelectionUpdate implements SelectionUpdate {

    protected int updateMode;
    protected Point coordinates;
    protected float applyAmount;
    protected BufferedImage updateImage;

    public ToolSelectionUpdate(int updateMode, Point coordinates, float applyAmount, BufferedImage updateImage) {
        this.updateImage = updateImage;
        this.applyAmount = applyAmount;
        this.coordinates = coordinates;
        this.updateMode = updateMode;
    }

    public ToolSelectionUpdate(int updateMode, float applyAmount, BufferedImage updateImage) {
        this(updateMode, new Point(0, 0), applyAmount, updateImage);
    }

    @Override
    public int getUpdateMode() {
        return updateMode;
    }

    @Override
    public Point getUpdateCoordinates() {
        return coordinates;
    }

    @Override
    public float getApplyAmount() {
        return applyAmount;
    }

    @Override
    public BufferedImage getUpdateImage() {
        return updateImage;
    }
}

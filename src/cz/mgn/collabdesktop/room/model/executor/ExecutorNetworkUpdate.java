/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.executor;

import cz.mgn.collabcanvas.interfaces.networkable.NetworkUpdate;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public class ExecutorNetworkUpdate implements NetworkUpdate {

    protected int updateID;
    protected int updateLayerID;
    protected int updateCanvasID = 0;
    protected boolean isAdd;
    protected Point updateCoordinates;
    protected BufferedImage updateImage;

    public ExecutorNetworkUpdate(int updateID, int updateLayerID, boolean isAdd, Point updateCoordinates, BufferedImage updateImage) {
        this.updateID = updateID;
        this.updateLayerID = updateLayerID;
        this.isAdd = isAdd;
        this.updateCoordinates = updateCoordinates;
        this.updateImage = updateImage;
    }

    @Override
    public int getUpdateID() {
        return updateID;
    }

    @Override
    public int getUpdateLayerID() {
        return updateLayerID;
    }

    @Override
    public int getUpdateCanvasID() {
        return updateCanvasID;
    }

    @Override
    public boolean isAdd() {
        return isAdd;
    }

    @Override
    public Point getUpdateCoordinates() {
        return updateCoordinates;
    }

    @Override
    public BufferedImage getUpdateImage() {
        return updateImage;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.executor.interfaces;

/**
 *
 *   @author indy
 */
public interface Layers {

    public void addLayer(int layerID, int identificator, boolean sucesfull);

    public void setLayersOrder(int[] layersOrderIDs);

    public void setLayerName(int layerID, String name);
}

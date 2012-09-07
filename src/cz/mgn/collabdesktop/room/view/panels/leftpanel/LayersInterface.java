/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.view.panels.leftpanel;

/**
 *
 *  @author indy
 */
public interface LayersInterface {
    
    public void addLayer(int identificator, int position, String name);
    
    public void removeLayer(int layerID);
    
    public void setLayerPosition(int layerID, int position);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.view;

import cz.mgn.collabdesktop.room.view.eframes.EFrame;

/**
 *
 * @author indy
 */
public interface DeskInterface {

    public void resolutionInfo(int width, int height);
    
    public void showWindow(EFrame eFrame);
}
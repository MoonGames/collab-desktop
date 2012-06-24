/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces;

/**
 *
 *   @author indy
 */
public interface Update {

    public void update(int x, int y, int width, int height);
    
    public void repaint();
}

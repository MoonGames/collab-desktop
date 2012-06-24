/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel;

/**
 *
 * @author indy
 */
public interface ScrollInterface {

    public void resetScrollPoint();

    public void setScrollPoint(int x, int y);
    
    public void dragScroll(int x, int y);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.downpanel.interfaces;

/**
 *
 * @author indy
 */
public interface SystemInterface {

    public void makeHTTPImage();

    public void leaveRoom();

    public void saveAsImage();

    public float zoomTo100();

    public float zoomIn();

    public float zoomOut();

    public void print();

    public void tweet();
}

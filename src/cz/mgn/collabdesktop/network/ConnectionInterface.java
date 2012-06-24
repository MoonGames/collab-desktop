/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.network;

/**
 *
 *   @author indy
 */
public interface ConnectionInterface {

    public void connectionError();
    
    public void connectionSuccessful();
    
    public void connectionClosed();
}

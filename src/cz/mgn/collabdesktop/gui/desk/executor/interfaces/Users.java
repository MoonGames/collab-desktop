/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.executor.interfaces;

/**
 *
 *   @author indy
 */
public interface Users {

    public void usersList(String[] nicks);

    public void chat(String nick, String message);
}

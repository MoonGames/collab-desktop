/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.downpanel.frames.saveimage;

import java.io.File;

/**
 *
 * @author indy
 */
public interface SaveImageInterface<O> {

    public void fileChoosed(File file, O data);

    public void saveImageClosed();
}

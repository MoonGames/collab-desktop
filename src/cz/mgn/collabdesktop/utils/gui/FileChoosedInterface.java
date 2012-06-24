/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.utils.gui;

import java.io.File;

/**
 *
 *   @author indy
 */
public interface FileChoosedInterface<O> {

    public void fileChoosed(File file, O data);
}

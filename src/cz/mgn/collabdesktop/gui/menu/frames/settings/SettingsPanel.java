/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.menu.frames.settings;

import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 *
 *  @author indy
 */
public abstract class SettingsPanel extends JPanel {
    
    public SettingsPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
    }
    
    public abstract String getPanelName();
    
    public void addProperty(String name, JComponent component) {
        add(new JLabel(name));
        add(component);
        add(new JSeparator());
    }
}

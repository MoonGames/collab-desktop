/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.menu.frames.settings;

import cz.mgn.collabdesktop.menu.MenuFrame;
import cz.mgn.collabdesktop.menu.frames.settings.sections.Basic;
import cz.mgn.collabdesktop.menu.frames.settings.sections.LoadAndSave;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 *
 *   @author indy
 */
public class SettingsFrame extends MenuFrame {
    
    protected JTabbedPane tabs;

    public SettingsFrame() {
        super();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        centerWindow();
        setVisible(true);
    }

    @Override
    protected String getSectionName() {
        return "settings";
    }

    @Override
    protected void initComponents() {
        setPreferredSize(new Dimension(400, 400));
        setSize(getPreferredSize());
        setLayout(new BorderLayout());
        tabs = new JTabbedPane();
        add(tabs);
        
        addSection(new Basic());
        addSection(new LoadAndSave());
    }
    
    public void addSection(SettingsPanel panel) {
        tabs.add(panel.getPanelName(), panel);
    }
}

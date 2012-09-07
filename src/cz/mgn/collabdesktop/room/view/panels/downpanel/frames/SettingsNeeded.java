/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.view.panels.downpanel.frames;

import cz.mgn.collabdesktop.room.view.eframes.EFrame;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author indy
 */
public class SettingsNeeded extends EFrame {
    
    protected JTextArea message;
    
    public SettingsNeeded(String message) {
        super();
        this.message.setText(message);
    }

    @Override
    protected String getSectionName() {
        return "settings needed";
    }

    @Override
    protected void initComponents() {
        setPreferredSize(new Dimension(300, 60));
        setSize(getPreferredSize());
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        message = new JTextArea();
        message.setBorder(null);
        message.setOpaque(false);
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        c.add(message);
    }

    @Override
    public void windowClosed() {
    }
}

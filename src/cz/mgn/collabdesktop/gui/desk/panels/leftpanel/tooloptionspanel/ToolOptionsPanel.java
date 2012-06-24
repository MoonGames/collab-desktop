/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.leftpanel.tooloptionspanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

/**
 *
 *  @author indy
 */
public class ToolOptionsPanel extends JPanel {

    protected JLabel noOptions = new JLabel("This tool has no options.");

    public ToolOptionsPanel() {
        initComponents();
    }

    protected void initComponents() {
        setBorder(new TitledBorder("Tool options"));
        setPreferredSize(new Dimension(100, 100));
        setLayout(new BorderLayout());
        noOptions.setFont(noOptions.getFont().deriveFont(Font.PLAIN));
        noOptions.setHorizontalAlignment(JLabel.CENTER);
    }

    public void setTollOptionsPanel(JPanel panel) {
        removeAll();
        if (panel != null) {
            add(panel, BorderLayout.CENTER);
        } else {
            add(noOptions, BorderLayout.CENTER);
        }
        updateUI();
    }
}
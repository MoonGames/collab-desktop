/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.leftpanel.layerspanel;

import cz.mgn.collabdesktop.gui.desk.eframes.EFrame;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author indy
 */
public class LayerName extends EFrame implements ActionListener {

    protected LayerNameInterface layerNameInterface = null;
    //
    protected JTextField name;
    protected JButton done;

    public LayerName(LayerNameInterface layerNameInterface) {
        super();
        this.layerNameInterface = layerNameInterface;
    }

    public void setDefaultName(String name) {
        this.name.setText(name);
    }

    @Override
    protected String getSectionName() {
        return "layer name";
    }

    @Override
    protected void initComponents() {
        setPreferredSize(new Dimension(250, 70));
        setSize(getPreferredSize());
        setResizable(false);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelName = new JLabel("Name:");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0f;
        c.weighty = 1f;
        c.insets = new Insets(0, 0, 0, 5);
        add(labelName, c);

        name = new JTextField();
        name.addActionListener(this);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1f;
        c.weighty = 1f;
        c.insets = new Insets(0, 0, 0, 0);
        add(name, c);

        done = new JButton("Done");
        done.addActionListener(this);
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0f;
        c.weighty = 1f;
        c.insets = new Insets(0, 5, 0, 0);
        add(done, c);
    }

    @Override
    public void windowClosed() {
        layerNameInterface.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = this.name.getText();
        if (name.isEmpty()) {
            layerNameInterface.close();
        } else {
            layerNameInterface.done(name);
        }
        dispose();
    }
}

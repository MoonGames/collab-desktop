/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.paintbucket;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author indy
 */
public class PaintBucketPanel extends JPanel implements ChangeListener, ActionListener {

    protected JSlider tolerance;
    protected JTextField toleranceValue;

    public PaintBucketPanel() {
        initComponents();
    }
    
    public int getTolerance() {
        return tolerance.getValue();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.insets = new Insets(4, 0, 4, 5);
        add(new JLabel("Tolerance:"), c);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.insets = new Insets(4, 0, 4, 5);
        add(toleranceValue = new JTextField("0"), c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weightx = 1;
        c.insets = new Insets(4, 5, 4, 5);
        add(tolerance = new JSlider(0, 510), c);



        tolerance.addChangeListener(this);
        tolerance.setValue(0);
        toleranceValue.addActionListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == tolerance) {
            toleranceValue.setText("" + tolerance.getValue());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toleranceValue) {
            String text = toleranceValue.getText();
            try {
                float value = Float.parseFloat(text);
                if (value >= 0 && value <= 510) {
                    toleranceValue.setText("" + tolerance.getValue());
                    tolerance.setValue((int) value);
                }
            } catch (NumberFormatException ex) {
            }
        }
    }
}

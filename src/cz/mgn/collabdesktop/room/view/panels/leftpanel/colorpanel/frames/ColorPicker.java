/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.view.panels.leftpanel.colorpanel.frames;

import cz.mgn.collabdesktop.room.view.eframes.EFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class ColorPicker extends EFrame implements ActionListener {

    protected ColorPickerInterface colorPickerInterface;
    //
    protected JColorChooser colorChooser;
    protected JButton select;
    protected JButton cancel;

    public ColorPicker(ColorPickerInterface colorPickerInterface, Color startColor) {
        super();
        this.colorPickerInterface = colorPickerInterface;
        colorChooser.setColor(startColor);
    }

    @Override
    protected String getSectionName() {
        return "color picker";
    }

    @Override
    protected void initComponents() {
        setPreferredSize(new Dimension(480, 400));
        setSize(getPreferredSize());
        setLayout(new BorderLayout(0, 5));

        colorChooser = new JColorChooser();
        add(colorChooser, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 5, 0));
        select = new JButton("Select");
        select.addActionListener(this);
        buttons.add(select);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        buttons.add(cancel);
        add(buttons, BorderLayout.SOUTH);
    }

    @Override
    public void windowClosed() {
        colorPickerInterface.closed();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            colorPickerInterface.closed();
        } else {
            colorPickerInterface.colorPicked(colorChooser.getColor());
        }
        dispose();
    }
}

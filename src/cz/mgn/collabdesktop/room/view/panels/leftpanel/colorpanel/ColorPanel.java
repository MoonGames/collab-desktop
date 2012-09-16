/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.view.panels.leftpanel.colorpanel;

import cz.mgn.collabdesktop.room.model.paintengine.PaintEngine;
import cz.mgn.collabdesktop.room.model.paintengine.PaintEngineListener;
import cz.mgn.collabdesktop.room.model.paintengine.ToolInfoInterface;
import cz.mgn.collabdesktop.room.view.DeskInterface;
import cz.mgn.collabdesktop.room.view.panels.leftpanel.colorpanel.frames.ColorPicker;
import cz.mgn.collabdesktop.room.view.panels.leftpanel.colorpanel.frames.ColorPickerInterface;
import cz.mgn.collabdesktop.utils.TextUtils;
import cz.mgn.collabdesktop.utils.settings.Settings;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author indy
 */
public class ColorPanel extends JPanel implements ActionListener, PaintEngineListener {

    protected DeskInterface desk = null;
    protected PaintEngine paintEngine = null;
    protected ColorButton colorButton = null;
    protected JTextField colorLabel = null;
    protected boolean colorPickerShowing = false;

    public ColorPanel(PaintEngine paintEngine, DeskInterface desk) {
        this.paintEngine = paintEngine;
        this.desk = desk;
        paintEngine.addListener(this);
        initComponents();
    }

    /**
     * init swing
     */
    protected void initComponents() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(getPreferredSize().width, 25));

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;

        colorButton = new ColorButton();
        colorButton.setPreferredSize(new Dimension(32, 32));
        colorButton.addActionListener(this);
        c.insets = new Insets(0, 0, 0, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0f;
        c.weighty = 1f;
        add(colorButton, c);

        colorLabel = new JTextField();
        colorLabel.addActionListener(this);
        colorLabel.setEditable(true);
        colorLabel.setOpaque(false);
        colorLabel.setFont(colorLabel.getFont().deriveFont(Font.BOLD));
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1f;
        c.weighty = 1f;
        add(colorLabel, c);

        setColor(Color.BLACK);
    }

    public void setColor(Color color) {
        paintEngine.setColor(color.getRGB());
    }

    /**
     * sets what color is displayed on color button and color code text filed
     *
     * @param color color what will be displayed
     */
    protected void displayColor(Color color) {
        colorButton.setColor(color);
        if (Settings.defaultColorStringRepresentation == Settings.COLOR_STRING_REPRESENTATION_HEXADECIMAL) {
            colorLabel.setText(TextUtils.generateHexolor(color));
        } else {
            colorLabel.setText(TextUtils.generateRGBColor(color));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == colorButton) {
            if (!colorPickerShowing) {
                colorPickerShowing = true;
                ColorPicker cp = new ColorPicker(new ColorPickerInterface() {
                    @Override
                    public void colorPicked(Color color) {
                        colorPickerShowing = false;
                        setColor(color);
                    }

                    @Override
                    public void closed() {
                        colorPickerShowing = false;
                    }
                }, colorButton.getColor());
                desk.showWindow(cp);
            }
        } else if (e.getSource() == colorLabel) {
            parseColor();
        }
    }

    /**
     * parse color from color code text filed and use it
     */
    protected void parseColor() {
        String text = colorLabel.getText();
        if (text.startsWith("rgb (")) {
            Color color = TextUtils.parseRGBColor(text);
            if (color != null) {
                setColor(color);
            }
        } else if (text.startsWith("#")) {
            Color color = TextUtils.parseHexColor(text);
            if (color != null) {
                setColor(color);
            }
        }
    }

    @Override
    public void selectedToolChanged(ToolInfoInterface selectedTool) {
    }

    @Override
    public void colorChanged(int newColor) {
        displayColor(new Color(newColor));
    }
}

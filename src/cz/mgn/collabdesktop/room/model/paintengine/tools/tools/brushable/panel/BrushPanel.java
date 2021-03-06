/*
 * Collab desktop - Software for shared drawing via internet in real-time
 * Copyright (C) 2012 Martin Indra <aktive@seznam.cz>
 *
 * This file is part of Collab desktop.
 *
 * Collab desktop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Collab desktop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Collab desktop.  If not, see <http://www.gnu.org/licenses/>.
 */

package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.panel;

import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.BrushEventsListener;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.brush.Brush;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.brush.BrushIO;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class BrushPanel extends JPanel implements ChangeListener, BrushSelectionListener {

    protected BrushsList brushList = null;
    protected BrushEventsListener brushEventListener;
    protected Brush selectedBrush = null;
    //
    protected JSlider scale;
    protected JLabel scaleValueLabel;
    protected JSlider jitter;
    protected JLabel jitterValueLabel;
    protected JSlider step;
    protected JLabel stepValueLabel;
    protected JSlider opacity;
    protected JLabel opacityValueLabel;
    //
    protected JButton createBrush;
    protected JButton editBrush;

    public BrushPanel(BrushEventsListener brushEventListener) {
        this.brushEventListener = brushEventListener;
        init();
    }

    protected void init() {
        setLayout(new BorderLayout(0, 10));
        add(createBurshsPanel(), BorderLayout.CENTER);
        add(createBrushOptionsPanel(), BorderLayout.SOUTH);

        brushSelected(selectedBrush);
    }

    protected JPanel createBrushOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.insets = new Insets(4, 0, 4, 5);
        panel.add(new JLabel("Scale:"), c);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.insets = new Insets(4, 0, 4, 0);
        scale = new JSlider(0, 100);
        scale.addChangeListener(this);
        panel.add(scale, c);
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0;
        c.insets = new Insets(4, 5, 4, 5);
        scaleValueLabel = new JLabel("1");
        scaleValueLabel.setMinimumSize(new Dimension(35, scaleValueLabel.getMinimumSize().height));
        panel.add(scaleValueLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.insets = new Insets(4, 0, 4, 5);
        panel.add(new JLabel("Jitter:"), c);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        c.insets = new Insets(4, 0, 4, 0);
        jitter = new JSlider(0, 100);
        jitter.addChangeListener(this);
        panel.add(jitter, c);
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0;
        c.insets = new Insets(4, 5, 4, 5);
        jitterValueLabel = new JLabel("0");
        panel.add(jitterValueLabel, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        c.insets = new Insets(4, 0, 4, 5);
        panel.add(new JLabel("Step:"), c);
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        c.insets = new Insets(4, 0, 4, 0);
        step = new JSlider(1, 200);
        step.addChangeListener(this);
        panel.add(step, c);
        c.gridx = 2;
        c.gridy = 2;
        c.weightx = 0;
        c.insets = new Insets(4, 5, 4, 5);
        stepValueLabel = new JLabel("1");
        panel.add(stepValueLabel, c);

        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        c.insets = new Insets(4, 0, 4, 5);
        panel.add(new JLabel("Opacity:"), c);
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 1;
        c.insets = new Insets(4, 0, 4, 0);
        opacity = new JSlider(0, 100);
        opacity.addChangeListener(this);
        panel.add(opacity, c);
        c.gridx = 2;
        c.gridy = 3;
        c.weightx = 0;
        c.insets = new Insets(4, 5, 4, 5);
        opacityValueLabel = new JLabel("0");
        panel.add(opacityValueLabel, c);

        return panel;
    }

    protected float getScaleValue() {
        return (float) Math.pow(10f, (scale.getValue() - 50) / 50f);
    }

    protected int countValue(float source) {
        int value = (int) (Math.log10(source) * 50f) + 50;
        value = Math.max(0, Math.min(scale.getMaximum(), value));
        return value;
    }

    protected void setScaleValue(float scaleV) {
        scale.setValue(countValue(scaleV));
    }

    protected JPanel createBurshsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 5));

        brushList = new BrushsList();
        JScrollPane pane = new JScrollPane(brushList);
        pane.setBorder(null);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(pane, BorderLayout.CENTER);

        brushList.addBrush(BrushIO.loadFromResources("brush-1", "circle 1"));
        brushList.addBrush(BrushIO.loadFromResources("brush-3", "circle 3"));
        brushList.addBrush(BrushIO.loadFromResources("brush-5", "circle 5"));
        brushList.addBrush(BrushIO.loadFromResources("brush-7", "circle 7"));
        brushList.addBrush(BrushIO.loadFromResources("brush-9", "circle 9"));
        brushList.addBrush(BrushIO.loadFromResources("brush-11", "circle 11"));
        brushList.addBrush(BrushIO.loadFromResources("brush-13", "circle 13"));
        brushList.addBrush(BrushIO.loadFromResources("brush-15", "circle 15"));
        brushList.addBrush(BrushIO.loadFromResources("fuzzy-9", "fuzzy 9"));
        brushList.addBrush(BrushIO.loadFromResources("fuzzy-15", "fuzzy 15"));
        //TODO: load user defined buttons

        selectedBrush = brushList.getSelectedBrush();
        brushList.setBrushSelectionListener(this);

        return panel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Object source = e.getSource();
        if (source == scale) {
            float value = getScaleValue();
            scaleValueLabel.setText("" + (Math.round(value * 100) / 100f));
            if (scale.getValue() != countValue(selectedBrush.getScale())) {
                selectedBrush.setScale(value);
            }
            brushEventListener.brushScaled();
        } else if (source == jitter) {
            float value = jitter.getValue() / 10f;
            jitterValueLabel.setText("" + value);
            selectedBrush.setJitter(value);
        } else if (source == step) {
            float value = step.getValue() / 10f;
            stepValueLabel.setText("" + value);
            selectedBrush.setStep(value);
        } else if (source == opacity) {
            float value = opacity.getValue() / 100f;
            opacityValueLabel.setText("" + opacity.getValue());
            selectedBrush.setOpacity(value);
        }
    }

    @Override
    public void brushSelected(Brush brush) {
        selectedBrush = brush;
        setScaleValue(selectedBrush.getScale());
        jitter.setValue((int) (selectedBrush.getJitter() * 10f));
        jitterValueLabel.setText("" + selectedBrush.getJitter());
        step.setValue((int) (selectedBrush.getStep() * 10));
        stepValueLabel.setText("" + selectedBrush.getStep());
        opacity.setValue((int) (selectedBrush.getOpacity() * 100f));
        opacityValueLabel.setText("" + opacity.getValue());
        brushEventListener.brushSelected(selectedBrush);
    }
}

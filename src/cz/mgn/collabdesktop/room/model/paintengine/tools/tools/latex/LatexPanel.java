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

package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.latex;

import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.latex.latexutil.LatexInterface;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.latex.latexutil.LatexUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class LatexPanel extends JPanel implements ActionListener {
    
    protected LatexImageListener lil;
    protected int color = 0xff000000;
    //
    protected final int MAX_STEPS = 10;
    protected int step = 0;
    protected ArrayList<String> steps = new ArrayList<String>();
    //
    protected JButton next;
    protected JButton previous;
    protected JTextArea latexSource;
    protected JButton render;
    protected JSpinner fontSize;

    public LatexPanel(LatexImageListener lil) {
        this.lil = lil;
        initComponents();
    }

    protected void initComponents() {
        setLayout(new BorderLayout(0, 5));

        JPanel pn = new JPanel(new GridLayout(1, 2, 5, 0));
        previous = new JButton("Previous");
        previous.addActionListener(this);
        pn.add(previous);
        next = new JButton("Next");
        next.addActionListener(this);
        pn.add(next);

        add(pn, BorderLayout.NORTH);

        latexSource = new JTextArea();
        latexSource.setLineWrap(true);
        JScrollPane sp = new JScrollPane(latexSource);
        add(sp, BorderLayout.CENTER);

        JPanel dp = new JPanel(new GridLayout(1, 2, 5, 0));
        fontSize = new JSpinner(new SpinnerNumberModel(12, 4, 64, 1));
        dp.add(fontSize);

        render = new JButton("Render");
        render.addActionListener(this);
        dp.add(render);

        add(dp, BorderLayout.SOUTH);
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == render) {
            while (steps.size() >= MAX_STEPS) {
                steps.remove(0);
            }
            String tex = latexSource.getText();
            if (!tex.isEmpty()) {
                if (steps.isEmpty() || !steps.get(steps.size() - 1).equals(tex)) {
                    steps.add(tex);
                    step = steps.size() - 1;
                }
                LatexUtil.generateMath(new LatexInterface() {

                    @Override
                    public void latechRendered(BufferedImage render) {
                        lil.setLatexImage(render);
                    }
                }, tex, (Integer) fontSize.getValue(), new Color(color));
            }
        } else if (e.getSource() == previous) {
            if (step > 0 && step <= steps.size()) {
                step--;
                latexSource.setText(steps.get(step));
            }
        } else if (e.getSource() == next) {
            if (step < (steps.size() - 1)) {
                step++;
                latexSource.setText(steps.get(step));
            }
        }
    }
}

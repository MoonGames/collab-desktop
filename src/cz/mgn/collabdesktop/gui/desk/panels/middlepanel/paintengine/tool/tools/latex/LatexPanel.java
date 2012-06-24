/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.tools.latex;

import cz.mgn.collabdesktop.utils.latexutil.LatexInterface;
import cz.mgn.collabdesktop.utils.latexutil.LatexUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 *        @author indy
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

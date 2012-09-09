/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.panel;

import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.brush.Brush;
import cz.mgn.collabdesktop.utils.gui.WrapLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class BrushsList extends JPanel implements ActionListener {

    protected BrushButton selected = null;
    protected BrushSelectionListener brushSelectionListener = null;

    public BrushsList() {
        setLayout(new WrapLayout(WrapLayout.LEFT, 5, 5));
    }

    public void addBrush(Brush brush) {
        BrushButton button = new BrushButton(brush);
        if (selected == null) {
            selected = button;
            selected.select();
        }
        button.addActionListener(this);
        add(button);
    }

    public Brush getSelectedBrush() {
        if (selected != null) {
            return selected.getBrush();
        }
        return null;
    }

    public void setBrushSelectionListener(BrushSelectionListener brushSelectionListener) {
        this.brushSelectionListener = brushSelectionListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof BrushButton) {
            if (selected != null) {
                selected.unselect();
            }
            BrushButton button = (BrushButton) source;
            selected = button;
            selected.select();
            if (brushSelectionListener != null) {
                brushSelectionListener.brushSelected(selected.getBrush());
            }
        }
    }

    protected class BrushButton extends JButton {

        protected Brush brush = null;
        protected boolean selected = false;

        public BrushButton(Brush brush) {
            this.brush = brush;
            init();
        }

        protected void init() {
            setOpaque(false);
            setToolTipText(brush.getName());
            setPreferredSize(new Dimension(32, 32));
        }

        public void select() {
            selected = true;
            repaint();
        }

        public void unselect() {
            selected = false;
            repaint();
        }

        public Brush getBrush() {
            return brush;
        }

        @Override
        public void paintComponent(Graphics g2) {
            Graphics2D g = (Graphics2D) g2;
            g.clearRect(0, 0, getWidth(), getHeight());

            if (selected) {
                g.setColor(Color.GRAY);
            } else {
                g.setColor(Color.WHITE);
            }
            g.fillRect(0, 0, getWidth(), getHeight());

            BufferedImage tp = brush.getSourceImage();
            float scale = 1f;
            if ((getWidth() - 6) < tp.getWidth()) {
                scale = (float) (getWidth() - 6) / (float) tp.getWidth();
            }
            if ((getHeight() - 6) < tp.getHeight()) {
                scale = Math.min(scale, (float) (getHeight() - 6) / (float) tp.getHeight());
            }


            if (scale != 1) {
                BufferedImage tps = tp;
                tp = new BufferedImage((int) (tp.getWidth() * scale), (int) (tp.getHeight() * scale), BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D gtp = (Graphics2D) tp.getGraphics();
                gtp.drawImage(tps, 0, 0, tp.getWidth(), tp.getHeight(), null);
                gtp.dispose();
            }
            g.drawImage(tp, (getWidth() - tp.getWidth()) / 2, (getHeight() - tp.getHeight()) / 2, null);
        }
    }
}

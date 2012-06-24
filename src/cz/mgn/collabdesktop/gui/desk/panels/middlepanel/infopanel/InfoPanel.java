/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.infopanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 *  @author indy
 */
public class InfoPanel extends JPanel implements InfoInterface {

    protected JLabel coordLabel;
    protected JLabel zoomLabel;
    protected JLabel infoString;

    public InfoPanel() {
        initComponents();
    }

    protected void initComponents() {
        setOpaque(false);
        setPreferredSize(new Dimension(200, 25));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 4));

        coordLabel = new JLabel("-");
        coordLabel.setFont(coordLabel.getFont().deriveFont(Font.PLAIN, 12));
        coordLabel.setPreferredSize(new Dimension(120, 20));
        add(coordLabel);

        zoomLabel = new JLabel("zoom: 100%");
        zoomLabel.setFont(zoomLabel.getFont().deriveFont(Font.PLAIN, 12));
        zoomLabel.setPreferredSize(new Dimension(120, 20));
        add(zoomLabel);

        infoString = new JLabel("-");
        infoString.setFont(infoString.getFont().deriveFont(Font.PLAIN, 12));
        add(infoString);
    }

    @Override
    public void setMouseCoord(float x, float y) {
        if (x < 0 || y < 0) {
            coordLabel.setText("-");
        } else {
            x = (float) (Math.floor(x * 10f) / 10);
            y = (float) (Math.floor(y * 10f) / 10);
            coordLabel.setText("[" + x + ", " + y + "]");
        }
    }

    @Override
    public void setZoom(float zoom) {
        zoom = (float) (Math.floor(zoom * 1000f) / 10);
        zoomLabel.setText("zoom: " + zoom + "%");
    }

    @Override
    public void showInfoString(String string) {
        infoString.setText(string);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.view.panels.leftpanel.colorpanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;

/**
 *
 * @author indy
 */
public class ColorButton extends JButton {
    
    protected Color color = Color.BLACK;
    
    public ColorButton() {
        setToolTipText("press to choose color");
    }
    
    @Override
    public void paintComponent(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    public void setColor(Color color) {
        this.color = color;
        repaint();
    }
    
    public Color getColor() {
        return color;
    }
}

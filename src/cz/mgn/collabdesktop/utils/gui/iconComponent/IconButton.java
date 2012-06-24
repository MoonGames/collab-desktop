/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.utils.gui.iconComponent;

import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author indy
 */
public class IconButton extends JButton implements MouseListener {

    protected BufferedImage df = null;
    protected BufferedImage pressed = null;
    protected BufferedImage hover = null;
    protected BufferedImage show = null;

    public IconButton(String source, Icon icon) {
        super(icon);
        init(source);
    }

    protected void init(String source) {
        setBorder(null);
        setOpaque(false);
        loadImages(source);
        addMouseListener(this);
        show = df;
        setPreferredSize(new Dimension(df.getWidth(), df.getHeight()));
    }

    protected void loadImages(String source) {
        pressed = ImageUtil.loadImageFromResources("/resources/images/buttons/" + source + "/pressed.png");
        hover = ImageUtil.loadImageFromResources("/resources/images/buttons/" + source + "/hover.png");
        df = ImageUtil.loadImageFromResources("/resources/images/buttons/" + source + "/default.png");
    }

    @Override
    public void paintComponent(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        g.clearRect(0, 0, getWidth(), getHeight());
        if (show != null) {
            g.drawImage(show, 0, 0, null);
        }
        Icon icon = getIcon();
        if (icon != null) {
            icon.paintIcon(this, g2, (getWidth() - icon.getIconWidth()) / 2, (getHeight() - icon.getIconHeight()) / 2);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        show = pressed;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        show = hover;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        show = hover;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        show = df;
    }
}

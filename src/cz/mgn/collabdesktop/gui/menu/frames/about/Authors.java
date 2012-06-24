/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.menu.frames.about;

import cz.mgn.collabdesktop.gui.menu.MenuFrame;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 *    @author indy
 */
public class Authors extends MenuFrame {

    public Authors() {
        super();
    }

    @Override
    protected String getSectionName() {
        return "authors";
    }

    @Override
    protected void initComponents() {
        AuthorsPanel authorsPanel = new AuthorsPanel();
        Insets insets = getInsets();
        Dimension size = new Dimension(authorsPanel.getPreferredSize());
        size.width += insets.left + insets.right;
        size.height += insets.bottom + insets.top;
        setPreferredSize(size);
        setSize(getPreferredSize());
        setMaximumSize(new Dimension(size));
        setMinimumSize(new Dimension(size.width / 2, size.height / 2));
        setLayout(new BorderLayout());
        add(authorsPanel);
    }

    protected class AuthorsPanel extends JPanel {

        protected BufferedImage authors = null;

        public AuthorsPanel() {
            init();
        }

        protected void init() {
            authors = ImageUtil.loadImageFromResources("/resources/images/authors.png");
            setPreferredSize(new Dimension(authors.getWidth() + 10, authors.getHeight() + 30));
        }

        @Override
        public void paintComponent(Graphics g2) {
            Graphics2D g = (Graphics2D) g2;
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            int w = authors.getWidth();
            int h = authors.getHeight();
            if (w > getWidth() || h > getHeight()) {
                float scale = Math.min((float) getWidth() / (float) w, (float) getHeight() / (float) h);
                w *= scale;
                h *= scale;
            }
            int x = (getWidth() - w) / 2;
            int y = (getHeight() - h) / 2;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(authors, x, y, w, h, null);
        }
    }
}

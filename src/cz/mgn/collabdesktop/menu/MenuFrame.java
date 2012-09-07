/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.menu;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 *            @author indy
 */
public abstract class MenuFrame extends JFrame {

    public MenuFrame() {
        setTitle("Collab - " + getSectionName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image icon = Toolkit.getDefaultToolkit().getImage(MenuFrame.class.getResource("/resources/images/icon-32.png"));
        setIconImage(icon);
        initComponents();
    }

    protected abstract String getSectionName();

    protected abstract void initComponents();

    protected void centerWindow() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(Math.max(0, (dim.width - getWidth()) / 2), Math.max(0, (dim.height - getHeight()) / 2));
    }

    public void setWindowCenterLocation(int cX, int cY) {
        int x = Math.max(0, cX - (getWidth() / 2));
        int y = Math.max(0, cY - (getHeight() / 2));
        setLocation(x, y);
    }

    protected void goTo(MenuFrame menuFrame, boolean dialog) {
        int cX = getLocationOnScreen().x + (getWidth() / 2);
        int cY = getLocationOnScreen().y + (getHeight() / 2);
        if (dialog) {
            menuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else {
            dispose();
        }
        menuFrame.setWindowCenterLocation(cX, cY);
        menuFrame.setVisible(true);
    }
}

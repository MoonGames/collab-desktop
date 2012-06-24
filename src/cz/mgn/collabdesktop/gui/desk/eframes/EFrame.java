/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.eframes;

import cz.mgn.collabdesktop.gui.menu.MenuFrame;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 *   @author indy
 */
public abstract class EFrame extends JDialog implements WindowListener {

    public EFrame() {
        addWindowListener(this);
        setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        setTitle("Collab - " + getSectionName());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        Image icon = Toolkit.getDefaultToolkit().getImage(MenuFrame.class.getResource("/resources/images/icon-32.png"));
        setIconImage(icon);
        initComponents();
    }

    public void showOnCenter(JFrame over) {
        Point position = over.getLocationOnScreen();
        position.x += over.getWidth() / 2;
        position.y += over.getHeight() / 2;
        position.x -= getWidth() / 2;
        position.y -= getHeight() / 2;
        position.x = Math.max(0, position.x);
        position.y = Math.max(0, position.y);
        setLocation(position);
        setVisible(true);
    }

    protected abstract String getSectionName();

    protected abstract void initComponents();

    public abstract void windowClosed();

    @Override
    public void windowClosing(WindowEvent e) {
        windowClosed();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}

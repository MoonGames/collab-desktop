/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.view;

import cz.mgn.collabdesktop.room.view.eframes.EFrame;
import cz.mgn.collabdesktop.room.model.executor.CommandExecutor;
import cz.mgn.collabdesktop.room.model.paintengineOld.PaintEngine;
import cz.mgn.collabdesktop.room.view.panels.downpanel.DownPanel;
import cz.mgn.collabdesktop.room.view.panels.leftpanel.LeftPanel;
import cz.mgn.collabdesktop.room.view.panels.middlepanel.MiddlePanel;
import cz.mgn.collabdesktop.menu.MenuFrame;
import cz.mgn.collabdesktop.menu.frames.ChooseRoom;
import cz.mgn.collabdesktop.menu.frames.about.About;
import cz.mgn.collabdesktop.menu.frames.settings.SettingsFrame;
import cz.mgn.collabdesktop.utils.CConstans;
import cz.mgn.collabdesktop.utils.Utils;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

/**
 *
 * @author indy
 */
public class DeskFrame extends javax.swing.JFrame implements cz.mgn.collabdesktop.room.model.executor.interfaces.CollabSystem, DeskInterface {

    protected String roomName = "";
    protected CommandExecutor executor = null;
    protected MiddlePanel middlePanel = null;
    protected LeftPanel leftPanel = null;
    protected DownPanel downPanel = null;

    /**
     * Creates new form DeskFrame
     */
    public DeskFrame(CommandExecutor executor, String roomName, int x, int y) {
        this.executor = executor;
        this.roomName = roomName;
        Image icon = Toolkit.getDefaultToolkit().getImage(MenuFrame.class.getResource("/resources/images/icon-32.png"));
        setIconImage(icon);
        initComponents();
        setLocation(Math.max(0, x - (getWidth() / 2)), Math.max(0, y - (getHeight() / 2)));
        setTitle("Collab - " + roomName);
        localInit();
    }

    protected void localInit() {
        executor.setSystem(this);
        middlePanel = new MiddlePanel(executor, this);
        PaintEngine paintEngine = new PaintEngine(middlePanel.getCanvas());
        paintPlace.add(middlePanel);

        leftPanel = new LeftPanel(paintEngine, executor, this);
        leftSidePanel.add(leftPanel);

        downPanel = new DownPanel(executor, this, middlePanel.getCanvas(), roomName);
        downSidePanel.add(downPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leftSidePanel = new javax.swing.JPanel();
        downSidePanel = new javax.swing.JPanel();
        paintPlace = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 450));

        leftSidePanel.setBackground(new java.awt.Color(249, 125, 2));
        leftSidePanel.setPreferredSize(new java.awt.Dimension(250, 561));
        leftSidePanel.setLayout(new java.awt.BorderLayout());

        downSidePanel.setBackground(new java.awt.Color(253, 21, 18));
        downSidePanel.setPreferredSize(new java.awt.Dimension(512, 200));
        downSidePanel.setLayout(new java.awt.BorderLayout());

        paintPlace.setLayout(new java.awt.BorderLayout());

        jMenu1.setText("File");

        jMenuItem2.setText("Settings");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setText("Quit");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Help");

        jMenuItem3.setText("Online");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem1.setText("About");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(leftSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(downSidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                    .addComponent(paintPlace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftSidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(paintPlace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(downSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        Utils.browse(CConstans.WEB_PAGE_MAIN);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        new About();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        new SettingsFrame();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel downSidePanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel leftSidePanel;
    private javax.swing.JPanel paintPlace;
    // End of variables declaration//GEN-END:variables

    @Override
    public void disconnectFromRoom() {
        Point p = getLocationOnScreen();
        p.x += getWidth() / 2;
        p.y += getHeight() / 2;

        dismiss();
        dispose();

        ChooseRoom cr = new ChooseRoom(executor.getClient());
        cr.setVisible(true);
        cr.setWindowCenterLocation(p.x, p.y);
    }

    public void dismiss() {
        executor.dismiss();
        middlePanel.dismiss();
    }

    @Override
    public void resolutionInfo(int width, int height) {
        setTitle("Collab - " + roomName + " (" + width + " x " + height + ")");
    }

    @Override
    public void showWindow(EFrame eFrame) {
        eFrame.showOnCenter(this);
    }
}

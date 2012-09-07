/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.view.panels.downpanel;

import cz.mgn.collabcanvas.canvas.CollabCanvas;
import cz.mgn.collabdesktop.room.view.DeskInterface;
import cz.mgn.collabdesktop.room.model.executor.CommandExecutor;
import cz.mgn.collabdesktop.room.model.executor.interfaces.Users;
import cz.mgn.collabdesktop.room.view.panels.downpanel.frames.saveimage.SaveImage;
import cz.mgn.collabdesktop.room.view.panels.downpanel.frames.saveimage.SaveImageInterface;
import cz.mgn.collabdesktop.room.view.panels.downpanel.frames.SettingsNeeded;
import cz.mgn.collabdesktop.room.view.panels.downpanel.frames.twitter.TwitterMessage;
import cz.mgn.collabdesktop.room.view.panels.downpanel.frames.twitter.TwitterMessageInterface;
import cz.mgn.collabdesktop.room.view.panels.downpanel.interfaces.MessageInterface;
import cz.mgn.collabdesktop.room.view.panels.downpanel.interfaces.PaintingSystemInterface;
import cz.mgn.collabdesktop.room.view.panels.downpanel.interfaces.SystemInterface;
import cz.mgn.collabdesktop.room.view.panels.downpanel.subpanels.ChatPanel;
import cz.mgn.collabdesktop.room.view.panels.downpanel.subpanels.SystemPanel;
import cz.mgn.collabdesktop.room.view.panels.downpanel.subpanels.UsersPanel;
import cz.mgn.collabdesktop.utils.apis.twitter.CollabTwitter;
import cz.mgn.collabdesktop.utils.settings.Settings;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class DownPanel extends JPanel implements Users, MessageInterface, SystemInterface, SaveImageInterface<BufferedImage>, TwitterMessageInterface {

    protected DeskInterface desk = null;
    protected CollabCanvas canvas = null;
    protected CommandExecutor executor = null;
    protected String roomName = "";
    protected ChatPanel chatPanel = null;
    protected UsersPanel usersPanel = null;
    protected SystemPanel systemPanel = null;
    //
    protected SaveImage<BufferedImage> saveImage = null;
    protected TwitterMessage twitterMessage = null;

    public DownPanel(CommandExecutor executor, DeskInterface desk, CollabCanvas canvas, String roomName) {
        this.executor = executor;
        this.roomName = roomName;
        this.desk = desk;
        this.canvas = canvas;
        initComponents();
        executor.setUsers(this);
    }

    protected void initComponents() {
        setDoubleBuffered(true);
        setBorder(null);
        setLayout(new BorderLayout(8, 0));

        systemPanel = new SystemPanel(this);
        add(systemPanel, BorderLayout.WEST);
        systemPanel.setPreferredSize(new Dimension(116, 100));

        chatPanel = new ChatPanel(this);
        add(chatPanel, BorderLayout.CENTER);

        usersPanel = new UsersPanel();
        add(usersPanel, BorderLayout.EAST);
        usersPanel.setPreferredSize(new Dimension(100, 100));
    }

    @Override
    public void usersList(String[] nicks) {
        usersPanel.showUsers(nicks);
    }

    @Override
    public void chat(String nick, String message) {
        chatPanel.showMessage(nick, message);
    }

    @Override
    public void sendMessage(String message) {
        executor.sendChat(message);
    }

    @Override
    public void leaveRoom() {
        executor.sendDisconnectFromRoom();
    }

    @Override
    public void saveAsImage() {
        if (saveImage == null) {
            BufferedImage toSave = canvas.getPaintable().getImage(null);
            saveImage = new SaveImage<BufferedImage>(this, toSave, roomName + ".png");
            desk.showWindow(saveImage);
        }
    }

    @Override
    public float zoomIn() {
        return canvas.getZoomable().addToZoom(0.5f);
    }

    @Override
    public float zoomOut() {
        return canvas.getZoomable().addToZoom(-0.5f);
    }

    @Override
    public float zoomTo100() {
        return canvas.getZoomable().setZoom(1f);
    }

    @Override
    public void fileChoosed(File file, BufferedImage data) {
        saveImage = null;
        try {
            String f = file.getPath();
            int i = f.lastIndexOf(".");
            if (i >= 0) {
                f = f.substring(0, i);
            }
            f = f + ".png";
            file = new File(f);

            ImageIO.write(data, "PNG", file);
        } catch (IOException ex) {
            Logger.getLogger(DownPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void saveImageClosed() {
        saveImage = null;
    }

    @Override
    public void makeHTTPImage() {
        executor.sendMakeHTTPImage();
    }

    @Override
    public void print() {
        PrinterJob pj = PrinterJob.getPrinterJob();

        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (PrinterException exc) {
            }
        }
    }

    @Override
    public void tweet() {
        if (!Settings.twitterAccessToken.isEmpty() && !Settings.twitterAccessTokenSecret.isEmpty()) {
            if (twitterMessage != null) {
                twitterMessage.dispose();
            }
            desk.showWindow(twitterMessage = new TwitterMessage(this, canvas.getPaintable().getImage(null), roomName));
        } else {
            desk.showWindow(new SettingsNeeded("Twitter account isn't set. Go to settings to resolve it."));
        }
    }

    @Override
    public void twitterMessageTweet(final BufferedImage image, final String text) {
        twitterMessage = null;
        Thread t = new Thread() {

            @Override
            public void run() {
                CollabTwitter.tweet(roomName, text, image, Settings.twitterAccessToken, Settings.twitterAccessTokenSecret);
            }
        };
        t.start();
    }

    @Override
    public void tweetMessageClosed() {
        twitterMessage = null;
    }
}

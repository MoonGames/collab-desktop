/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.menu.frames;

import cz.mgn.collabdesktop.menu.MenuFrame;
import cz.mgn.collabdesktop.menu.frames.about.About;
import cz.mgn.collabdesktop.menu.frames.settings.SettingsFrame;
import cz.mgn.collabdesktop.utils.CConstans;
import cz.mgn.collabdesktop.utils.Utils;
import cz.mgn.collabdesktop.utils.settings.Settings;
import cz.mgn.collabdesktop.utils.settings.SettingsIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 *  @author indy
 */
public class ConnectServer extends MenuFrame implements ActionListener {

    protected JTextField serverOption;
    protected JTextField nickOption;

    public ConnectServer() {
        super();
        centerWindow();
    }

    @Override
    protected String getSectionName() {
        return "connect server";
    }

    @Override
    protected void initComponents() {
        menuInit();

        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        JLabel labelNick = new JLabel("Nick:");
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(labelNick, c);

        nickOption = new JTextField(Settings.defaultNick);
        nickOption.addActionListener(this);
        c.weightx = 1f;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(nickOption, c);

        JLabel labelServer = new JLabel("Server:");
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(labelServer, c);

        serverOption = new JTextField(Settings.defaultServer);
        serverOption.addActionListener(this);
        c.weightx = 1f;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(serverOption, c);

        JButton buttonConnect = new JButton("Connect");
        buttonConnect.addActionListener(this);
        c.weightx = 0f;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 1;
        pane.add(buttonConnect, c);


        Insets in = getInsets();
        Dimension size = new Dimension(400, 120);
        size.width += in.left + in.right;
        size.height += in.top + in.bottom;
        setPreferredSize(size);
        setSize(getPreferredSize());
        setResizable(false);
    }

    protected void menuInit() {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        menuBar.add(file);
        JMenu help = new JMenu("Help");
        menuBar.add(help);

        JMenuItem menuItem = new JMenuItem("Settings", KeyEvent.VK_S);
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                goTo(new SettingsFrame(), true);
            }
        });
        file.add(menuItem);

        menuItem = new JMenuItem("Online", KeyEvent.VK_O);
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Utils.browse(CConstans.WEB_PAGE_MAIN);
            }
        });
        help.add(menuItem);
        menuItem = new JMenuItem("About", KeyEvent.VK_A);
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                About about = new About();
                goTo(about, true);
            }
        });
        help.add(menuItem);

        setJMenuBar(menuBar);
    }

    protected void connect() {
        String address = serverOption.getText();
        String nick = nickOption.getText();
        if (!address.isEmpty() && !nick.isEmpty()) {
            Settings.defaultNick = nick;
            Settings.defaultServer = address;
            SettingsIO.writeSettings();

            goTo(new ConnectingProgress(nick, address), false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        connect();
    }
}

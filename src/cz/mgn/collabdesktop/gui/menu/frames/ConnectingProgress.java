/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.menu.frames;

import cz.mgn.collabdesktop.gui.menu.MenuFrame;
import cz.mgn.collabdesktop.network.Client;
import cz.mgn.collabdesktop.network.ConnectionInterface;
import cz.mgn.collabdesktop.network.commands.CommandGenerator;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 *          @author indy
 */
public class ConnectingProgress extends MenuFrame implements ConnectionInterface {

    protected Client client;
    protected String nick = "";

    public ConnectingProgress(String nick, String address) {
        super();
        this.nick = nick;
        connect(address);
    }

    protected void connect(String address) {
        client = new Client(address);
        client.setConnectionInterface(this);
        client.start();
    }

    @Override
    protected String getSectionName() {
        return "connecting to server";
    }

    @Override
    protected void initComponents() {
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());

        JLabel label = new JLabel("Connecting to server...");
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(label, c);

        Insets in = getInsets();
        Dimension size = new Dimension(400, 80);
        size.width += in.left + in.right;
        size.height += in.top + in.bottom;
        setPreferredSize(size);
        setSize(getPreferredSize());
        setResizable(false);
    }

    protected void noConnection() {
        client.setConnectionInterface(null);
        goTo(new ConnectServer(), false);
    }

    @Override
    public void connectionError() {
        noConnection();
    }

    @Override
    public void connectionSuccessful() {
        client.send(CommandGenerator.generateSetNickCommand(nick));
        client.setConnectionInterface(null);
        goTo(new ChooseRoom(client), false);
    }

    @Override
    public void connectionClosed() {
        noConnection();
    }
}

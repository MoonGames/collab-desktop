/*
 * Collab desktop - Software for shared drawing via internet in real-time
 * Copyright (C) 2012 Martin Indra <aktive@seznam.cz>
 *
 * This file is part of Collab desktop.
 *
 * Collab desktop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Collab desktop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Collab desktop.  If not, see <http://www.gnu.org/licenses/>.
 */

package cz.mgn.collabdesktop.room.view.panels.downpanel.subpanels;

import cz.mgn.collabdesktop.room.view.panels.downpanel.interfaces.MessageInterface;
import cz.mgn.collabdesktop.utils.gui.textpane.TextPaneHyperlinks;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class ChatPanel extends JPanel implements ActionListener {

    protected SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    protected MessageInterface messageInterface = null;
    protected JScrollPane messagesScroll = null;
    protected JTextField text = null;
    protected JButton send = null;
    protected ChatTextArea messages = null;

    public ChatPanel(MessageInterface messageInterface) {
        this.messageInterface = messageInterface;
        initComponents();
    }

    protected void initComponents() {
        setOpaque(false);
        setBorder(null);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        
        messages = new ChatTextArea(30);

        messagesScroll = new JScrollPane(messages);
        messagesScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.insets = new Insets(0, 0, 5, 0);
        c.weightx = 1f;
        c.weighty = 1f;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(messagesScroll, c);

        text = new JTextField();
        text.addActionListener(this);
        c.insets = new Insets(0, 0, 5, 5);
        c.weightx = 1f;
        c.weighty = 0f;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(text, c);

        send = new JButton("send");
        send.addActionListener(this);
        c.insets = new Insets(0, 0, 5, 0);
        c.weightx = 0f;
        c.weighty = 0f;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        add(send, c);
    }

    public void showMessage(String nick, String message) {
        messages.addMessage(nick, message);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!text.getText().isEmpty()) {
            messageInterface.sendMessage(text.getText());
            text.setText("");
        }
    }

    public class ChatTextArea extends TextPaneHyperlinks {

        protected int maxMessages = 10;
        protected ArrayList<String> messages = new ArrayList<String>();

        public ChatTextArea(int maxMessages) {
            this.maxMessages = maxMessages;
            setEditable(false);
            DefaultCaret caret = (DefaultCaret) getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        }

        public void addMessage(String nick, String message) {
            String msg = "" + nick + " (" + dateFormat.format(new Date()) + "): " + message;

            messages.add(msg);
            if (messages.size() > maxMessages) {
                messages.remove(0);
            }

            String all = "";
            for (String messageL : messages) {
                all = all + "" + messageL + "\n";
            }
            all = all.substring(0, all.length() - 1);
            setText(all);
        }
    }
}

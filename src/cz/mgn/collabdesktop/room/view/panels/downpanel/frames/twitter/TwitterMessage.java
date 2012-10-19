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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.view.panels.downpanel.frames.twitter;

import cz.mgn.collabdesktop.room.view.eframes.EFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author indy
 */
public class TwitterMessage extends EFrame implements ActionListener {

    protected TwitterMessageInterface tmi;
    protected BufferedImage roomImage;
    //
    protected JTextArea text;
    protected JButton tweet;
    protected JButton cancel;

    public TwitterMessage(TwitterMessageInterface tmi, BufferedImage roomImage, String roomName) {
        super();
        this.tmi = tmi;
        this.roomImage = roomImage;
        text.setText(" #" + roomName + " #Collab");
        text.setCaretPosition(0);
    }

    @Override
    protected String getSectionName() {
        return "tweet";
    }

    @Override
    protected void initComponents() {
        setPreferredSize(new Dimension(300, 300));
        setSize(getPreferredSize());
        setLayout(new BorderLayout(0, 10));

        //TODO: maximal characters count!
        text = new JTextArea();
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(text);
        add(sp, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        add(buttonsPanel, BorderLayout.SOUTH);

        tweet = new JButton("Tweet");
        tweet.addActionListener(this);
        buttonsPanel.add(tweet);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        buttonsPanel.add(cancel);
    }

    @Override
    public void windowClosed() {
        tmi.tweetMessageClosed();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            System.out.println("test a");
            dispose();
            tmi.tweetMessageClosed();
        } else if (e.getSource() == tweet) {
            System.out.println("test b");
            dispose();
            tmi.twitterMessageTweet(roomImage, text.getText());
        }
    }
}

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

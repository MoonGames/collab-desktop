/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.downpanel.subpanels;

import cz.mgn.collabdesktop.gui.desk.panels.downpanel.interfaces.MessageInterface;
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
 * @author indy
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

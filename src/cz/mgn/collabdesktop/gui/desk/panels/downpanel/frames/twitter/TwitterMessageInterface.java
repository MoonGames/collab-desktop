/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.downpanel.frames.twitter;

import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public interface TwitterMessageInterface {

    public void twitterMessageTweet(BufferedImage image, String text);

    public void tweetMessageClosed();
}

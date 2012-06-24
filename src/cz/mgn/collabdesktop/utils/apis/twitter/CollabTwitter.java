/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.utils.apis.twitter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author indy
 */
public class CollabTwitter {

    public static void tweet(String roomName, String text, BufferedImage roomImage, String accessToken, String accessTokenSecret) {
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true) //
                    .setOAuthConsumerKey("PLFCUaLARG3rW66MbME1Kg") // twitter app: MG Collab desktop
                    .setOAuthConsumerSecret("Rf5G5htymWOVnC4KGT5lSzaAzlgW6mUwXNIPnywI8") //
                    .setOAuthAccessToken(accessToken) //
                    .setOAuthAccessTokenSecret(accessTokenSecret);
            Twitter twitter = new TwitterFactory(cb.build()).getInstance();
            StatusUpdate su = new StatusUpdate(text);
            su.media(roomName, makeInputStreamFromImage(roomImage));
            twitter.updateStatus(su);
        } catch (IOException ex) {
            Logger.getLogger(CollabTwitter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TwitterException ex) {
            Logger.getLogger(CollabTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static InputStream makeInputStreamFromImage(BufferedImage source) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(source, "PNG", out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}

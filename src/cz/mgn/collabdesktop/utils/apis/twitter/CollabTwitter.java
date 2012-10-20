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
 * @author Martin Indra <aktive@seznam.cz>
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

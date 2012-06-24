/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.utils.settings;

import cz.mgn.collabdesktop.utils.homestorage.HomeStorage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author indy
 */
public class SettingsIO {

    protected static final String SETTINGS_DIRECTORY = "settings";
    protected static final String SETTINGS_FILE = "settings.xml";
    //
    protected static final String TAG_MAIN = "collab-desktop-settings";
    protected static final String TAG_CONNECTION = "connection";
    protected static final String TAG_CONNECTION_ATTR_NICK = "nick";
    protected static final String TAG_CONNECTION_ATTR_SERVER = "server";
    protected static final String TAG_CONNECTION_ATTR_PORT = "port";
    protected static final String TAG_ROOM = "room";
    protected static final String TAG_ROOM_WIDTH = "default-width";
    protected static final String TAG_ROOM_HEIGHT = "default-height";
    protected static final String TAG_ROOM_NAME = "name";
    protected static final String TAG_ROOM_MAXIMAL_WIDTH = "maximal-width";
    protected static final String TAG_ROOM_MAXIMAL_HEIGHT = "maximal-height";
    protected static final String TAG_COLOR = "color";
    protected static final String TAG_COLOR_DEFAULT_COLOR_STRING = "default-color-string";
    protected static final String TAG_TWITTER = "twitter";
    protected static final String TAG_TWITTER_ACCESS_TOKEN = "access-token";
    protected static final String TAG_TWITTER_ACCESS_TOKEN_SECRET = "access-token-secret";

    public static void loadSettings() {
        try {
            File file = HomeStorage.getHomeFolder();
            file = new File(file.getPath() + "/" + SETTINGS_DIRECTORY + "/" + SETTINGS_FILE);
            if (file.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();
                loadSettings(doc.getDocumentElement());;
            }
        } catch (SAXException ex) {
            Logger.getLogger(SettingsIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SettingsIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SettingsIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static void loadSettings(Element document) {
        loadConnectionSettings(document);
        loadRoomSettigns(document);
        loadColorSettigns(document);
        loadTwitterSettings(document);
    }

    protected static void loadTwitterSettings(Element document) {
        NodeList list = document.getElementsByTagName(TAG_TWITTER);
        if (list.getLength() > 0) {
            Element twitter = (Element) list.item(0);
            String param = twitter.getAttribute(TAG_TWITTER_ACCESS_TOKEN);
            if (!param.isEmpty()) {
                Settings.twitterAccessToken = param;
            }
            param = twitter.getAttribute(TAG_TWITTER_ACCESS_TOKEN_SECRET);
            if (!param.isEmpty()) {
                Settings.twitterAccessTokenSecret = param;
            }
        }
    }

    protected static void loadColorSettigns(Element document) {
        NodeList list = document.getElementsByTagName(TAG_COLOR);
        if (list.getLength() > 0) {
            Element color = (Element) list.item(0);
            String param = color.getAttribute(TAG_COLOR_DEFAULT_COLOR_STRING);
            if (!param.isEmpty()) {
                try {
                    Settings.defaultColorStringRepresentation = Integer.parseInt(param);
                } catch (NumberFormatException ex) {
                    Logger.getLogger(SettingsIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    protected static void loadRoomSettigns(Element document) {
        NodeList list = document.getElementsByTagName(TAG_ROOM);
        if (list.getLength() > 0) {
            Element room = (Element) list.item(0);
            String param = room.getAttribute(TAG_ROOM_WIDTH);
            if (!param.isEmpty()) {
                try {
                    Settings.defaultRoomWidth = Integer.parseInt(param);
                } catch (NumberFormatException ex) {
                    Logger.getLogger(SettingsIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            param = room.getAttribute(TAG_ROOM_HEIGHT);
            if (!param.isEmpty()) {
                try {
                    Settings.defaultRoomHeight = Integer.parseInt(param);
                } catch (NumberFormatException ex) {
                    Logger.getLogger(SettingsIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            param = room.getAttribute(TAG_ROOM_MAXIMAL_WIDTH);
            if (!param.isEmpty()) {
                try {
                    Settings.maximalRoomWidth = Integer.parseInt(param);
                } catch (NumberFormatException ex) {
                    Logger.getLogger(SettingsIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            param = room.getAttribute(TAG_ROOM_MAXIMAL_HEIGHT);
            if (!param.isEmpty()) {
                try {
                    Settings.maximalRoomHeight = Integer.parseInt(param);
                } catch (NumberFormatException ex) {
                    Logger.getLogger(SettingsIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            param = room.getAttribute(TAG_ROOM_NAME);
            if (!param.isEmpty()) {
                Settings.defaultRoomName = param;
            }
        }
    }

    protected static void loadConnectionSettings(Element document) {
        NodeList list = document.getElementsByTagName(TAG_CONNECTION);
        if (list.getLength() > 0) {
            Element connection = (Element) list.item(0);
            String param = connection.getAttribute(TAG_CONNECTION_ATTR_NICK);
            if (!param.isEmpty()) {
                Settings.defaultNick = param;
            }
            param = connection.getAttribute(TAG_CONNECTION_ATTR_SERVER);
            if (!param.isEmpty()) {
                Settings.defaultServer = param;
            }
            param = connection.getAttribute(TAG_CONNECTION_ATTR_PORT);
            if (!param.isEmpty()) {
                try {
                    Settings.defaultPort = Integer.parseInt(param);
                } catch (NumberFormatException ex) {
                    Logger.getLogger(SettingsIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void writeSettings() {
        createSettingsFiles();
        try {
            File file = HomeStorage.getHomeFolder();
            file = new File(file.getPath() + "/" + SETTINGS_DIRECTORY + "/" + SETTINGS_FILE);

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(TAG_MAIN);
            doc.appendChild(rootElement);

            writeSettings(rootElement, doc);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(SettingsIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SettingsIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static void writeSettings(Element rootElement, Document doc) {
        Element connection = doc.createElement(TAG_CONNECTION);
        connection.setAttribute(TAG_CONNECTION_ATTR_NICK, Settings.defaultNick);
        connection.setAttribute(TAG_CONNECTION_ATTR_PORT, "" + Settings.defaultPort);
        connection.setAttribute(TAG_CONNECTION_ATTR_SERVER, Settings.defaultServer);
        rootElement.appendChild(connection);

        Element room = doc.createElement(TAG_ROOM);
        room.setAttribute(TAG_ROOM_WIDTH, "" + Settings.defaultRoomWidth);
        room.setAttribute(TAG_ROOM_HEIGHT, "" + Settings.defaultRoomHeight);
        room.setAttribute(TAG_ROOM_MAXIMAL_WIDTH, "" + Settings.maximalRoomWidth);
        room.setAttribute(TAG_ROOM_MAXIMAL_HEIGHT, "" + Settings.maximalRoomHeight);
        room.setAttribute(TAG_ROOM_NAME, Settings.defaultRoomName);
        rootElement.appendChild(room);

        Element color = doc.createElement(TAG_COLOR);
        color.setAttribute(TAG_COLOR_DEFAULT_COLOR_STRING, "" + Settings.defaultColorStringRepresentation);
        rootElement.appendChild(color);

        Element twitter = doc.createElement(TAG_TWITTER);
        twitter.setAttribute(TAG_TWITTER_ACCESS_TOKEN, "" + Settings.twitterAccessToken);
        twitter.setAttribute(TAG_TWITTER_ACCESS_TOKEN_SECRET, "" + Settings.twitterAccessTokenSecret);
        rootElement.appendChild(twitter);
    }

    protected static void createSettingsFiles() {
        File directory = HomeStorage.getHomeFolder();
        directory = new File(directory.getPath() + "/" + SETTINGS_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = HomeStorage.getHomeFolder();
        file = new File(file.getPath() + "/" + SETTINGS_DIRECTORY + "/" + SETTINGS_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(SettingsIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

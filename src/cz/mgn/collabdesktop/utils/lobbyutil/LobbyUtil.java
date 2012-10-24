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

package cz.mgn.collabdesktop.utils.lobbyutil;

import cz.mgn.collabdesktop.utils.HTTPUtil;
import cz.mgn.collabdesktop.utils.settings.Settings;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 *   @author Martin Indra <aktive@seznam.cz>
 */
public class LobbyUtil {

    protected static final String TAG_SERVER = "server";
    protected static final String TAG_SERVER_PARAM_ADDRESS = "address";
    protected static final String TAG_SERVER_PARAM_NAME = "name";
    protected static ArrayList<ServerLobby> servers = new ArrayList<ServerLobby>();

    public static ArrayList<ServerLobby> getServers() {
        return servers;
    }

    /**
     *   loads lobby from server
     */
    public static void loadLobby(final LobbyListener listener) {
        new Thread() {
            @Override
            public void run() {
                listener.lobbyReceived(loadLobby());
            }
        }.start();
    }

    protected static ArrayList<ServerLobby> loadLobby() {
        ArrayList<ServerLobby> lobby = new ArrayList<ServerLobby>();
        try {
            String lobbyXML = HTTPUtil.loadHTTPToString(new URL(Settings.lobbySourceURL));
            lobby.addAll(loadLobbyFromXML(lobbyXML));
        } catch (MalformedURLException ex) {
            Logger.getLogger(LobbyUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        mergeWithLocallyPersistentServers(lobby);
        servers = lobby;
        return lobby;
    }

    protected static ArrayList<ServerLobby> loadLobbyFromXML(String source) {
        ArrayList<ServerLobby> lobby = new ArrayList<ServerLobby>();
        ByteArrayInputStream in = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            in = new ByteArrayInputStream(source.getBytes("UTF-8"));

            Document doc = dBuilder.parse(in);
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            NodeList servers = root.getElementsByTagName(TAG_SERVER);
            for (int i = 0; i < servers.getLength(); i++) {
                lobby.add(loadServerFromXMLElement((Element) servers.item(i)));
            }
        } catch (SAXException ex) {
            Logger.getLogger(LobbyUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LobbyUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(LobbyUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(LobbyUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lobby;
    }

    protected static ServerLobby loadServerFromXMLElement(Element element) {
        String name = element.getAttribute(TAG_SERVER_PARAM_NAME);
        String address = element.getAttribute(TAG_SERVER_PARAM_ADDRESS);
        ServerLobby server = new ServerLobby(address, name);

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Text) {
                server.setDescription(node.getTextContent().trim());
            }
        }
        return server;
    }

    protected static void mergeWithLocallyPersistentServers(ArrayList<ServerLobby> servers) {
        ServerLobby defaultServer = new ServerLobby(Settings.defaultServer, "Default server");
        defaultServer.setDescription("It's yours default server. You can change it in settings.");
        servers.add(0, defaultServer);
    }
}

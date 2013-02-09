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

package cz.mgn.collabdesktop.utils.settings;

/**
 *
 *     @author Martin Indra <aktive@seznam.cz>
 */
public class Settings {

    public static final int COLOR_STRING_REPRESENTATION_RGB = 0;
    public static final int COLOR_STRING_REPRESENTATION_HEXADECIMAL = 1;
    //
    /**
     * ignore port option in settings
     */
    public static boolean forcePort = false;
    /**
     * ignore server option in settings
     */
    public static boolean forceServer = false;
    //
    public static boolean debugMode = false;
    //
    public static String defaultServer = "collab01.mgn.cz";
    public static int defaultPort = 30125;
    public static String defaultNick = "";
    public static int maximalRoomWidth = 800;
    public static int maximalRoomHeight = 600;
    public static int defaultRoomWidth = 800;
    public static int defaultRoomHeight = 400;
    public static String defaultRoomName = "room";
    public static int defaultColorStringRepresentation = COLOR_STRING_REPRESENTATION_HEXADECIMAL;
    public static String twitterAccessToken = "";
    public static String twitterAccessTokenSecret = "";
    public static boolean isLobbyEnabled = true;
    public static String lobbySourceURL = "http://collab.mgn.cz/lobby/lobby.xml";
}

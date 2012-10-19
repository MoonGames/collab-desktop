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
package cz.mgn.collabdesktop.utils.settings;

/**
 *
 *     @author indy
 */
public class Settings {

    public static final int COLOR_STRING_REPRESENTATION_RGB = 0;
    public static final int COLOR_STRING_REPRESENTATION_HEXADECIMAL = 1;
    //
    public static String defaultServer = "indy-home.name";
    public static int defaultPort = 30125;
    public static String defaultNick = "";
    public static int maximalRoomWidth = 800;
    public static int maximalRoomHeight = 600;
    public static int defaultRoomWidth = 800;
    public static int defaultRoomHeight = 500;
    public static String defaultRoomName = "";
    public static int defaultColorStringRepresentation = COLOR_STRING_REPRESENTATION_HEXADECIMAL;
    public static String twitterAccessToken = "";
    public static String twitterAccessTokenSecret = "";
}

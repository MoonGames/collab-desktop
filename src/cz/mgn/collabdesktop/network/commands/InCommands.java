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
package cz.mgn.collabdesktop.network.commands;

/**
 *
 *    @author indy
 */
public class InCommands {

    public static final byte IN_COMMAND_PAINT = 1;
    public static final byte IN_COMMAND_REMOVE = 2;
    public static final byte IN_COMMAND_ADD_LAYER = 3;
    public static final byte IN_COMMAND_LAYERS_ORDER = 4;
    public static final byte IN_COMMAND_SET_RESOLUTION = 5;
    public static final byte IN_COMMAND_SET_LAYER_NAME = 6;
    public static final byte IN_COMMAND_ROOMS_LIST = 7;
    public static final byte IN_COMMAND_JOIN_ROOM = 8;
    public static final byte IN_COMMAND_USERS_LIST = 9;
    public static final byte IN_COMMAND_CHAT = 10;
    public static final byte IN_COMMAND_DISCONNECT_FROM_ROOM = 11;
}

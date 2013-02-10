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

package cz.mgn.collabdesktop.room.model.paintengine.userevents;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class KeyCombination {

    protected ArrayList<CollabPanelKeyEvent.KeyCode> combination = null;

    public KeyCombination(ArrayList<CollabPanelKeyEvent.KeyCode> combination) {
        this.combination = combination;
    }

    /**
     * Tests if key event is equal to this combination
     *
     * @param e source event (to compare)
     * @return true if this combination is in event
     */
    public boolean test(CollabPanelKeyEvent e) {
        ArrayList<CollabPanelKeyEvent.KeyCode> pressedKeys = e.
                getPressedKeyCodes();
        if (pressedKeys.size() != combination.size()) {
            return false;
        }
        for (int i = 0; i < combination.size(); i++) {
            if (combination.get(i) != pressedKeys.get(i)) {
                return false;
            }
        }
        return true;
    }
}

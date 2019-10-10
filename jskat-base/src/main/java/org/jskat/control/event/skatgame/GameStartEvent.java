/**
 * Copyright (C) 2019 Jan Schäfer (jansch@users.sourceforge.net)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jskat.control.event.skatgame;

import org.jskat.data.SkatGameData;
import org.jskat.util.GameVariant;
import org.jskat.util.Player;

import java.util.Objects;

/**
 * Event for game start.
 */
public final class GameStartEvent implements SkatGameEvent {

    public final long gameNo;
    public final GameVariant gameVariant;
    public final Player leftPlayerPosition;
    public final Player rightPlayerPosition;
    public final Player userPosition;

    public GameStartEvent(final long gameNo,
                          final GameVariant gameVariant,
                          final Player leftPlayerPosition,
                          final Player rightPlayerPosition,
                          final Player userPosition) {
        this.gameNo = gameNo;
        this.gameVariant = gameVariant;
        this.leftPlayerPosition = leftPlayerPosition;
        this.rightPlayerPosition = rightPlayerPosition;
        this.userPosition = userPosition;
    }

    @Override
    public void processForward(final SkatGameData data) {
    }

    @Override
    public void processBackward(final SkatGameData data) {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameStartEvent)) {
            return false;
        }
        final GameStartEvent that = (GameStartEvent) o;
        return Objects.equals(gameNo, that.gameNo)
                && Objects.equals(gameVariant, that.gameVariant)
                && Objects.equals(leftPlayerPosition, that.leftPlayerPosition)
                && Objects.equals(rightPlayerPosition, that.rightPlayerPosition)
                && Objects.equals(userPosition, that.userPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameNo, gameVariant, leftPlayerPosition, rightPlayerPosition, userPosition);
    }
}

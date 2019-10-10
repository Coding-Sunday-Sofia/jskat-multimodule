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
import org.jskat.util.CardList;
import org.jskat.util.Player;

import java.util.Objects;

/**
 * Event for discard skat.
 */
public final class DiscardSkatEvent extends AbstractPlayerMoveEvent {

    public final CardList discardedSkat = new CardList();

    public DiscardSkatEvent(final Player player, final CardList discardedSkat) {
        super(player);
        this.discardedSkat.addAll(discardedSkat);
    }

    @Override
    public final void processForward(final SkatGameData data) {
        data.setDiscardedSkat(player, discardedSkat);
    }

    @Override
    public final void processBackward(final SkatGameData data) {
        data.setSkatCards(new CardList());
        data.addPlayerCards(player, discardedSkat);
    }

    @Override
    protected String getMoveDetails() {
        return discardedSkat.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiscardSkatEvent)) {
            return false;
        }
        final DiscardSkatEvent that = (DiscardSkatEvent) o;
        return Objects.equals(player, that.player)
                && Objects.equals(discardedSkat, that.discardedSkat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, discardedSkat);
    }
}

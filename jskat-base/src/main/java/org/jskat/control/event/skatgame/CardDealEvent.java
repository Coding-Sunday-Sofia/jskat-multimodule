/**
 * Copyright (C) 2019 Jan Schäfer (jansch@users.sourceforge.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
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

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Event for card dealing.
 */
public final class CardDealEvent implements SkatGameEvent {

    public final Map<Player, CardList> playerCards;
    public final CardList skat;

    public CardDealEvent(final Map<Player, CardList> playerCards, final CardList skat) {
        this.playerCards = Collections.unmodifiableMap(playerCards);
        this.skat = skat.getImmutableCopy();
    }

    @Override
    public final void processForward(final SkatGameData data) {
        for (final Player player : playerCards.keySet()) {
            data.addDealtCards(player, playerCards.get(player));
        }
        data.setDealtSkatCards(skat);
    }

    @Override
    public final void processBackward(final SkatGameData data) {
        for (final Player player : playerCards.keySet()) {
            data.removeDealtCards(player, playerCards.get(player));
        }
        data.removeDealtSkatCards(skat);
    }

    @Override
    public String toString() {
        String result = "Dealt cards:\n";
        for (final Entry<Player, CardList> entry : playerCards.entrySet()) {
            result += entry.getKey() + ": " + entry.getValue() + "\n";
        }
        result += "Skat: " + skat;
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CardDealEvent)) {
            return false;
        }
        final CardDealEvent that = (CardDealEvent) o;
        return Objects.equals(playerCards, that.playerCards)
                && Objects.equals(skat, that.skat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerCards, skat);
    }
}

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
import org.jskat.util.Player;

public abstract class AbstractBidEvent extends AbstractPlayerMoveEvent {

    public final int bid;

    public AbstractBidEvent(final Player player, final int bid) {
        super(player);
        this.bid = bid;
    }

    @Override
    public final void processForward(final SkatGameData data) {
        data.addPlayerBid(player, bid);
    }

    @Override
    public final void processBackward(final SkatGameData data) {
        data.removeLastPlayerBid(player);
    }

    @Override
    protected final String getMoveDetails() {
        return Integer.toString(bid);
    }
}
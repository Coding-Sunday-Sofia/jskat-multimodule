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
import org.jskat.util.Player;

import java.util.Objects;

/**
 * Event for calling contra.
 */
public final class ReEvent extends AbstractPlayerMoveEvent {

    public ReEvent(final Player player) {
        super(player);
    }

    @Override
    public final void processForward(final SkatGameData data) {
        data.setRe(true);
    }

    @Override
    public final void processBackward(final SkatGameData data) {
        data.setRe(false);
    }

    @Override
    protected String getMoveDetails() {
        return "re";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReEvent)) {
            return false;
        }
        final ReEvent that = (ReEvent) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }
}

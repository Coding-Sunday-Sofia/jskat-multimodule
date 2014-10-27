/**
 * Copyright (C) 2003 Jan Schäfer (jansch@users.sourceforge.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
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

/**
 * Event for game start.
 */
public final class GameStartEvent implements SkatGameEvent {

	private final Player dealer;

	public GameStartEvent(Player dealer) {
		this.dealer = dealer;
	}

	@Override
	public final void processForward(SkatGameData data) {
		data.setDealer(dealer);
	}

	@Override
	public final void processBackward(SkatGameData data) {
		data.setDealer(null);
	}
}

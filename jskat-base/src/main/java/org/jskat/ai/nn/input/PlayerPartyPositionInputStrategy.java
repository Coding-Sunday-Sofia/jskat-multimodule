/**
 * Copyright (C) 2018 Jan Schäfer (jansch@users.sourceforge.net)
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
package org.jskat.ai.nn.input;

import org.jskat.player.ImmutablePlayerKnowledge;
import org.jskat.util.Card;
import org.jskat.util.Player;

public class PlayerPartyPositionInputStrategy extends AbstractPositionInputStrategy {

	@Override
	public double[] getNetworkInput(final ImmutablePlayerKnowledge knowledge, final Card cardToPlay) {

		final double[] result = getEmptyInputs();

		for (final Player player : knowledge.getPlayerPartyMembers()) {
			setPositionInput(result, player);
		}

		return result;
	}
}

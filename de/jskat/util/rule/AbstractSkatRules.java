/*

@ShortLicense@

Authors: @JS@
         @MJL@

Released: @ReleaseDate@

 */

package de.jskat.util.rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jskat.data.Trick;
import de.jskat.util.Card;
import de.jskat.util.GameType;
import de.jskat.util.Player;

/**
 * Abstract implementation of skat rules
 */
public abstract class AbstractSkatRules implements BasicSkatRules {

	private static Log log = LogFactory.getLog(AbstractSkatRules.class);

	@Override
	public Player calculateTrickWinner(GameType gameType, Trick trick) {

		Player trickWinner = null;
		Card first = trick.getFirstCard();
		Card second = trick.getSecondCard();
		Card third = trick.getThirdCard();
		Player trickForeHand = trick.getForeHand();

		if (isCardBeatsCard(gameType, first, second)) {

			if (isCardBeatsCard(gameType, second, third)) {
				// trick winner is hind hand
				trickWinner = trickForeHand.getRightNeighbor();
			} else {
				// trick winner is middle hand
				trickWinner = trickForeHand.getLeftNeighbor();
			}
		} else {

			if (isCardBeatsCard(gameType, first, third)) {
				// trick winner is hind hand
				trickWinner = trickForeHand.getRightNeighbor();
			} else {
				// trick winner is fore hand
				trickWinner = trickForeHand;
			}
		}

		log.debug("Trick fore hand: " + trickForeHand); //$NON-NLS-1$
		log.debug("Trick winner: " + trickWinner); //$NON-NLS-1$

		return trickWinner;
	}
}

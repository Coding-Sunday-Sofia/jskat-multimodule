/**
 * JSkat - A skat program written in Java
 * by Jan Schäfer, Markus J. Luzius, Daniel Loreck and Sascha Laurien
 *
 * Version 0.14.0-SNAPSHOT
 * Copyright (C) 2013-11-02
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jskat.ai.newalgorithm;

import org.apache.log4j.Logger;
import org.jskat.ai.newalgorithm.exception.IllegalMethodException;
import org.jskat.util.Card;
import org.jskat.util.CardList;
import org.jskat.util.GameType;
import org.jskat.util.Player;
import org.jskat.util.Rank;
import org.jskat.util.Suit;

public class AlgorithmOpponentGrand extends AbstractAlgorithmAI {
	private static final Logger log = Logger
			.getLogger(AlgorithmOpponentGrand.class);

	AlgorithmOpponentGrand(final AlgorithmAI p, GameType pGameType) {
		super(p, pGameType);

		log.debug("Defining player <" + myPlayer.getPlayerName() + "> as "
				+ this.getClass().getName());
	}

	@Override
	protected Card startGame() {
		log.debug("OpponentGrand starts Game: " + knowledge.getPlayerPosition());

		return playStartGameCard(knowledge.getOwnCards(),
				knowledge.getTrickCards(), oPlayedCards, oNotOpponentCards,
				oSituation, knowledge.getDeclarer());
	}

	@Override
	protected Card playForehandCard() {
		log.debug("OpponentGrand plays Forehand-Card: "
				+ knowledge.getPlayerPosition());

		return playForehandCard(knowledge.getOwnCards(),
				knowledge.getTrickCards(), oPlayedCards, oNotOpponentCards,
				oSituation, knowledge.getDeclarer());
	}

	@Override
	protected Card playMiddlehandCard() {
		log.debug("OpponentGrand plays Middlehand-Card: "
				+ knowledge.getPlayerPosition());

		return playMiddlehandCard(knowledge.getOwnCards(),
				knowledge.getTrickCards(), oPlayedCards, oNotOpponentCards,
				oSituation, knowledge.getDeclarer());
	}

	@Override
	protected Card playRearhandCard() {
		log.debug("OpponentGrand plays Rearhand-Card: "
				+ knowledge.getPlayerPosition());

		return playRearhandCard(knowledge.getOwnCards(),
				knowledge.getTrickCards(), oPlayedCards, oNotOpponentCards,
				oSituation, knowledge.getPlayerPosition(),
				knowledge.getDeclarer());
	}

	@Override
	public CardList discardSkat(BidEvaluator bidEvaluator) {
		throw new IllegalMethodException(
				"AlgorithmOpponentGrand has nothing to discard!");
	}

	// static methods for creating JUnit-tests and test cardplaybehavior
	public static Card playStartGameCard(CardList pCards, CardList pTrickCards,
			CardList pPlayedCards, CardList pNotOpponentCards,
			Situation pSituation, Player pDeclarer) {
		return playForehandCard(pCards, pTrickCards, pPlayedCards,
				pNotOpponentCards, pSituation, pDeclarer);
	}

	public static Card playForehandCard(CardList pCards, CardList pTrickCards,
			CardList pPlayedCards, CardList pNotOpponentCards,
			Situation pSituation, Player pDeclarer) {
		pCards.sort(pSituation.getGameType());
		boolean tDeclarerInMiddle = Player.FOREHAND.getLeftNeighbor() == pDeclarer;

		CardList possibleCards = new CardList();

		for (Suit s : Suit.values()) {
			if (!pCards.hasSuit(GameType.GRAND, s)) {
				continue;
			}

			Card possibleHighCard = pCards.get(pCards.getFirstIndexOfSuit(s,
					false)); // highest Card
			Card possibleLowCard = pCards.get(pCards.getLastIndexOfSuit(s,
					false)); // lowest Card

			// Wenn hoechste Karte und weniger als 5 Karten bislang gespielt
			// wurden -> spielen
			if (Helper.isHighestSuitCard(possibleHighCard, pPlayedCards,
					pTrickCards) && pPlayedCards.getSuitCount(s, false) < 5) {
				return possibleHighCard;
			}
			// ansonsten nur spielen, wenn Solo-Spieler in der Mitte
			else if (tDeclarerInMiddle) {
				// Wenn Mitspieler Blank ist
				if (pSituation.isRightPlayerBlankOnColor(s)) {
					return possibleHighCard;
				}
				possibleCards.add(possibleLowCard);
			} else if (!tDeclarerInMiddle
					&& pSituation.isLeftPlayerBlankOnColor(s)) {
				return possibleHighCard;
			}
		}

		if (!possibleCards.isEmpty()) {
			return playRandomCard(possibleCards);
		}

		possibleCards = getPossibleMaxValueCards(pCards, 0);
		if (!possibleCards.isEmpty()) {
			return playRandomCard(possibleCards);
		}
		possibleCards = getPossibleMaxValueCards(pCards, 3);
		if (!possibleCards.isEmpty()) {
			return playRandomCard(possibleCards);
		}
		possibleCards = getPossibleMaxValueCards(pCards, 4);
		if (!possibleCards.isEmpty()) {
			return playRandomCard(possibleCards);
		}

		return getRandomAllowedCard(pCards, null, pSituation.getGameType());
	}

	public static Card playMiddlehandCard(CardList pCards,
			CardList pTrickCards, CardList pPlayedCards,
			CardList pNotOpponentCards, Situation pSituation, Player pDeclarer) {
		pCards.sort(GameType.CLUBS);
		boolean tDeclarerInForehand = Player.MIDDLEHAND.getRightNeighbor() == pDeclarer;
		Card tForehandCard = pTrickCards.get(0);
		Suit tSuit = tForehandCard.getSuit();

		CardList possibleCards = new CardList();

		// Solo-Spieler hat die erste Karte gelegt
		if (tDeclarerInForehand) {
			// Wenn Bube gespielt
			if (tForehandCard.getRank() == Rank.JACK) {
				// Wenn Bube vorhanden
				if (pCards.get(0).getRank() == Rank.JACK) {
					// Wenn schlagbar, dann den niedrigsten Buben spielen, der
					// die Vorhand schlaegt
					if (pCards.get(0).beats(GameType.GRAND, tForehandCard)) {
						if (pCards.get(1).beats(GameType.GRAND, tForehandCard)) {
							if (pCards.get(2).beats(GameType.GRAND,
									tForehandCard)) {
								return pCards.get(2);
							}
							return pCards.get(1);
						}
						return pCards.get(0);
					}
					// Sonst niedrigsten Buben spielen
					return pCards.get(Helper.countJacks(pCards) - 1);
				}

				// Wenn der Mitspieler noch einen Buben haben koennte, der
				// besser ist
				if (Helper.countJacks(pNotOpponentCards) != 3
						&& (!pNotOpponentCards.contains(Card.CJ)
								&& tForehandCard != Card.CJ || pNotOpponentCards
								.contains(Card.CJ)
								&& !pNotOpponentCards.contains(Card.SJ)
								&& tForehandCard != Card.SJ)) {
					CardList tPossibleCards = new CardList();
					// Butter eine hohe Karte in der Farbe in der der
					// Solo-Spieler blank ist
					for (Suit s : pSituation.getRightPlayerBlankSuits()) {
						if (pCards.getSuitCount(s, false) == 0) {
							continue;
						}
						Card lPossibleHighCard = pCards.get(pCards
								.getFirstIndexOfSuit(s, false));
						if (lPossibleHighCard.getPoints() >= 10) {
							return lPossibleHighCard;
						} else if (lPossibleHighCard.getPoints() >= 4) {
							tPossibleCards.add(lPossibleHighCard);
						}
					}
					if (!tPossibleCards.isEmpty()) {
						return playRandomCard(tPossibleCards);
					}
					// Butter eine andere hohe Karte
					for (Suit s : Suit.values()) {
						if (pCards.getSuitCount(s, false) == 0) {
							continue;
						}
						Card lPossibleHighCard = pCards.get(pCards
								.getFirstIndexOfSuit(s, false));
						if (lPossibleHighCard.getPoints() >= 10) {
							return lPossibleHighCard;
						} else if (lPossibleHighCard.getPoints() >= 4) {
							tPossibleCards.add(lPossibleHighCard);
						}
					}
				}
			}
			// Wenn Farbe gespielt
			else if (tForehandCard.getRank() != Rank.JACK) {
				Card possibleHighCard = pCards.get(pCards.getFirstIndexOfSuit(
						tSuit, false)); // highest Card
				Card possibleLowCard = pCards.get(pCards.getLastIndexOfSuit(
						tSuit, false)); // lowest Card

				// Wenn Karten der Farbe auf der Hand
				if (pCards.hasSuit(pSituation.getGameType(), tSuit)) {
					if (pCards.getSuitCount(tSuit, false) == 1) {
						return possibleHighCard;
					}
					// Wenn schlagbar
					if (possibleHighCard.beats(pSituation.getGameType(),
							tForehandCard)) {
						// Wenn die zweithÃƒÂ¶chste Karte anschliessend
						// unschlagbar ist
						if (Helper.isHighestSuitCard(pCards.get(pCards
								.getFirstIndexOfSuit(tSuit, false) + 1),
								pSituation.getGameType(), pNotOpponentCards,
								pTrickCards)
								|| pNotOpponentCards.getSuitCount(tSuit, false) > 2) {
							return possibleHighCard;
						}
						for (int i = pCards.getLastIndexOfSuit(tSuit, false); i >= pCards
								.getFirstIndexOfSuit(tSuit, false); i--) {
							if (pCards.get(i).beats(pSituation.getGameType(),
									tForehandCard)) {
								return pCards.get(i);
							}
						}
					}
					if (Helper.getSuitCardsToBinary(pNotOpponentCards, tSuit)
							+ Helper.getSuitCardsToBinary(pTrickCards, tSuit) == 127) {
						return possibleHighCard;
					}
					// Wenn nicht schlagbar
					return possibleLowCard;
				}
				// Spieler hat die Farbe blank
				// Wenn Bube hat
				if (pCards.get(0).getRank() == Rank.JACK) {
					// >=10 Punkte niedrigsten Buben spielen
					if (tForehandCard.getPoints() >= 10) {
						pCards.get(Helper.countJacks(pCards) - 1);
					}
				}
				// Wenn die Karte die Hoechste ist || Mitspieler keine Karte der
				// Farbe hat -> Lusche werfen
				if (Helper.isHighestSuitCard(tForehandCard,
						pSituation.getGameType(), pPlayedCards, pTrickCards)
						|| pSituation.isLeftPlayerBlankOnColor(tSuit)) {
					return getLowValueCard(pCards, pSituation, tForehandCard,
							pPlayedCards, pTrickCards);
				}
			}
			// Blank Suites vom Solo-Spieler freispielen
			for (Suit s : pSituation.getRightPlayerBlankSuits()) {
				Card lPossibleLowCard = pCards.get(pCards.getLastIndexOfSuit(s,
						false));
				if (lPossibleLowCard != null
						&& lPossibleLowCard.getPoints() <= 4) {
					return lPossibleLowCard;
				}
			}
			if (!possibleCards.isEmpty()) {
				return playRandomCard(possibleCards);
			}

			// Lusche werfen
			return getLowValueCard(pCards, pSituation, tForehandCard,
					pPlayedCards, pTrickCards);
		}
		// Solo-Spieler sitzt hinter dem Spieler
		else {
			// Solo-Spieler ist blank auf der Farbe oder hat eine moegliche
			// Karte -> wahrscheinlich gedrueckt!
			if (pSituation.getLeftPlayerBlankSuits().contains(tSuit)
					|| pNotOpponentCards.getSuitCount(tSuit, false) >= 6) {
				return pCards.get(pCards.getLastIndexOfSuit(tSuit, false));
			}

			// Wenn Spieler bedienen muss
			if (pCards.getSuitCount(tSuit, false) > 0) {
				Card possibleHighCard = pCards.get(pCards.getFirstIndexOfSuit(
						tSuit, false)); // highest Card
				Card possibleLowCard = pCards.get(pCards.getLastIndexOfSuit(
						tSuit, false)); // lowest Card
				// Hat der Spieler die hoechste Karte -> spielen
				if (Helper.isHighestSuitCard(possibleHighCard, pPlayedCards,
						pTrickCards)) {
					return possibleHighCard;
				}
				return possibleLowCard;
			}

			// Spieler kann nicht bedienen
			// Vorhandkarte ist die hÃƒÂ¶chste
			if (Helper.isHighestSuitCard(tForehandCard, pPlayedCards,
					pTrickCards)) {
				// Solo-Spieler ist blank auf der Farbe
				if (pSituation.isLeftPlayerBlankOnColor(tSuit)) {
					// Solo-Spieler hat keinen Buben mehr -> Buttern
					if (Helper.countJacks(pPlayedCards)
							+ Helper.countJacks(pCards) == 4
							|| !pSituation.leftPlayerHasTrump()) {
						return getHighValueCard(pCards, pSituation,
								tForehandCard);
					} else {
						possibleCards = getPossibleMaxValueCards(pCards, 0);
						if (!possibleCards.isEmpty()) {
							return playRandomCard(possibleCards);
						}
						possibleCards = getPossibleMaxValueCards(pCards, 3);
						if (!possibleCards.isEmpty()) {
							return playRandomCard(possibleCards);
						}
						possibleCards = getPossibleMaxValueCards(pCards, 4);
						if (!possibleCards.isEmpty()) {
							return playRandomCard(possibleCards);
						}
					}
				}
				// Solo-Spieler hat moeglicherweise noch eine Karte der Farbe ->
				// Buttern
				else {
					return getHighValueCard(pCards, pSituation, tForehandCard);
				}
			}
		}

		return getRandomAllowedCard(pCards, tForehandCard,
				pSituation.getGameType());
	}

	public static Card playRearhandCard(CardList pCards, CardList pTrickCards,
			CardList pPlayedCards, CardList pNotOpponentCards,
			Situation pSituation, Player pPlayerPosition, Player pDeclarer) {
		pCards.sort(pSituation.getGameType());
		Card tForehandCard = pTrickCards.get(0);
		Card tMiddlehandCard = pTrickCards.get(1);

		Card tCardToBeat = tForehandCard;
		if (tMiddlehandCard.beats(pSituation.getGameType(), tCardToBeat)) {
			tCardToBeat = tMiddlehandCard;
		}

		// ArrayList<Suit> tDeclarerBlankSuits =
		// pSituation.getLeftPlayerBlankSuits();
		// if(Player.REARHAND.getRightNeighbor() == pDeclarer)
		// tDeclarerBlankSuits = pSituation.getRightPlayerBlankSuits();
		// Grand spiel: Hinterhand karte muss gespielt werden
		// Erste Karte ist ein Bube
		if (tForehandCard.getRank() == Rank.JACK) {
			// Wenn ein Bube vorhanden
			if (pCards.get(0).getRank() == Rank.JACK) {
				// Wenn der Stich dem Solo-Spieler gehÃƒÂ¶rt -> versuchen zu
				// stechen
				if (tMiddlehandCard.beats(pSituation.getGameType(),
						tForehandCard) == (pPlayerPosition.getRightNeighbor() == pDeclarer)) {
					if (pCards.get(0).beats(GameType.GRAND, tForehandCard)) {
						if (pCards.get(1).beats(GameType.GRAND, tForehandCard)) {
							if (pCards.get(2).beats(GameType.GRAND,
									tForehandCard)) {
								return pCards.get(2);
							}
							return pCards.get(1);
						}
						return pCards.get(0);
					}
					// Sonst niedrigsten Buben spielen
					return pCards.get(Helper.countJacks(pCards) - 1);
				}
			}
			// Kein Buben im eigenen Blatt
			// Stich gehÃƒÂ¶rt dem Solo-Spieler -> Lusche werfen
			if (tMiddlehandCard.beats(pSituation.getGameType(), tForehandCard) == (pPlayerPosition
					.getRightNeighbor() == pDeclarer)) {
				return getLowValueCard(pCards, pSituation, tForehandCard,
						pPlayedCards, pTrickCards);
			}
			// Mitspieler gehÃƒÂ¶rt der Stich -> Buttern
			return getHighValueCard(pCards, pSituation, tForehandCard);
		}
		// Farbe gespielt
		else {
			// Muss bedienen
			if (pCards.hasSuit(pSituation.getGameType(),
					tForehandCard.getSuit())) {
				Card possibleHighCard = pCards.get(pCards.getFirstIndexOfSuit(
						tForehandCard.getSuit(), false)); // highest Card
				Card possibleLowCard = pCards.get(pCards.getLastIndexOfSuit(
						tForehandCard.getSuit(), false)); // lowest Card
				// Solo-Spieler hat den Stich
				if (tMiddlehandCard.beats(pSituation.getGameType(),
						tForehandCard) == (pPlayerPosition.getRightNeighbor() == pDeclarer)) {
					// Wenn selbst schlagbar -> Schlagen
					if (possibleHighCard.beats(pSituation.getGameType(),
							tCardToBeat)) {
						return possibleHighCard;
					}
					// -> Karte mit niedrigem Wert
					return possibleLowCard;
				}
				// Mitspieler hat den Stich -> Karte mit hohem Wert
				return possibleHighCard;
			}
			// Kann nicht bedienen
			// Solospieler hat den Stich
			// Hat Bube && viele Punkte -> Stechen
			// Lusche werfen
			if (tMiddlehandCard.beats(pSituation.getGameType(), tForehandCard) == (pPlayerPosition
					.getRightNeighbor() == pDeclarer)) {
				if (pCards.get(0).getRank() == Rank.JACK
						&& pTrickCards.getTotalValue() > 9) {
					return pCards.get(Helper.countJacks(pCards) - 1);
				}
				return getLowValueCard(pCards, pSituation, tForehandCard,
						pPlayedCards, pTrickCards);
			}
			// Mitspieler hat den Stich -> Buttern
			return getHighValueCard(pCards, pSituation, tForehandCard);
		}
	}

	// Buttern
	public static Card getHighValueCard(CardList pCards, Situation pSituation,
			Card pInitialCard) {
		CardList possibleCards = new CardList();

		for (Suit s : Suit.values()) {
			int lSuitCount = pCards.getSuitCount(s, false);
			if (lSuitCount == 0) {
				continue;
			}

			Card possibleHighCard = pCards.get(pCards.getFirstIndexOfSuit(s,
					false)); // highest Card

			if (lSuitCount == 1) {
				if (possibleHighCard.getPoints() > 4) {
					return possibleHighCard;
				} else {
					possibleCards.add(possibleHighCard);
				}
			}
			if (pSituation.isLeftPlayerBlankOnColor(s)) {
				possibleCards.add(possibleHighCard);
			}
			if (lSuitCount >= 3) {
				possibleCards.add(possibleHighCard);
			}
		}
		if (!possibleCards.isEmpty()) {
			return playRandomCard(possibleCards);
		} else {
			return getRandomAllowedCard(pCards, pInitialCard,
					pSituation.getGameType());
		}
	}

	// Lusche Werfen
	public static Card getLowValueCard(CardList pCards, Situation pSituation,
			Card pInitialCard, CardList pPlayedCards, CardList pTrickCards) {
		CardList possibleCards = new CardList();

		for (Suit s : Suit.values()) {
			int lSuitCount = pCards.getSuitCount(s, false);
			if (lSuitCount == 0) {
				continue;
			}

			Card possibleHighCard = pCards.get(pCards.getFirstIndexOfSuit(s,
					false)); // highest Card
			Card possibleLowCard = pCards.get(pCards.getLastIndexOfSuit(s,
					false)); // lowest Card

			if (lSuitCount == 1 && possibleHighCard.getPoints() < 10) {
				return possibleHighCard;
			}
			if (lSuitCount == 2
					&& Helper.isHighestSuitCard(possibleHighCard, pPlayedCards,
							pTrickCards)) {
				return possibleLowCard;
			}
			if (lSuitCount >= 3) {
				possibleCards.add(possibleLowCard);
			}
		}
		if (!possibleCards.isEmpty()) {
			return playRandomCard(possibleCards);
		} else {
			return getRandomAllowedCard(pCards, pInitialCard,
					pSituation.getGameType());
		}
	}
}
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
package org.jskat.player;

import org.jskat.AbstractJSkatTest;
import org.jskat.data.GameAnnouncement;
import org.jskat.data.GameAnnouncement.GameAnnouncementFactory;
import org.jskat.data.Trick;
import org.jskat.util.Card;
import org.jskat.util.GameType;
import org.jskat.util.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for class PlayerKnowledge
 */
public class PlayerKnowledgeTest extends AbstractJSkatTest {

    private static final Logger log = LoggerFactory
            .getLogger(PlayerKnowledgeTest.class);

    PlayerKnowledge knowledge;
    Set<Card> playerCards;
    Set<Card> otherCards;

    /**
     * Setting up all variables
     */
    @BeforeEach
    public void setUp() {

        knowledge = new PlayerKnowledge();

        knowledge.setPlayerPosition(Player.MIDDLEHAND);

        // Cards for the player
        playerCards = new HashSet<>();
        playerCards.add(Card.CA);
        playerCards.add(Card.CQ);
        playerCards.add(Card.C8);
        playerCards.add(Card.ST);
        playerCards.add(Card.SQ);
        playerCards.add(Card.DT);
        playerCards.add(Card.DK);
        playerCards.add(Card.D7);
        playerCards.add(Card.HJ);
        playerCards.add(Card.HA);

        // other cards
        otherCards = new HashSet<Card>();
        for (final Card card : Card.values()) {
            if (!playerCards.contains(card)) {
                otherCards.add(card);
            }
        }
    }

    /**
     * Test the player knowledge after fresh initialization
     */
    @Test
    public void testInitialization() {

        assertTrue(knowledge.getOwnCards().isEmpty());
        assertEquals(0, knowledge.getTrumpCount());

        for (final Card card : playerCards) {
            assertTrue(knowledge.couldHaveCard(Player.FOREHAND, card), Player.FOREHAND + " could have card " + card);
            assertTrue(knowledge.couldHaveCard(Player.MIDDLEHAND, card), Player.MIDDLEHAND + " could have card " + card);
            assertTrue(knowledge.couldHaveCard(Player.REARHAND, card), Player.REARHAND + " could have card " + card);
            assertFalse(knowledge.hasCard(Player.FOREHAND, card), "No certain information about Card " + card + " on " + Player.FOREHAND);
            assertFalse(knowledge.hasCard(Player.MIDDLEHAND, card), "No certain information about Card " + card + " on " + Player.MIDDLEHAND);
            assertFalse(knowledge.hasCard(Player.REARHAND, card), "No certain information about Card " + card + " on " + Player.REARHAND);
            assertTrue(knowledge.couldLieInSkat(card), "Card " + card + " could lie in skat.");
        }
        for (final Card card : otherCards) {
            assertTrue(knowledge.couldHaveCard(Player.FOREHAND, card), "Card " + card + " should be on " + Player.FOREHAND);
            assertTrue(knowledge.couldHaveCard(Player.MIDDLEHAND, card), "Card " + card + " should be on " + Player.MIDDLEHAND);
            assertTrue(knowledge.couldHaveCard(Player.REARHAND, card), "Card " + card + " should be on " + Player.REARHAND);
            assertFalse(knowledge.hasCard(Player.FOREHAND, card), "No certain information about Card " + card + " on " + Player.FOREHAND);
            assertFalse(knowledge.hasCard(Player.MIDDLEHAND, card), "No certain information about Card " + card + " on " + Player.MIDDLEHAND);
            assertFalse(knowledge.hasCard(Player.REARHAND, card), "No certain information about Card " + card + " on " + Player.REARHAND);
            assertTrue(knowledge.couldLieInSkat(card), "Card " + card + " could lie in skat.");
        }
    }

    /**
     * Test player knowledge after dealing
     */
    @Test
    public void testDealing() {

        dealPlayerCards();

        assertEquals(10, knowledge.getOwnCards().size());
        assertEquals(0, knowledge.getTrumpCount());

        for (final Card card : playerCards) {
            assertFalse(knowledge.couldHaveCard(Player.FOREHAND, card), Player.FOREHAND + " could not have card " + card);
            assertTrue(knowledge.couldHaveCard(Player.MIDDLEHAND, card), Player.MIDDLEHAND + " could have card " + card);
            assertFalse(knowledge.couldHaveCard(Player.REARHAND, card), Player.REARHAND + " could not have card " + card);
            assertFalse(knowledge.hasCard(Player.FOREHAND, card), "No certain information about Card " + card + " on " + Player.FOREHAND);
            assertTrue(knowledge.hasCard(Player.MIDDLEHAND, card), Player.MIDDLEHAND + " could have card " + card);
            assertFalse(knowledge.hasCard(Player.REARHAND, card), "No certain information about Card " + card + " on " + Player.REARHAND);
            assertFalse(knowledge.couldLieInSkat(card), "Card " + card + " could not lie in skat.");
        }
        for (final Card card : otherCards) {
            assertCouldHaveCard(Player.FOREHAND, card);
            assertCouldNotHaveCard(Player.MIDDLEHAND, card);
            assertCouldHaveCard(Player.REARHAND, card);
            assertHasNotCard(Player.FOREHAND, card);
            assertHasNotCard(Player.MIDDLEHAND, card);
            assertHasNotCard(Player.REARHAND, card);
            assertTrue(knowledge.couldLieInSkat(card), "Card " + card + " could lie in skat.");
        }
    }

    @Test
    public void testCardPlay_SuitGame() {

        dealPlayerCards();

        final GameAnnouncementFactory factory = GameAnnouncement.getFactory();
        factory.setGameType(GameType.CLUBS);
        knowledge.setGame(factory.getAnnouncement());
        knowledge.setDeclarer(Player.FOREHAND);

        knowledge.setNextTrick(0, Player.FOREHAND);

        final Card foreHandCard = Card.SA;
        final Card middleHandCard = Card.SQ;
        final Card rearHandCard = Card.H7;

        // Fore hand plays
        assertCouldHaveCard(Player.FOREHAND, Card.SA);
        assertCouldNotHaveCard(Player.FOREHAND, Card.ST);
        assertCouldHaveCard(Player.FOREHAND, Card.SK);
        assertCouldNotHaveCard(Player.FOREHAND, Card.SQ);
        assertCouldHaveCard(Player.FOREHAND, Card.S9);
        assertCouldHaveCard(Player.FOREHAND, Card.S8);
        assertCouldHaveCard(Player.FOREHAND, Card.S7);

        knowledge.setCardPlayed(Player.FOREHAND, foreHandCard);

        assertCouldNotHaveCard(Player.FOREHAND, Card.SA);
        assertCouldNotHaveCard(Player.FOREHAND, Card.ST);
        assertCouldHaveCard(Player.FOREHAND, Card.SK);
        assertCouldNotHaveCard(Player.FOREHAND, Card.SQ);
        assertCouldHaveCard(Player.FOREHAND, Card.S9);
        assertCouldHaveCard(Player.FOREHAND, Card.S8);
        assertCouldHaveCard(Player.FOREHAND, Card.S7);

        assertCouldNotHaveCard(Player.MIDDLEHAND, foreHandCard);
        assertCouldNotHaveCard(Player.REARHAND, foreHandCard);

        // Middle hand plays
        assertCouldNotHaveCard(Player.MIDDLEHAND, Card.SA);
        assertCouldHaveCard(Player.MIDDLEHAND, Card.ST);
        assertCouldNotHaveCard(Player.MIDDLEHAND, Card.SK);
        assertCouldHaveCard(Player.MIDDLEHAND, Card.SQ);
        assertCouldNotHaveCard(Player.MIDDLEHAND, Card.S9);
        assertCouldNotHaveCard(Player.MIDDLEHAND, Card.S8);
        assertCouldNotHaveCard(Player.MIDDLEHAND, Card.S7);

        knowledge.setCardPlayed(Player.MIDDLEHAND, middleHandCard);

        assertCouldNotHaveCard(Player.FOREHAND, middleHandCard);
        assertCouldNotHaveCard(Player.MIDDLEHAND, middleHandCard);
        assertCouldNotHaveCard(Player.REARHAND, middleHandCard);

        // Rear hand plays
        assertCouldHaveCard(Player.REARHAND, Card.CJ);
        assertCouldHaveCard(Player.REARHAND, Card.SJ);
        assertCouldNotHaveCard(Player.REARHAND, Card.HJ);
        assertCouldHaveCard(Player.REARHAND, Card.DJ);
        assertCouldNotHaveCard(Player.REARHAND, Card.SA);
        assertCouldNotHaveCard(Player.REARHAND, Card.ST);
        assertCouldHaveCard(Player.REARHAND, Card.SK);
        assertCouldNotHaveCard(Player.REARHAND, Card.SQ);
        assertCouldHaveCard(Player.REARHAND, Card.S9);
        assertCouldHaveCard(Player.REARHAND, Card.S8);
        assertCouldHaveCard(Player.REARHAND, Card.S7);

        knowledge.setCardPlayed(Player.REARHAND, rearHandCard);

        assertCouldNotHaveCard(Player.FOREHAND, rearHandCard);
        assertCouldNotHaveCard(Player.MIDDLEHAND, rearHandCard);
        assertCouldNotHaveCard(Player.REARHAND, rearHandCard);

        // Rear hand has not followed suit
        assertCouldHaveCard(Player.REARHAND, Card.CJ);
        assertCouldHaveCard(Player.REARHAND, Card.SJ);
        assertCouldNotHaveCard(Player.REARHAND, Card.HJ);
        assertCouldHaveCard(Player.REARHAND, Card.DJ);
        assertCouldNotHaveCard(Player.REARHAND, Card.SA);
        assertCouldNotHaveCard(Player.REARHAND, Card.ST);
        assertCouldNotHaveCard(Player.REARHAND, Card.SK);
        assertCouldNotHaveCard(Player.REARHAND, Card.SQ);
        assertCouldNotHaveCard(Player.REARHAND, Card.S9);
        assertCouldNotHaveCard(Player.REARHAND, Card.S8);
        assertCouldNotHaveCard(Player.REARHAND, Card.S7);
    }

    private void dealPlayerCards() {

        // set up player cards
        knowledge.setPlayerPosition(Player.MIDDLEHAND);
        knowledge.addOwnCards(playerCards);
    }

    private void assertCouldHaveCard(final Player player, final Card card) {
        assertTrue(knowledge.couldHaveCard(player, card), player + " could have card " + card);
    }

    private void assertCouldNotHaveCard(final Player player, final Card card) {
        assertFalse(knowledge.couldHaveCard(player, card), player + " could not have card " + card);
    }

    private void assertHasCard(final Player player, final Card card) {
        assertTrue(knowledge.hasCard(player, card), player + " should have card " + card);
    }

    private void assertHasNotCard(final Player player, final Card card) {
        assertFalse(knowledge.hasCard(player, card), player + " should not have card " + card);
    }

    private void assertCouldLieInSkat(final Card card) {
        assertTrue(knowledge.couldLieInSkat(card), card + " could lie in skat");
    }

    private void assertCouldNotLieInSkat(final Card card) {
        assertFalse(knowledge.couldLieInSkat(card), card + " could not lie in skat");
    }

    /**
     * Tests setting of tricks
     */
    @Test
    public void testSetTrick() {

        knowledge.setNextTrick(0, Player.FOREHAND);

        assertEquals(0, knowledge.getCompletedTricks().size());

        knowledge.addCompletedTrick(new Trick(0, Player.FOREHAND));

        assertEquals(1, knowledge.getCompletedTricks().size());
    }
}

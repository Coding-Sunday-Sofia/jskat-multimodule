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
package org.jskat.control.iss;

import org.jskat.data.GameAnnouncement;
import org.jskat.data.iss.ChatMessage;
import org.jskat.util.Card;
import org.jskat.util.CardList;
import org.jskat.util.GameType;

/**
 * Generator for ISS messages
 */
class MessageGenerator {

    String loginName;

    MessageGenerator(final String loginName) {
        this.loginName = loginName;
    }

    String getLoginAndPasswordMessage(final String password) {
        return "login " + loginName + " " + password;
    }

    static String getPasswordMessage(final String password) {

        return password;
    }

    String getChatMessage(final ChatMessage message) {
        // FIXME (jan 30.01.2011) refactor ChatMessage with ChatMessageType
        if ("Lobby".equals(message.getChatName())) {
            return "yell " + message.getMessage();
        } else {
            return "table " + message.getChatName() + ' ' + loginName + " tell " + message.getMessage();
        }
    }

    static String getTableCreationMessage() {

        // TODO table creation for four player
        return "create / 3";
    }

    static String getJoinTableMessage(final String tableName) {

        return "join " + tableName;
    }

    static String getObserveTableMessage(final String tableName) {

        return "observe " + tableName;
    }

    String getLeaveTableMessage(final String tableName) {

        return "table " + tableName + ' ' + loginName + " leave";
    }

    String getReadyMessage(final String tableName) {

        return "table " + tableName + ' ' + loginName + " ready";
    }

    String getTalkEnabledMessage(final String tableName) {

        return "table " + tableName + ' ' + loginName + " gametalk";
    }

    String getTableSeatChangeMessage(final String tableName) {

        return "table " + tableName + ' ' + loginName + " 34";
    }

    String getInvitePlayerMessage(final String tableName, final String invitee) {

        return "table " + tableName + ' ' + loginName + " invite " + invitee;
    }

    String getPassMoveMessage(final String tableName) {

        return "table " + tableName + ' ' + loginName + " play p";
    }

    String getHoldBidMoveMessage(final String tableName) {

        return "table " + tableName + ' ' + loginName + " play y";
    }

    String getBidMoveMessage(final String tableName, final int bidValue) {

        return "table " + tableName + ' ' + loginName + " play " + bidValue;
    }

    String getPickUpSkatMoveMessage(final String tableName) {
        return "table " + tableName + ' ' + loginName + " play s";
    }

    String getGameAnnouncementMoveMessage(final String tableName,
                                          final GameAnnouncement gameAnnouncement) {

        String gameAnnouncementString = getGameTypeString(
                gameAnnouncement.getGameType(), gameAnnouncement.isHand(),
                gameAnnouncement.isOuvert(), gameAnnouncement.isSchneider(),
                gameAnnouncement.isSchwarz());

        if (!gameAnnouncement.isHand()) {

            final CardList skat = gameAnnouncement.getDiscardedCards();
            gameAnnouncementString += "." + getIssCardString(skat.get(0)) + "."
                    + getIssCardString(skat.get(1));
        }

        return "table " + tableName + ' ' + loginName + " play " + gameAnnouncementString;
    }

    private static String getGameTypeString(final GameType gameType,
                                            final boolean hand, final boolean ouvert, final boolean schneider,
                                            final boolean schwarz) {

        String result = getGameTypeString(gameType);

        if (hand) {
            result += "H";
        }

        if (ouvert) {
            result += "O";
        }

        if (schneider) {
            result += "S";
        }

        if (schwarz) {
            result += "Z";
        }

        return result;
    }

    private static String getGameTypeString(final GameType gameType) {
        switch (gameType) {
            case CLUBS:
                return "C";
            case SPADES:
                return "S";
            case HEARTS:
                return "H";
            case DIAMONDS:
                return "D";
            case NULL:
                return "N";
            case GRAND:
                return "G";
            default:
                // FIXME (jan 02.11.2010) Ramsch games are not allowed on ISS
                return null;
        }
    }

    String getCardMoveMessage(final String tableName, final Card card) {
        return "table " + tableName + ' ' + loginName + " play " + getIssCardString(card);
    }

    private static String getIssCardString(final Card card) {
        return card.getSuit().getShortString() + card.getRank().getShortString();
    }

    String getResignMessage(final String tableName) {

        return "table " + tableName + ' ' + loginName + " play RE";
    }

    String getShowCardsMessage(final String tableName) {

        return "table " + tableName + ' ' + loginName + " play SC";
    }

    static String getInvitationAcceptedMessage(final String tableName,
                                               final String invitationTicket) {

        return "join " + tableName + " " + invitationTicket;
    }
}

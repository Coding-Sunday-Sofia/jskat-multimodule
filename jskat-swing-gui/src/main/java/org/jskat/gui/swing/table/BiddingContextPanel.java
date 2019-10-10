/**
 * This file is part of JSkat.
 * <p>
 * JSkat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * JSkat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with JSkat.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * This file is part of JSkat.
 * <p>
 * JSkat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * JSkat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with JSkat.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * This file is part of JSkat.
 * <p>
 * JSkat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * JSkat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with JSkat.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * This file is part of JSkat.
 *
 * JSkat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JSkat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JSkat.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jskat.gui.swing.table;

import org.jskat.gui.action.JSkatAction;
import org.jskat.gui.img.JSkatGraphicRepository;
import org.jskat.gui.swing.LayoutFactory;
import org.jskat.util.Player;

import javax.swing.*;

/**
 * Holds all widgets for bidding
 */
class BiddingContextPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private BidBubblePanel leftOpponentBid;
    private BidBubblePanel rightOpponentBid;
    private BidBubblePanel userBid;
    private BidBubblePanel foreHandBidLabel;
    private BidBubblePanel middleHandBidLabel;
    private BidBubblePanel rearHandBidLabel;
    private JButton bidButton;
    private JButton passButton;
    private GameAnnouncePanel announcePanel;

    Action makeBidAction;
    Action holdBidAction;

    /**
     * Bidding panel
     *
     * @param actions
     *            Action map
     */
    BiddingContextPanel(final ActionMap actions, final JSkatGraphicRepository bitmaps,
                        final JSkatUserPanel userPanel) {

        initPanel(actions, bitmaps, userPanel);
    }

    private void initPanel(final ActionMap actions, final JSkatGraphicRepository bitmaps,
                           final JSkatUserPanel userPanel) {

        setLayout(LayoutFactory.getMigLayout(
                "fill", "[shrink][grow][shrink]", "fill"));

        final JPanel blankPanel = new JPanel();
        blankPanel.setOpaque(false);
        add(blankPanel, "width 25%");

        final JPanel biddingPanel = getBiddingPanel(actions, bitmaps);
        biddingPanel.setOpaque(false);
        add(biddingPanel, "grow");

        this.announcePanel = new GameAnnouncePanel(actions, userPanel, null);
        add(this.announcePanel, "width 25%");

        setOpaque(false);
    }

    private JPanel getBiddingPanel(final ActionMap actions,
                                   final JSkatGraphicRepository bitmaps) {

        final JPanel biddingPanel = new JPanel(LayoutFactory.getMigLayout("fill"));

        this.leftOpponentBid = new BidBubblePanel(bitmaps.getLeftBidBubble());
        this.rightOpponentBid = new BidBubblePanel(bitmaps.getRightBidBubble());
        this.userBid = new BidBubblePanel(bitmaps.getUserBidBubble());

        biddingPanel.add(this.leftOpponentBid, "center");
        biddingPanel.add(this.rightOpponentBid, "center, wrap");
        biddingPanel.add(this.userBid, "span 2, center, wrap");

        this.makeBidAction = actions.get(JSkatAction.MAKE_BID);
        this.holdBidAction = actions.get(JSkatAction.HOLD_BID);
        this.bidButton = new JButton(this.makeBidAction);
        this.passButton = new JButton(actions.get(JSkatAction.PASS_BID));
        biddingPanel.add(this.bidButton, "center");
        biddingPanel.add(this.passButton, "center");

        return biddingPanel;
    }

    void setUserPosition(final Player player) {
        // FIXME (jansch 09.11.2010) code duplication with
        // SkatTablePanel.setPositions()
        switch (player) {
            case FOREHAND:
                this.foreHandBidLabel = this.userBid;
                this.middleHandBidLabel = this.leftOpponentBid;
                this.rearHandBidLabel = this.rightOpponentBid;
                break;
            case MIDDLEHAND:
                this.foreHandBidLabel = this.rightOpponentBid;
                this.middleHandBidLabel = this.userBid;
                this.rearHandBidLabel = this.leftOpponentBid;
                break;
            case REARHAND:
                this.foreHandBidLabel = this.leftOpponentBid;
                this.middleHandBidLabel = this.rightOpponentBid;
                this.rearHandBidLabel = this.userBid;
                break;
        }
    }

    void setBid(final Player player, final int bidValue) {

        switch (player) {
            case FOREHAND:
                this.foreHandBidLabel.setBidValue(bidValue);
                break;
            case MIDDLEHAND:
                this.middleHandBidLabel.setBidValue(bidValue);
                break;
            case REARHAND:
                this.rearHandBidLabel.setBidValue(bidValue);
                break;
        }
    }

    void setPass(final Player player) {

        switch (player) {
            case FOREHAND:
                this.foreHandBidLabel.setBidValue(-1);
                break;
            case MIDDLEHAND:
                this.middleHandBidLabel.setBidValue(-1);
                break;
            case REARHAND:
                this.rearHandBidLabel.setBidValue(-1);
                break;
        }
    }

    void setBidValueToMake(final int bidValue) {

        this.bidButton.setAction(this.makeBidAction);
        this.bidButton.setText(String.valueOf(bidValue));
    }

    void setBidValueToHold(final int bidValue) {

        this.bidButton.setAction(this.holdBidAction);
        this.bidButton.setText(String.valueOf(bidValue));
    }

    void resetPanel() {
        this.foreHandBidLabel.setBidValue(0);
        this.middleHandBidLabel.setBidValue(0);
        this.rearHandBidLabel.setBidValue(0);
        setBidValueToMake(18);
        this.announcePanel.resetPanel();
    }
}

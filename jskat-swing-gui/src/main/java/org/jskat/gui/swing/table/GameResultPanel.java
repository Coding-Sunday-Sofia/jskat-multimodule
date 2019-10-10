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

import org.jskat.data.GameSummary;
import org.jskat.data.Trick;
import org.jskat.gui.swing.LayoutFactory;
import org.jskat.util.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds all informations about a game at the end
 */
public class GameResultPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(GameResultPanel.class);

    private Player userPosition;
    private List<TrickPanel> trickPanelList;

    /**
     * Game result panel
     */
    public GameResultPanel() {
        initPanel();
    }

    private void initPanel() {
        setLayout(LayoutFactory.getMigLayout("fill", "fill", "fill"));

        trickPanelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            trickPanelList.add(new TrickPanel(false));
        }

        final JPanel trickPanel = new JPanel(LayoutFactory.getMigLayout(
                "fill", "fill", "fill"));
        for (final TrickPanel panel : trickPanelList) {
            trickPanel.add(panel);
        }
        trickPanel.setOpaque(false);

        add(trickPanel, "grow");

        setOpaque(false);
    }

    public void setGameSummary(final GameSummary summary) {

        final List<Trick> tricks = summary.getTricks();

        log.debug("Trick size: " + tricks.size());

        for (int i = 0; i < 10; i++) {

            final TrickPanel trickPanel = trickPanelList.get(i);
            trickPanel.clearCards();

            if (i < tricks.size()) {
                final Trick trick = tricks.get(i);
                if (trick != null) {
                    trickPanel.setUserPosition(userPosition);
                    trickPanel.addCard(trick.getForeHand(),
                            trick.getFirstCard());
                    trickPanel.addCard(trick.getForeHand().getLeftNeighbor(),
                            trick.getSecondCard());
                    trickPanel.addCard(trick.getForeHand().getRightNeighbor(),
                            trick.getThirdCard());
                }
            }
        }
    }

    public void setUserPosition(final Player newUserPosition) {

        userPosition = newUserPosition;
    }

    public void resetPanel() {

        for (final TrickPanel panel : trickPanelList) {

            panel.clearCards();
        }
    }
}

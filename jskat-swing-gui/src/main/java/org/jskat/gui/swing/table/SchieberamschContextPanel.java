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

import org.jskat.data.GameAnnouncement;
import org.jskat.data.GameAnnouncement.GameAnnouncementFactory;
import org.jskat.gui.action.JSkatAction;
import org.jskat.gui.img.JSkatGraphicRepository;
import org.jskat.gui.img.JSkatGraphicRepository.Icon;
import org.jskat.gui.img.JSkatGraphicRepository.IconSize;
import org.jskat.gui.swing.LayoutFactory;
import org.jskat.util.Card;
import org.jskat.util.CardList;
import org.jskat.util.GameType;
import org.jskat.util.JSkatResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Context panel for Schieberamsch
 */
class SchieberamschContextPanel extends JPanel {

    JSkatGraphicRepository bitmaps = JSkatGraphicRepository.INSTANCE;
    JSkatResourceBundle strings = JSkatResourceBundle.INSTANCE;

    private static final String GRAND_HAND = "GRAND_HAND";
    private static final String DISCARD = "DISCARD";
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory
            .getLogger(SchieberamschContextPanel.class);

    private final DiscardPanel discardPanel;
    JPanel centerPanel;

    SchieberamschContextPanel(final ActionMap actions,
                              final JSkatUserPanel newUserPanel, final int maxCards) {

        setLayout(LayoutFactory.getMigLayout(
                "fill", "[shrink][grow][shrink]", "fill"));

        final JPanel blankPanel = new JPanel();
        blankPanel.setOpaque(false);
        add(blankPanel, "width 25%");

        this.centerPanel = new JPanel(new CardLayout());
        final JPanel grandHandPanel = getGrandHandSchiebeRamschPanel(actions);
        this.centerPanel.add(grandHandPanel, GRAND_HAND);

        this.discardPanel = new DiscardPanel(actions, 4);
        this.centerPanel.add(this.discardPanel, DISCARD);

        this.centerPanel.setOpaque(false);
        add(this.centerPanel, "grow");

        add(new SkatSchiebenPanel(actions, this.discardPanel), "width 25%");

        setOpaque(false);

        resetPanel();
    }

    // FIXME: same code can be found in class SkatTabelPanel for
    // Contra-Re-Context-Panel
    public JPanel getGrandHandSchiebeRamschPanel(final ActionMap actions) {
        final JPanel result = new JPanel(LayoutFactory.getMigLayout("fill"));

        final JPanel question = new JPanel();
        final JLabel questionIconLabel = new JLabel(new ImageIcon(
                this.bitmaps.getUserBidBubble()));
        question.add(questionIconLabel);
        final JLabel questionLabel = new JLabel(
                this.strings.getString("want_play_grand_hand"));
        questionLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        question.add(questionLabel);
        result.add(question, "center, growx, span 2, wrap");

        final JButton grandHandButton = new JButton(
                actions.get(JSkatAction.PLAY_GRAND_HAND));
        grandHandButton.setIcon(new ImageIcon(this.bitmaps.getIconImage(Icon.OK,
                IconSize.BIG)));
        grandHandButton.setText(this.strings.getString("yes"));
        grandHandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    final GameAnnouncement ann = getGameAnnouncement();

                    e.setSource(ann);
                    // fire event again
                    grandHandButton.dispatchEvent(e);
                } catch (final IllegalArgumentException except) {
                    log.error(except.getMessage());
                }
            }

            private GameAnnouncement getGameAnnouncement() {
                final GameAnnouncementFactory factory = GameAnnouncement.getFactory();
                factory.setGameType(GameType.GRAND);
                factory.setHand(Boolean.TRUE);
                return factory.getAnnouncement();
            }
        });

        final JButton schieberamschButton = new JButton(
                actions.get(JSkatAction.PLAY_SCHIEBERAMSCH));
        schieberamschButton.setIcon(new ImageIcon(this.bitmaps.getIconImage(
                Icon.STOP, IconSize.BIG)));
        schieberamschButton.setText(this.strings.getString("no"));
        schieberamschButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    showPanel(DISCARD);
                } catch (final IllegalArgumentException except) {
                    log.error(except.getMessage());
                }
            }
        });

        final JPanel grandHandPanel = new JPanel();
        grandHandPanel.add(grandHandButton);
        grandHandPanel.setOpaque(false);
        result.add(grandHandPanel, "width 50%");

        final JPanel schieberamschPanel = new JPanel();
        schieberamschPanel.add(schieberamschButton);
        schieberamschPanel.setOpaque(false);
        result.add(schieberamschPanel, "width 50%");

        result.setOpaque(false);

        return result;
    }

    public void resetPanel() {

        this.discardPanel.resetPanel();
        showPanel(GRAND_HAND);
    }

    void showPanel(final String panelName) {
        ((CardLayout) this.centerPanel.getLayout()).show(this.centerPanel, panelName);
    }

    public void removeCard(final Card card) {
        this.discardPanel.removeCard(card);
    }

    public boolean isHandFull() {
        return this.discardPanel.isHandFull();
    }

    public void addCard(final Card card) {
        this.discardPanel.addCard(card);
    }

    public void setSkat(final CardList skat) {
        this.discardPanel.setSkat(skat);
    }
}

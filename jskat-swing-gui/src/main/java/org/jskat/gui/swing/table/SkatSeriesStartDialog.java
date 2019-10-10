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

import org.jskat.control.JSkatMaster;
import org.jskat.gui.swing.AbstractI18NComboBoxRenderer;
import org.jskat.gui.swing.LayoutFactory;
import org.jskat.player.JSkatPlayerResolver;
import org.jskat.util.JSkatResourceBundle;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Asks for user defined options on start of a skat series
 */
public class SkatSeriesStartDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final String CANCEL = "CANCEL";
    private static final String START = "START";

    private static final String PLAYER1_DEFAULT_NAME = "Jan";
    private static final String PLAYER2_DEFAULT_NAME = "Markus";
    private static final String PLAYER3_DEFAULT_NAME = System
            .getProperty("user.name");

    private final Component parent;

    private final JSkatResourceBundle strings;

    private JTextField player1name;
    private JTextField player2name;
    private JTextField player3name;
    private JComboBox player1;
    private JComboBox player2;
    private JComboBox player3;
    private JSpinner numberOfRounds;
    private JCheckBox unlimited;
    private JCheckBox onlyPlayRamsch;

    /**
     * Constructor
     *
     * @param parent
     *            Parent component of the skat series start dialog
     */
    public SkatSeriesStartDialog(final Component parent) {

        this.parent = parent;
        this.strings = JSkatResourceBundle.INSTANCE;

        initGUI();
    }

    private void initGUI() {

        setModalityType(ModalityType.APPLICATION_MODAL);
        setResizable(false);

        setTitle(this.strings.getString("start_series"));

        final Container root = getContentPane();
        root.setLayout(LayoutFactory.getMigLayout());

        final List<String> playerTypes = new ArrayList<>();
        for (final String aiPlayer : JSkatPlayerResolver
                .getAllAIPlayerImplementations()) {
            playerTypes.add(aiPlayer);
        }
        Collections.sort(playerTypes);

        root.add(new JLabel(this.strings.getString("player") + " 1"));
        this.player1name = new JTextField(PLAYER1_DEFAULT_NAME);
        root.add(this.player1name, "span2, growx");
        this.player1 = new JComboBox(playerTypes.toArray());
        this.player1.setRenderer(new PlayerComboBoxRenderer());
        root.add(this.player1, "growx, wrap");

        root.add(new JLabel(this.strings.getString("player") + " 2"));
        this.player2name = new JTextField(PLAYER2_DEFAULT_NAME);
        root.add(this.player2name, "span2, growx");
        this.player2 = new JComboBox(playerTypes.toArray());
        this.player2.setRenderer(new PlayerComboBoxRenderer());
        root.add(this.player2, "growx, wrap");
        root.add(new JLabel(this.strings.getString("player") + " 3"));

        this.player3name = new JTextField(PLAYER3_DEFAULT_NAME);
        root.add(this.player3name, "span2, growx");
        // Human player can only be player 3
        playerTypes.add(JSkatPlayerResolver.HUMAN_PLAYER_CLASS);
        this.player3 = new JComboBox(playerTypes.toArray());
        this.player3.setRenderer(new PlayerComboBoxRenderer());
        this.player3.setSelectedIndex(this.player3.getItemCount() - 1);
        root.add(this.player3, "span2, growx, wrap");

        root.add(new JLabel(this.strings.getString("number_of_rounds")));
        this.numberOfRounds = new JSpinner(new SpinnerNumberModel(12, 1, 48, 1));
        root.add(this.numberOfRounds);
        this.unlimited = new JCheckBox(this.strings.getString("unlimited"));
        this.unlimited.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(final ChangeEvent e) {
                if (SkatSeriesStartDialog.this.unlimited.isSelected()) {
                    SkatSeriesStartDialog.this.numberOfRounds.setEnabled(false);
                } else {
                    SkatSeriesStartDialog.this.numberOfRounds.setEnabled(true);
                }
            }
        });
        root.add(this.unlimited, "wrap");
        root.add(new JLabel(this.strings.getString("ramsch")));
        this.onlyPlayRamsch = new JCheckBox(this.strings.getString("only_play_ramsch"));
        root.add(this.onlyPlayRamsch, "wrap");

        final JPanel buttonPanel = new JPanel(LayoutFactory.getMigLayout());
        final JButton start = new JButton(this.strings.getString("start"));
        start.setActionCommand(START);
        start.addActionListener(this);
        buttonPanel.add(start);
        final JButton cancel = new JButton(this.strings.getString("cancel"));
        cancel.setActionCommand(CANCEL);
        cancel.addActionListener(this);
        buttonPanel.add(cancel);
        root.add(buttonPanel, "span 4, center");

        pack();
    }

    /**
     * @see JDialog#setVisible(boolean)
     */
    @Override
    public void setVisible(final boolean isVisible) {

        if (isVisible) {

            setLocationRelativeTo(this.parent);
        }

        super.setVisible(isVisible);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent e) {

        if (CANCEL.equals(e.getActionCommand())) {
            setVisible(false);
        } else if (START.equals(e.getActionCommand())) {
            if (this.player1name.getText().isEmpty()
                    || this.player2name.getText().isEmpty()
                    || this.player3name.getText().isEmpty()) {
                JSkatMaster.showEmptyInputNameMessage();
                return;
            }

            final List<String> playerTypes = new ArrayList<>();
            playerTypes.add((String) this.player1.getSelectedItem());
            playerTypes.add((String) this.player2.getSelectedItem());
            playerTypes.add((String) this.player3.getSelectedItem());

            final List<String> playerNames = new ArrayList<>();
            playerNames.add(this.player1name.getText());
            playerNames.add(this.player2name.getText());
            playerNames.add(this.player3name.getText());

            setVisible(false);

            JSkatMaster.INSTANCE.startSeries(playerTypes, playerNames,
                    Integer.parseInt(this.numberOfRounds.getValue().toString()),
                    this.unlimited.isSelected(), this.onlyPlayRamsch.isSelected(), 100);
        }
    }

    private class PlayerComboBoxRenderer extends AbstractI18NComboBoxRenderer {

        private static final long serialVersionUID = 1L;

        PlayerComboBoxRenderer() {
            super();
        }

        @Override
        public String getValueText(final Object value) {

            String result = " ";

            final String player = (String) value;

            if (player != null) {
                if ("org.jskat.ai.newalgorithm.AlgorithmAI".equals(player)) {
                    result = SkatSeriesStartDialog.this.strings.getString("algorithmic_nextgen_player");
                } else if ("org.jskat.ai.mjl.AIPlayerMJL".equals(player)) {
                    result = SkatSeriesStartDialog.this.strings.getString("algorithmic_player");
                } else if ("org.jskat.ai.rnd.AIPlayerRND".equals(player)) {
                    result = SkatSeriesStartDialog.this.strings.getString("random_player");
                } else if ("org.jskat.ai.nn.AIPlayerNN".equals(player)) {
                    result = SkatSeriesStartDialog.this.strings.getString("neural_network_player");
                } else if ("org.jskat.gui.human.SwingHumanPlayer"
                        .equals(player)) {
                    result = SkatSeriesStartDialog.this.strings.getString("human_player");
                } else {
                    result = player;
                }
            }
            return result;
        }
    }
}

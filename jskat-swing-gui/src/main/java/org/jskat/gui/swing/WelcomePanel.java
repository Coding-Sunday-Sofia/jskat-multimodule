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
package org.jskat.gui.swing;

import org.jskat.gui.action.JSkatAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for welcome message and options for playing local or online games
 */
public class WelcomePanel extends AbstractTabPanel {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(WelcomePanel.class);

    /**
     * @see AbstractTabPanel#AbstractTabPanel(String, ActionMap)
     * @param newTableName
     *            Table name
     * @param actions
     *            Actions
     */
    public WelcomePanel(final String newTableName, final ActionMap actions) {

        super(newTableName, actions);
        log.debug("SkatTablePanel: name: " + newTableName);
    }

    /**
     * @see AbstractTabPanel#initPanel()
     */
    @Override
    protected void initPanel() {

        setLayout(LayoutFactory.getMigLayout("fill"));
        add(getWelcomePanel(), "grow, center");
    }

    private JScrollPane getWelcomePanel() {

        final JPanel welcomePanel = new JPanel(LayoutFactory.getMigLayout(
                "fill", "[]", "[shrink][grow]"));

        welcomePanel.add(createHeaderPanel(), "center, wrap");
        welcomePanel.add(createButtonPanel(), "center");

        return new JScrollPane(welcomePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private JPanel createHeaderPanel() {

        final JPanel headerPanel = new JPanel(
                LayoutFactory.getMigLayout("fill"));
        final JLabel logoLabel = new JLabel(new ImageIcon(
                this.bitmaps.getJSkatLogoImage()));
        headerPanel.add(logoLabel);
        final JLabel headerLabel = new JLabel(
                this.strings.getString("welcome_to_jskat"));
        headerLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
        headerPanel.add(headerLabel, "center");
        return headerPanel;
    }

    private JPanel createButtonPanel() {

        final JButton issTableButton = new JButton(getActionMap().get(
                JSkatAction.SHOW_ISS_LOGIN));
        final JLabel issTableDescription = new JLabel("<html>"
                + this.strings.getString("explain_iss_table_1") + "<br>"
                + this.strings.getString("explain_iss_table_2"));

        final JButton localTableButton = new JButton(this.getActionMap().get(
                JSkatAction.CREATE_LOCAL_TABLE));
        final JLabel localTableDescription = new JLabel("<html>"
                + this.strings.getString("explain_local_table_1") + "<br>"
                + this.strings.getString("explain_local_table_2"));

        final JButton optionsButton = new JButton(getActionMap().get(
                JSkatAction.PREFERENCES));
        final JLabel optionsDescription = new JLabel(
                this.strings.getString("explain_options_1"));

        final JButton quitButton = new JButton(getActionMap().get(
                JSkatAction.EXIT_JSKAT));
        final JLabel quitDescription = new JLabel(
                this.strings.getString("explain_exit"));

        final JPanel buttonPanel = new JPanel(LayoutFactory.getMigLayout(
                "", "[][]", "[center][center][center][center]"));
        buttonPanel.add(issTableButton, "growx, shrinky");
        buttonPanel.add(issTableDescription, "wrap");
        buttonPanel.add(localTableButton, "growx, shrinky");
        buttonPanel.add(localTableDescription, "wrap");
        buttonPanel.add(optionsButton, "growx, shrinky, gapy 1cm");
        buttonPanel.add(optionsDescription, "gapy 1cm, wrap");
        buttonPanel.add(quitButton, "growx, shrinky, gapy 1cm");
        buttonPanel.add(quitDescription, "gapy 1cm, wrap");

        return buttonPanel;
    }

    @Override
    protected void setFocus() {
        // no focus needed
    }
}

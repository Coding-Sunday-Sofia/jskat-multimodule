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
import org.jskat.gui.action.JSkatAction;
import org.jskat.gui.swing.LayoutFactory;
import org.jskat.util.Player;

import javax.swing.*;
import java.util.List;

class GameOverPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private GameResultPanel gameResultPanel;

    public GameOverPanel(final ActionMap actions, final List<JSkatAction> activeActions) {

        initPanel(actions, activeActions);
    }

    private void initPanel(final ActionMap actions, final List<JSkatAction> activeActions) {

        this.setLayout(LayoutFactory.getMigLayout("fill", "fill", "fill"));

        final JPanel panel = new JPanel(LayoutFactory.getMigLayout("fill", "fill", "[grow][shrink]"));
        gameResultPanel = new GameResultPanel();
        panel.add(gameResultPanel, "grow, wrap");

        final JPanel buttonPanel = new JPanel(LayoutFactory.getMigLayout("fill"));
        for (final JSkatAction action : activeActions) {
            buttonPanel.add(new JButton(actions.get(action)), "center, shrink");
        }
        buttonPanel.setOpaque(false);
        panel.add(buttonPanel, "center");

        panel.setOpaque(false);
        this.add(panel, "center");

        setOpaque(false);
    }

    void setUserPosition(final Player player) {

        gameResultPanel.setUserPosition(player);
    }

    void setGameSummary(final GameSummary summary) {

        gameResultPanel.setGameSummary(summary);
    }

    public void resetPanel() {

        gameResultPanel.resetPanel();
    }
}

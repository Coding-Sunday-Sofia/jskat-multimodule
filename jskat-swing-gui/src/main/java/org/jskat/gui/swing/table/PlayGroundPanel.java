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

import org.jskat.gui.img.JSkatGraphicRepository;
import org.jskat.gui.swing.LayoutFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Main panel for the play ground
 */
public class PlayGroundPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Image backGroundImage;

    /**
     * Constructor
     *
     * @param gameInfoPanel
     *            Game info panel
     * @param leftOpponentPanel
     *            Left opponent panel
     * @param rightOpponentPanel
     *            Right opponent panel
     * @param gameContextPanel
     *            Game context panel
     * @param userPanel
     *            User panel
     */
    public PlayGroundPanel(final GameInformationPanel gameInfoPanel,
                           final OpponentPanel leftOpponentPanel, final OpponentPanel rightOpponentPanel,
                           final JPanel gameContextPanel, final JSkatUserPanel userPanel) {

        super(LayoutFactory.getMigLayout(
                "fill, ins 0, gap 0 0", "fill", "[shrink][shrink][grow][shrink][shrink]"));

        add(gameInfoPanel, "span 2, growx, shrinky, align center, wrap");
        add(leftOpponentPanel,
                "width 50%, growx, growy, hmin 15%, hmax 15%, align left");
        add(rightOpponentPanel,
                "width 50%, growx, growy, hmin 15%, hmax 15%, align right, wrap");
        add(gameContextPanel, "span 2, growx, growy, align center, wrap");
        add(userPanel,
                "span 2, growx, growy, hmin 33%, hmax 33%, align center, wrap");

        this.backGroundImage = JSkatGraphicRepository.INSTANCE
                .getSkatTableImage();
    }

    @Override
    public void paintComponent(final Graphics g) {

        final int width = getWidth();
        final int height = getHeight();

        final int imageWidth = this.backGroundImage.getWidth(null);
        final int imageHeight = this.backGroundImage.getHeight(null);

        int currX = 0;
        int currY = 0;

        while (currX < width) {
            while (currY < height) {
                g.drawImage(this.backGroundImage, currX, currY, null);
                currY += imageHeight;
            }
            currY = 0;
            currX += imageWidth;
        }
    }
}

/**
 * Copyright (C) 2019 Jan Schäfer (jansch@users.sourceforge.net)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jskat.control.event;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.jskat.AbstractJSkatTest;
import org.jskat.control.event.skatgame.ContraEvent;
import org.jskat.data.SkatGameData;
import org.jskat.util.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContraEventTest extends AbstractJSkatTest {

    private SkatGameData data;
    private ContraEvent event;

    @BeforeEach
    public void setUp() {
        data = new SkatGameData();
        event = new ContraEvent(Player.FOREHAND);
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(ContraEvent.class).verify();
    }

    @Test
    public void skatGameDataAfterEvent() {

        event.processForward(data);

        assertThat(data.isContra(), is(true));
    }

    @Test
    public void skatGameDataBeforeEvent() {

        event.processForward(data);
        event.processBackward(data);

        assertThat(data.isContra(), is(false));
    }
}

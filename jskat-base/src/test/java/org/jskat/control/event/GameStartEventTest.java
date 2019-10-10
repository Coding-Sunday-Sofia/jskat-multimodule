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
import org.jskat.control.event.skatgame.GameStartEvent;
import org.junit.jupiter.api.Test;

public class GameStartEventTest extends AbstractJSkatTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(GameStartEvent.class).verify();
    }
}

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
package org.jskat.control.event.skatgame;

import org.jskat.data.SkatGameData;

/**
 * Interface for events during a Skat game
 */
public interface SkatGameEvent {
    /**
     * Processes the event forward.
     *
     * @param data Game data
     */
    void processForward(SkatGameData data);

    /**
     * Processes the event backward.
     *
     * @param data Game data
     */
    void processBackward(SkatGameData data);
}

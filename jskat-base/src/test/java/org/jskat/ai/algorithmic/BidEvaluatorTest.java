/**
 * Copyright (C) 2019 Jan Schäfer (jansch@users.sourceforge.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jskat.ai.algorithmic;

import org.jskat.AbstractJSkatTest;
import org.jskat.util.Card;
import org.jskat.util.CardList;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test cases for class Card
 */
public class BidEvaluatorTest extends AbstractJSkatTest {

    private static final Logger log = LoggerFactory.getLogger(BidEvaluatorTest.class);

    /**
     * Test double sorting
     */
    @Test
    public void testGetMaxBid() {

        log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        final CardList cards = new CardList(Arrays.asList(Card.CJ, Card.DJ, Card.CA, Card.CK, Card.CQ, Card.C8, Card.SQ,
                Card.HT, Card.H8, Card.D9));

        // sort cards
        BidEvaluator eval = new BidEvaluator(cards);

        assertThat(eval.getMaxBid(), is(24));

        cards.remove(Card.DJ);
        cards.add(Card.SJ);
        cards.sort(null);
        eval = new BidEvaluator(cards);
        assertThat(eval.getMaxBid(), is(36));
        log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

}

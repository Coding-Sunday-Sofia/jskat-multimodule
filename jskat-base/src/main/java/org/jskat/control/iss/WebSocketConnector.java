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
package org.jskat.control.iss;

import org.eclipse.jetty.websocket.WebSocket.Connection;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;
import org.jskat.data.JSkatOptions.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * StreamConnector to International Skat Server ISS
 */
class WebSocketConnector extends AbstractIssConnector {

    private static final Logger log = LoggerFactory
            .getLogger(WebSocketConnector.class);

    private WebSocketConnection webSocket;

    /**
     * Establishes a connection with ISS
     *
     * @return TRUE if the connection was successful
     */
    @Override
    public boolean establishConnection(final IssController issControl) {

        log.debug("WebSocketConnector.establishConnection()");

        try {
            final WebSocketClientFactory factory = new WebSocketClientFactory();
            factory.start();

            final WebSocketClient client = factory.newWebSocketClient();

            client.setMaxIdleTime(30 * 60 * 1000); // 30 minutes
            client.setMaxTextMessageSize(4096);
            client.setProtocol("chat");

            webSocket = new WebSocketConnection(issControl);

            final Connection connection = client
                    .open(new URI(
                                    "ws://" + OPTIONS.getString(Option.ISS_ADDRESS) + ":"
                                            + OPTIONS.getInteger(Option.ISS_PORT)),
                            webSocket, 10, TimeUnit.SECONDS);

            if (connection.isOpen()) {
                return true;
            }

        } catch (final java.net.UnknownHostException e) {
            log.error("Cannot open connection to ISS");
            issControl.showErrorMessage(STRINGS
                    .getString("cant_connect_to_iss"));
            return false;
        } catch (final java.io.IOException e) {
            log.error("IOException: " + e.toString());
            return false;
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public OutputChannel getOutputChannel() {
        return webSocket;
    }

    /**
     * Closes the connection to ISS
     */
    @Override
    public void closeConnection() {
        webSocket.messageHandler.interrupt();
        webSocket.connection.close();
        log.debug("connection closed");
    }

    /**
     * Checks whether there is an open connection
     *
     * @return TRUE if there is an open connection
     */
    @Override
    public boolean isConnected() {
        return webSocket != null && webSocket.connection != null
                && webSocket.connection.isOpen();
    }
}

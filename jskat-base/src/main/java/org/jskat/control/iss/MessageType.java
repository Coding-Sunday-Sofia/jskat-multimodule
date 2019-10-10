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

/**
 * All message types from ISS
 */
public enum MessageType {

	/**
	 * Password message
	 */
	PASSWORD("password:"),
	/**
	 * Welcome message
	 */
	WELCOME("Welcome"),
	/**
	 * Version message
	 */
	VERSION("Version"),
	/**
	 * Clients update message
	 */
	CLIENTS("clients"),
	/**
	 * Tables update message
	 */
	TABLES("tables"),
	/**
	 * Table create message
	 */
	CREATE("create"),
	/**
	 * Table invite message
	 */
	INVITE("invite"),
	/**
	 * Table update message
	 */
	TABLE("table"),
	/**
	 * Table destroy message
	 */
	DESTROY("destroy"),
	/**
	 * Error message
	 */
	ERROR("error"),
	/**
	 * Chat message
	 */
	TEXT("text"),
	/**
	 * Lobby message
	 */
	YELL("yell"),
	/**
	 * UNKNOWN message
	 */
	UNKNOWN("");

	private String messageStart;

	private MessageType(String startToken) {
		messageStart = startToken;
	}

	/**
	 * Gets the message type by a string
	 * 
	 * @param searchToken
	 *            Search string
	 * @return Message type or {@link #UNKNOWN}
	 */
	public static MessageType getByString(String searchToken) {

		for (MessageType type : MessageType.values()) {
			if (type.messageStart.equals(searchToken)) {
				return type;
			}
		}

		return UNKNOWN;
	}
}

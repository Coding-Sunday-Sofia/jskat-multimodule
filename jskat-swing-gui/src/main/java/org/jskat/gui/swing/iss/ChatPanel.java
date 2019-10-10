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
package org.jskat.gui.swing.iss;

import org.jskat.data.iss.ChatMessage;
import org.jskat.gui.action.JSkatAction;
import org.jskat.gui.swing.AbstractTabPanel;
import org.jskat.gui.swing.LayoutFactory;
import org.jskat.util.JSkatResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Chat panel for ISS
 */
class ChatPanel extends JPanel implements ChangeListener {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(ChatPanel.class);

    private JTextField inputLine;
    private Map<String, JTextArea> chats;
    private JTabbedPane chatTabs;

    String activeChatName;

    /**
     * Constructor
     */
    ChatPanel(final AbstractTabPanel parent) {

        initPanel(parent.getActionMap());
    }

    private void initPanel(final ActionMap actions) {

        final JSkatResourceBundle strings = JSkatResourceBundle.INSTANCE;

        setLayout(LayoutFactory.getMigLayout("fill", "fill", "[grow][shrink]"));
        setMinimumSize(new Dimension(100, 100));
        setPreferredSize(new Dimension(100, 100));

        this.chats = new HashMap<>();
        this.chatTabs = new JTabbedPane();
        this.chatTabs.setTabPlacement(SwingConstants.BOTTOM);
        this.chatTabs.setAutoscrolls(true);
        this.chatTabs.addChangeListener(this);
        add(this.chatTabs, "grow, wrap");

        addNewChat(strings.getString("lobby"), "lobby");

        this.inputLine = new JTextField(20);
        this.inputLine.setAction(actions.get(JSkatAction.SEND_CHAT_MESSAGE));
        this.inputLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {

                final String message = ChatPanel.this.inputLine.getText();
                log.debug("Chat message: " + message);

                final ChatMessage chatMessage = new ChatMessage(
                        ChatPanel.this.activeChatName, message);
                e.setSource(chatMessage);
                // fire event again
                ChatPanel.this.inputLine.dispatchEvent(e);

                ChatPanel.this.inputLine.setText(null);
            }
        });
        add(this.inputLine, "growx");
    }

    JTextArea addNewChat(final String title, final String name) {

        final JTextArea chat = getChat();
        this.chats.put(name, chat);
        final JScrollPane scrollPane = new JScrollPane(chat);
        scrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setName(name);

        this.chatTabs.add(title, scrollPane);
        this.chatTabs.setSelectedIndex(this.chatTabs.getComponentCount() - 1);
        this.activeChatName = name;

        return chat;
    }

    private static JTextArea getChat() {

        final JTextArea chat = new JTextArea(7, 50);
        chat.setEditable(false);
        chat.setLineWrap(true);

        return chat;
    }

    void appendMessage(final ChatMessage message) {

        log.debug("Appending chat message: " + message);

        JTextArea chat = this.chats.get(message.getChatName());

        if (chat == null) {
            // new chat --> create chat text area first
            chat = addNewChat(message.getChatName(), message.getChatName());
        }

        chat.append(message.getMessage() + '\n');
        moveScrollBarToBottom(chat);
    }

    private static void moveScrollBarToBottom(final JTextArea chat) {
        chat.selectAll();
        final int x = chat.getSelectionEnd();
        chat.select(x, x);
    }

    @Override
    public void stateChanged(final ChangeEvent e) {

        if (e.getSource() instanceof JTabbedPane) {

            final JTabbedPane tabs = (JTabbedPane) e.getSource();
            final Component tab = tabs.getSelectedComponent();

            this.activeChatName = tab.getName();
            log.debug("Chat " + this.activeChatName + " activated.");
        }
    }

    public void setFocus() {
        this.inputLine.requestFocus();
    }
}

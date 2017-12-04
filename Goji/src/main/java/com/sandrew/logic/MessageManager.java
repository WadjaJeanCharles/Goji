/**
 *
 */
package com.sandrew.logic;

import java.util.Optional;
import java.util.Set;

import com.sandrew.domain.Message;
import com.sandrew.domain.User;

/**
 * Handles all the messages in and out of the system.
 */
public interface MessageManager {

    /**
     * Adds or updates a message into the DAO layer.
     *
     * @param message
     *            The message to add or update.
     */
    public void addOrUpdateMessage(Message message);

    /**
     * Let a user recommend a message.
     *
     * @param message
     *            The message to be recommended.
     * @param user
     *            The user recommending the message.
     */
    public void recommendMessage(Message message, User user);

    /**
     * Gets all the messages sent by a user.
     *
     * @param user
     *            The user whose messages needs to be returned.
     *
     * @return The messages sent by a user.
     */
    public Set<Message> getUserMessages(User user);

    /**
     * Gets all the users who recommended a message.
     *
     * @return All the users who recommended a message.
     */
    public Set<User> getMessageRecommenders();

    /**
     * Gets the message which has been recommended the most.
     *
     * @return
     */
    public Optional<Message> getMostRecommendedMessage();
}

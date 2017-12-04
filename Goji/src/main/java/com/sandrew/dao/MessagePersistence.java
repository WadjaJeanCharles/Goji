/**
 *
 */
package com.sandrew.dao;

import java.util.Set;

import com.sandrew.domain.Message;
import com.sandrew.domain.User;

/**
 * Responsible for messages persistence.
 */
public interface MessagePersistence {

    /**
     * Persists a message to the persistence layer.
     *
     * @param message
     *            The message to persist.
     */
    public void addOrUpdateMessage(Message message);

    /**
     * Persists a user to the database.
     *
     * @param user
     *            The user to persist.
     */
    public void persistUser(User user);

    /**
     * Gets all the messages persisted in the persistence layer.
     *
     * @return All the persisted messages, or an empty set if there are no messages in the persistence layer.
     */
    public Set<Message> getMessages();

    /**
     * Gets all the users in the persistence layer.
     *
     * @return All the persisted users, or an empty set if there are no users in the persistence layer.
     */
    public Set<User> getUsers();
}

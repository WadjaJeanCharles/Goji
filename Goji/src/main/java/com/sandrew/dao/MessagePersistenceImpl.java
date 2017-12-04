/**
 *
 */
package com.sandrew.dao;

import java.util.Optional;
import java.util.Set;

import com.sandrew.domain.Message;
import com.sandrew.domain.User;
import com.sandrew.logic.MessageManager;

/**
 *
 */
public class MessagePersistenceImpl implements MessagePersistence, MessageManager {

    /**
     * {@inheritDoc}
     */
    @Override
    public void recommendMessage(final Message message, final User user) {
        throw new UnsupportedOperationException("This method is not implemented yet");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Message> getUserMessages(final User user) {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<User> getMessageRecommenders() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Message> getMostRecommendedMessage() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addOrUpdateMessage(final Message message) {
        throw new UnsupportedOperationException("This method is not implemented yet");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persistUser(final User user) {
        throw new UnsupportedOperationException("This method is not implemented yet");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Message> getMessages() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<User> getUsers() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

}

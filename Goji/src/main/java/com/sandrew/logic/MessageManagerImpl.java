/**
 *
 */
package com.sandrew.logic;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;

import com.sandrew.dao.MessagePersistence;
import com.sandrew.domain.Message;
import com.sandrew.domain.User;

/**
 *
 */
public class MessageManagerImpl implements MessageManager {

    private final MessagePersistence dao;

    /**
     * Creates a new message manager connected to a persistence layer.
     *
     * @param dao
     *            The persistence layer.
     * @throws NullPointerException
     *             If the persistence layer implementation is null.
     */
    public MessageManagerImpl(final MessagePersistence dao) {
        checkNotNull(dao);
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addOrUpdateMessage(final Message message) {
        this.dao.addOrUpdateMessage(message);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void recommendMessage(final Message message, final User user) {
        message.addRecommender(user);
        this.dao.addOrUpdateMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Message> getUserMessages(final User user) {
        /*
         * Could be implemented in the dao layer: return this.dao.getUserMessages(user);
         */
        final Set<Message> allMessages = this.dao.getMessages();

        final Set<Message> result = allMessages.stream().filter(x -> x.getSender().equals(user))
                        .collect(Collectors.toSet());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<User> getMessageRecommenders() {
        final Set<Message> allMessages = this.dao.getMessages();

        final Set<User> recommenders = allMessages.stream()
                        .filter(x -> x.getRecommendedBy().size() > NumberUtils.INTEGER_ZERO)
                        .map(Message::getRecommendedBy).flatMap(Set::stream).collect(Collectors.toSet());

        return recommenders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Message> getMostRecommendedMessage() {

        return this.getRecommendedMessages().stream().max(new MessageRecommendedComparator());
    }

    /**
     * Gets all the messages which have been recommended at list once.
     *
     * @return All the messages which have been recommended at list once.
     */
    private Set<Message> getRecommendedMessages() {
        final Set<Message> allMessages = this.dao.getMessages();

        return allMessages.stream().filter(x -> x.getRecommendedBy().size() >= NumberUtils.INTEGER_ONE)
                        .collect(Collectors.toSet());
    }

}

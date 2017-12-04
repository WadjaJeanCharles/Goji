/**
 *
 */
package com.sandrew.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents a message in the system.
 */
public final class Message {

    /**
     * Unique per message.
     */
    private final long id;

    /**
     * Content of the message.
     */
    private final String content;

    /**
     * Sender of the message.
     */
    private final User sender;

    /**
     * Collection of users who recommended the message.
     */
    private final Set<User> recommendedBy = new HashSet<User>();

    /**
     * Creates a new message.
     *
     * @param id
     *            ID of the message.
     * @param content
     *            Content of the message.
     * @param sender
     *            Sender of the message.
     * @throws NullPointerException
     *             If the sender is null;
     * @throws IllegalArgumentException
     *             If the message content is blank.
     */
    public Message(final long id, final String content, final User sender) {
        checkNotNull(sender);
        checkArgument(StringUtils.isNotBlank(content));

        this.id = id;
        this.content = content;
        this.sender = sender;
    }

    /**
     * @return the id
     */
    public long getId() {
        return this.id;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * @return the sender
     */
    public User getSender() {
        return this.sender;
    }

    /**
     * @return the recommendedBy
     */
    public Set<User> getRecommendedBy() {
        return this.recommendedBy;
    }

    /**
     * Adds a user who recommended the message.
     *
     * @param user
     *            The user who recommended the message.
     * @throws NullPointerException
     *             if the user is null.
     */
    public void addRecommender(final User user) {
        checkNotNull(user);

        if (this.sender.equals(user) == false) {
            this.recommendedBy.add(user);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.content == null) ? 0 : this.content.hashCode());
        result = (prime * result) + (int) (this.id ^ (this.id >>> 32));
        result = (prime * result) + ((this.sender == null) ? 0 : this.sender.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (this.content == null) {
            if (other.content != null) {
                return false;
            }
        } else if (!this.content.equals(other.content)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (this.sender == null) {
            if (other.sender != null) {
                return false;
            }
        } else if (!this.sender.equals(other.sender)) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Message [id=" + this.id + ", content=" + this.content + ", sender=" + this.sender + ", recommendedBy="
                        + this.recommendedBy + "]";
    }
}

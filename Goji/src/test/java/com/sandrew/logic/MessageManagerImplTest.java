/**
 *
 */
package com.sandrew.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.sandrew.dao.MessagePersistence;
import com.sandrew.dao.MessagePersistenceImpl;
import com.sandrew.domain.Message;
import com.sandrew.domain.User;

/**
 *
 */
public class MessageManagerImplTest {

    private MessageManager mgr;

    private MessagePersistence dao;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.dao = mock(MessagePersistenceImpl.class);
        this.mgr = new MessageManagerImpl(this.dao);
        doNothing().when(this.dao).addOrUpdateMessage(any(Message.class));
    }

    /**
     * Test method for
     * {@link com.sandrew.logic.MessageManagerImpl#recommendMessage(com.sandrew.domain.Message, com.sandrew.domain.User)}.
     */
    @Test
    public final void testRecommendMessage() {
        final User u1 = new User(1, "JC");
        final User u2 = new User(2, "Marc");
        final User u3 = new User(3, "Charles");

        final Message m1 = new Message(1, "Hello", u1);
        this.mgr.recommendMessage(m1, u2);
        this.mgr.recommendMessage(m1, u3);

        when(this.dao.getMessages()).thenReturn(Sets.newHashSet(m1));
        final Set<User> expected = Sets.newHashSet(u2, u3);

        final Set<Message> u1Messages = this.mgr.getUserMessages(u1);

        assertEquals(1, u1Messages.size());
        final Message result = u1Messages.iterator().next();
        assertEquals(expected, result.getRecommendedBy());
    }

    /**
     * Test method for
     * {@link com.sandrew.logic.MessageManagerImpl#recommendMessage(com.sandrew.domain.Message, com.sandrew.domain.User)}.
     */
    @Test
    public final void testRecommendMessage_noRecommendedMessage() {
        final User u1 = new User(1, "JC");

        final Message m1 = new Message(1, "Hello", u1);

        when(this.dao.getMessages()).thenReturn(Sets.newHashSet(m1));

        final Set<Message> u1Messages = this.mgr.getUserMessages(u1);

        assertEquals(1, u1Messages.size());
        final Message result = u1Messages.iterator().next();
        assertEquals(new HashSet<User>(), result.getRecommendedBy());
    }

    /**
     * Test method for {@link com.sandrew.logic.MessageManagerImpl#getUserMessages(com.sandrew.domain.User)}.
     */
    @Test
    public final void testGetUserMessages() {
        final User u1 = new User(1, "JC");
        final User u2 = new User(2, "Marc");

        final Message m1 = new Message(1, "Hello", u1);
        final Message m2 = new Message(2, "World", u1);
        final Message m3 = new Message(3, "Salut", u2);

        when(this.dao.getMessages()).thenReturn(Sets.newHashSet(m1, m2, m3));

        final Set<Message> expected1 = Sets.newHashSet(m1, m2);

        assertEquals(expected1, this.mgr.getUserMessages(u1));
    }

    /**
     * Test method for {@link com.sandrew.logic.MessageManagerImpl#getUserMessages(com.sandrew.domain.User)}.
     */
    @Test
    public final void testGetUserMessages_userHasNoMessage() {
        final User u1 = new User(1, "JC");
        final User u2 = new User(2, "Marc");

        final Message m1 = new Message(1, "Hello", u2);
        final Message m2 = new Message(2, "World", u2);

        this.mgr.addOrUpdateMessage(m1);
        this.mgr.addOrUpdateMessage(m2);

        when(this.dao.getMessages()).thenReturn(Sets.newHashSet(m1, m2));

        final Set<Message> expected1 = Sets.newHashSet();

        assertEquals(expected1, this.mgr.getUserMessages(u1));
    }

    /**
     * Test method for {@link com.sandrew.logic.MessageManagerImpl#getMessageRecommenders()}.
     */
    @Test
    public final void testGetMessageRecommenders() {
        final User u1 = new User(1, "JC");
        final User u2 = new User(2, "Marc");
        final User u3 = new User(3, "Sophie");
        final User u4 = new User(4, "Claude");
        final User u5 = new User(5, "Clement");

        final Message m1 = new Message(1, "Hello", u1);
        final Message m2 = new Message(2, "World", u1);
        final Message m3 = new Message(3, "Salut", u2);
        final Message m4 = new Message(3, "Buenos", u3);

        this.mgr.recommendMessage(m1, u2);
        this.mgr.recommendMessage(m1, u5);
        this.mgr.recommendMessage(m2, u4);

        when(this.dao.getMessages()).thenReturn(Sets.newHashSet(m1, m2, m3, m4));

        final Set<User> expected = Sets.newHashSet(u2, u4, u5);

        assertEquals(expected, this.mgr.getMessageRecommenders());

    }

    /**
     * Test method for {@link com.sandrew.logic.MessageManagerImpl#getMessageRecommenders()}.
     */
    @Test
    public final void testGetMessageRecommenders_MessageRecommendedBySameUserSeveralTime() {
        final User u1 = new User(1, "JC");
        final User u2 = new User(2, "Marc");
        final User u3 = new User(3, "Sophie");
        final User u4 = new User(4, "Claude");
        final User u5 = new User(5, "Clement");

        final Message m1 = new Message(1, "Hello", u1);
        final Message m2 = new Message(2, "World", u1);
        final Message m3 = new Message(3, "Salut", u2);
        final Message m4 = new Message(3, "Buenos", u3);

        /*
         * m1 recommended by u2 three time.
         */
        this.mgr.recommendMessage(m1, u2);
        this.mgr.recommendMessage(m1, u5);
        this.mgr.recommendMessage(m2, u4);
        this.mgr.recommendMessage(m1, u2);
        this.mgr.recommendMessage(m1, u2);

        when(this.dao.getMessages()).thenReturn(Sets.newHashSet(m1, m2, m3, m4));

        final Set<User> expected = Sets.newHashSet(u2, u4, u5);

        assertEquals(expected, this.mgr.getMessageRecommenders());

    }

    /**
     * Test method for {@link com.sandrew.logic.MessageManagerImpl#getMostRecommendedMessage()}.
     */
    @Test
    public final void testGetMostRecommendedMessage() {
        final User u1 = new User(1, "JC");
        final User u2 = new User(2, "Marc");
        final User u3 = new User(3, "Sophie");
        final User u4 = new User(4, "Claude");
        final User u5 = new User(5, "Clement");

        final Message m1 = new Message(1, "Hello", u1);
        final Message m2 = new Message(2, "World", u1);
        final Message m3 = new Message(3, "Salut", u2);
        final Message m4 = new Message(3, "Buenos", u3);

        /*
         * m1 recommended twice, m2, m3 and m4 once.
         */
        this.mgr.recommendMessage(m1, u2);
        this.mgr.recommendMessage(m1, u5);
        this.mgr.recommendMessage(m2, u4);
        this.mgr.recommendMessage(m3, u4);
        this.mgr.recommendMessage(m4, u3);

        when(this.dao.getMessages()).thenReturn(Sets.newHashSet(m1, m2, m3, m4));

        final Optional<Message> msgOptional = this.mgr.getMostRecommendedMessage();

        assertTrue(msgOptional.isPresent());
        assertEquals(m1, msgOptional.get());
    }

    /**
     * Test method for {@link com.sandrew.logic.MessageManagerImpl#getMostRecommendedMessage()}.
     */
    @Test
    public final void testGetMostRecommendedMessage_NoRecommendedMessage() {
        final User u1 = new User(1, "JC");
        final User u2 = new User(2, "Marc");
        final User u3 = new User(3, "Sophie");

        final Message m1 = new Message(1, "Hello", u1);
        final Message m2 = new Message(2, "World", u1);
        final Message m3 = new Message(3, "Salut", u2);
        final Message m4 = new Message(3, "Buenos", u3);

        this.mgr.addOrUpdateMessage(m1);
        this.mgr.addOrUpdateMessage(m2);
        this.mgr.addOrUpdateMessage(m3);
        this.mgr.addOrUpdateMessage(m4);

        when(this.dao.getMessages()).thenReturn(Sets.newHashSet(m1, m2, m3, m4));

        final Optional<Message> msgOptional = this.mgr.getMostRecommendedMessage();

        assertFalse(msgOptional.isPresent());
    }

    /**
     * Test method for {@link com.sandrew.logic.MessageManagerImpl#getMostRecommendedMessage()}.
     */
    @Test
    public final void testGetMostRecommendedMessage_MessagesRecommendedTheSameNumberOfTime() {
        final User u1 = new User(1, "JC");
        final User u2 = new User(2, "Marc");
        final User u3 = new User(3, "Sophie");

        final Message m1 = new Message(1, "Hello", u1);
        final Message m2 = new Message(2, "World", u1);
        final Message m3 = new Message(3, "Salut", u2);
        final Message m4 = new Message(3, "Buenos", u3);

        /*
         * m1 & m2 are recommended twice, m3 once, m4 not at all.
         */
        this.mgr.recommendMessage(m1, u2);
        this.mgr.recommendMessage(m1, u3);
        this.mgr.recommendMessage(m2, u1);
        this.mgr.recommendMessage(m2, u3);
        this.mgr.recommendMessage(m3, u1);
        this.mgr.addOrUpdateMessage(m4);

        when(this.dao.getMessages()).thenReturn(Sets.newHashSet(m1, m2, m3, m4));

        final Optional<Message> msgOptional = this.mgr.getMostRecommendedMessage();

        assertTrue(msgOptional.isPresent());

        assertTrue(msgOptional.get().equals(m1) || msgOptional.get().equals(m2));
    }

    /**
     * Test method for {@link com.sandrew.logic.MessageManagerImpl#getMostRecommendedMessage()}.
     */
    @Test
    public final void testGetMostRecommendedMessage_MessagesRecommendedSeveralTimeBySameUser() {
        final User u1 = new User(1, "JC");
        final User u2 = new User(2, "Marc");
        final User u3 = new User(3, "Sophie");

        final Message m1 = new Message(1, "Hello", u1);
        final Message m2 = new Message(2, "World", u2);
        final Message m3 = new Message(3, "Salut", u2);
        final Message m4 = new Message(3, "Buenos", u3);

        /*
         * m1 recommended thrice by u2, m2 recommended by two different users
         */
        this.mgr.recommendMessage(m1, u2);
        this.mgr.recommendMessage(m1, u2);
        this.mgr.recommendMessage(m1, u2);
        this.mgr.recommendMessage(m2, u1);
        this.mgr.recommendMessage(m2, u3);
        this.mgr.addOrUpdateMessage(m4);

        when(this.dao.getMessages()).thenReturn(Sets.newHashSet(m1, m2, m3, m4));

        final Optional<Message> msgOptional = this.mgr.getMostRecommendedMessage();

        assertTrue(msgOptional.isPresent());

        assertTrue(msgOptional.get().equals(m2));
    }
}

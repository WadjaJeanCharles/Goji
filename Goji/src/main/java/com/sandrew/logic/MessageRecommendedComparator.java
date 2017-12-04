/**
 *
 */
package com.sandrew.logic;

import java.util.Comparator;

import com.sandrew.domain.Message;

/**
 * Compares messages based on the number of recommendations.
 */
public class MessageRecommendedComparator implements Comparator<Message> {

    /**
     * Compares messages based on the number of recommendations.
     *
     * @return -1 if arg0 has less recommender than arg1, 0 if they have the same number of recommender, 1 otherwise.
     */
    @Override
    public int compare(final Message arg0, final Message arg1) {
        return new Integer(arg0.getRecommendedBy().size()).compareTo(arg1.getRecommendedBy().size());
    }

}

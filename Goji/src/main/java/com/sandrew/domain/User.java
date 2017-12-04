/**
 *
 */
package com.sandrew.domain;

/**
 * Represents a person using the messaging system. The person can send messages or recommand any of them.
 */
public final class User {

    /**
     * Unique per person
     */
    private final long id;

    /**
     * Name of the person.
     */
    private final String name;

    /**
     * Creates a messaging system user.
     *
     * @param id
     *            ID of the user.
     * @param name
     *            Name of the user.
     */
    public User(final long id, final String name) {
        super();
        this.id = id;
        this.name = name;
    }

    /**
     * @return the id
     */
    public long getId() {
        return this.id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
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
        result = (prime * result) + (int) (this.id ^ (this.id >>> 32));
        result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
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
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
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
        return "User [id=" + this.id + ", name=" + this.name + "]";
    }
}

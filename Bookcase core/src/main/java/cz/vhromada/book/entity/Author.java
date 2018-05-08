package cz.vhromada.book.entity;

import java.util.Objects;

import cz.vhromada.common.Movable;

/**
 * A class represents author.
 *
 * @author Vladimir Hromada
 */
public class Author implements Movable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * First name
     */
    private String firstName;

    /**
     * Middle name
     */
    private String middleName;

    /**
     * Last name
     */
    private String lastName;

    /**
     * Position
     */
    private Integer position;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Returns first name.
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets a new value to first name.
     *
     * @param firstName new value
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns middle name.
     *
     * @return middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets a new value to middle name.
     *
     * @param middleName new value
     */
    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    /**
     * Returns last name.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets a new value to last name.
     *
     * @param lastName new value
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Author) || id == null) {
            return false;
        }

        return id.equals(((Author) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Author [id=%d, firstName=%s, middleName=%s, lastName=%s, position=%d]", id, firstName, middleName, lastName, position);
    }

}

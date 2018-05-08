package cz.vhromada.book.web.fo;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

/**
 * A class represents FO for category.
 *
 * @author Vladimir Hromada
 */
public class CategoryFO implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * Name
     */
    private @NotBlank String name;

    /**
     * Position
     */
    private Integer position;

    /**
     * Returns ID.
     *
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets a new value to ID.
     *
     * @param id new value
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Returns name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new value to name.
     *
     * @param name new value
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns position.
     *
     * @return position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Sets a new value to position.
     *
     * @param position new value
     */
    public void setPosition(final Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CategoryFO) || id == null) {
            return false;
        }

        return id.equals(((CategoryFO) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("CategoryFO [id=%d, name=%s, position=%d]", id, name, position);
    }

}

package start;

/**
 * Gemeinsame Basisklasse fuer Entitaeten mit technischer ID.
 *
 * Version 5: Vermeidet redundanten ID-Code in mehreren Klassen.
 */
public abstract class BaseEntity {

    private int id;

    protected BaseEntity() {
        this(0);
    }

    protected BaseEntity(int id) {
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID darf nicht negativ sein!");
        }
        this.id = id;
    }
}
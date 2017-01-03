package hu.kecskesk;

/**
 * Created by kkrisz on 2017.01.02..
 */
public class El {
    public int id;
    public Csucs be;
    public Csucs ki;

    public El(int id, Csucs be, Csucs ki) {
        this.id = id;
        this.be = be;
        this.ki = ki;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        El el = (El) o;

        return id == el.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return id + ": " + be.id + "-" + ki.id;
    }
}

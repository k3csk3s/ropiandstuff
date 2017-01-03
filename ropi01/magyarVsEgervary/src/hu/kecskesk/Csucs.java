package hu.kecskesk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkrisz on 2017.01.02..
 */
public class Csucs {
    public int id;
    public List<El> elek;

    public Csucs(int id) {
        this.id = id;
        elek = new ArrayList<>();
    }

    public El connect(Csucs c, int id) {
        El e = new El(id, c, this);
        this.elek.add(e);
        c.elek.add(e);
        return e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Csucs csucs = (Csucs) o;

        return id == csucs.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "csucs" + id;
    }
}

package hu.kecskesk;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kkrisz on 2017.01.02..
 */
public class CimkezettCsucs {
    public int c;
    public List<SulyozottEl> elek;
    public int id;


    public CimkezettCsucs(int id) {
        this.id = id;
        elek = new ArrayList<>();
        c = 0;
    }

    public SulyozottEl connect(CimkezettCsucs c, int id, int w) {
        SulyozottEl e = new SulyozottEl(id, c, this, w);
        this.elek.add(e);
        c.elek.add(e);
        return e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CimkezettCsucs that = (CimkezettCsucs) o;

        if (c != that.c) return false;
        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = c;
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "CimkezettCsucs{" +
                "c=" + c +
                ", id=" + id +
                '}';
    }
}

package hu.kecskesk;

/**
 * Created by kkrisz on 2017.01.02..
 */
public class SulyozottEl {
    public int id;
    public int w;
    public CimkezettCsucs be;
    public CimkezettCsucs ki;


    public SulyozottEl(int id, CimkezettCsucs be, CimkezettCsucs ki, int _w) {
        this.id = id;
        this.be = be;
        this.ki = ki;
        w = _w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SulyozottEl that = (SulyozottEl) o;

        if (id != that.id) return false;
        if (w != that.w) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + w;
        return result;
    }

    @Override
    public String toString() {
        return "SulyozottEl{" +
                "id=" + id +
                ", w=" + w +
                ", be=" + be.id +
                ", ki=" + ki.id +
                '}';
    }
}

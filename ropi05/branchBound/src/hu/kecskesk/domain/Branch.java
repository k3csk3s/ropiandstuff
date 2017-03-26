package hu.kecskesk.domain;

/**
 * Branch & bound branch
 *
 * Created by kkrisz on 2017.01.05..
 */
public class Branch {
   public LinearisProgram lp;
   public Integer f;
   public Integer g;
   public Integer w;

    public Branch(LinearisProgram lp, Integer f, Integer g, Integer w) {
        this.lp = lp;
        this.f = f;
        this.g = g;
        this.w = w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Branch branch = (Branch) o;

        if (lp != null ? !lp.equals(branch.lp) : branch.lp != null) return false;
        if (f != null ? !f.equals(branch.f) : branch.f != null) return false;
        if (g != null ? !g.equals(branch.g) : branch.g != null) return false;
        return w != null ? w.equals(branch.w) : branch.w == null;
    }

    @Override
    public int hashCode() {
        int result = lp != null ? lp.hashCode() : 0;
        result = 31 * result + (f != null ? f.hashCode() : 0);
        result = 31 * result + (g != null ? g.hashCode() : 0);
        result = 31 * result + (w != null ? w.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "lp=" + lp +
                ", f=" + f +
                ", g=" + g +
                ", w=" + w +
                '}';
    }
}

package hu.kecskesk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkrisz on 2017.01.02..
 */
public class Parositas {
    public List<El> mEl;
    public List<Csucs> mCsucs;

    public Parositas() {
        mEl = new ArrayList<>();
        mCsucs = new ArrayList<>();
    }

    public void add(El e) {
        if (mEl.contains(e)) {
            throw new RuntimeException("already have e.");
        } else if (mCsucs.contains(e.be)) {
            throw new RuntimeException("already have e be.");
        } else if (mCsucs.contains(e.ki)) {
            throw new RuntimeException("already have e ki.");
        } else {
            mEl.add(e);
            mCsucs.add(e.be);
            mCsucs.add(e.ki);
        }
    }

    public void remove(El e) {
        if (!mEl.contains(e)) {
            System.out.println("info - Unnecess remove");
        }

        mEl.remove(e);
        mCsucs.remove(e.be);
        mCsucs.remove(e.ki);
    }

    public void addAll(List<El> elek) {
        for (El e: elek) {
            add(e);
        }
    }

    public void removeAll(List<El> elek) {
        for (El e: elek) {
            remove(e);
        }
    }

    @Override
    public String toString() {
        String s = "Parositas: \n";

        for (El e : mEl) {
            s += e.be.id + " - " + e.ki.id + "\n";
        }

        return s;
    }

    public boolean containsElById(int id) {
        return mEl.stream().anyMatch(el -> el.id == id);
    }

    public boolean containsCsucsById(int id) {
        return mCsucs.stream().anyMatch(cs -> cs.id == id);
    }
}

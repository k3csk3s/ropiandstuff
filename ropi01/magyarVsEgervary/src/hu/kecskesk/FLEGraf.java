package hu.kecskesk;

import java.util.List;

/**
 * Created by kkrisz on 2017.01.02..
 */
public class FLEGraf {
    public List<Csucs> F;
    public List<Csucs> L;
    public List<El> E;

    public FLEGraf(List<Csucs> f, List<Csucs> l, List<El> e) {
        F = f;
        L = l;
        E = e;
    }

    public El getEById(int id) {
        return E.stream().filter(el -> el.id == id).findFirst().get();
    }

    public Csucs getFById(int id) {
        return F.stream().filter(cs -> cs.id == id).findFirst().get();
    }

    public Csucs getLById(int id) {
        return L.stream().filter(cs -> cs.id == id).findFirst().get();
    }

    public boolean isFById(int id) {
        return F.stream().anyMatch(cs -> cs.id == id);
    }
}

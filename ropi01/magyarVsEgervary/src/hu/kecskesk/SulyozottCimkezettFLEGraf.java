package hu.kecskesk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kkrisz on 2017.01.02..
 */
public class SulyozottCimkezettFLEGraf {
    public List<CimkezettCsucs> F;
    public List<CimkezettCsucs> L;
    public List<SulyozottEl> E;

    public SulyozottCimkezettFLEGraf(List<CimkezettCsucs> f, List<CimkezettCsucs> l, List<SulyozottEl> e) {
        F = f;
        L = l;
        E = e;
    }

    public FLEGraf toFLEGraf() {
        List<Csucs> newF = new ArrayList<>();
        List<Csucs> newL = new ArrayList<>();
        List<El> newE = new ArrayList<>();

        Map<Integer, Csucs> csucsMap = new HashMap<>();

        for (CimkezettCsucs cimkezettCsucs : F) {
            Csucs newCsucs = new Csucs(cimkezettCsucs.id);
            newF.add(newCsucs);
            csucsMap.put(newCsucs.id, newCsucs);
        }
        for (CimkezettCsucs cimkezettCsucs : L) {
            Csucs newCsucs = new Csucs(cimkezettCsucs.id);
            newL.add(newCsucs);
            csucsMap.put(newCsucs.id, newCsucs);
        }
        for (SulyozottEl sulyozottEl : E) {
            newE.add(csucsMap.get(sulyozottEl.be.id).connect(csucsMap.get(sulyozottEl.ki.id), sulyozottEl.id));
        }

        return new FLEGraf(newF, newL, newE);
    }

    public int getSize() {
        return F.size() > L.size() ? F.size() : L.size();
    }
}

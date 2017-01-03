package hu.kecskesk;

import com.sun.prism.shader.DrawCircle_ImagePattern_Loader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kkrisz on 2017.01.02..
 */
public class SulyozottParositas {
    public List<SulyozottEl> mEl;
    public List<CimkezettCsucs> mCsucs;

    public SulyozottParositas() {
        mEl = new ArrayList<>();
        mCsucs = new ArrayList<>();
    }

    @Override
    public String toString() {
        String s = "Sulyozott Parositas: \n";

        for (SulyozottEl e : mEl) {
            s += e.be.id + " - " + e.ki.id + "\n";
        }

        return s;
    }

    public int getSumW() {
        return mEl.stream().mapToInt(el -> el.w).sum();
    }

    public int getSumC() {
        return mCsucs.stream().mapToInt(csucs -> csucs.c).sum();
    }
}

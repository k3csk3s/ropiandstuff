package hu.kecskesk;

import com.sun.xml.internal.ws.message.ByteArrayAttachment;

import java.util.*;
import java.util.stream.Collectors;

public class Ropi01App {

    public static void main(String[] args) {
        //testMM();
        testEA();
    }

    private static void testMM() {
        Csucs c1 = new Csucs(1);
        Csucs c2 = new Csucs(2);
        Csucs c3 = new Csucs(3);
        Csucs c4 = new Csucs(4);
        Csucs c5 = new Csucs(5);
        Csucs c6 = new Csucs(6);

        El e1 = c1.connect(c4, 1);
        El e2 = c1.connect(c2, 2);
        El e3 = c3.connect(c4, 3);
        El e4 = c3.connect(c6, 4);
        El e5 = c5.connect(c2, 5);

        List<Csucs> F = Arrays.asList(c1, c3, c5);
        List<Csucs> L = Arrays.asList(c2, c4, c6);
        List<El> E = Arrays.asList(e1, e2, e3, e4, e5);

        FLEGraf fleGraf = new FLEGraf(F, L, E);

        Parositas magyarPar = magyarModszer(fleGraf);
        System.out.println(magyarPar.toString());
    }

    private static void testEA() {
        CimkezettCsucs c1 = new CimkezettCsucs(1);
        CimkezettCsucs c2 = new CimkezettCsucs(2);
        CimkezettCsucs c3 = new CimkezettCsucs(3);
        CimkezettCsucs c4 = new CimkezettCsucs(4);
        CimkezettCsucs c5 = new CimkezettCsucs(5);
        CimkezettCsucs c6 = new CimkezettCsucs(6);

        SulyozottEl e1 = c1.connect(c2, 1, 1);
        SulyozottEl e2 = c1.connect(c4, 2, 2);
        SulyozottEl e3 = c3.connect(c2, 3, 3);
        SulyozottEl e4 = c3.connect(c4, 4, 1);
        SulyozottEl e5 = c3.connect(c6, 5, 3);
        SulyozottEl e6 = c5.connect(c4, 6, 2);
        SulyozottEl e7 = c5.connect(c6, 8, 1);

        List<CimkezettCsucs> F = Arrays.asList(c1, c3, c5);
        List<CimkezettCsucs> L = Arrays.asList(c2, c4, c6);
        List<SulyozottEl> E = Arrays.asList(e1, e2, e3, e4, e5, e6, e7);

        SulyozottCimkezettFLEGraf fleGraf = new SulyozottCimkezettFLEGraf(F, L, E);

        SulyozottParositas egervaryPar = egervaryAlgoritmus(fleGraf);
        System.out.println(egervaryPar.toString());
    }

    private static SulyozottParositas egervaryAlgoritmus(SulyozottCimkezettFLEGraf cimkezettFLEGraf) {
        setFToMaxE(cimkezettFLEGraf);

        SulyozottParositas sulyozottParositas = getPirosSulyozottParositas(cimkezettFLEGraf);

        while (sulyozottParositas.mEl.size() != cimkezettFLEGraf.getSize()) {
            FL13bontas fl13bontas = getF13Bontas(cimkezettFLEGraf, sulyozottParositas);

            int delta = calculateDelta(fl13bontas);
            cimkezDelta(fl13bontas, cimkezettFLEGraf, delta);
            sulyozottParositas = getPirosSulyozottParositas(cimkezettFLEGraf);
        }

        return sulyozottParositas;
    }

    private static void cimkezDelta(FL13bontas fl13bontas, SulyozottCimkezettFLEGraf cimkezettFLEGraf, int delta) {
        for (CimkezettCsucs f : cimkezettFLEGraf.F) {
            if (fl13bontas.F1.contains(f) || fl13bontas.F2.contains(f)) {
                f.c -= delta;
            }
        }

        for (CimkezettCsucs l : cimkezettFLEGraf.L) {
            if (fl13bontas.L2.contains(l)) {
                l.c += delta;
            }
        }
    }

    private static int calculateDelta(FL13bontas fl13bontas) {
        int delta = Integer.MAX_VALUE;
        List<CimkezettCsucs> F1uF2 = new ArrayList<>();
        F1uF2.addAll(fl13bontas.F1);
        F1uF2.addAll(fl13bontas.F2);
        List<CimkezettCsucs> L1uL3 = new ArrayList<>();
        L1uL3.addAll(fl13bontas.L1);
        L1uL3.addAll(fl13bontas.L3);

        for (CimkezettCsucs f : F1uF2) {
            for (SulyozottEl el : f.elek) {
                CimkezettCsucs veg = el.be.equals(f) ? el.ki : el.be;
                if (L1uL3.contains(veg)) {
                    // El megy F1 u F2bol L1 u L3ba
                    if (delta > (veg.c + f.c - el.w)) {
                        delta = (veg.c + f.c - el.w);
                    }
                }
            }
        }

        return delta;
    }

    public static FL13bontas getF13Bontas(SulyozottCimkezettFLEGraf cimkezettFLEGraf, SulyozottParositas sulyozottParositas) {
        FL13bontas fl13bontas = new FL13bontas();
        // F1 = F-M
        fl13bontas.F1.addAll(cimkezettFLEGraf.F);
        fl13bontas.F1.removeAll(sulyozottParositas.mCsucs);

        // L1 = L-M
        fl13bontas.L1.addAll(cimkezettFLEGraf.L);
        fl13bontas.L1.removeAll(sulyozottParositas.mCsucs);

        // L2 F1ből alt.úton elérhető
        fl13bontas.L2 = szelessegiKereses(fl13bontas.F1, sulyozottParositas);

        // F2 = L2 M szerinti párjai
        fl13bontas.F2 = sulyozottParositas.mEl.stream()
                .filter(sulyozottEl -> fl13bontas.L2.contains(sulyozottEl.be)
                    || fl13bontas.L2.contains(sulyozottEl.ki))
                .map(sulyozottEl -> fl13bontas.L2.contains(sulyozottEl.be) ? sulyozottEl.ki : sulyozottEl.be)
                .collect(Collectors.toList());

        // F3 = F - F1 - F2
        fl13bontas.F3.addAll(cimkezettFLEGraf.F);
        fl13bontas.F3.removeAll(fl13bontas.F1);
        fl13bontas.F3.removeAll(fl13bontas.F2);

        // L3 = L - L1 - L2
        fl13bontas.L3.addAll(cimkezettFLEGraf.L);
        fl13bontas.L3.removeAll(fl13bontas.L1);
        fl13bontas.L3.removeAll(fl13bontas.L2);

        return fl13bontas;
    }

    private static SulyozottParositas getPirosSulyozottParositas(SulyozottCimkezettFLEGraf cimkezettFLEGraf) {
        FLEGraf pirosReszgraf = findPirosreszgraf(cimkezettFLEGraf);
        Parositas parositas = magyarModszer(pirosReszgraf);
        return combine(cimkezettFLEGraf, parositas);
    }

    private static SulyozottParositas combine(SulyozottCimkezettFLEGraf cimkezettFLEGraf, Parositas parositas) {
        SulyozottParositas sulyozottParositas = new SulyozottParositas();

        List<SulyozottEl> mEl = cimkezettFLEGraf.E.stream()
                .filter(sulyozottEl -> parositas.containsElById(sulyozottEl.id))
                .collect(Collectors.toList());



        List<CimkezettCsucs> mCsucs = new ArrayList<CimkezettCsucs>();
        mCsucs.addAll(cimkezettFLEGraf.F);
        mCsucs.addAll(cimkezettFLEGraf.L);

        mCsucs = mCsucs.stream()
                .filter(cimkezettCsucs -> parositas.containsCsucsById(cimkezettCsucs.id))
                .collect(Collectors.toList());

        sulyozottParositas.mEl = mEl;
        sulyozottParositas.mCsucs = mCsucs;
        return sulyozottParositas;
    }


    private static FLEGraf findPirosreszgraf(SulyozottCimkezettFLEGraf cimkezettFLEGraf) {
        FLEGraf csupaszitottFLEGraf = cimkezettFLEGraf.toFLEGraf();


        Set<Csucs> newF = new HashSet<>();
        Set<Csucs> newL = new HashSet<>();
        List<El> newE = new ArrayList<>();


        for (SulyozottEl se : cimkezettFLEGraf.E) {
            if (se.w == se.be.c + se.ki.c) {
                newE.add(csupaszitottFLEGraf.getEById(se.id));

                if (csupaszitottFLEGraf.isFById(se.be.id)) {
                    newF.add(csupaszitottFLEGraf.getFById(se.be.id));
                    newL.add(csupaszitottFLEGraf.getLById(se.ki.id));
                } else {
                    newF.add(csupaszitottFLEGraf.getFById(se.ki.id));
                    newL.add(csupaszitottFLEGraf.getLById(se.be.id));
                }
            }
        }

        List<El> torlendo = new ArrayList<>();
        torlendo.addAll(csupaszitottFLEGraf.E);
        torlendo.removeAll(newE);

        List<Csucs> newFList = new ArrayList<>();
        newFList.addAll(newF);
        List<Csucs> newLList = new ArrayList<>();
        newLList.addAll(newL);

        for (Csucs f : newFList) {
            f.elek.removeAll(torlendo);
        }

        for (Csucs l : newLList) {
            l.elek.removeAll(torlendo);
        }

        return new FLEGraf(newFList, newLList, newE);
    }

    private static void setFToMaxE(SulyozottCimkezettFLEGraf fleGraf) {
        for (CimkezettCsucs f : fleGraf.F) {
            int maxSuly = f.elek.stream().mapToInt(el -> el.w).max().getAsInt();
            f.c = maxSuly;
        }
    }

    private static Parositas magyarModszer(FLEGraf fleGraf) {
        Parositas parositas = new Parositas();

        List<El> javitoUt = findJavitoUt(fleGraf, parositas);
        while (javitoUt != null) {
            List<El> jMinM = new ArrayList<>();
            jMinM.addAll(javitoUt);
            jMinM.removeAll(parositas.mEl);

            List<El> jMetszM = new ArrayList<>();
            jMetszM.addAll(javitoUt);
            jMetszM.removeAll(jMinM);

            parositas.removeAll(jMetszM);
            parositas.addAll(jMinM);

            javitoUt = findJavitoUt(fleGraf, parositas);
        }

        return parositas;
    }

    private static List<El> findJavitoUt(FLEGraf fleGraf, Parositas parositas) {
        List<Csucs> ptlanF = new ArrayList<>();
        ptlanF.addAll(fleGraf.F);
        List<Csucs> ptlanL = new ArrayList<>();
        ptlanL.addAll(fleGraf.L);

        for (El e : parositas.mEl) {
            ptlanF.remove(e.be);
            ptlanF.remove(e.ki);
            ptlanL.remove(e.be);
            ptlanL.remove(e.ki);
        }

        if (!ptlanF.isEmpty() && !ptlanL.isEmpty()) {

            for (Csucs f : ptlanF) {
                List<El> javitoUt = new ArrayList<>();

                return findJavRekurziv(20, parositas, javitoUt, ptlanF, ptlanL, f, true);
            }
        }

        return null;
    }

    private static List<El> findJavRekurziv(int max,
                                         Parositas parositas,
                                         List<El> javitoUt,
                                         List<Csucs> ptlanF,
                                         List<Csucs> ptlanL,
                                         Csucs current,
                                         boolean fToL) {
        if (max < 1) {
            throw new RuntimeException("rekurziv tulcsord");
        }

        // Minden elre az akt. csucsbol
        for (El e : current.elek) {
            // Ha nem jartunk mar arra
            if (!javitoUt.contains(e)){
                // Az el masik vege
                Csucs veg = e.be.equals(current) ? e.ki : e.be;
                // Fbol Lbe
                if (fToL) {
                    // Az el nem szabadna Mbeli legyen
                    if (!parositas.mEl.contains(e)) {
                        // Ha a vegpont ptlan, van egy utunk
                        if (ptlanL.contains(veg)) {
                            javitoUt.add(e);
                            return javitoUt;
                        } else {
                            // Ha nem akkor kell egy felfele ut ami M-beli
                            List<El> recUt = findJavitoRecCall(max, parositas, javitoUt, ptlanF, ptlanL, veg, e, fToL);
                            if (recUt != null) return recUt;
                        }
                    }
                } else {
                    //Lbol Fbe: megkeressuk a parositott vegpontjat
                    List<El> recUt = findJavitoRecCall(max, parositas, javitoUt, ptlanF, ptlanL, veg, e, fToL);
                    if (recUt != null) return recUt;
                }
            }
        }

        return null;
    }

    private static List<El> findJavitoRecCall(int max,
                                              Parositas parositas,
                                              List<El> javitoUt,
                                              List<Csucs> ptlanF,
                                              List<Csucs> ptlanL,
                                              Csucs veg,
                                              El e,
                                              boolean fToL) {
        if (parositas.mCsucs.contains(veg)) {
            javitoUt.add(e);
            List<El> tempUtVar = findJavRekurziv(max - 1, parositas, javitoUt, ptlanF, ptlanL, veg, !fToL);
            if (tempUtVar != null) {
                return tempUtVar;
            }
        }
        javitoUt.remove(e);
        return null;
    }

    private static List<CimkezettCsucs> szelessegiKereses(List<CimkezettCsucs> F1, SulyozottParositas parositas) {
        Set<CimkezettCsucs> L2 = new HashSet<>();

        for (CimkezettCsucs f : F1) {
            L2.addAll(szelessegiKeresesRek(20, F1, f, L2, true, parositas));
        }


        List<CimkezettCsucs> L2list = new ArrayList<CimkezettCsucs>();
        L2list.addAll(L2);
        return L2list;
    }

    private static Set<CimkezettCsucs> szelessegiKeresesRek(int max, List<CimkezettCsucs> F1, CimkezettCsucs current,
                                                            Set<CimkezettCsucs> L2, boolean currentF,
                                                            SulyozottParositas parositas) {
        if (max < 1) {
            throw new RuntimeException("rekurziv tulcsord");
        }

        for (SulyozottEl el : current.elek) {
            if (piros(el)) {
                CimkezettCsucs veg = el.be.equals(current) ? el.ki : el.be;
                if (L2.contains(veg) || !parositas.mCsucs.contains(veg)) {
                    return L2;
                }

                if (currentF) {
                    L2.add(veg);
                }

                L2.addAll(szelessegiKeresesRek(max - 1, F1, veg, L2, !currentF, parositas));
            }
        }

        return L2;
    }

    private static boolean piros(SulyozottEl el) {
        return el.w == (el.be.c + el.ki.c);
    }
}

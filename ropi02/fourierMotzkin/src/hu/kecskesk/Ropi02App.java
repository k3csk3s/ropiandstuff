package hu.kecskesk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ropi02App {

    public static void main(String[] args) {
        testFM();
    }

    private static void testFM() {
        LinearisProgram lp = getMockLP3DP();
        LinearisProgram dp = getDualis(lp);

        LinearisProgram lpNext = fourierMotzin(lp);
        while (lpNext.n > 1) {
            lpNext = fourierMotzin(lpNext);
        }

        fourierOsztas(lpNext);
        System.out.println("Az eredmény:\n" + lpNext.toString());
        System.out.println("Az eredmény érékelése:\n" + ertekel(lpNext));

        LinearisProgram dpNext = fourierMotzin(dp);
        while (dpNext.n > 1) {
            dpNext = fourierMotzin(dpNext);
        }

        fourierOsztas(dpNext);
        System.out.println("A duális eredmény:\n" + dpNext.toString());
        System.out.println("Az duális eredmény érékelése:\n" + ertekel(dpNext));
    }

    private static LinearisProgram getDualis(LinearisProgram lp) {
        LinearisProgram dp = new LinearisProgram(lp.m, lp.n);
        dp.A = transpose(lp.A);
        dp.b = lp.c;
        dp.c = lp.b;
        return dp;
    }

    private static List<List<Double>> transpose(List<List<Double>> table) {
        List<List<Double>> ret = new ArrayList<>();
        final int N = table.get(0).size();
        for (int i = 0; i < N; i++) {
            List<Double> col = new ArrayList<>();
            for (List<Double> row : table) {
                col.add(row.get(i));
            }
            ret.add(col);
        }
        return ret;
    }

    private static LinearisProgram getMockLP1() {
        List<List<Double>> A = getMockA1();
        List<Double> b = getMockB1();
        return new LinearisProgram(3 ,6 , A, b);
    }

    private static LinearisProgram getMockLP2() {
        List<List<Double>> A = getMockA2();
        List<Double> b = getMockB2();
        return new LinearisProgram(5 ,4 , A, b);
    }

    private static LinearisProgram getMockLP3DP() {
        LinearisProgram lp = new LinearisProgram(3,5);
        List<List<Double>> A = getMockA3();
        List<Double> b = getMockB3();
        List<Double> c = getMockC3();
        lp.A = A;
        lp.b = b;
        lp.c = c;

        return lp;
    }

    private static LinearisProgram getMockLP4DP() {
        LinearisProgram lp = new LinearisProgram(4,5);
        List<List<Double>> A = getMockA4();
        List<Double> b = getMockB4();
        List<Double> c = getMockC4();
        lp.A = A;
        lp.b = b;
        lp.c = c;

        return lp;
    }

    private static List<Double> getMockB1() {
        return new ArrayList<>(Arrays.asList(-4.0, 3.0, -4.0, 0.0, 0.0, 0.0));
    }

    private static List<Double> getMockB2() {
        return new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0));
    }

    private static List<Double> getMockB3() {
        return new ArrayList<>(Arrays.asList(3.0, -3.0, 1.0, 0.0, 0.0));
    }

    private static List<Double> getMockC3() {
        return new ArrayList<>(Arrays.asList(1.0, 0.0, 1.0));
    }

    private static List<List<Double>> getMockA3() {
        List<Double> a1 = new ArrayList<>(Arrays.asList(7.0, 5.0, 3.0));
        List<Double> a2 = new ArrayList<>(Arrays.asList(-7.0, -5.0, -3.0));
        List<Double> a3 = new ArrayList<>(Arrays.asList(2.0, 4.0, 1.0));
        List<Double> a4 = new ArrayList<>(Arrays.asList(-1.0 , 0.0, 0.0));
        List<Double> a5 = new ArrayList<>(Arrays.asList(0.0, -1.0, 0.0));

        return new ArrayList<>(Arrays.asList(a1, a2, a3, a4, a5));
    }

    private static List<Double> getMockB4() {
        return new ArrayList<>(Arrays.asList(-3.0, 14.0, -14.0, 11.0, 10.0));
    }

    private static List<Double> getMockC4() {
        return new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 1.0));
    }

    private static List<List<Double>> getMockA4() {
        List<Double> a1 = new ArrayList<>(Arrays.asList(5.0, 0.0, -10.0, 1.0));
        List<Double> a2 = new ArrayList<>(Arrays.asList(5.0, 0.0, 0.0, 2.0));
        List<Double> a3 = new ArrayList<>(Arrays.asList(-5.0, 0.0, 0.0, -2.0));
        List<Double> a4 = new ArrayList<>(Arrays.asList(0.0, 0.0, 1.0, 4.0));
        List<Double> a5 = new ArrayList<>(Arrays.asList(-2.0, 3.0, 4.0, 1.0));

        return new ArrayList<>(Arrays.asList(a1, a2, a3, a4, a5));
    }

    private static List<List<Double>> getMockA1() {
        List<Double> a1 = new ArrayList<>(Arrays.asList(-2.0, -1.0, 1.0));
        List<Double> a2 = new ArrayList<>(Arrays.asList(1.0, 3.0, 1.0));
        List<Double> a3 = new ArrayList<>(Arrays.asList(-1.0, -1.0, -2.0));
        List<Double> a4 = new ArrayList<>(Arrays.asList(-1.0 , 0.0, 0.0));
        List<Double> a5 = new ArrayList<>(Arrays.asList(0.0, -1.0, 0.0));
        List<Double> a6 = new ArrayList<>(Arrays.asList(0.0, 0.0, -1.0));

        return new ArrayList<>(Arrays.asList(a1, a2, a3, a4, a5, a6));
    }

    private static List<List<Double>> getMockA2() {
        List<Double> a1 = new ArrayList<>(Arrays.asList(1.0, 1.0, 0.0, 0.0, 0.0));
        List<Double> a2 = new ArrayList<>(Arrays.asList(0.0, 1.0, 1.0, 0.0, 0.0));
        List<Double> a3 = new ArrayList<>(Arrays.asList(0.0, 0.0, 1.0, 1.0, 0.0));
        List<Double> a4 = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 1.0, 1.0));

        return new ArrayList<>(Arrays.asList(a1, a2, a3, a4));
    }

    private static String ertekel(LinearisProgram lpNext) {
        List<List<Double>> aZero = new ArrayList<>();
        List<List<Double>> aPlus = new ArrayList<>();
        List<List<Double>> aMinus = new ArrayList<>();


        List<Double> bZero = new ArrayList<>();
        List<Double> bPlus = new ArrayList<>();
        List<Double> bMinus = new ArrayList<>();

        for (int m = 0; m < lpNext.m; m++) {
            if (lpNext.A.get(m).get(0) == 0) {
                aZero.add(lpNext.A.get(m).subList(0, lpNext.n));
                bZero.add(lpNext.b.get(m));
            } else if (lpNext.A.get(m).get(0) == 1) {
                aPlus.add(lpNext.A.get(m).subList(0, lpNext.n));
                bPlus.add(lpNext.b.get(m));
            } else if (lpNext.A.get(m).get(0) == -1) {
                aMinus.add(lpNext.A.get(m).subList(0, lpNext.n));
                bMinus.add(lpNext.b.get(m));
            }
        }

        if (bZero.stream().anyMatch(aDouble -> aDouble < 0)) {
            return "bZeroban vna negativ tag, nem megoldható";
        }

        if (aPlus.isEmpty() || aMinus.isEmpty()) {
            return "aPlus aMinusz üres, megoldható";
        }

        if (bPlus.isEmpty() || bMinus.isEmpty()) {
            return "bPlus bMinusz üres, ???";
        }

        double min = bPlus.stream().min(Double::compareTo).get();
        double max = bMinus.stream().max((o1, o2) -> o1.compareTo(o2) * -1).get();

        if (min >= -max) {
            return "a legkisebb bPlusz: " + min + " nagyobb, mint a legnagyobb bMinusz negáltja: " + -max + ". megoldható.";
        } else {
            return "a legkisebb bPlusz: " + min + " kisebb, mint a legnagyobb bMinusz negáltja: " + -max + ". nem megoldható.";
        }
    }

    private static LinearisProgram fourierMotzin(LinearisProgram lp) {
        System.out.println("FM " + lp.n + "változós:\n" + lp.toString());

        fourierOsztas(lp);

        System.out.println("osztas utan:\n" + lp.toString());

        List<List<Double>> aZero = new ArrayList<>();
        List<List<Double>> aPlus = new ArrayList<>();
        List<List<Double>> aMinus = new ArrayList<>();


        List<Double> bZero = new ArrayList<>();
        List<Double> bPlus = new ArrayList<>();
        List<Double> bMinus = new ArrayList<>();

        for (int m = 0; m < lp.m; m++) {
            if (lp.A.get(m).get(0) == 0) {
                aZero.add(lp.A.get(m).subList(1, lp.n));
                bZero.add(lp.b.get(m));
            } else if (lp.A.get(m).get(0) == 1) {
                aPlus.add(lp.A.get(m).subList(1, lp.n));
                bPlus.add(lp.b.get(m));
            } else if (lp.A.get(m).get(0) == -1) {
                aMinus.add(lp.A.get(m).subList(1, lp.n));
                bMinus.add(lp.b.get(m));
            }
        }

        List<List<Double>> aAdded = new ArrayList<>();
        List<Double> bAdded = new ArrayList<>();

        for (List<Double> rowInPlus : aPlus) {
            for (List<Double> rowInMinus : aMinus) {
                List<Double> added = IntStream.range(0, rowInPlus.size())
                        .mapToObj(i -> rowInPlus.get(i) + rowInMinus.get(i))
                        .collect(Collectors.toList());

                aAdded.add(added);


                Double added_b = bPlus.get(aPlus.indexOf(rowInPlus)) + bMinus.get(aMinus.indexOf(rowInMinus));
                bAdded.add(added_b);
            }
        }

        LinearisProgram lpNext = new LinearisProgram(lp.n - 1, aAdded.size() + aZero.size());
        lpNext.A.addAll(aAdded);
        lpNext.A.addAll(aZero);
        lpNext.b.addAll(bAdded);
        lpNext.b.addAll(bZero);

        lpNext.c = lp.c;

        return lpNext;
    }

    private static void fourierOsztas(LinearisProgram lp) {
        for (int m = 0; m < lp.m; m++) {
            double divider = Math.abs(lp.A.get(m).get(0));
            if (divider != 0) {
                lp.b.set(m, lp.b.get(m) / divider);
            }
            for (int n = 0; n < lp.n; n++) {
                if (divider != 0) {
                    lp.A.get(m).set(n, lp.A.get(m).get(n) / divider);
                }
            }
        }
    }
}

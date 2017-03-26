package hu.kecskesk.data;

import hu.kecskesk.domain.LinearisProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Creates mock data for LPs and such
 *
 * Created by kkrisz on 2017.01.05..
 */
public class MockLPSupplier {

    /**
     * Feladat 1: sikertelen LP
     */
    public static LinearisProgram getMockLP1DP() {
        LinearisProgram lp = new LinearisProgram(3, 6);
        List<List<Double>> A = getMockA1();
        List<Double> b = getMockB1();
        List<Double> c = getMockC1();
        lp.A = A;
        lp.b = b;
        lp.c = c;

        return lp;
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

    private static List<Double> getMockB1() {
        return new ArrayList<>(Arrays.asList(-4.0, 3.0, -4.0, 0.0, 0.0, 0.0));
    }

    private static List<Double> getMockC1() {
        return new ArrayList<>(Arrays.asList(1.0, 0.5, 1.0));
    }

    /**
     * Feladat 2:
     */
    public static LinearisProgram getMockLP2DP() {
        LinearisProgram lp = new LinearisProgram(5,4);
        List<List<Double>> A = getMockA2();
        List<Double> b = getMockB2();
        List<Double> c = getMockC2();
        lp.A = A;
        lp.b = b;
        lp.c = c;

        return lp;
    }

    private static List<List<Double>> getMockA2() {
        List<Double> a1 = new ArrayList<>(Arrays.asList(1.0, 1.0, 0.0, 0.0, 0.0));
        List<Double> a2 = new ArrayList<>(Arrays.asList(0.0, 1.0, 1.0, 0.0, 0.0));
        List<Double> a3 = new ArrayList<>(Arrays.asList(0.0, 0.0, 1.0, 1.0, 0.0));
        List<Double> a4 = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 1.0, 1.0));

        return new ArrayList<>(Arrays.asList(a1, a2, a3, a4));
    }

    private static List<Double> getMockB2() {
        return new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0));
    }

    private static List<Double> getMockC2() {
        return new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0));
    }

    /**
     * Feladat 3: 2016.4.19 ZH 1
     */
    public static LinearisProgram getMockLP3DP() {
        LinearisProgram lp = new LinearisProgram(3,5);
        List<List<Double>> A = getMockA3();
        List<Double> b = getMockB3();
        List<Double> c = getMockC3();
        lp.A = A;
        lp.b = b;
        lp.c = c;

        return lp;
    }

    private static List<List<Double>> getMockA3() {
        List<Double> a1 = new ArrayList<>(Arrays.asList(7.0, 5.0, 3.0));
        List<Double> a2 = new ArrayList<>(Arrays.asList(-7.0, -5.0, -3.0));
        List<Double> a3 = new ArrayList<>(Arrays.asList(2.0, 4.0, 1.0));
        List<Double> a4 = new ArrayList<>(Arrays.asList(-1.0 , 0.0, 0.0));
        List<Double> a5 = new ArrayList<>(Arrays.asList(0.0, -1.0, 0.0));

        return new ArrayList<>(Arrays.asList(a1, a2, a3, a4, a5));
    }

    private static List<Double> getMockB3() {
        return new ArrayList<>(Arrays.asList(3.0, -3.0, 1.0, 0.0, 0.0));
    }

    private static List<Double> getMockC3() {
        return new ArrayList<>(Arrays.asList(1.0, 0.0, 1.0));
    }

    /**
     * Feladat 4: 2015.4.14 zh
     */
    public static LinearisProgram getMockLP4DP() {
        LinearisProgram lp = new LinearisProgram(4,5);
        List<List<Double>> A = getMockA4();
        List<Double> b = getMockB4();
        List<Double> c = getMockC4();
        lp.A = A;
        lp.b = b;
        lp.c = c;

        return lp;
    }

    private static List<List<Double>> getMockA4() {
        List<Double> a1 = new ArrayList<>(Arrays.asList(5.0, 0.0, -10.0, 1.0));
        List<Double> a2 = new ArrayList<>(Arrays.asList(0.0, 5.0, 0.0, 2.0));
        List<Double> a3 = new ArrayList<>(Arrays.asList(0.0, -5.0, 0.0, -2.0));
        List<Double> a4 = new ArrayList<>(Arrays.asList(0.0, 0.0, 1.0, 4.0));
        List<Double> a5 = new ArrayList<>(Arrays.asList(-2.0, 3.0, 4.0, 1.0));

        return new ArrayList<>(Arrays.asList(a1, a2, a3, a4, a5));
    }

    private static List<Double> getMockB4() {
        return new ArrayList<>(Arrays.asList(-3.0, 14.0, -14.0, 11.0, 10.0));
    }

    private static List<Double> getMockC4() {
        return new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 1.0));
    }

    /**
     * Feladat 5:
     */
    public static LinearisProgram getMockLP5DP() {
        LinearisProgram lp = new LinearisProgram(4,8);
        List<List<Double>> A = getMockA5();
        List<Double> b = getMockB5();
        List<Double> c = getMockC5();
        lp.A = A;
        lp.b = b;
        lp.c = c;

        return lp;
    }

    private static List<List<Double>> getMockA5() {
        List<Double> a1 = new ArrayList<>(Arrays.asList(1.0, 0.0, 0.0, -1.0));
        List<Double> a2 = new ArrayList<>(Arrays.asList(1.0, 1.0, 0.0, 0.0));
        List<Double> a3 = new ArrayList<>(Arrays.asList(0.0, 1.0, 1.0, -1.0));
        List<Double> a4 = new ArrayList<>(Arrays.asList(0.0, 0.0 , -1.0, 0.0));
        List<Double> a5 = new ArrayList<>(Arrays.asList(-1.0, 0.0, 0.0, 0.0));
        List<Double> a6 = new ArrayList<>(Arrays.asList(0.0, -1.0, 0.0, 0.0));
        List<Double> a7 = new ArrayList<>(Arrays.asList(0.0, 0.0, -1.0, 0.0));
        List<Double> a8 = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, -1.0));

        return new ArrayList<>(Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8));
    }

    private static List<Double> getMockB5() {
        return new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0));
    }

    private static List<Double> getMockC5() {
        return new ArrayList<>(Arrays.asList(5.0, 2.0, 0.0, -5.0));
    }

    /**
     * Feladat 6:
     */
    public static LinearisProgram getMockLP6DP() {
        LinearisProgram lp = new LinearisProgram(2,4);
        List<List<Double>> A = getMockA6();
        List<Double> b = getMockB6();
        List<Double> c = getMockC6();
        lp.A = A;
        lp.b = b;
        lp.c = c;

        return lp;
    }

    private static List<List<Double>> getMockA6() {
        List<Double> a1 = new ArrayList<>(Arrays.asList(2.0, 3.0));
        List<Double> a2 = new ArrayList<>(Arrays.asList(4.0, 1.0));
        List<Double> a3 = new ArrayList<>(Arrays.asList(-1.0, 0.0));
        List<Double> a4 = new ArrayList<>(Arrays.asList(0.0, -1.0));

        return new ArrayList<>(Arrays.asList(a1, a2, a3, a4));
    }

    private static List<Double> getMockB6() {
        return new ArrayList<>(Arrays.asList(18.0, 16.0, 0.0, 0.0));
    }

    private static List<Double> getMockC6() {
        return new ArrayList<>(Arrays.asList(1.0, 1.0));
    }

    /**
     * Feladat 7:
     */
    public static LinearisProgram getMockLP7DP() {
        LinearisProgram lp = new LinearisProgram(4,10);
        List<List<Double>> A = getMockA7();
        List<Double> b = getMockB7();
        List<Double> c = getMockC7();
        lp.A = A;
        lp.b = b;
        lp.c = c;

        return lp;
    }

    private static List<List<Double>> getMockA7() {
        List<Double> a1 = new ArrayList<>(Arrays.asList(1.0, 1.0, 0.0, 0.0));
        List<Double> a2 = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 0.0));
        List<Double> a3 = new ArrayList<>(Arrays.asList(0.0, 1.0, 1.0, 1.0));
        List<Double> a4 = new ArrayList<>(Arrays.asList(0.0, 1.0 , 1.0, 1.0));
        List<Double> a5 = new ArrayList<>(Arrays.asList(0.0, 0.0, 1.0, 1.0));
        List<Double> a6 = new ArrayList<>(Arrays.asList(-1.0, -1.0, 0.0, 0.0));
        List<Double> a7 = new ArrayList<>(Arrays.asList(-1.0, -1.0, -1.0, 0.0));
        List<Double> a8 = new ArrayList<>(Arrays.asList(0.0, -1.0, -1.0, -1.0));
        List<Double> a9 = new ArrayList<>(Arrays.asList(0.0, -1.0 , -1.0, -1.0));
        List<Double> a10 = new ArrayList<>(Arrays.asList(0.0, 0.0, -1.0, -1.0));

        return new ArrayList<>(Arrays.asList(a1, a10, a2, a3, a4, a5, a6, a7, a8, a9));
    }

    private static List<Double> getMockB7() {
        return new ArrayList<>(Arrays.asList(1.0, -1.0, 2.0, 2.0, 2.0, 1.0, -1.0, -1.0, -1.0, -1.0));
    }

    private static List<Double> getMockC7() {
        return new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0));
    }

    /**
     * Feladat 8:
     */



}

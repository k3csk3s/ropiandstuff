package hu.kecskesk;

import hu.kecskesk.data.MockLPSupplier;
import hu.kecskesk.domain.DPErtekeles;
import hu.kecskesk.domain.DualisProgram;
import hu.kecskesk.domain.LPErtekeles;
import hu.kecskesk.domain.LinearisProgram;
import hu.kecskesk.helper.MatrixUtility;
import hu.kecskesk.solver.FourierMotzkinSolver;
import hu.kecskesk.solver.IntegerProgramSolver;

public class Ropi05App {

    private static final FourierMotzkinSolver FOURIER_MOTZKIN_SOLVER = new FourierMotzkinSolver();
    private static final IntegerProgramSolver INTEGER_PROGRAM_SOLVER = new IntegerProgramSolver();

    public static void main(String[] args) {
        testFM();
        //testIP();
    }

    private static void testFM() {
        LinearisProgram lp = MockLPSupplier.getMockLP7DP();
        LinearisProgram lpEredeti = new LinearisProgram(lp);
        DPErtekeles dpErtekeles = fourierMotzkinIteration(lp);

        while (dpErtekeles.sikeres && lpEredeti.n > 1) {
            LinearisProgram lpBehelyettesitett = behelyettesit(lpEredeti, dpErtekeles.yb);
            lp.x.add(dpErtekeles.yb);
            lpEredeti = new LinearisProgram(lpBehelyettesitett);
            dpErtekeles = fourierMotzkinIteration(lpBehelyettesitett);
        }

        if (dpErtekeles.sikeres) {
            behelyettesit(lpEredeti, dpErtekeles.yb);
            lp.x.add(dpErtekeles.yb);
            lp.x.sort((o1, o2) -> -1 * new Integer(lp.x.indexOf(o1)).compareTo(lp.x.indexOf(o2)));
            System.out.println("cx = " + MatrixUtility.multiply(lp.c, lp.x));
        }
    }

    private static DPErtekeles fourierMotzkinIteration(LinearisProgram lp) {
        DualisProgram dp;
        LPErtekeles lpErtekeles;
        DPErtekeles dpErtekeles;
        dp = new DualisProgram(lp, true);


        System.out.println("----------------------------------------------");
        System.out.println(lp);
        System.out.println();

        lpErtekeles = FOURIER_MOTZKIN_SOLVER.solveAndEvaluateLP(lp, "Az LP primer");

        if (lpErtekeles.sikeres) {
            dpErtekeles = FOURIER_MOTZKIN_SOLVER.solveAndEvaluateDP(dp, lpErtekeles);
            return dpErtekeles;
        } else {
            //return lpErtekeles;
            return null;
        }
    }

    private static LinearisProgram behelyettesit(LinearisProgram lp, Double x1) {
        LinearisProgram lpUj = new LinearisProgram(lp);
        System.out.println("!!!! az x" + lpUj.n + " = " + x1);
        for (int i = 0; i < lpUj.m; i++) {
            lpUj.b.set(i, lpUj.b.get(i) - x1 * lpUj.A.get(i).get(lpUj.n - 1));
            lpUj.A.get(i).remove(lpUj.n - 1);
        }

        lpUj.c.remove(lpUj.n - 1);
        lpUj.n--;

        return lpUj;
    }

    private static void testIP() {
        LinearisProgram ip = MockLPSupplier.getMockLP4DP();
        LinearisProgram idp = new DualisProgram(ip, true);

        INTEGER_PROGRAM_SOLVER.solveAndEvaluateLP(ip, "Az IP primer");
        INTEGER_PROGRAM_SOLVER.solveAndEvaluateLP(idp, "Az IDP duális");
    }
}

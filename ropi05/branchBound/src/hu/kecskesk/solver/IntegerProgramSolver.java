package hu.kecskesk.solver;

import hu.kecskesk.domain.Branch;
import hu.kecskesk.domain.LinearisProgram;
import hu.kecskesk.helper.MatrixUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.in;

/**
 * Utility class IP megoldására
 *
 * Created by kkrisz on 2017.01.05..
 */
public class IntegerProgramSolver {
    private static final FourierMotzkinSolver FOURIER_MOTZKIN_SOLVER = new FourierMotzkinSolver();

    public void solveAndEvaluateLP(LinearisProgram ip, String lpName) {
        System.out.println(MatrixUtility.draw(ip.A));
        if (totalisanUnimodularis(ip)) {
            System.out.println("A mártix TU.");
            FOURIER_MOTZKIN_SOLVER.solveAndEvaluateLP(ip, lpName);
        } else {
            System.out.println("A mátrix nem TU.");
            branchAndBound(ip);
        }
    }

    private void branchAndBound(LinearisProgram ip) {
        List<Branch> branches = new ArrayList<>();
        Branch baseBranch = new Branch(ip, 100, -100, Integer.MAX_VALUE);
        branches.add(baseBranch);
        Double zStar = Double.MAX_VALUE;
        List<Double> xStar = new ArrayList<>();

        while (!branches.isEmpty()) {
            Branch currentBranch = branches.get(0);
            branches.remove(currentBranch);
            if (currentBranch.w > zStar) {
                boolean sikeres = FOURIER_MOTZKIN_SOLVER
                        .solveAndEvaluateLP(ip, "bnb branch f: " + currentBranch.f).sikeres;
                double zI = MatrixUtility.multiply(ip.c, ip.x);
                if (zI <= zStar) {

                }

            }
        }

        System.out.println("A megoldás z*: " + zStar + " és x*: " + xStar);
    }

    private boolean totalisanUnimodularis(LinearisProgram lp) {
        List<Double> tuInts = new ArrayList<>(Arrays.asList(1.0,-1.0,0.0));

        List<List<List<Double>>> reszmatrixok = MatrixUtility.negyzetesReszmatrixok(lp.A);
        for (List<List<Double>> reszMatrix: reszmatrixok) {
            Double determinant = MatrixUtility.determinant(MatrixUtility.squareToArrayForm(reszMatrix));
            if (!tuInts.contains(determinant)) {
                return false;
            }
        }

        return true;
    }
}

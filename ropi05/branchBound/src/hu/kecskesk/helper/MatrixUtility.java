package hu.kecskesk.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NavigableMap;
import java.util.stream.Collectors;

/**
 * Collections of matrix functions
 * <p>
 * Created by kkrisz on 2017.01.05..
 */
public class MatrixUtility {
    public static List<List<Double>> transpose(List<List<Double>> table) {
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

    public static <T> String draw(List<List<T>> A) {
        String drawnA = "\n";
        for (List<T> row : A) {
            drawnA += row + "\n";
        }

        return drawnA;
    }

    public static List<List<List<Double>>> negyzetesReszmatrixok(List<List<Double>> A) {
        List<List<List<Double>>> negyzetesReszmatrixok = new ArrayList<>();
        int mMax = A.size();
        if (A.get(0) == null) {
            throw new RuntimeException("empty matrix");
        }
        int nMax = A.get(0).size();

        int kMax = nMax < mMax ? nMax : mMax;

        List<Integer> rowIds = getIds(mMax);
        List<Integer> columnIds = getIds(nMax);

        List<List<Integer>> rowCombinations = combine(rowIds, kMax);
        List<List<Integer>> columnCombinations = combine(columnIds, kMax);

        for (List<Integer> rowCombination : rowCombinations) {
            for (List<Integer> columnCombination : columnCombinations) {
                if (rowCombination.size() == columnCombination.size()) {
                    List<List<Double>> reszMatrix = new ArrayList<>();

                    for (Integer i : rowCombination) {
                        List<Double> newRow = new ArrayList<>();
                        for (Integer j : columnCombination) {
                            newRow.add(A.get(i).get(j));
                        }
                        reszMatrix.add(newRow);
                    }

                    negyzetesReszmatrixok.add(reszMatrix);
                }
            }
        }

        return negyzetesReszmatrixok;
    }

    private static List<Integer> getIds(int max) {
        List<Integer> ids = new ArrayList<>();

        for (int i = 0; i < max; i++) {
            ids.add(i);
        }

        return ids;
    }

    private static <T> List<List<T>> combine(List<T> list, int maxK) {
        List<List<T>> combinations = new ArrayList<>();
        for (int i = 1; i <= maxK; i++) {
            combinations.addAll(getCombinations(list, i));
        }
        return combinations;
    }

    private static <T> List<List<T>> getCombinations(List<T> list, int k) {
        List<List<T>> combinations = new ArrayList<>();
        if (k == 0) {
            combinations.add(new ArrayList<T>());
            return combinations;
        }
        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            List<T> rest = getSublist(list, i + 1);
            for (List<T> previous : getCombinations(rest, k - 1)) {
                previous.add(element);
                combinations.add(previous);
            }
        }
        return combinations;
    }

    private static <T> List<T> getSublist(List<T> list, int i) {
        List<T> sublist = new ArrayList<T>();
        for (int j = i; j < list.size(); j++) {
            sublist.add(list.get(j));
        }
        return sublist;
    }

    public static double[][] squareToArrayForm(List<List<Double>> matrix) {
        double[][] arrayForm = new double[matrix.size()][matrix.size()];

        for (int a = 0; a < arrayForm.length; a++) {
            for (int b = 0; b < arrayForm.length; b++) {
                arrayForm[a][b] = matrix.get(a).get(b);
            }
        }

        return arrayForm;
    }

    public static double determinant(double[][] matrix) { //method sig. takes a matrix (two dimensional array), returns determinant.
        double sum = 0;
        int s;
        if (matrix.length == 1) {  //bottom case of recursion. size 1 matrix determinant is itself.
            return (matrix[0][0]);
        }
        for (int i = 0; i < matrix.length; i++) { //finds determinant using row-by-row expansion
            double[][] smaller = new double[matrix.length - 1][matrix.length - 1]; //creates smaller matrix- values not in same row, column
            for (int a = 1; a < matrix.length; a++) {
                for (int b = 0; b < matrix.length; b++) {
                    if (b < i) {
                        smaller[a - 1][b] = matrix[a][b];
                    } else if (b > i) {
                        smaller[a - 1][b - 1] = matrix[a][b];
                    }
                }
            }
            if (i % 2 == 0) { //sign changes based on i
                s = 1;
            } else {
                s = -1;
            }
            sum += s * matrix[0][i] * (determinant(smaller)); //recursive step: determinant of larger determined by smaller.
        }
        return (sum); //returns determinant value. once stack is finished, returns final determinant.
    }

    public static Double multiply(List<Double> c, List<Double> x) {
        if (c.size() != x.size()) {
            throw new RuntimeException("invalid mx multipl.");
        }
        double result = 0;
        for (int i = 0; i < c.size(); i++){
            result += c.get(i) * x.get(i);
        }

        return result;
    }
}

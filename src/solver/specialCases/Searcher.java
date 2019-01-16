package solver.specialCases;

/**
 * Created by DIMA, on 10.12.2018
 */
public class Searcher {
    public static int searchRowDown(int startRow, double[][] matrix){
        for(int i = startRow + 1; i < matrix.length; i ++){
            if(Math.abs(matrix[i][startRow]) > Checker.DELTA_DOUBLE){
                return i;
            }
        }
        return -1;
    }

    public static int searchColumnRight(int row, double[][] matrix){
        for(int column = row + 1; column < matrix[row].length - 1; column++){
            if(Math.abs(matrix[row][column]) > Checker.DELTA_DOUBLE){
                return column;
            }
        }
        return -1;
    }

/*    public static int[][] searchInBottomLeftCorner(int startRow, int startColumn, double[][] matrix){
        int[][] rowAndColumn = null;
        for(int i = startRow + 1; i < matrix.length; i++){
           // for(int j = )
        }
        return rowAndColumn;
    }*/
}

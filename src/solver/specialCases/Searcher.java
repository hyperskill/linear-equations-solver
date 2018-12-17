package solver.specialCases;

/**
 * Created by DIMA, on 10.12.2018
 */
public class Searcher {
    public static int searchRowDown(int startRow, int targetColumn, double[][] matrix){
        int row = -1;
        for(int i = startRow + 1; i < matrix.length; i ++){
            if(Math.abs(matrix[i][targetColumn]) > Checker.DELTA_DOUBLE){
                return i;
            }
        }
        return row;
    }

    public static int searchColumnRight(int startColumn, int currentRow, double[][] matrix){
        int column = -1;
        for(int i = startColumn + 1; i < matrix[currentRow].length - 1; i++){
            if(Math.abs(matrix[currentRow][i]) > Checker.DELTA_DOUBLE){
                return i;
            }
        }
        return column;
    }

    public static int[][] searchInBottomLeftCorner(int startRow, int startColumn, double[][] matrix){
        int[][] rowAndColumn = null;
        for(int i = startRow + 1; i < matrix.length; i++){
           // for(int j = )
        }
        return rowAndColumn;
    }
}

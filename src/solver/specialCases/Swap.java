package solver.specialCases;

/**
 * Created by DIMA, on 06.12.2018
 */
public class Swap {
    private int oldColumnsNumber;
    private int newColumnsNumber;

    public Swap(int oldColumnsNumber, int newColumnsNumber) {
        this.oldColumnsNumber = oldColumnsNumber;
        this.newColumnsNumber = newColumnsNumber;
    }

    public int getOldColumnsNumber() {
        return oldColumnsNumber;
    }

    public int getNewColumnsNumber() {
        return newColumnsNumber;
    }
}

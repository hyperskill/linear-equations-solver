package solver.specialCases;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Created by DIMA, on 06.12.2018
 */
public class SwapStore {
    public static Deque<Swap> swapStack = new ArrayDeque<>();

    public static void addSwap(int oldColumn, int newColumn){
        Swap swap = new Swap(oldColumn, newColumn);
        swapStack.addLast(swap);
    }




}

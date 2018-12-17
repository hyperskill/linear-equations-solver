package solver.tmpTests;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by DIMA, on 17.12.2018
 */
public class TestVoid {
    public String getStringMultyTwo(int [] arr){
        String s = Arrays.stream(arr).boxed().map(String::valueOf).collect(Collectors.joining(" "));
        System.out.println(s);
        arr = deleteElem(arr, 3);
        s = Arrays.stream(arr).boxed().map(String::valueOf).collect(Collectors.joining(" "));
        System.out.println(s);
        devide(arr, 3);
        return Arrays.stream(arr).boxed().map(String::valueOf).collect(Collectors.joining(" "));



    }

    private int[] deleteElem(int[] arr, int index){
        int[] ar1 = new int[arr.length -1];
        for(int i = 0; i < index; i++){
            ar1[i] = arr[i];
        }
        for(int i = index+1; i < arr.length; i++){
            ar1[i-1] = arr[i];
        }
        //System.out.println(Arrays.stream(ar1).boxed().map(String::valueOf).collect(Collectors.joining(" ")));
        return ar1;
    }

    private void changeInt(int a){
        a = a*2;
    }

    private void devide (int[] arr, int a){
        for(int i = 0; i < arr.length; i++){
            arr[i] = arr[i]/a;
        }



    }

    public static void main(String[] args) {
        int[] a = {0,1,2,3,4,5,6,7};
        TestVoid t = new TestVoid();
        System.out.println(t.getStringMultyTwo(a));
    }


}

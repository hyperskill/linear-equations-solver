package solver.tmpTests;

import solver.Complex;

/**
 * Created by DIMA, on 16.01.2019
 */
public class TestSplit {

    private static Complex parseComplexString(String item){
        String[] items = item.split("[-,+]");
        int len = items.length;
        double real = 0;
        double imaginary = 0;
        switch (len){
            case 3:
                String[] items1 = item.split("\\+");
                if(items1[0].endsWith("i")){
                    // -real && -imaginary
                    real = Double.parseDouble(items[1]) * -1;
                    imaginary = Double.parseDouble(items[2].substring(0, items[2].length()-1)) * -1;
                }else {
                    // -real && imaginary
                    real = Double.parseDouble(items[1]) * -1;
                    imaginary = Double.parseDouble(items[2].substring(0, items[2].length()-1));
                }
                break;
            case 2:
                if(items[0].equals("")){
                    // -real || -imaginary
                    if(items[1].endsWith("i")){
                        // -imaginary
                        imaginary = Double.parseDouble(items[1].substring(0, items[1].length()-1)) * -1;
                        real = 0;
                    }else {
                        // -real
                        real = Double.parseDouble(items[1]) * -1;
                        imaginary = 0;
                    }
                }else {
                    // real && imaginary
                    items1 = item.split("\\+");
                    if(items1[0].endsWith("i")){
                        // real && -imaginary
                        real = Double.parseDouble(items[0]);
                        imaginary = Double.parseDouble(items[1].substring(0, items[1].length()-1)) * -1;
                    }else {
                        // real && imaginary
                        real = Double.parseDouble(items[0]);
                        imaginary = Double.parseDouble(items[1].substring(0, items[1].length()-1));
                    }

                }
                break;
            case 1:
                if(items[0].endsWith("i")){
                    // imaginary
                    imaginary = Double.parseDouble(items[0].substring(0, items[0].length()-1));
                    real = 0;
                }else {
                    // real
                    real = Double.parseDouble(items[0]);
                    imaginary = 0;
                }
                break;

        }
        return new Complex(real, imaginary);
    }

    public static void main(String[] args) {
        String[] items = {"1.5-1.1i", "-2.3i", "-1.6-2.6i", "-2.3", "5.6i", "5.6", "1.6+5.6i"};
        for(String item : items){
            System.out.println(parseComplexString(item));
        }

    }
}

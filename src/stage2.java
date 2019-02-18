import java.util.Scanner;

public class stage2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double a = sc.nextDouble();
        double b = sc.nextDouble();
        double c = sc.nextDouble();
        double d = sc.nextDouble();
        double e = sc.nextDouble();
        double f = sc.nextDouble();
        //a * x + b *y = c
        //d * x + e *y = d
        double x,y;
        y = (f - c*d/a)/(e-b*d/a);
        x = (c - b*y)/a;
        System.out.println(x+" "+y);
    }
}

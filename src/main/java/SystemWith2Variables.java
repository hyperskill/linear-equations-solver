import java.util.Scanner;

public class SystemWith2Variables {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double a,
                b,
                c,
                d,
                f,
                e,
                y,
                x;
        a = sc.nextDouble();
        b = sc.nextDouble();
        c = sc.nextDouble();
        d = sc.nextDouble();
        e = sc.nextDouble();
        f = sc.nextDouble();
        y = (f - c * d / a) / (e - b * d / a);
        x = (c - b * y) / a;
        System.out.println(x + " " + y);
    }
}

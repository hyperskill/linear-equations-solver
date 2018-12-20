import java.util.Scanner;
import java.util.Arrays;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
	
	makeGood maker = new makeGood();

	File file = new File("in.txt");
	try(Scanner scanner = new Scanner(file)) {
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		// n - число неизвестных (без ответа), m - число уравнений
		ComplexNumber[] line = new ComplexNumber[n + 1];
		Row[] row = new Row[m];
		String s = "";
		for (int j = 0; j < m; j++) 
		{	
			for (int i = 0; i <= n; i++){
				s = scanner.next();
				line[i] = maker.stringToComplex(s);
			}
			row[j] = new Row(n, line);
		}
		Matrix matrix = new Matrix(row, n, m);

		matrix.steppedViewDown();
	} catch (FileNotFoundException e) {
		System.out.println("I hope that never happen");
	}


	}
}


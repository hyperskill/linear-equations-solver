import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
/**
i - неизвестная, j - уравнение
*/
public class fourth {
	public static void main(String[] args) {
		File file = new File("in.txt");
		try(Scanner scanner = new Scanner(file)) {
			int n = scanner.nextInt();
			int m = scanner.nextInt();
			// n - число неизвестных, m - число уравнений
			double[] line = new double[n + 1];
			Row[] row = new Row[m];
			for (int j = 0; j < m; j++) 
			{	
				for (int i = 0; i <= n; i++){line[i] = scanner.nextDouble();}
				row[j] = new Row(n, line);
			}
			Matrix matrix = new Matrix(row, n, m);
			matrix.steppedViewDown();
		} catch (FileNotFoundException e) {
			System.out.println("I hope that never happen");
		}
	}
}

class Row {
	int N;
	// N - число неизвестных
	double[] line = new double[N];
	public Row(int N, double[] row) {
		this.line = Arrays.copyOf(row, N + 1);
		this.N = N;
	}

	double getValue(int i) {
		return this.line[i];
	}

	void add(Row other) {
		for (int i = 0; i <= this.N; i++) {
			this.line[i] += other.line[i];
		}
	}
	void mul(double num) {
		for (int i = 0; i <= this.N; i++) {
			this.line[i] = this.line[i] * num;
		}
	}

	String getString() {
		return Arrays.toString(this.line);
	}
}

class Matrix {
	int N;
	int M;
	Row[] rows = new Row[1];
	public Matrix(Row[] row, int n, int m) {
		this.rows = row;
		this.N = n;
		this.M = m;
	}
// true - ненулевое
	boolean checkValue(int i, int j) {  
		return (this.rows[j].getValue(i) == 0 ? false: true);
	}


	String resetAllDown(int i, int j) {
		String s = "";
		double coef = this.rows[j].getValue(i);		
		this.rows[j].mul(1 / coef);
		s = (1 / coef) +  "*R" + (j + 1) + " -> R" + (j + 1) + "\n";
		for (int k = j + 1; k < this.M; k++) {
			coef = this.rows[k].getValue(i);
			if (coef != 0) {
				this.rows[k].mul( (-1) / coef);
				this.rows[k].add(this.rows[j]);
				s = s + (-1) / coef + "*R" + (k + 1) + " + R" + (j + 1) + " -> R" + (k + 1) + "\n";
			}
		}
		return s;
	}

	String resetAllUp(int i, int j) {
		String s = "";
		double coef = this.rows[j].getValue(i);
		this.rows[j].mul(1 / coef);
		s = (1 / coef) +  "*R" + (j + 1) + " -> R" + (j + 1) + "\n";
		for (int k = j - 1; k >= 0; k--) {
			coef = this.rows[k].getValue(i);
			if (coef != 0) {
				this.rows[k].mul( (-1) / coef);
				this.rows[k].add(this.rows[j]); 
				s = s + (-1) / coef + "*R" + (k + 1) + " + R" + (j + 1) + " -> R" + (k + 1) + "\n";
			}
		}
		return s;
	}

	void steppedViewDown () {
		int j = 0;
		int i = 0;
		String s = "";
		s += "start solving the equation\n";
		s += "Rows manipulation\n";
		for (; i <= this.N && j < this.M; i++) {
			if (this.checkValue(i, j)) {
				s += this.resetAllDown(i, j);
				j += 1;
			}
			else { //пробегаю вниз. Если есть ненулевой - свапаю, если нет - перехожу к след i и j;
				for (int k = j + 1; k < this.M; k++) {
					if (this.checkValue(i, k)) {
						this.swap(i, k);
						s = s + "Swap R" + (i + 1) + " with R" + (k + 1) + "\n";
					}
				}
				if (!this.checkValue(i, j)) {
					i += 1;
				}
				i -= 1;
			}
		}

		if (j == 0) {
			s += "infinity solutions\n";
		}

		else {
			if (!this.checkNonzero(0, j - 1)) {
				s += "no solutions\n";
			}
			else {
				if (j != this.N) {
					s += "infinity solutions\n";
				}
				else {
					s += this.steppedViewUp();
				}
			}
		}
		File file = new File("out.txt");
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(s);
		} catch (IOException e) {
			System.out.printf("Exception");
		}
	}

	String steppedViewUp () {
		String s = "";
		for (int j = this.N - 1; j > 0; j--) {
			s += this.resetAllUp(j, j);
		}
		s += this.solve();
		return s;
	}
// true - есть ненулевой элемент помимо ответа
	boolean checkNonzero(int i, int j) {
		if (i < this.N - 1) {
			return (this.checkNonzero(i + 1, j) || this.checkValue(i, j));
		}
		else {
			return this.checkValue(i, j);
		}
	}

	void swap(int i, int j) { 
		Row row = this.rows[i];
		this.rows[i] = this.rows[j];
		this.rows[j] = row;
	}
	String getString() {
		String s = "";
		for (int i = 0; i < this.M; i++) {
			s += this.rows[i].getString();
		}
		return s;
	}
	String solve () {
		String s = "";
		s += "The solution is: (";
		for (int i = 0; i < this.N - 1; i++) {
			s = s + this.rows[i].getValue(this.N) + ", ";
		}
		s = s + this.rows[this.N - 1].getValue(this.N) + ")\n";
		return s;
	}

}

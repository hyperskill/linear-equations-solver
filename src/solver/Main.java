/**
Заранее извиняюсь у того, кто будет читать мой код.
В static происходит считывание с файла. matrix.diagonalizable() - это метод который "делает все", т.е. и преобразовывает матрицу 
и записывает в файл out.txt решение
Я предполагаю что файлы in.txt и out.txt находятся в той же директории, что и основная программа.
Далее, у класса Row есть основные методы: сложение с другим объектом этого класса (add) и умножение на число (mul)
Ну и getValue делает ровно то, что он должен делать.
У класса Matrix diagonalizable_down, diagonalizable_up - методы, которые приводят матрицу произвольную (с ненулевым определителем!
я тут и везде предполагал то, что система совместна!) к верхнетреугольному и нижнетреугольному видам. 
Их композиция, естественно, дает диагональную матрицу и метод solve - переписывание того, что стоит справа от равно в системе
т.к. в диагонализации так же реализован метод, который "делает на диагонали единички".
swap - перестановка двух строчек.
Поидее, можно написать метод для транспозиции и хватит только diagonalizable_down, но я не уверен
так же, поидее, можно сделать наследуемый класс - диагональных матриц и вывод ответа загнать в этот же класс. Может быть это 
даже ускорит программу (если избавиться от лишних действий с приведением главной диагонали к единичной, т.к. 
там эти действия выполняются чаще, чем нужно)
ну и, естественно, еще нужно добавить метод det для подсчета решений через определители
я не знаю какие из методов стоит реализовывать, скорее всего, через некоторое время мой внутренний перфекционист заставит себя
удалить весь этот ужасный код и переписать нормально, но я пока не привык к стандартам и не развил хороший вкус на джава код -.-
*/
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
public class Main {
	public static void main(String[] args) {
		File file = new File("in.txt");
		try(Scanner scanner = new Scanner(file)) {
			int n = scanner.nextInt();
			double[] line = new double[n + 1];
			Row[] row = new Row[n];
			for (int j = 0; j < n; j++) 
			{	
				for (int i = 0; i <= n; i++){line[i] = scanner.nextDouble();}
				row[j] = new Row(n, line);
			}
			Matrix matrix = new Matrix(row, n);
			matrix.diagonalizable();
		} catch (FileNotFoundException e) {
			System.out.println("I hope that never happen");
		}
	}
}
/*
Я не уверен, правильно ли я передаю параметры.
Т.е. у меня есть класс Row, сначала я хотел зашить получение всей информации 
о строке внутрь конструктора Row, но проблема, с которой я столкнулся:
dimension mismatch.
т.е. мне нужно было в классе Row как-то сделать массив даблов,
и как-то подгадать его размерность, но размерность я сразу не мог установить
в итоге получился такой (костыль?), что я в main считываю строку, а потом
уже полученную строку отправляю в конструктор Row.
В таком случае, вероятно, мне не нужно передавать параметр N, но я решил
его пока оставить, не знаю зачем.
P.s. как минимум мне нужен параметр N для copyOf, в джаве, оказывается, arr1 = arr2 - приравнивание ссылок
*/		
/*
Я думаю, класс Row (по крайней мере в моей реализации) тут излишен и можно было хранить все
в массива даблов, но я его написал для набивания руки
*/
class Row { // N на 1 больше, чем вход, т.к. тут я учитываю и ответ (то, что справа от равно)
	int N;
	double[] line = new double[N];
	public Row(int N, double[] row) {
		this.line = Arrays.copyOf(row, N + 1);
		this.N = N;
	}

	double getValue(int i) {
		return this.line[i];
	}

	void add(Row other) { //поидее можно сделать private?
		for (int i = 0; i <= this.N; i++) {
			this.line[i] += other.line[i];
		}
	}
	void mul(double num) { //умножение строки на константу
		for (int i = 0; i <= this.N; i++) {
			this.line[i] = this.line[i] * num;
		}
	}

	String getString() {
		return Arrays.toString(this.line);
	}
}

class Matrix {
/*
это очередным грабли (с инт Н), т.к. у меня не получилось найти длину массива Row, то я передавал длину из мэина,
не уверен, что это хорошая практика
*/
	int N;
	Row[] rows = new Row[1];
	public Matrix(Row[] row, int n) {
		this.rows = row;
		this.N = n;
	}

	boolean checkValue(int i, int j) { // true - значит этот элемент не 0; j строка i столбец 
		return (this.rows[j].getValue(i) == 0 ? false: true);
	}


	void diagonalizable() {	
		String s = "";
		s += "start solving the equation\n";
		s += "Rows manipulation\n";
		s += this.diagonalizable_down();
		s += this.diagonalizable_up();
		s += this.solve();
		File file = new File("out.txt");

		try (FileWriter writer = new FileWriter(file)) {
			writer.write(s);
		} catch (IOException e) {
			System.out.printf("Exception");
		}
	}
	String diagonalizable_down() {
		String s = "";
		for (int i = 0; i < this.N; i++) { 
			if(!this.checkValue(i, i)) { 
				for (int k = (i < this.N - 1) ? i + 1: this.N; k < this.N; k++) {
					if (this.checkValue(i, k)) {
						s = s + "Swap " + (i + 1) + " and " + (k + 1) + " rows\n";
						this.swap(i, k); 
					}
				}
			}
			else {
				double coef = this.rows[i].getValue(i);
				this.rows[i].mul(1 / coef);
				s = s + 1 / coef + " * R" + (i + 1) + " -> R" + (i + 1) + "\n";
				for (int j = (i < this.N - 1) ? i + 1: this.N; j < this.N; j++) {
					coef = (-1) * this.rows[j].getValue(i);
					//тут самое худшее место, но я не придумал, как сделать лучше
					//есть вариант только подправить add и mul в Row, но мне кажется это плохая идея
					this.rows[i].mul(coef);
					this.rows[j].add(this.rows[i]);
					this.rows[i].mul(1 / coef);
					s = s + coef + " * R" + (i + 1);
					s = s + " R" + (j + 1) + " -> R" + (j + 1) + "\n";
					s = s + coef + " * R" + (i + 1) + " -> R" + (i + 1) + "\n";
				}
				
			}
		}
		return s;
	}
	String diagonalizable_up() {
		String s = "";
		for (int i = this.N - 1; i >= 0; i--) {
			if(!this.checkValue(i, i)) {
				for (int k = (i > 0) ? i - 1: -1; k >= 0; k--) {
					if (this.checkValue(i, k)) {
						s = s + "Swap " + (i + 1) + " and " + (k + 1) + " rows" + "\n";
						this.swap(i, k);
					}
				}
			}
			else {
				double coef = this.rows[i].getValue(i);
				this.rows[i].mul(1 / coef);
				s = s + 1 / coef + " * R" + (i + 1) + " -> R" + (i + 1) + "\n";
				for (int j = (i > 0) ? i - 1: -1; j >= 0; j--) {
					coef = (-1) * this.rows[j].getValue(i);
					//тут самое худшее место, но я не знаю как переписать лучше, сначала решу, а потом оптимизирую
					this.rows[i].mul(coef);
					this.rows[j].add(this.rows[i]);
					this.rows[i].mul(1 / coef);
					s = s + coef + " * R" + (i + 1);
					s = s + " R" + (j + 1) + " -> R" + (j + 1);
					s = s + coef + " * R" + (i + 1) + " -> R" + (i + 1) + "\n";
				}
			}
		}
		return s;
	}
/*
тут свап работает так, как бы я этого хотел, но не так, как я думаю, что он должен работать ((
т.е. когда я пробовал так манипулировать с arrays, я увидел, что массивы записываются по ссылке
а тут происходит что-то в стиле arr1.copyOf(arr2), не совсем понимаю почему так
*/
	void swap(int i, int j) { 
		Row row = this.rows[i];
		this.rows[i] = this.rows[j];
		this.rows[j] = row;
	}
	String getString() {
		String s = "";
		for (int i = 0; i < this.N; i++) {
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

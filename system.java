/**
я тут еще не допилил считывание с файла (и считывание В файл ответов) 
и не допилил текстовый вид преобразований над строками
но хотел бы отправить, т.к. понимаю что мой код совершенно ужасен, я уже в нем немного путаюсь

*/
import java.util.*;

public class system {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		double[] line = new double[n + 1];
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
		Row[] row = new Row[n];
		for (int j = 0; j < n; j++) 
		{	
			for (int i = 0; i <= n; i++){line[i] = in.nextDouble();}
			row[j] = new Row(n, line);
		}
		Matrix matrix = new Matrix(row, n);
		System.out.println(matrix.getString());
		matrix.diagonalizable();
		System.out.println(matrix.getString());
	}
}
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
		this.diagonalizable_down();
		this.diagonalizable_up();
	}

	void diagonalizable_down() {
		for (int i = 0; i < this.N; i++) { 
			if(!this.checkValue(i, i)) { 
				for (int k = (i < this.N - 1) ? i + 1: this.N; k < this.N; k++) {
					if (this.checkValue(i, k)) {
						this.swap(i, k); 
					}
				}
			}
			else {
				double coef = this.rows[i].getValue(i);
				this.rows[i].mul(1 / coef);
				for (int j = (i < this.N - 1) ? i + 1: this.N; j < this.N; j++) {
					coef = (-1) * this.rows[j].getValue(i);
					//тут самое худшее место, но я не знаю как переписать лучше, сначала решу, а потом оптимизирую
					this.rows[i].mul(coef);
					this.rows[j].add(this.rows[i]);
					this.rows[i].mul(1 / coef);
				}
				
			}
		}
	}

	void diagonalizable_up() {
		for (int i = this.N - 1; i >= 0; i--) {
			if(!this.checkValue(i, i)) {
				for (int k = (i > 0) ? i - 1: -1; k >= 0; k--) {
					if (this.checkValue(i, k)) {
						this.swap(i, k);
					}
				}
			}
			else {
				double coef = this.rows[i].getValue(i);
				this.rows[i].mul(1 / coef);
				for (int j = (i > 0) ? i - 1: -1; j >= 0; j--) {
					coef = (-1) * this.rows[j].getValue(i);
					//тут самое худшее место, но я не знаю как переписать лучше, сначала решу, а потом оптимизирую
					this.rows[i].mul(coef);
					this.rows[j].add(this.rows[i]);
					this.rows[i].mul(1 / coef);
				}
			}
		}
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
}

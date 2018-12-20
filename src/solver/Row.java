/**
Я решительно не понимаю 1 важную вещь. Сейчас в конструкторе у меня цикл и вроде как лишние приравнения, сделано это потому,
что иначе при задании массива строк у нас присваивание будет (как я понимаю) по ссылке и в такой ситуации:
Row[] row = new Row[5]; 
row[0] = first_row; ... row[4] = fifth_row; в row[0] будет храниться  fifth_row и я не совсем понимаю почему. Точнее, я не понимаю
почему при таком задании конструктора как тут, все работает правильно.
*/
class Row { // N на 1 больше, чем вход, т.к. тут я учитываю и ответ (то, что справа от равно)
	int N;
	ComplexNumber[] line = new ComplexNumber[1];
	public Row(int N, ComplexNumber[] row) {
		this.line = new ComplexNumber[N + 1];
		for (int i = 0; i <= N; i++) {
			this.line[i] = row[i];
		}
		this.N = N;
	}

	ComplexNumber getValue(int i) {
		return this.line[i];
	}

	void add(Row other) { //поидее можно сделать private?
		for (int i = 0; i <= this.N; i++) {
			this.line[i] = this.line[i].add(other.line[i]);
		}
	}
	void mul(ComplexNumber num) { //умножение строки на константу
		for (int i = 0; i <= this.N; i++) {
			this.line[i] = this.line[i].mul(num);
		}
	}

	String getString() {
		String s = "[";
		for (int i = 0; i <= this.N - 1; i++) {
			s = s + this.getValue(i).toString() + ", ";
		}
		s = s + this.getValue(this.N).toString() + "]";
		return s;
	}
}

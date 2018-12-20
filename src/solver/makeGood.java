class makeGood {
	ComplexNumber stringToComplex(String s) {
		ComplexNumber num = new ComplexNumber(0, 0);
		String[] parts = new String[2];
		if (s.contains("+")) {
			parts = s.split("\\+");
			String re = parts[0];
			String im = parts[1].substring(0, parts[1].length() -1);
			double Re = Double.parseDouble(re);
			double Im = Double.parseDouble(im);
			num = new ComplexNumber(Re, Im);
		}

		else if (s.indexOf("-") > 0 || s.substring(1, s.length()).indexOf("-") >= 0)
		{
//			System.out.println("Yep, I can parse it too");
			if (s.indexOf("-") == 0) {
				parts = s.substring(1, s.length()).split("-");
				double Re = -Double.parseDouble(parts[0]);
				double Im = -Double.parseDouble(parts[1].substring(0, parts[1].length() - 1));
				num = new ComplexNumber(Re, Im);
			}
			else {
				parts = s.substring(0, s.length()).split("-");
				double Re = Double.parseDouble(parts[0]);
				double Im = -Double.parseDouble(parts[1].substring(0, parts[1].length() - 1));
				num = new ComplexNumber(Re, Im);
			}
		}

		else {
			if (s.contains("i")) {
				double Re = 0;
				double Im = Double.parseDouble(s.substring(0, s.length() - 1));
				num = new ComplexNumber(Re, Im);
			}
			else {
				double Re = Double.parseDouble(s);
				double Im = 0;
				num = new ComplexNumber(Re, Im);
			}
		}
		return num;
	}
}

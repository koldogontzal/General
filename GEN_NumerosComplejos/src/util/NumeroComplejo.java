package util;


public class NumeroComplejo {
	private double parteReal;
	private double parteImaginaria;

	public NumeroComplejo(double parteReal, double parteImaginaria) {
		this.parteReal = parteReal;
		this.parteImaginaria = parteImaginaria;
	}

	public NumeroComplejo(NumeroComplejo z) {
		this.parteReal = z.parteReal;
		this.parteImaginaria = z.parteImaginaria;
	}

	public NumeroComplejo(boolean coordenadasRadialesEnGrados, double radio, double angulo) {
		if (coordenadasRadialesEnGrados) {
			// El angulo esta en grados
			this.parteReal = radio * Math.cos(Math.toRadians(angulo));
			this.parteImaginaria = radio * Math.sin(Math.toRadians(angulo));
		} else {
			// El angulo esta en radianes
			this.parteReal = radio * Math.cos(angulo);
			this.parteImaginaria = radio * Math.sin(angulo);
		}
	}

	public double getParteReal() {
		return this.parteReal;
	}

	public double getParteImaginaria() {
		return this.parteImaginaria;
	}

	public double getRadio() {
		return Math.sqrt(this.parteReal * this.parteReal + this.parteImaginaria * this.parteImaginaria);
	}

	public double getAngulo(boolean coordenadasRadialesEnGrados) {
		if (coordenadasRadialesEnGrados) {
			// El angulo esta en grados
			return Math.toDegrees(Math.atan2(this.parteImaginaria, this.parteReal));

		} else {
			// El angulo esta en radianes
			return Math.atan2(this.parteImaginaria, this.parteReal);
		}
	}
}
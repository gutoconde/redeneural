package br.com.gutoconde.redeneural;

public class FuncaoTanh implements FuncaoDeAtivacao{

	@Override
	public Double calcular(double entrada) {
		return (2.0 /(1 + Math.exp(-2.0 * entrada))) - 1 ;
	}

	@Override
	public Double calcularDerivada(double entrada) {
		return 1.0 - Math.pow(entrada, 2.0);
	}
	
	@Override
	public Double calcular(double entrada, Double[] entradas) {
		return calcular(entrada);
	}

	@Override
	public Double calcularDerivada(double entrada, Double[] entradas) {
		return calcularDerivada(entrada);
	}

}

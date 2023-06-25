package br.com.gutoconde.redeneural;

public class FuncaoIdentidade implements FuncaoDeAtivacao{

	public Double calcular(double entrada) {
		return entrada;
	}

	@Override
	public Double calcularDerivada(double entrada) {
		return 0.0;
	}
	
	@Override
	public Double calcular(double entrada, Double[] entradas) {
		return calcular(entrada);
	}

	@Override
	public Double calcularDerivada(double entrada, Double[] entradas) {
		return calcularDerivada(entrada, entradas);
	}
	
}

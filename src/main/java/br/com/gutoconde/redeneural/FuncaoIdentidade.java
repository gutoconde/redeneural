package br.com.gutoconde.redeneural;

public class FuncaoIdentidade implements FuncaoDeAtivacao{

	public Double calcular(double entrada) {
		return entrada;
	}

	@Override
	public Double calcularDerivada(double entrada) {
		return 0.0;
	}
	
	
}

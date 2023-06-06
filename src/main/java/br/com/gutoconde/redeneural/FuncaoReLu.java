package br.com.gutoconde.redeneural;

public class FuncaoReLu implements FuncaoDeAtivacao{

	@Override
	public Double calcular(double entrada) {
		if(entrada >= 0.0) {
			return entrada;
		}
		return 0.0;
	}

	@Override
	public Double calcularDerivada(double entrada) {
		if(entrada >= 0.0) {
			return 1.0;
		}
		return 0.0;
	}

	
	
}

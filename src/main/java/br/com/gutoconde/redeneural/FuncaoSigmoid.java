package br.com.gutoconde.redeneural;

public class FuncaoSigmoid implements FuncaoDeAtivacao {
	
	public FuncaoSigmoid() {}
	
	@Override
	public Double calcular(double entrada) {
		return  1 / (1 + Math.exp(-entrada));
	}

	@Override
	public Double calcularDerivada(double entrada) {
		double valorSig = calcular(entrada);
		return valorSig * ( 1 - valorSig);
	}

	@Override
	public Double calcular(double entrada, Double[] entradas) {
		return calcular(entrada);
	}

	@Override
	public Double calcularDerivada(double entrada, Double[] entradas) {
		return calcularDerivada(entrada);
	}

	@Override
	public Double calcularFatorDeAjusteDoDelta(double entrada) {
		return calcularDerivada(entrada);
	}
	
	

}

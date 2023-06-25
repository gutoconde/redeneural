package br.com.gutoconde.redeneural;

public class FuncaoSoftMax implements FuncaoDeAtivacao {

	@Override
	public Double calcular(double entrada) {
		throw new UnsupportedOperationException("Operação não suportada por esta função de ativação.");
	}

	@Override
	public Double calcularDerivada(double entrada) {
		throw new UnsupportedOperationException("Operação não suportada por esta função de ativação.");
	}

	@Override
	public Double calcular(double entrada, Double[] entradas) {
		double somatorio = 0.0;
		for(int i = 0; i < entradas.length; i++) {
			somatorio += Math.exp(entradas[i]);
		}
		return Math.exp(entrada) / somatorio;
	}

	@Override
	public Double calcularDerivada(double entrada, Double[] entradas) {
		//double valorSoft = calcular(entrada, entradas);
		//return valorSoft * (1.0 - valorSoft);
		return 1.0;
	}

}

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
		//Usa o maior valor para normalizar
		double maiorValor = entradas[0];
		for(int i = 1; i < entradas.length; i++) {
			maiorValor = entradas[i] > maiorValor  ? entradas[i] : maiorValor;
		}
		
		for(int i = 0; i < entradas.length; i++) {
			somatorio += Math.exp(entradas[i] - maiorValor);
		}
		
		return Math.exp(entrada - maiorValor) / somatorio;
	}

	@Override
	public Double calcularDerivada(double entrada, Double[] entradas) {
		double valorSoft = calcular(entrada, entradas);
		return valorSoft * (1.0 - valorSoft);
	}

	@Override
	public Double calcularAjusteDelta(double entrada) {
		return 1.0;
	}

}

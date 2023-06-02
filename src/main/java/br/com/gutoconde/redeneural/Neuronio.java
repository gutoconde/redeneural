package br.com.gutoconde.redeneural;

import br.com.gutoconde.redeneural.util.RandomUtil;

public class Neuronio {

	private Double pesos[];
	
	private Double bias;
	
	private FuncaoDeAtivacao funcaoDeAtivacao;
	
	private Double[] entradas;
	private Double saida;
	
	private Double delta;
	
	private Neuronio(int numeroEntradas, FuncaoDeAtivacao funcaoDeAtivacao) {
		this.funcaoDeAtivacao = funcaoDeAtivacao;
		pesos = new Double[numeroEntradas];
	}
	
	private Neuronio(FuncaoDeAtivacao funcaoDeAtivacao, Double pesos[]) {
		this.funcaoDeAtivacao = funcaoDeAtivacao;
		this.pesos = pesos;
	}
	
	public static Neuronio criar(int numeroEntradas, FuncaoDeAtivacao funcaoDeAtivacao) {
		return new Neuronio(numeroEntradas, funcaoDeAtivacao);
	}
	
	public void setPesos(Double pesos[]) {
		this.pesos = pesos;
	}
	
	public Double[] getPesos() {
		return this.pesos;
	}
	
	public Double getBias() {
		return bias;
	}

	public void setBias(Double bias) {
		this.bias = bias;
	}

	public FuncaoDeAtivacao getFuncaoDeAtivacao() {
		return this.funcaoDeAtivacao;
	}
	
	public Double getSaida() {
		return saida;
	}
	
	public Double[] getEntradas() {
		return this.entradas;
	}
	
	public Double getDelta() {
		return delta;
	}

	public void setDelta(Double delta) {
		this.delta = delta;
	}

	public void inicializar(int numeroEntradas) {
		this.pesos = new Double[numeroEntradas];
		for(int i = 0; i < numeroEntradas; i++) {
			pesos[i] = RandomUtil.gerarNumeroAleatorio(-0.1, 0.1);
		}
		this.bias = RandomUtil.gerarNumeroAleatorio(-0.1, 0.1);
	}
	
	public Double calcular(Double entradas[]) throws RedeNeuralException{
		this.entradas = entradas;
		this.saida = 0.0;
		
		if(entradas == null) {
			throw new RedeNeuralException("Entradas neurônio não podem ser null.");
		}
		if(pesos == null) {
			throw new RedeNeuralException("Pesos do neurônio não podem ser null.");
		}
		if(bias == null) {
			throw new RedeNeuralException("Bias do neurônio não pode ser null.");
		}
		
		if(entradas.length != pesos.length) {
			throw new RedeNeuralException("O numero de pesos deve ser igual ao número de entradas do neurônio.");
		}
		Double somatorio = 0.0;
		for (int i = 0; i < pesos.length; i++) {
			somatorio = somatorio + entradas[i] * pesos[i];
		}
		
		somatorio = somatorio + bias;
		saida = funcaoDeAtivacao.calcular(somatorio);
		return saida; 
		
	}
}

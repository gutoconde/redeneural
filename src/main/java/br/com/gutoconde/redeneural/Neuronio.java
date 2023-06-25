package br.com.gutoconde.redeneural;

import java.io.Serializable;

import br.com.gutoconde.redeneural.util.RandomUtil;

public class Neuronio implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double pesos[];
	
	private Double bias;
	
	private Double gradientesAcumulados[];
	
	private Double gradienteAcumuladoBias;
	
	private transient FuncaoDeAtivacao funcaoDeAtivacao;
	
	private transient Double[] entradas;
	
	private transient Double somatorio = 0.0;
	
	private transient Double saida;
	
	private transient Double delta;
	
	private Neuronio(int numeroEntradas, FuncaoDeAtivacao funcaoDeAtivacao) {
		this.funcaoDeAtivacao = funcaoDeAtivacao;
		pesos = new Double[numeroEntradas];
	}
	
	private Neuronio(FuncaoDeAtivacao funcaoDeAtivacao, Double pesos[], Double bias) {
		this.funcaoDeAtivacao = funcaoDeAtivacao;
		this.pesos = pesos;
		this.bias = bias;
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
	
	public Double[] getGradientesAcumulados() {
		return gradientesAcumulados;
	}

	public void setGradientesAcumulados(Double[] gradientesAcumulados) {
		this.gradientesAcumulados = gradientesAcumulados;
	}
	
	public Double getGradienteAcumuladoBias() {
		return gradienteAcumuladoBias;
	}

	public void setGradienteAcumuladoBias(Double gradienteAcumuladoBias) {
		this.gradienteAcumuladoBias = gradienteAcumuladoBias;
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
		this.gradientesAcumulados = new Double[numeroEntradas];
		for(int i = 0; i < numeroEntradas; i++) {
			pesos[i] = RandomUtil.gerarNumeroAleatorio(-0.1,  0.1);
			gradientesAcumulados[i] = 0.0;
		}
		this.bias = RandomUtil.gerarNumeroAleatorio(-0.1, 0.1);
		this.gradienteAcumuladoBias = 0.0;
	}
	
	public Double calcularSomatorio(Double entradas[]) throws RedeNeuralException{
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
		this.somatorio = 0.0;
		for (int i = 0; i < pesos.length; i++) {
			somatorio += entradas[i] * pesos[i];
		}
		this.somatorio += bias;
		return this.somatorio;
	}
	
	public Double calcularFuncaoDeAtivacao(Double somatorio, Double[] somatorios) {
		assert this.funcaoDeAtivacao != null;
		this.saida = this.funcaoDeAtivacao.calcular(somatorio, somatorios);
		return this.saida;
	}
	
	public String toJSON() {
		StringBuilder str = new StringBuilder("");
		str.append("\n");
		str.append("   ");
		str.append("{ ");
		if(pesos != null && pesos.length > 0) {
			str.append("\n");
			str.append("    ");
			str.append("pesos : [ ");
			for(int i=0; i <getPesos().length; i++) {
				str.append(getPesos()[i]);
				if(i != getPesos().length -1) {
					str.append(",");
				}
			}
			str.append(" ],");
			str.append("\n");
			str.append("    ");
			str.append("bias : ");
			str.append(getBias());
			str.append("\n");
			str.append("   ");
			str.append("} ");
		}
		return str.toString();
	}
}

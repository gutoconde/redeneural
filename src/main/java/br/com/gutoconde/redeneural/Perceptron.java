package br.com.gutoconde.redeneural;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Perceptron implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Camada camadaDeEntrada;
	
	private transient Double[] entradas;
	
	private transient Double[] saidas;
	
	private Integer numeroCamadas = 0;
	
	private Perceptron(List<Integer> neuroniosEmCadaCamada, List<FuncaoDeAtivacao> funcoesAtivacaoEmCadaCamada) {
		assert neuroniosEmCadaCamada != null && funcoesAtivacaoEmCadaCamada != null;
		assert neuroniosEmCadaCamada.size() == funcoesAtivacaoEmCadaCamada.size();
		this.numeroCamadas = neuroniosEmCadaCamada.size();
		Camada camada = null;
		for(int i=0; i < numeroCamadas; i++) {
			if(camada == null) {
				camada = Camada.criarPrimeiraCamada(neuroniosEmCadaCamada.get(i));
				camadaDeEntrada = camada;
			} else {
				camada = camada.criarProximaCamada(neuroniosEmCadaCamada.get(i), funcoesAtivacaoEmCadaCamada.get(i));
			}
		}
	}
	
	public static Perceptron criar(List<Integer> neuroniosEmCadaCamada, FuncaoDeAtivacao funcaoDeAtivacao) {
		List<FuncaoDeAtivacao> funcoes = new ArrayList<FuncaoDeAtivacao>();
		for (int i = 0; i < neuroniosEmCadaCamada.size(); i++) {
			funcoes.add(funcaoDeAtivacao);
		}
		return criar(neuroniosEmCadaCamada, funcoes);
	}
	
	public static Perceptron criar(List<Integer> neuroniosEmCadaCamada, List<FuncaoDeAtivacao> funcoesAtivacaoEmCadaCamada) {
		return new Perceptron(neuroniosEmCadaCamada, funcoesAtivacaoEmCadaCamada);
	}
	
	public Integer getNumeroCamadas() {
		return this.numeroCamadas;
	}
	
	Camada getCamadaDeEntrada() {
		return this.camadaDeEntrada;
	}
	
	Camada getCamadaDeSaida() {
		Camada camada = getCamadaDeEntrada();
		while(!camada.isCamadaDeSaida()) {
			camada = camada.getCamadaSeguinte();
		}
		return camada;
	}
	
	public void inicializar() {
		assert camadaDeEntrada != null;
		camadaDeEntrada.inicializar();
	}
	
	public Double[] calcular(Double[] entradas) throws RedeNeuralException {
		this.entradas = entradas;
		this.saidas = camadaDeEntrada.calcular(entradas);
		return this.saidas; 
	}

	@Override
	public String toString() {
		return "Perceptron : { entradas: " + Arrays.toString(entradas) + ", saidas: " + Arrays.toString(saidas) + "}";
	}
	
	public String toJSON() {
		Camada camada = getCamadaDeEntrada();
		StringBuilder str = new StringBuilder();
		
		str.append("{ rede : { ");
		str.append("\n");
		str.append(" ");
		str.append("camadas : [ ");
		do {
			camada = camada.getCamadaSeguinte();
			str.append(camada.toJSON());
		}while(!camada.isCamadaDeSaida());
		str.append("\n");
		str.append(" ");
		str.append("]");
		str.append("\n");
		str.append(" ");
		str.append("}} ");
		return str.toString();
	}
	
}

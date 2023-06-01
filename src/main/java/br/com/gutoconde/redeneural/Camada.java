package br.com.gutoconde.redeneural;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Camada {
	
	private static Logger logger = Logger.getLogger(Camada.class.getName());
	
	private int numeroNeuronios = 0;
	
	private Neuronio[] neuronios;;
	
	private Camada camadaAnterior;
	
	private Camada camadaSeguinte;
	
	private Double[] entradas;
	
	private Double[] saidas;
		
	private Camada(int numeroNeuronios, Camada camadaAnterior) {
		this.numeroNeuronios = numeroNeuronios;
		this.camadaAnterior = camadaAnterior;
		if(camadaAnterior != null) {
			camadaAnterior.setCamadaSeguinte(this);
		}
	}
	
	static Camada criarPrimeiraCamada(int numeroNeuronios) {
		return new Camada(numeroNeuronios, null);
	}
	
	Camada criarProximaCamada(int numeroNeuronios, FuncaoDeAtivacao funcaoDeativacao) {
		Camada camada = new Camada(numeroNeuronios, this);
		camada.criarNeuronios(funcaoDeativacao);
		return camada;
	}
	
	private Neuronio[] criarNeuronios(FuncaoDeAtivacao funcaoDeAtivacao) {
		int numeroEntradasPorNeuronio = this.numeroNeuronios;
		this.neuronios = new Neuronio[this.numeroNeuronios];
		for (int i = 0; i < this.numeroNeuronios; i++) {
			neuronios[i] = Neuronio.criar(numeroEntradasPorNeuronio, funcaoDeAtivacao); 
		}
		return this.neuronios;
	}
	
	public boolean isCamadaDeEntrada() {
		return getCamadaAnterior() == null;
	}
	
	public boolean isCamadaDeSaida() {
		return getCamadaSeguinte() == null;
	}
	
	private Camada setCamadaSeguinte(Camada camada) {
		this.camadaSeguinte = camada;
		return this;
	}
	
	public int getNumeroNeuronios() {
		return this.numeroNeuronios;
	}
	
	public Camada getCamadaSeguinte() {
		return camadaSeguinte;
	}
	
	public Camada getCamadaAnterior() {
		return camadaAnterior;
	}
	
	Neuronio[] getNeuronios() {
		return this.neuronios;
	}
	
	public Double[] calcular(Double[] entradas) throws RedeNeuralException{
		Double[] resultado = new Double[numeroNeuronios];
		this.entradas = entradas;
		this.saidas = calcularNeuronios(entradas);
		resultado = saidas;
		if(!isCamadaDeSaida()) {
			resultado = camadaSeguinte.calcular(saidas);
		}
		return resultado;
	}
	
	private Double[] calcularNeuronios(Double[]  entradas) throws RedeNeuralException {
		Double[] saidas = new Double[numeroNeuronios];
		//A camada de entrada apenas repassa as entradas
		if(isCamadaDeEntrada()) {
			saidas = entradas;
		} else {
			for (int i = 0; i < numeroNeuronios; i++) {
				saidas[i] = neuronios[i].calcular(entradas);
			}
		}
		return saidas;
	}
	
	void inicializar() {
		if(!isCamadaDeEntrada()) {
			int numeroEntradasPorNeuronio = getCamadaAnterior().getNumeroNeuronios();
			for (Neuronio neuronio : neuronios) {
				neuronio.inicializar(numeroEntradasPorNeuronio);
			}
		}
		
		if(!isCamadaDeSaida()) {
			getCamadaSeguinte().inicializar();
		}
	}

	@Override
	public String toString() {
		return "Camada : { entradas: " + Arrays.toString(entradas) + ", saidas: " + Arrays.toString(saidas) + "}";
	}
	
	

}
package br.com.gutoconde.redeneural;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Backpropagation {
	
	private static Logger logger = Logger.getLogger(Backpropagation.class.getName());
	
	private Perceptron rede;
	
	private double taxaAprendizado;
	
	private double erroMaximo;
	
	private int numeroEpocas;
	
	public Backpropagation(Perceptron rede,
			double taxaAprendizado, double erroMaxino, 
			int numeroEpocas) {
		this.rede = rede;
		this.taxaAprendizado = taxaAprendizado;
		this.erroMaximo = erroMaxino;
		this.numeroEpocas = numeroEpocas;
	}
	
	public void treinar(List<Double[]> conjuntoEntradas, List<Double[]> conjuntoSaidasDesejadas) throws RedeNeuralException {
		assert conjuntoEntradas.size() == conjuntoSaidasDesejadas.size();
		rede.inicializar();
		int iteracoes = 0;
		double erroMedio = 0.0;
		do {
			double erroAcumulado = 0.0;
			erroMedio = 0.0;
			for (int i = 0; i < conjuntoEntradas.size(); i++) {
				Double[] entradas = conjuntoEntradas.get(i);
				Double[] saidasDesejadas = conjuntoSaidasDesejadas.get(i);
				Double[] saidas = rede.calcular(entradas);
				erroAcumulado = erroAcumulado + calcularErro(saidas, saidasDesejadas);
				calcularDeltas(entradas, saidasDesejadas);
				atualizarPesos();
			}
			iteracoes++;
			erroMedio = erroAcumulado / ( 2.0 *  new Double(conjuntoEntradas.size()));
			logger.info("Erro médio: " + erroMedio + " iteracoes: " + iteracoes);
			
		}while(erroMedio > erroMaximo && iteracoes < numeroEpocas);
	}
	
	Double calcularErro(Double[] saidas, Double[] saidasDesejadas) {
		Double erro = 0.0;
		for(int i =0; i< saidas.length; i++) {
			erro = erro + Math.pow(saidas[i] - saidasDesejadas[i]  , 2.0) / 2.0;
		}
		return erro;
	}
	
	void calcularDeltas(Double[] entradas, Double[] saidasDesejadas) throws RedeNeuralException {
		Camada camada = rede.getCamadaDeSaida();
		//Inicializa o indice da camada k = 0 seria a camada de entrada
		int k = rede.getNumeroCamadas() - 1;
		
		while(!camada.isCamadaDeEntrada()) {
			
			for(int i = 0; i < camada.getNumeroNeuronios(); i++ ) {
				Neuronio neuronio = camada.getNeuronios()[i];
				double saida = neuronio.getSaida();
				double delta = 0;
				
				if(camada.isCamadaDeSaida()) {
					double erro = saidasDesejadas[i] - saida;
					delta =  erro * neuronio.getFuncaoDeAtivacao().calcularDerivada(saida);
				} else {
					Camada camadaSeguinte = camada.getCamadaSeguinte();
					double somatorio = 0.0;
					for(int j = 0; j < camadaSeguinte.getNumeroNeuronios(); j++) {
						Neuronio neurorioCamadaSeguinte = camadaSeguinte.getNeuronios()[j];
						double w = neurorioCamadaSeguinte.getPesos()[i];
						 
						somatorio = somatorio + w * neurorioCamadaSeguinte.getDelta();
					}
					delta = somatorio * neuronio.getFuncaoDeAtivacao().calcularDerivada(saida);
				}
				neuronio.setDelta(delta);
			};
			
			camada = camada.getCamadaAnterior();
			k--;
		}
	}
	
	void atualizarPesos() {
		//A Atualizacao dos pesos comeca a partir das camadas de entrada
		Camada camada = rede.getCamadaDeEntrada();
		int k = 1;
		while(k < rede.getNumeroCamadas()) {
			camada = camada.getCamadaSeguinte();
			for(int j = 0; j < camada.getNumeroNeuronios(); j++) {
				Neuronio neuronio = camada.getNeuronios()[j];
				
				for(int i = 0; i < neuronio.getPesos().length; i++ ) {
					
					neuronio.getPesos()[i] = neuronio.getPesos()[i]  
							+ this.taxaAprendizado * neuronio.getDelta() * neuronio.getEntradas()[i];
				}
				neuronio.setBias(neuronio.getBias() + this.taxaAprendizado * neuronio.getDelta()); 
			}
			k++;
		}
	}

}

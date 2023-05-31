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
				Map<Integer, Double[]> deltasPorCamada = calcularDeltas(entradas, saidasDesejadas);
				atualizarPesos(deltasPorCamada);
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
	
	Map<Integer, Double[]> calcularDeltas(Double[] entradas, Double[] saidasDesejadas) throws RedeNeuralException {
		//deltasPorCamada: guarda os deltas calculados
		Map<Integer, Double[]> deltasPorCamada = new HashMap<Integer, Double[]>();
		
		Camada camada = rede.getCamadaDeSaida();
		
		//Inicializa o indice da camada k = 0 seria a camada de entrada
		int k = rede.getNumeroCamadas() - 1;
		
		while(!camada.isCamadaDeEntrada()) {
			
			for(int i = 0; i < camada.getNumeroNeuronios(); i++ ) {
				Neuronio neuronio = camada.getNeuronios()[i];
				double saida = neuronio.getSaida();
				double delta = 0;
				
				if(camada.isCamadaDeSaida()) {
					double erro = saida - saidasDesejadas[i];
					//verificar se deve multiplicar o delta por 2
					delta =  erro * neuronio.getFuncaoDeAtivacao().calcularDerivada(saida);
					//delta = erro * saida * ( 1.0 - saida); 
				} else {
					Camada camadaSeguinte = camada.getCamadaSeguinte();
					double somatorio = 0.0;
					for(int j = 0; j < camadaSeguinte.getNumeroNeuronios(); j++) {
						Neuronio neurorioCamadaSeguinte = camadaSeguinte.getNeuronios()[j];
						double w = neurorioCamadaSeguinte.getPesos()[i];
						double deltaCamadaSeguinte = deltasPorCamada.get(k+1)[j]; 
						somatorio = somatorio + w * deltaCamadaSeguinte;
					}
					delta = somatorio * neuronio.getFuncaoDeAtivacao().calcularDerivada(saida);
					//delta = somatorio * saida * (1.0 - saida);
				}
				
				if(deltasPorCamada.get(k) == null) {
					deltasPorCamada.put(k, new Double[camada.getNeuronios().length]);
				};
				
				deltasPorCamada.get(k)[i] = delta;
			};
			
			camada = camada.getCamadaAnterior();
			k--;
		}
		return deltasPorCamada;
	}

	void atualizarPesos(Map<Integer, Double[]> deltasPorCamada) {
		//A Atualizacao dos pesos comeca a partir das camadas de entrada
		Camada camada = rede.getCamadaDeEntrada();
		int k = 1;
		while(k < rede.getNumeroCamadas()) {
			camada = camada.getCamadaSeguinte();
			for(int j = 0; j < camada.getNumeroNeuronios(); j++) {
				Neuronio neuronio = camada.getNeuronios()[j];
				Double[] deltasDaCamdada = deltasPorCamada.get(k);
				for(int i = 0; i < neuronio.getPesos().length; i++ ) {
					
					//neuronio.getPesos()[i] = neuronio.getPesos()[i]  
					//		- this.taxaAprendizado * deltasDaCamdada[j] * neuronio.getEntradas()[i];
					neuronio.getPesos()[i] = neuronio.getPesos()[i]  
							-  this.taxaAprendizado * deltasDaCamdada[j] * neuronio.getSaida();
				}
				neuronio.setBias(neuronio.getBias() - this.taxaAprendizado * deltasDaCamdada[j]); 
			}
			k++;
		}
	}

}

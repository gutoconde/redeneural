package br.com.gutoconde.redeneural;

import java.util.List;
import java.util.logging.Logger;

public class Backpropagation {
	
	private static Logger logger = Logger.getLogger(Backpropagation.class.getName());
	
	private Perceptron rede;
	
	private FuncaoDeCusto funcaoDeCusto;
	
	private double taxaAprendizado;
	
	private double erroMaximo;
	
	private int numeroEpocas;
	
	public Backpropagation(Perceptron rede,
			double taxaAprendizado, double erroMaxino, 
			int numeroEpocas) {
		this(rede, new ErroQuadraticoMedio(),
				taxaAprendizado, erroMaxino,
				numeroEpocas);
	}
	
	public Backpropagation(Perceptron rede, FuncaoDeCusto funcaoDeCusto,
			double taxaAprendizado, double erroMaxino, 
			int numeroEpocas) {
		this.rede = rede;
		this.taxaAprendizado = taxaAprendizado;
		this.erroMaximo = erroMaxino;
		this.numeroEpocas = numeroEpocas;
		this.funcaoDeCusto = funcaoDeCusto;
	}
	
	public void treinarModoOnline(List<Double[]> conjuntoEntradas, List<Double[]> conjuntoSaidasDesejadas) throws RedeNeuralException {
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
				erroAcumulado += calcularErro(saidas, saidasDesejadas);
				calcularDeltas(saidasDesejadas);
				atualizarPesos();
			}
			iteracoes++;
			erroMedio = erroAcumulado / ( 2.0 *  new Double(conjuntoEntradas.size()));
			logger.info("Erro médio: " + erroMedio + " iteracoes: " + iteracoes);
			
		}while(erroMedio > erroMaximo && iteracoes < numeroEpocas);
	}
	
	public void treinarModoBatch(List<Double[]> conjuntoEntradas, List<Double[]> conjuntoSaidasDesejadas) throws RedeNeuralException {
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
				erroAcumulado += calcularErro(saidas, saidasDesejadas);
				calcularDeltas(saidasDesejadas);
				calcularGradientesAcumulados();
			}
			atualizarPesosComGradientesAcumulados();
			iteracoes++;
			erroMedio = Math.abs(erroAcumulado / ( 2.0 *  new Double(conjuntoEntradas.size())));
			//erroMedio = erroAcumulado / ( 2.0 *  new Double(conjuntoEntradas.size()));
			logger.info("Erro médio: " + erroMedio + " iteracoes: " + iteracoes);
			
		}while(erroMedio > erroMaximo && iteracoes < numeroEpocas);
	}
	
	Double calcularErro(Double[] saidas, Double[] saidasEsperadas) {
		return funcaoDeCusto.calcular(saidas, saidasEsperadas);
	}
	
	void calcularDeltas(Double[] saidasDesejadas) throws RedeNeuralException {
		Camada camada = rede.getCamadaDeSaida();
		
		while(!camada.isCamadaDeEntrada()) {
			
			for(int i = 0; i < camada.getNumeroNeuronios(); i++ ) {
				Neuronio neuronio = camada.getNeuronios()[i];
				double saida = neuronio.getSaida();
				double delta = 0;
				
				if(camada.isCamadaDeSaida()) {
					double erro = saidasDesejadas[i] - saida;
					delta =  erro * neuronio.getFuncaoDeAtivacao().calcularDerivada(saida, camada.getSaidas());
				} else {
					Camada camadaSeguinte = camada.getCamadaSeguinte();
					double somatorio = 0.0;
					for(int j = 0; j < camadaSeguinte.getNumeroNeuronios(); j++) {
						Neuronio neurorioCamadaSeguinte = camadaSeguinte.getNeuronios()[j];
						double w = neurorioCamadaSeguinte.getPesos()[i];
						 
						somatorio += w * neurorioCamadaSeguinte.getDelta();
					}
					delta = somatorio * neuronio.getFuncaoDeAtivacao().calcularDerivada(saida);
				}
				neuronio.setDelta(delta);
			};
			camada = camada.getCamadaAnterior();
		}
	}
	
	void atualizarPesos() {
		Camada camada = rede.getCamadaDeEntrada();
		do {
			camada = camada.getCamadaSeguinte();
			for(int i=0; i < camada.getNumeroNeuronios(); i++) {
				Neuronio neuronio = camada.getNeuronios()[i];
				for(int j=0; j < neuronio.getPesos().length; j++) {
					neuronio.getPesos()[j] += taxaAprendizado * neuronio.getDelta() * neuronio.getEntradas()[j];   
				}
				neuronio.setBias(neuronio.getBias() + taxaAprendizado * neuronio.getDelta());
			}
		} while(!camada.isCamadaDeSaida());
	}
	
	private void calcularGradientesAcumulados() {
		Camada camada = rede.getCamadaDeEntrada();
		do {
			camada = camada.getCamadaSeguinte();
			for(int i=0; i < camada.getNumeroNeuronios(); i++) {
				Neuronio neuronio = camada.getNeuronios()[i];
				for(int j=0; j < neuronio.getPesos().length; j++) {
					neuronio.getGradientesAcumulados()[j] += neuronio.getDelta() * neuronio.getEntradas()[j];
				}
				neuronio.setGradienteAcumuladoBias(neuronio.getGradienteAcumuladoBias()+ neuronio.getDelta());
			}
		} while(!camada.isCamadaDeSaida());
	}
	
	private void atualizarPesosComGradientesAcumulados() {
		Camada camada = rede.getCamadaDeEntrada();
		do {
			camada = camada.getCamadaSeguinte();
			for(int i=0; i < camada.getNumeroNeuronios(); i++) {
				Neuronio neuronio = camada.getNeuronios()[i];
				for(int j=0; j < neuronio.getPesos().length; j++) {
					neuronio.getPesos()[j] += taxaAprendizado * neuronio.getGradientesAcumulados()[j];   
				}
				neuronio.setBias(neuronio.getBias() + taxaAprendizado * neuronio.getGradienteAcumuladoBias());
			}
		} while(!camada.isCamadaDeSaida());
	}

	

}

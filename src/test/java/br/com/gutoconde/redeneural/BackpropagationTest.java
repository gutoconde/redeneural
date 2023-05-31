package br.com.gutoconde.redeneural;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class BackpropagationTest {

	@Test
	public void testarCalcularErro() {
		Backpropagation backpropagation = new Backpropagation(null, 0, 0, 0);
		Double[] saidas = new Double[] {0.5 , 0.98, 0.3};
		Double[] saidasEsperadas = new Double[] {0.1 ,1.0, 0.5};
		double erro = backpropagation.calcularErro(saidas, saidasEsperadas);
		assertEquals(0.1002, erro, 0.00001);
		
		saidas = new Double[] {23.0, 0.22, 0.987, 7.0};
		saidasEsperadas = new Double[] {2.0, 0.1, 2.0 , 5.0};
		erro = backpropagation.calcularErro(saidas, saidasEsperadas);
		assertEquals(223.0202845, erro, 0.0001);
		
		saidas = new Double[]  { 10.0, 4.0, 2.8};
		saidasEsperadas = new Double[] {10.0, 3.0, 9.6};
		erro = backpropagation.calcularErro(saidas, saidasEsperadas);
		assertEquals(23.62, erro, 0.0001);
	}
	
	@Test
	public void testarAtualizarPesos() throws RedeNeuralException {
		List<Integer> neuroniosPorCamada = Arrays.asList(new Integer[] {2, 2, 1});
		Perceptron perceptron = Perceptron.criar(neuroniosPorCamada, new FuncaoSigmoid());
		Camada camada1 = perceptron.getCamadaDeEntrada().getCamadaSeguinte();
		camada1.getNeuronios()[0].setPesos(new Double[] { 0.1, 0.1 });
		camada1.getNeuronios()[0].setBias(0.1);
		camada1.getNeuronios()[1].setPesos(new Double[] { 0.1, 0.1 });
		camada1.getNeuronios()[1].setBias(0.1);
		
		Camada camadaDeSaida = perceptron.getCamadaDeSaida();
		camadaDeSaida.getNeuronios()[0].setPesos(new Double[] { 0.1, 0.1 });
		camadaDeSaida.getNeuronios()[0].setBias(0.1);
		
		Double[] saidas = perceptron.calcular(new Double[] {0.0, 0.0});
		assertEquals(0.5510702395380238, saidas[0]);
		
		
		Map<Integer, Double[]> deltasPorCamada = new HashMap<Integer, Double[]>();
		deltasPorCamada.put(1, new Double[] {0.01, 0.02});
		deltasPorCamada.put(2, new Double[] {0.01});
		
		double peso1_0_0 = camada1.getNeuronios()[0].getPesos()[0];
		double peso1_0_1_= camada1.getNeuronios()[0].getPesos()[1];
				
		Backpropagation backpropagation = new Backpropagation(perceptron, 0.01, 0, 0);
		backpropagation.atualizarPesos(deltasPorCamada);
		
		//assertEquals(peso1_0_0 - 0.01 * 0.01 * , camada1.getNeuronios()[0].getPesos()[0]);
		
		
	}
}

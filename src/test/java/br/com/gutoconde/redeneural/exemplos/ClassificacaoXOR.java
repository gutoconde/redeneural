package br.com.gutoconde.redeneural.exemplos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.gutoconde.redeneural.Backpropagation;
import br.com.gutoconde.redeneural.FuncaoSigmoid;
import br.com.gutoconde.redeneural.Perceptron;
import br.com.gutoconde.redeneural.RedeNeuralException;

public class ClassificacaoXOR {

	@Test
	public void testarClassificacaoXOR() throws RedeNeuralException {
		//Criando rede
		List<Integer> camadas = Arrays.asList(new Integer[]{2,2,1});
		Perceptron rede = Perceptron.criar(camadas, new FuncaoSigmoid());
		Backpropagation backprop = new Backpropagation(rede, 0.1, 0.04, 10000000);
		
		List<Double[]> entradas = new ArrayList<Double[]>();
		entradas.add(new Double[]{0.01, 0.01});
		entradas.add(new Double[]{0.01, 1.0});
		entradas.add(new Double[]{1.0, 1.0});
		entradas.add(new Double[]{1.0, 0.01});
		
		List<Double[]> saidasDesejadas = new ArrayList<Double[]>();
		saidasDesejadas.add(new Double[]{0.0});
		saidasDesejadas.add(new Double[]{1.0});
		saidasDesejadas.add(new Double[]{0.0});
		saidasDesejadas.add(new Double[]{1.0});
		
		
		backprop.treinar(entradas, saidasDesejadas);
		
		//testando a rede
		
		Double[] resultado = rede.calcular(new Double[]{0.0, 0.0});
		assertEquals(0.0, resultado[0], 0.01);
		
		resultado = rede.calcular(new Double[]{0.0, 1.0});
		assertEquals(1.0, resultado[0], 0.01);
		
		resultado = rede.calcular(new Double[]{1.0, 0.0});
		assertEquals(1.0, resultado[0], 0.01);
		
		resultado = rede.calcular(new Double[]{1.0, 1.0});
		assertEquals(0.0, resultado[0], 0.01);
	}
	
}

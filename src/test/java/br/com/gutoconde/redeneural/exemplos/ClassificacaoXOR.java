package br.com.gutoconde.redeneural.exemplos;

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
		Backpropagation backprop = new Backpropagation(rede, 0.1, 0.001, 4000);
		
		List<Double[]> entradas = new ArrayList<Double[]>();
		
		entradas.add(new Double[]{1.0, 0.0});
		entradas.add(new Double[]{0.0, 1.0});
		entradas.add(new Double[]{1.0, 1.0});
		entradas.add(new Double[]{0.0, 0.0});
		
		List<Double[]> saidasDesejadas = new ArrayList<Double[]>();
		saidasDesejadas.add(new Double[]{1.0});
		saidasDesejadas.add(new Double[]{1.0});
		saidasDesejadas.add(new Double[]{0.0});
		saidasDesejadas.add(new Double[]{0.0});
		
		
		backprop.treinarModoBatch(entradas, saidasDesejadas);
		
		System.out.println(rede.toJSON());
		
		//testando a rede
		System.out.println("Fim do treinamento: ");
		Double[] resultado = rede.calcular(new Double[]{0.0, 0.0});
		System.out.println("0 0 - " + resultado[0]);
		//assertEquals(-1.0, resultado[0], 0.1);
		
		resultado = rede.calcular(new Double[]{0.0, 1.0});
		System.out.println("0 1 - " + resultado[0]);
		//assertEquals(1.0, resultado[0], 0.1);
		
		resultado = rede.calcular(new Double[]{1.0, 0.0});
		System.out.println("1 0 - " + resultado[0]);
		//assertEquals(1.0, resultado[0], 0.1);
		
		resultado = rede.calcular(new Double[]{1.0, 1.0});
		System.out.println("1 1 - " + resultado[0]);
		//assertEquals(-1.0, resultado[0], 0.1);
	}
	
}

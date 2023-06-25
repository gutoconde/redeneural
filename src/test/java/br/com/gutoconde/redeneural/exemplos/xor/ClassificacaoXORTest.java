package br.com.gutoconde.redeneural.exemplos.xor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.gutoconde.redeneural.Backpropagation;
import br.com.gutoconde.redeneural.EntropiaCruzada;
import br.com.gutoconde.redeneural.FuncaoSigmoid;
import br.com.gutoconde.redeneural.FuncaoSoftMax;
import br.com.gutoconde.redeneural.Perceptron;
import br.com.gutoconde.redeneural.RedeNeuralException;

public class ClassificacaoXORTest {

	@Test
	public void testarClassificacaoXOR() throws RedeNeuralException {
		//Criando rede
		List<Integer> camadas = Arrays.asList(new Integer[]{2,2,1});
		Perceptron rede = Perceptron.criar(camadas, new FuncaoSigmoid());
		Backpropagation backprop = new Backpropagation(rede, 0.1, 0.000001, 10000);
		
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
		assertEquals(0.0, resultado[0], 0.1);
		
		resultado = rede.calcular(new Double[]{0.0, 1.0});
		System.out.println("0 1 - " + resultado[0]);
		assertEquals(1.0, resultado[0], 0.1);
		
		resultado = rede.calcular(new Double[]{1.0, 0.0});
		System.out.println("1 0 - " + resultado[0]);
		assertEquals(1.0, resultado[0], 0.1);
		
		resultado = rede.calcular(new Double[]{1.0, 1.0});
		System.out.println("1 1 - " + resultado[0]);
		assertEquals(0.0, resultado[0], 0.1);
	}
	
	@Test
	public void testarClassificacaoXOR2Saidas() throws RedeNeuralException {
		//Criando rede
		List<Integer> camadas = Arrays.asList(new Integer[]{2,3,2});
		Perceptron rede = Perceptron.criar(camadas, new FuncaoSigmoid());
		Backpropagation backprop = new Backpropagation(rede, 0.1, 0.000001, 20000);
		
		List<Double[]> entradas = new ArrayList<Double[]>();
		
		entradas.add(new Double[]{1.0, 0.0});
		entradas.add(new Double[]{0.0, 1.0});
		entradas.add(new Double[]{1.0, 1.0});
		entradas.add(new Double[]{0.0, 0.0});
		
		List<Double[]> saidasDesejadas = new ArrayList<Double[]>();
		saidasDesejadas.add(new Double[]{1.0, 0.0});
		saidasDesejadas.add(new Double[]{1.0, 0.0});
		saidasDesejadas.add(new Double[]{0.0, 1.0});
		saidasDesejadas.add(new Double[]{0.0, 1.0});
		
		
		backprop.treinarModoBatch(entradas, saidasDesejadas);
		
		System.out.println(rede.toJSON());
		
		//testando a rede
		System.out.println("Fim do treinamento: ");
		Double[] resultado = rede.calcular(new Double[]{0.0, 0.0});
		System.out.println("0 0 - " + resultado[0] + " " + resultado[1]);
		assertEquals(0.0, resultado[0], 0.1);
		assertEquals(1.0, resultado[1], 0.1);
		
		resultado = rede.calcular(new Double[]{0.0, 1.0});
		System.out.println("0 1 - " + resultado[0] + " " + resultado[1]);
		assertEquals(1.0, resultado[0], 0.1);
		assertEquals(0.0, resultado[1], 0.1);
		
		resultado = rede.calcular(new Double[]{1.0, 0.0});
		System.out.println("1 0 - " + resultado[0] + " " + resultado[1]);
		assertEquals(1.0, resultado[0], 0.1);
		assertEquals(0.0, resultado[1], 0.1);
		
		resultado = rede.calcular(new Double[]{1.0, 1.0});
		System.out.println("1 1 - " + resultado[0] + " " + resultado[1]);
		assertEquals(0.0, resultado[0], 0.1);
		assertEquals(1.0, resultado[1], 0.1);
	}
	
	@Test
	public void testarClassificacaoXOR3Saidas() throws RedeNeuralException {
		//Criando rede
		List<Integer> camadas = Arrays.asList(new Integer[]{2,4,3});
		Perceptron rede = Perceptron.criar(camadas, new FuncaoSigmoid());
		Backpropagation backprop = new Backpropagation(rede, 0.1, 0.000001, 100000);
		
		List<Double[]> entradas = new ArrayList<Double[]>();
		
		entradas.add(new Double[]{0.0, 0.0});
		entradas.add(new Double[]{0.0, 1.0});
		entradas.add(new Double[]{1.0, 0.0});		
		entradas.add(new Double[]{1.0, 1.0});
		
		
		List<Double[]> saidasDesejadas = new ArrayList<Double[]>();
		saidasDesejadas.add(new Double[]{0.0, 1.0, 1.0});
		saidasDesejadas.add(new Double[]{1.0, 0.0, 1.0});
		saidasDesejadas.add(new Double[]{1.0, 0.0, 0.0});
		saidasDesejadas.add(new Double[]{0.0, 1.0, 0.0});
		
		
		
		backprop.treinarModoBatch(entradas, saidasDesejadas);
		
		System.out.println(rede.toJSON());
		
		//testando a rede
		System.out.println("Fim do treinamento: ");
		Double[] resultado = rede.calcular(new Double[]{0.0, 0.0});
		System.out.println("0 0 - " + resultado[0] + " " + resultado[1] + " " + resultado[2]);
		assertEquals(0.0, resultado[0], 0.1);
		assertEquals(1.0, resultado[1], 0.1);
		assertEquals(1.0, resultado[2], 0.1);
		
		resultado = rede.calcular(new Double[]{0.0, 1.0});
		System.out.println("0 1 - " + resultado[0] + " " + resultado[1] + " " + resultado[2]);
		assertEquals(1.0, resultado[0], 0.1);
		assertEquals(0.0, resultado[1], 0.1);
		assertEquals(1.0, resultado[2], 0.1);
		
		resultado = rede.calcular(new Double[]{1.0, 0.0});
		System.out.println("1 0 - " + resultado[0] + " " + resultado[1] + " " + resultado[2]);
		assertEquals(1.0, resultado[0], 0.1);
		assertEquals(0.0, resultado[1], 0.1);
		assertEquals(0.0, resultado[2], 0.1);
		
		resultado = rede.calcular(new Double[]{1.0, 1.0});
		System.out.println("1 1 - " + resultado[0] + " " + resultado[1] + " " + resultado[2]);
		assertEquals(0.0, resultado[0], 0.1);
		assertEquals(1.0, resultado[1], 0.1);
		assertEquals(0.0, resultado[2], 0.1);
	}
	
	@Test
	public void testarClassificacaoXOR3SaidasComEntropiaCruzada() throws RedeNeuralException {
		//Criando rede
		List<Integer> camadas = Arrays.asList(new Integer[]{2,4,3});
		Perceptron rede = Perceptron.criar(camadas, new FuncaoSigmoid(), new FuncaoSoftMax());
		Backpropagation backprop = new Backpropagation(rede, new EntropiaCruzada(), 0.1, 0.000001, 10000);
		
		List<Double[]> entradas = new ArrayList<Double[]>();
		
		entradas.add(new Double[]{0.0, 0.0});
		entradas.add(new Double[]{0.0, 1.0});
		entradas.add(new Double[]{1.0, 0.0});		
		entradas.add(new Double[]{1.0, 1.0});
		
		
		List<Double[]> saidasDesejadas = new ArrayList<Double[]>();
		saidasDesejadas.add(new Double[]{0.0, 1.0, 1.0});
		saidasDesejadas.add(new Double[]{1.0, 0.0, 1.0});
		saidasDesejadas.add(new Double[]{1.0, 0.0, 0.0});
		saidasDesejadas.add(new Double[]{0.0, 1.0, 0.0});
		
		
		
		backprop.treinarModoBatch(entradas, saidasDesejadas);
		
		System.out.println(rede.toJSON());
		
		//testando a rede
		System.out.println("Fim do treinamento: ");
		Double[] resultado = rede.calcular(new Double[]{0.0, 0.0});
		System.out.println("0 0 - " + resultado[0] + " " + resultado[1] + " " + resultado[2]);
		//assertEquals(0.0, resultado[0], 0.1);
		//assertEquals(1.0, resultado[1], 0.1);
		//assertEquals(1.0, resultado[2], 0.1);
		
		resultado = rede.calcular(new Double[]{0.0, 1.0});
		System.out.println("0 1 - " + resultado[0] + " " + resultado[1] + " " + resultado[2]);
		//assertEquals(1.0, resultado[0], 0.1);
		//assertEquals(0.0, resultado[1], 0.1);
		//assertEquals(1.0, resultado[2], 0.1);
		
		resultado = rede.calcular(new Double[]{1.0, 0.0});
		System.out.println("1 0 - " + resultado[0] + " " + resultado[1] + " " + resultado[2]);
		//assertEquals(1.0, resultado[0], 0.1);
		//assertEquals(0.0, resultado[1], 0.1);
		//assertEquals(0.0, resultado[2], 0.1);
		
		resultado = rede.calcular(new Double[]{1.0, 1.0});
		System.out.println("1 1 - " + resultado[0] + " " + resultado[1] + " " + resultado[2]);
		//assertEquals(0.0, resultado[0], 0.1);
		//assertEquals(1.0, resultado[1], 0.1);
		//assertEquals(0.0, resultado[2], 0.1);
	}
	
}

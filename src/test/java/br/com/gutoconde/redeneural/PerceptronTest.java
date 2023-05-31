package br.com.gutoconde.redeneural;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PerceptronTest {
	
	private static Logger logger = Logger.getLogger(PerceptronTest.class.getName());

	@ParameterizedTest
	@CsvSource({"3,5,4,3", "5,7,7,3", "2,3,3,2"})
	public void testarCriacaoRedeCom4Camadas(Integer qtdeCamada1, Integer qtdeCamada2, Integer qtdeCamada3, Integer qtdeCamada4) throws RedeNeuralException {
		List<Integer> numeroNeuroniosPorCamada = new ArrayList<Integer>();
		numeroNeuroniosPorCamada.add(qtdeCamada1);
		numeroNeuroniosPorCamada.add(qtdeCamada2);
		numeroNeuroniosPorCamada.add(qtdeCamada3);
		numeroNeuroniosPorCamada.add(qtdeCamada4);
				
		Perceptron rede = Perceptron.criar(numeroNeuroniosPorCamada, new FuncaoSigmoid());
		rede.inicializar();
		
		Camada camada = rede.getCamadaDeEntrada();
		int i = 0;
		do {
			if(camada.isCamadaDeEntrada()) {
				assertNull(camada.getNeuronios());
			} else {
				assertEquals(numeroNeuroniosPorCamada.get(i), camada.getNeuronios().length);
			}
			
			i++;
			camada = camada.getCamadaSeguinte();
		} while(camada != null);
		
		Double[] entradas = new Double[qtdeCamada1];
		for(int j = 0; j < qtdeCamada1 ; j++) {
			entradas[j] = Math.random();
		}
		Double[] saidas = rede.calcular(entradas);
		logger.log(Level.INFO, "Resultado : " + rede );
	}
	
	@Test
	public void testarRedeCom3Camadas() throws RedeNeuralException {
		List<Integer> numeroNeuroniosPorCamada = new ArrayList<Integer>();
		numeroNeuroniosPorCamada.add(2);
		numeroNeuroniosPorCamada.add(2);
		numeroNeuroniosPorCamada.add(1);
				
		Perceptron rede = Perceptron.criar(numeroNeuroniosPorCamada, new FuncaoSigmoid());
		rede.inicializar();
		Camada camada = rede.getCamadaDeEntrada();
		
		Camada camada1 = camada.getCamadaSeguinte();
		camada1.getNeuronios()[0].setPesos(new Double[]{0.001624013073801, -2.77659104196188});
		camada1.getNeuronios()[0].setBias(-5.60974579271903);
		camada1.getNeuronios()[1].setPesos(new Double[]{0.001769174049259, -2.75231999323301});
		camada1.getNeuronios()[1].setBias(-5.60977280776227);
		
		Camada camada2 = camada1.getCamadaSeguinte();
		camada2.getNeuronios()[0].setPesos(new Double[]{-0.006948094799224, -0.007704118732512});
		camada2.getNeuronios()[0].setBias(0.001354491809387);
		
		assertArrayEquals(new Double[]{0.5003252579368054}, rede.calcular(new Double[] {0.0, 0.0}));
		assertArrayEquals(new Double[]{0.5003252352756140}, rede.calcular(new Double[] {1.0, 0.0}));
		assertArrayEquals(new Double[]{0.5003377772889818}, rede.calcular(new Double[] {0.0, 1.0}));
		assertArrayEquals(new Double[]{0.5003377758495211}, rede.calcular(new Double[] {1.0, 1.0}));
		
		
	}
}

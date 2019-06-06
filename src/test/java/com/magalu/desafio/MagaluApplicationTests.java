package com.magalu.desafio;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.magalu.desafio.domain.GameLog;
import com.magalu.desafio.util.Divisor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MagaluApplicationTests {
	
	private static final String LINHA_1 = "20:37 InitGame: \\sv_floodProtect\\";
	private static final String LINHA_2 = "20:38 ClientConnect: 2";
	private static final String LINHA_3 = "1:47 InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0";
	private final List<String> todasLinhas = new ArrayList<>();

	@Test
	public void contextLoads() {
	}
	
	@Before
	public void setup() {
		todasLinhas.clear();
		todasLinhas.add(LINHA_1);
		todasLinhas.add(LINHA_2);
		todasLinhas.add(LINHA_3);
	}
	
	@Test
	public void gerandoEntradas() {
		final List<GameLog> splitGames = Divisor.dividir(todasLinhas);
		Assert.assertEquals(2, splitGames.size());
		
		Assert.assertEquals("game-1", splitGames.get(0).getName());
		Assert.assertEquals("game-2", splitGames.get(1).getName());
		
		Assert.assertEquals(2, splitGames.get(0).getLog().size());
		Assert.assertEquals(1, splitGames.get(1).getLog().size());

		Assert.assertEquals(LINHA_1, splitGames.get(0).getLog().get(0));
		Assert.assertEquals(LINHA_2, splitGames.get(0).getLog().get(1));
		Assert.assertEquals(LINHA_3, splitGames.get(1).getLog().get(0));
	}

}

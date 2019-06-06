package com.magalu.desafio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.magalu.desafio.domain.Game;
import com.magalu.desafio.domain.GameLog;
import com.magalu.desafio.domain.Player;
import com.magalu.desafio.domain.PlayerDeath;
import com.magalu.desafio.enumerator.Death;
import com.magalu.desafio.util.Divisor;
import com.magalu.desafio.util.GameParser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MagaluApplicationTests {

	private static final Double PRECISAO = 0.0000001;

	private static final String LINHA_1 = "20:37 InitGame: \\sv_floodProtect\\\\1\\\\sv_maxPing\\\\0\\\\sv_minPing\\\\0\\\\sv_maxRate\\\\10000\\\\sv_minRate\\\\0\\\\sv_hostname\\\\Code Miner";
	private static final String LINHA_2 = "20:38 ClientConnect: 2";
	private static final String LINHA_3 = "1:47 InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0";
	private final List<String> todasLinhas = new ArrayList<>();

	@Test
	public void contextLoads() {
	}

	// Teste do Parser

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

	@Test
	public void analisadorPlayerKilledWorld1() {
		final GameLog gameLog = new GameLog("game-1");
		gameLog.addLog("0:00 InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner");
		gameLog.addLog("20:34 ClientUserinfoChanged: 2 n\\Mocinha\\t\\0\\model\\uriel/zael\\hmodel\\uriel/zael\\g_redteam\\\\g_blueteam\\\\c1\\5\\c2\\5\\hc\\100\\w\\0\\l\\0\\tt\\0\\tl\\0");
		gameLog.addLog("20:54 Kill: 1022 2 22: <world> killed Mocinha by MOD_TRIGGER_HURT");
		final List<Game> games = GameParser.parse(Arrays.asList(gameLog));
		Assert.assertEquals(1, games.size());
		Assert.assertEquals("game-1", games.get(0).getName());
		final List<Player> players = games.get(0).getPlayers();
		Assert.assertEquals(1, players.size());
		Assert.assertEquals("Mocinha", players.get(0).getNickName());
		final List<PlayerDeath> playerDeaths = games.get(0).getPlayerDeaths();
		Assert.assertEquals(1, playerDeaths.size());
		Assert.assertEquals("Mocinha", playerDeaths.get(0).getPlayer().getNickName());
		final Map<Death, Integer> countByDeath = games.get(0).getPlayerDeaths().get(0).getContarPorDeath();
		Assert.assertEquals(1, countByDeath.size());
		final List<Integer> counts = new ArrayList<>(countByDeath.values());
		Assert.assertEquals(1, counts.size());
		Assert.assertEquals(1, counts.get(0), 0.0001);
		final List<Death> deaths = new ArrayList<>(countByDeath.keySet());
		Assert.assertEquals(1, deaths.size());
		Assert.assertEquals(Death.MOD_TRIGGER_HURT, deaths.get(0));
	}
	
	@Test
	public void analisadorPlayerKilledWorld2() {
		final GameLog gameLog = new GameLog("game-1");
		gameLog.addLog("0:00 InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner");
		gameLog.addLog("20:34 ClientUserinfoChanged: 2 n\\Mocinha\\t\\0\\model\\uriel/zael\\hmodel\\uriel/zael\\g_redteam\\\\g_blueteam\\\\c1\\5\\c2\\5\\hc\\100\\w\\0\\l\\0\\tt\\0\\tl\\0");
		gameLog.addLog("20:54 Kill: 1022 2 22: <world> killed Mocinha by MOD_TRIGGER_HURT");		
		final List<Game> games = GameParser.parse(Arrays.asList(gameLog));		
		final List<PlayerDeath> playerDeaths = games.get(0).getPlayerDeaths();
		Assert.assertEquals(1, playerDeaths.size());
		Assert.assertEquals("Mocinha", playerDeaths.get(0).getPlayer().getNickName());
		final Map<Death, Integer> countByDeathType = games.get(0).getPlayerDeaths().get(0).getContarPorDeath();
		Assert.assertEquals(1, countByDeathType.size());
		final List<Integer> counts = new ArrayList<>(countByDeathType.values());
		Assert.assertEquals(1, counts.size());
		Assert.assertEquals(1, counts.get(0), PRECISAO);
		final List<Death> deathTypes = new ArrayList<>(countByDeathType.keySet());
		Assert.assertEquals(1, deathTypes.size());
		Assert.assertEquals(Death.MOD_TRIGGER_HURT, deathTypes.get(0));
	}
}

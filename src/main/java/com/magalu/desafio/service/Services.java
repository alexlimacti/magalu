package com.magalu.desafio.service;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.magalu.desafio.domain.Game;
import com.magalu.desafio.domain.Player;
import com.magalu.desafio.domain.PlayerDeath;
import com.magalu.desafio.domain.PlayerKill;
import com.magalu.desafio.enumerator.Death;

@Service
public class Services {
	
	private static final String ESPACO = "    ";

	//Leitura do arquivo
	public static List<String> lerArquivo(final String path) {
		return getLinhas(path);
	}
	
	private static List<String> getLinhas(final String path) {
		try {
			return Files.readAllLines(Paths.get(path));

		} catch (IOException e) {
			System.out.println("Erro na leitura do arquivo");
			return emptyList();
		}
	}
	
	
	//Impressao
	@SuppressWarnings("unused")
	private void nomeGame(final Game game) {
		System.out.println(game.getName() + ": {");
	}
	
	private String quotes(String string) {
		    if (!string.isEmpty()) {
		        string = "\"" + string + "\"";
		    }
		return string;
	}
	
	private List<PlayerKill> killPorPlayer(final List<Game> games) {
		return games.stream().map(Game::getPlayerKills).flatMap(List::stream).collect(toList());
	}
	
	private final Map<String, Integer> agruparKillPorPlayer(final List<Game> games) {
		final Map<String, Integer> killsByNickName = new HashMap<>();
		final List<PlayerKill> playerKills = killPorPlayer(games);
		for (final PlayerKill playerKill : playerKills) {
			final String playerNickName = playerKill.getPlayer().getNickName();
			final Integer count = playerKill.getCount();
			if (!killsByNickName.containsKey(playerNickName)) {
				killsByNickName.put(playerNickName, 0);
			}
			final Integer previousValue = killsByNickName.get(playerNickName);
			killsByNickName.put(playerNickName, previousValue + count);
		}
		return killsByNickName;
	}
	
	@SuppressWarnings("unused")
	private Map<Death, Integer> agruparPorDeath(final Game game) {

		final List<PlayerDeath> playerDeaths = game.getPlayerDeaths();
		final Map<Death, Integer> contarPorDeath = new HashMap<>();

		for (final PlayerDeath playerDeath : playerDeaths) {
			final Map<Death, Integer> playerCountByDeaths = playerDeath.getContarPorDeath();

			for (final Entry<Death, Integer> entry : playerCountByDeaths.entrySet()) {
				final Death death = entry.getKey();
				final Integer count = entry.getValue();

				if (!contarPorDeath.containsKey(death)) {
					contarPorDeath.put(death, 0);
				}

				final Integer previous = contarPorDeath.get(death);
				contarPorDeath.put(death, previous + count);
			}
		}

		return contarPorDeath;
	}
	
	@SuppressWarnings("unused")
	private void overall(final List<Game> games) {
		System.out.println("Overall: {");
		final Map<String, Integer> killsPorNickName = agruparKillPorPlayer(games);
		for (final Entry<String, Integer> entry : killsPorNickName.entrySet()) {
			final String nickName = entry.getKey();
			final Integer kills = entry.getValue();
			System.out.println(ESPACO + "\"" + nickName + "\": " + kills);
		}
		System.out.println("}");
	}
	
	@SuppressWarnings("unused")
	private void kills(final Game game) {
		if (game.getPlayerKills().isEmpty()) {
			return;
		}
		System.out.println(ESPACO + "Kills: {");
		final Iterator<PlayerKill> iterator = game.getPlayerKills().iterator();
		while (iterator.hasNext()) {
			final PlayerKill playerKill = iterator.next();
			final Player player = playerKill.getPlayer();
			System.out.print(ESPACO + ESPACO + "\"" + player.getNickName() + "\": " + playerKill.getCount());
			if (iterator.hasNext()) {
				System.out.println(",");

			} else {
				System.out.println();
			}
		}
		System.out.println(ESPACO + "}");
	}
	
	@SuppressWarnings("unused")
	private void totalKills(final Game game) {
		final Long totalCount = game.getPlayerKills().stream().map(PlayerKill::getCount)
				.mapToInt(Integer::intValue).count();
		System.out.println(ESPACO + "total_kills: " + totalCount + ";");
	}
	
	private void killsPorMeios(final Game game) {
		if (game.getPlayerDeaths().isEmpty()) {
			return;
		}
		System.out.println(ESPACO + "kills_por_meios: {");
		final Map<Death, Integer> countByDeaths = agruparPorDeath(game);
		final Iterator<Entry<Death, Integer>> iterator = countByDeaths.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<Death, Integer> countPorDeath = iterator.next();
			final Death death = countPorDeath.getKey();
			final Integer count = countPorDeath.getValue();
			System.out.print(ESPACO + ESPACO + "\"" + death + "\": " + count);
			if (iterator.hasNext()) {
				System.out.println(",");
			} else {
				System.out.println();
			}
		}
		System.out.println(ESPACO + "}");
	}
	
	@SuppressWarnings("unused")
	private void players(final Game game) {
		final String players = game.getPlayers().stream().map(Player::getNickName).map(this::quotes).collect(joining(", "));
		System.out.println(ESPACO + "players: [" + players + "]");
	}
	
	@SuppressWarnings("unused")
	private void game(final List<Game> games) {
		for (final Game game : games) {
			nomeGame(game);
			totalKills(game);
			players(game);
			kills(game);
			killsPorMeios(game);
			System.out.println("}");
		}
	}
	
	//Parser
	
}

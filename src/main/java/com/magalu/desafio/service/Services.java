package com.magalu.desafio.service;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.magalu.desafio.domain.Game;
import com.magalu.desafio.domain.Player;
import com.magalu.desafio.domain.PlayerKill;

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
	
	
	
	private String quotes(final String string) {
		return "\"" + string + "\"";
	}
	
	private List<PlayerKill> killByPlayer(final List<Game> games) {
		return games.stream().map(Game::getPlayerKills).flatMap(List::stream).collect(toList());
	}
	
	private final Map<String, Integer> agruparKillPorPlayer(final List<Game> games) {
		final Map<String, Integer> killsByNickName = new HashMap<>();
		final List<PlayerKill> playerKills = killByPlayer(games);
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
	private void overall(final List<Game> games) {
		System.out.println("Overall: {");
		final Map<String, Integer> killsByName = agruparKillPorPlayer(games);
		for (final Entry<String, Integer> entry : killsByName.entrySet()) {
			final String nickName = entry.getKey();
			final Integer kills = entry.getValue();
			System.out.println(ESPACO + "\"" + nickName + "\": " + kills);
		}
		System.out.println("}");
	}
	
	@SuppressWarnings("unused")
	private void totalKills(final Game game) {
		final Long totalCount = game.getPlayerKills().stream().map(PlayerKill::getCount)
				.mapToInt(Integer::intValue).count();
		System.out.println(ESPACO + "total_kills: " + totalCount + ";");
	}
	
	@SuppressWarnings("unused")
	private void players(final Game game) {
		final String players = game.getPlayers().stream().map(Player::getNickName).map(this::quotes).collect(joining(", "));

		System.out.println(ESPACO + "players: [" + players + "]");
	}
}

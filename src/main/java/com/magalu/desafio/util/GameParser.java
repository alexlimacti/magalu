package com.magalu.desafio.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.magalu.desafio.analisadores.AnalisadorDeCadeia;
import com.magalu.desafio.analisadores.AnalisadorDeath;
import com.magalu.desafio.analisadores.AnalisadorDeathWorld;
import com.magalu.desafio.analisadores.AnalisadorKiller;
import com.magalu.desafio.domain.Game;
import com.magalu.desafio.domain.GameLog;
import com.magalu.desafio.domain.Player;
import com.magalu.desafio.domain.PlayerDeath;
import com.magalu.desafio.domain.PlayerKill;

public class GameParser {

	public static List<Game> parse(final List<GameLog> gameLogs) {
		return new GameParser().gameLogs(gameLogs);
	}

	private List<Game> gameLogs(final List<GameLog> gameLogs) {
		final List<Game> games = new ArrayList<>();

		for (final GameLog gameLog : gameLogs) {
			games.add(gameLog(gameLog));
		}

		return games;
	}

	private Game gameLog(final GameLog gameLog) {
		final Map<String, Player> playerByUser = new HashMap<>();
		final Map<String, Player> playerNickName = new HashMap<>();
		final Map<String, PlayerKill> killNickName = new HashMap<>();
		final Map<String, PlayerDeath> deathNickName = new HashMap<>();

		final List<String> logLines = gameLog.getLog();
		lines(logLines, playerByUser, playerNickName, killNickName, deathNickName);

		final String name = gameLog.getName();
		final List<Player> players = new ArrayList<>(playerByUser.values());
		final List<PlayerKill> playerKills = new ArrayList<>(killNickName.values());
		final List<PlayerDeath> pplayerDeaths = new ArrayList<>(deathNickName.values());

		return new Game(name, players, playerKills, pplayerDeaths);
	}

	private void lines(final List<String> logLines, final Map<String, Player> playerByUser,
			final Map<String, Player> playerNickName, final Map<String, PlayerKill> killNickName,
			final Map<String, PlayerDeath> deathNickName) {

		for (final String logLine : logLines) {
			line(logLine, playerByUser, playerNickName, killNickName, deathNickName);
		}
	}

	private void line(final String logLine, final Map<String, Player> playerByUser,
			final Map<String, Player> playerNickName, final Map<String, PlayerKill> killNickName,
			final Map<String, PlayerDeath> deathNickName) {

		final Matcher userMatcher = Referencias.padraoDeLinha(Referencias.USUARIO_PATTERN).matcher(logLine);
		if (userMatcher.matches()) {
			final String user = userMatcher.group(3).trim();
			userLine(user, playerByUser, playerNickName);

			return;
		}

		final Matcher killMatcher = Referencias.padraoDeLinha(Referencias.KILL).matcher(logLine);
		if (killMatcher.matches()) {
			final String kill = killMatcher.group(3).trim();
			killLine(kill, playerByUser, killNickName, deathNickName);

			return;
		}
	}

	private void killLine(final String kill, final Map<String, Player> playerUser,
			final Map<String, PlayerKill> killNickName, final Map<String, PlayerDeath> deathNickName) {

		final Matcher matcher = Pattern.compile("([0-9]*)\\s([0-9]*)\\s([0-9]*)(.*)").matcher(kill);
		if (!matcher.matches()) {
			return;
		}

		final String killer = matcher.group(1);
		final String killed = matcher.group(2);
		final String deathType = matcher.group(3);

		prepararAnalisadorDeCadeia().resolve(killer, killed, deathType, playerUser, killNickName, deathNickName);
	}

	private AnalisadorDeCadeia prepararAnalisadorDeCadeia() {
		final AnalisadorDeCadeia analisadorKiller = new AnalisadorKiller(Optional.empty());
		final AnalisadorDeCadeia analisadorDeath = new AnalisadorDeath(Optional.of(analisadorKiller));
		final AnalisadorDeCadeia analisadorDeathWorld = new AnalisadorDeathWorld(Optional.of(analisadorDeath));

		return analisadorDeathWorld;
	}

	private void userLine(final String user, final Map<String, Player> playerByUser,
			final Map<String, Player> playerNickName) {

		final String userAux = user(user);
		final String playerName = playerName(user);

		if (!playerNickName.containsKey(playerName)) {
			playerNickName.put(playerName, new Player(playerName));
		}

		final Player player = playerNickName.get(playerName);
		playerByUser.put(userAux, player);
	}

	private String user(final String user) {
		if (user.length() <= 0) {
			return "";
		}

		return user.substring(0, 1).trim();
	}

	private String playerName(final String user) {
		if (user.length() <= 0) {
			return "";
		}

		final int playerNameStart = user.indexOf("n\\");
		if (playerNameStart <= 0) {
			return "";
		}

		final int playerNameEnd = user.indexOf("\\t\\");
		if (playerNameEnd <= 0) {
			return "";
		}

		return user.substring(playerNameStart + 2, playerNameEnd).trim();
	}
}

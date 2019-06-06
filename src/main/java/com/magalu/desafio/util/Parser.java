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

public class Parser {

	public static List<Game> parse(final List<GameLog> gameLogs) {
		return new Parser().parseGameLogs(gameLogs);
	}

	private List<Game> parseGameLogs(final List<GameLog> gameLogs) {
		final List<Game> games = new ArrayList<>();

		for (final GameLog gameLog : gameLogs) {
			games.add(gameLog(gameLog));
		}

		return games;
	}

	private Game gameLog(final GameLog gameLog) {
		final Map<String, Player> playerUser = new HashMap<>();
		final Map<String, Player> playerNickName = new HashMap<>();
		final Map<String, PlayerKill> killNickName = new HashMap<>();
		final Map<String, PlayerDeath> deathNickName = new HashMap<>();

		final List<String> logLines = gameLog.getLog();
		lines(logLines, playerUser, playerNickName, killNickName, deathNickName);

		final String name = gameLog.getName();
		final List<Player> players = new ArrayList<>(playerUser.values());
		final List<PlayerKill> playerKills = new ArrayList<>(killNickName.values());
		final List<PlayerDeath> pplayerDeaths = new ArrayList<>(deathNickName.values());

		return new Game(name, players, playerKills, pplayerDeaths);
	}

	private void lines(final List<String> logLines, final Map<String, Player> playerByUserId,
			final Map<String, Player> playerNickName, final Map<String, PlayerKill> killNickName,
			final Map<String, PlayerDeath> deathNickName) {

		for (final String logLine : logLines) {
			line(logLine, playerByUserId, playerNickName, killNickName, deathNickName);
		}
	}

	private void line(final String logLine, final Map<String, Player> playerByUserId,
			final Map<String, Player> playerNickName, final Map<String, PlayerKill> killNickName,
			final Map<String, PlayerDeath> deathNickName) {

		final Matcher userMatcher = Referencias.padraoDeLinha(Referencias.USUARIO_PATTERN).matcher(logLine);
		if (userMatcher.matches()) {
			final String user = userMatcher.group(3).trim();
			userLine(user, playerByUserId, playerNickName);

			return;
		}

		final Matcher killMatcher = Referencias.padraoDeLinha(Referencias.KILL).matcher(logLine);
		if (killMatcher.matches()) {
			final String kill = killMatcher.group(3).trim();
			killLine(kill, playerByUserId, killNickName, deathNickName);

			return;
		}
	}

	private void killLine(final String kill, final Map<String, Player> playerByUserId,
			final Map<String, PlayerKill> killNickName, final Map<String, PlayerDeath> deathNickName) {

		final Matcher matcher = Pattern.compile("([0-9]*)\\s([0-9]*)\\s([0-9]*)(.*)").matcher(kill);
		if (!matcher.matches()) {
			return;
		}

		final String killerId = matcher.group(1);
		final String killedId = matcher.group(2);
		final String deathTypeId = matcher.group(3);

		prepareAnalisadorDeCadeia().resolve(killerId, killedId, deathTypeId, playerByUserId, killNickName, deathNickName);
	}

	private AnalisadorDeCadeia prepareAnalisadorDeCadeia() {
		final AnalisadorDeCadeia killAnalyzer = new AnalisadorKiller(Optional.empty());
		final AnalisadorDeCadeia deathAnalyzer = new AnalisadorDeath(Optional.of(killAnalyzer));
		final AnalisadorDeCadeia worldDeathAnalyzer = new AnalisadorDeathWorld(Optional.of(deathAnalyzer));

		return worldDeathAnalyzer;
	}

	private void userLine(final String user, final Map<String, Player> playerByUserId,
			final Map<String, Player> playerNickName) {

		final String userId = user(user);
		final String playerName = playerNickName(user);

		if (!playerNickName.containsKey(playerName)) {
			playerNickName.put(playerName, new Player(playerName));
		}

		final Player player = playerNickName.get(playerName);
		playerByUserId.put(userId, player);
	}

	private String user(final String user) {
		if (user.length() <= 0) {
			return "";
		}

		return user.substring(0, 1).trim();
	}

	private String playerNickName(final String user) {
		if (user.length() <= 0) {
			return "";
		}

		final int playerNickNameStart = user.indexOf("n\\");
		if (playerNickNameStart <= 0) {
			return "";
		}

		final int playerNickNameEnd = user.indexOf("\\t\\");
		if (playerNickNameEnd <= 0) {
			return "";
		}

		return user.substring(playerNickNameStart + 2, playerNickNameEnd).trim();
	}
	
}

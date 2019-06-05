package com.magalu.desafio.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.magalu.desafio.domain.GameLog;

public class Divisor {

	public static List<GameLog> dividir(final List<String> todasLinhas) {
		return new Divisor().dividirGameLogs(todasLinhas);
	}
	
	private List<GameLog> dividirGameLogs(final List<String> todasLinhas) {
		final List<GameLog> gameLogs = new ArrayList<>();
		for (int index = 0; index < todasLinhas.size(); index++) {
			final String logLine = todasLinhas.get(index).trim();
			if (inicioGameLog(logLine)) {
				gameLogs.add(new GameLog(contrucaoNomeGameLog(gameLogs.size())));
			}
			ultimo(gameLogs).ifPresent(gameLog -> gameLog.addLog(logLine));
		}
		return gameLogs;
	}
	
	private String contrucaoNomeGameLog(final int index) {
		return "game-" + (index + 1);
	}
	
	private boolean inicioGameLog(final String logLine) {
		return Referencias.padraoDeLinha(Referencias.INICIO_JOGO).matcher(logLine).matches();
	}
	
	private Optional<GameLog> ultimo(final List<GameLog> gameLogs) {
		if (null == gameLogs || gameLogs.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(gameLogs.get(gameLogs.size() - 1));
	}
	
}

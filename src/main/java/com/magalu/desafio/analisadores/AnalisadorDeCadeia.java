package com.magalu.desafio.analisadores;

import java.util.Map;
import java.util.Optional;

import com.magalu.desafio.domain.Player;
import com.magalu.desafio.domain.PlayerDeath;
import com.magalu.desafio.domain.PlayerKill;

public interface AnalisadorDeCadeia {

	void resolve(final String killer, final String killed, final String deathType,
			final Map<String, Player> playerUser, final Map<String, PlayerKill> killNickName,
			final Map<String, PlayerDeath> deathNickName);

	default void next(final Optional<AnalisadorDeCadeia> next, final String killer, final String killed,
			final String deathType, final Map<String, Player> playerUser,
			final Map<String, PlayerKill> killNickName, final Map<String, PlayerDeath> deathNickName) {

		if (null != next) {
			next.ifPresent(analyzer -> analyzer.resolve(killer, killed, deathType, playerUser, killNickName,
					deathNickName));
		}
	}

}

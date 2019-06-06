package com.magalu.desafio.analisadores;

import java.util.Map;
import java.util.Optional;

import com.magalu.desafio.domain.Player;
import com.magalu.desafio.domain.PlayerDeath;
import com.magalu.desafio.domain.PlayerKill;

public class AnalisadorKiller implements AnalisadorDeCadeia {

	private final Optional<AnalisadorDeCadeia> next;


	public AnalisadorKiller(final Optional<AnalisadorDeCadeia> nextAnalizer) {
		this.next = nextAnalizer;
	}

	@Override
	public void resolve(final String killerId, final String killedId, final String deathTypeId,
			final Map<String, Player> playerByUserId, final Map<String, PlayerKill> killNickName,
			final Map<String, PlayerDeath> deathNickName) {

		if (!seMatou(killerId, killedId)) {
			processador(killerId, playerByUserId, killNickName);
		}

		next(this.next, killerId, killedId, deathTypeId, playerByUserId, killNickName, deathNickName);
	}

	private void processador(final String killerId, final Map<String, Player> playerByUserId,
			final Map<String, PlayerKill> killNickName) {
		final Player killer = playerByUserId.get(killerId);
		if (null == killer) {
			return;
		}

		final String killerName = killer.getNickName();

		if (!killNickName.containsKey(killerName)) {
			killNickName.put(killerName, new PlayerKill(killer));
		}

		killNickName.get(killerName).addCount();;
	}

	private boolean seMatou(final String killerId, final String killedId) {
		return killerId.equals(killedId);
	}

}

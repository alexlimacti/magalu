package com.magalu.desafio.analisadores;

import java.util.Map;
import java.util.Optional;

import com.magalu.desafio.domain.Player;
import com.magalu.desafio.domain.PlayerDeath;
import com.magalu.desafio.domain.PlayerKill;
import com.magalu.desafio.util.Referencias;

public class AnalisadorDeathWorld implements AnalisadorDeCadeia {

	private final Optional<AnalisadorDeCadeia> next;

	public AnalisadorDeathWorld(final Optional<AnalisadorDeCadeia> nextAnalizer) {
		this.next = nextAnalizer;
	}

	@Override
	public void resolve(final String killerId, final String killedId, final String deathTypeId,
			final Map<String, Player> playerByUserId, final Map<String, PlayerKill> killNickName,
			final Map<String, PlayerDeath> deathNickName) {

		if (killWorld(killerId)) {
			processo(killedId, playerByUserId, killNickName);
		}

		next(this.next, killerId, killedId, deathTypeId, playerByUserId, killNickName, deathNickName);
	}

	private void processo(final String killedId, final Map<String, Player> playerByUserId,
			final Map<String, PlayerKill> killNickName) {

		final Player killed = playerByUserId.get(killedId);
		if (null == killed) {
			return;
		}

		final String killedName = killed.getNickName();

		// Punishment for <world> death
		if (!killNickName.containsKey(killedName)) {
			killNickName.put(killedName, new PlayerKill(killed));
		}
		killNickName.get(killedName).remCount();;
	}

	private boolean killWorld(final String killerId) {
		return Referencias.KILL_WORLD_ID.equalsIgnoreCase(killerId);
	}

}

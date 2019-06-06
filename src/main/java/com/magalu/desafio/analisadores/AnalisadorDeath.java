package com.magalu.desafio.analisadores;

import java.util.Map;
import java.util.Optional;

import com.magalu.desafio.domain.Player;
import com.magalu.desafio.domain.PlayerDeath;
import com.magalu.desafio.domain.PlayerKill;
import com.magalu.desafio.enumerator.Death;

public class AnalisadorDeath implements AnalisadorDeCadeia {

	private final Optional<AnalisadorDeCadeia> next;

	public AnalisadorDeath(final Optional<AnalisadorDeCadeia> nextAnalizer) {
		this.next = nextAnalizer;
	}

	@Override
	public void resolve(final String killer, final String killed, final String death,
			final Map<String, Player> playerUser, final Map<String, PlayerKill> killNickName,
			final Map<String, PlayerDeath> deathNickName) {

		process(killed, death, playerUser, deathNickName);
		next(this.next, killer, killed, death, playerUser, killNickName, deathNickName);
	}

	private void process(final String killed, final String death, final Map<String, Player> playerUser,
			final Map<String, PlayerDeath> deathNickName) {
		final Player killedAux = playerUser.get(killed);
		if (null == killedAux) {
			return;
		}

		final String killedName = killedAux.getNickName();
		final Death deathAux = getDeath(death);

		if (!deathNickName.containsKey(killedName)) {
			deathNickName.put(killedName, new PlayerDeath(killedAux));
		}

		deathNickName.get(killedName).addDeath(deathAux);
	}

	private Death getDeath(final String death) {
		try {
			final Integer deathValue = Integer.valueOf(death);
			return Death.toEnum(deathValue);

		} catch (final Exception exception) {
			System.out.println("Erro ao analisar Death.");
			return Death.NOT_FOUND;
		}
	}

}

package com.magalu.desafio.domain;

import java.util.HashMap;
import java.util.Map;

import com.magalu.desafio.enumerator.Death;

public class PlayerDeath {
	
	private Player player;
	private Map<Death, Integer> contarPorDeath;
	
	public PlayerDeath(Player player) {
		this.player = player;
		this.contarPorDeath = new HashMap<>();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Map<Death, Integer> getContarPorDeath() {
		return contarPorDeath;
	}

	public void setContarPorDeath(Map<Death, Integer> contarPorDeath) {
		this.contarPorDeath = contarPorDeath;
	}
	
	public void addDeath(Death death) {
		if (!contarPorDeath.containsKey(death)) {
			contarPorDeath.put(death, 0);
		}

		final Integer valorAnterior = contarPorDeath.get(death);
		contarPorDeath.put(death, valorAnterior + 1);
	}
}

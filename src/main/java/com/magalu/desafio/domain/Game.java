package com.magalu.desafio.domain;

import java.util.List;

public class Game {
	
	private String name;

	private List<Player> players;

	private List<PlayerKill> playerKills;

	private List<PlayerDeath> playerDeaths;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<PlayerKill> getPlayerKills() {
		return playerKills;
	}

	public void setPlayerKills(List<PlayerKill> playerKills) {
		this.playerKills = playerKills;
	}

	public List<PlayerDeath> getPlayerDeaths() {
		return playerDeaths;
	}

	public void setPlayerDeaths(List<PlayerDeath> playerDeaths) {
		this.playerDeaths = playerDeaths;
	}

	public Game(String name, List<Player> players, List<PlayerKill> playerKills, List<PlayerDeath> playerDeaths) {
		super();
		this.name = name;
		this.players = players;
		this.playerKills = playerKills;
		this.playerDeaths = playerDeaths;
	}

}

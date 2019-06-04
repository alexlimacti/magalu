package com.magalu.desafio.domain;

public class PlayerKill {
	
	private Player player;
	private Integer count;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	public void addCount() {
		this.count++;
	}
	
	public void remCount() {
		this.count--;
	}
	
	public PlayerKill(Player player) {
		this.player = player;
		this.count = 0;
	}

}

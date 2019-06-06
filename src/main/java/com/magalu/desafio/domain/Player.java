package com.magalu.desafio.domain;

public class Player {
	
	private String nickName;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Player(String nickName) {
		super();
		this.nickName = nickName;
	}

}

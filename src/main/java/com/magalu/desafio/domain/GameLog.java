package com.magalu.desafio.domain;

import java.util.ArrayList;
import java.util.List;

public class GameLog {
	
	private String name;

	private List<String> log;
	
	public GameLog(String name) {
		this.name = name;
		this.log = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getLog() {
		return log;
	}

	public void setLog(List<String> log) {
		this.log = log;
	}

	public void addLog(String linha) {
		log.add(linha);
	}
}
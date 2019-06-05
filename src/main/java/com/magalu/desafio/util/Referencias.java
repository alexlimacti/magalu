package com.magalu.desafio.util;

import java.util.regex.Pattern;

public class Referencias {
	
	private static final String TEMPO = "([0-9]*[0-9]:[0-5][0-9])";
	public static final String INICIO_JOGO = "InitGame";
	
	public static Pattern padraoDeLinha(final String value) {
		return Pattern.compile(TEMPO + "\\s(" + value + ":)(.*?)");
	}

}

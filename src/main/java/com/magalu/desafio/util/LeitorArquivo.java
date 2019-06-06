package com.magalu.desafio.util;

import static java.util.Collections.emptyList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LeitorArquivo {
	
	public static List<String> readFile(final String path) {
		return new LeitorArquivo().getTodasLinhas(path);
	}
	
	private List<String> getTodasLinhas(final String path) {
		try {
			return Files.readAllLines(Paths.get(path));

		} catch (IOException e) {
			System.out.println("Erro na leitura do arquvivo");
			return emptyList();
		}
	}

}

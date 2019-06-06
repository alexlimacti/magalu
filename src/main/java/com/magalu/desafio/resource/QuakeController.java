package com.magalu.desafio.resource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magalu.desafio.domain.Game;
import com.magalu.desafio.domain.GameLog;
import com.magalu.desafio.service.Services;
import com.magalu.desafio.util.Divisor;
import com.magalu.desafio.util.GameParser;
import com.magalu.desafio.util.LeitorArquivo;

@RestController
@RequestMapping(path = "api/v1/")
public class QuakeController {
	
	@Autowired
	private Services services;
	
	@GetMapping
	public ResponseEntity<List<Game>> find() throws IOException {
		File file = new ClassPathResource("games.log").getFile();
		final List<String> todasLinhas = LeitorArquivo.readFile(file.getPath());
		final List<GameLog> gameLogs = Divisor.dividir(todasLinhas);
		final List<Game> games = GameParser.parse(gameLogs);
		services.games(games);
		return ResponseEntity.ok().body(games);
	}

}

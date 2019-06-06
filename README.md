## Projeto desenvolvido como desafio para o LuizaLabs

## Requisitos
- Java 8
- Spring Boot 2.1.5

## Preparação do Ambiente
Seguir os passos descritos para Ambiente em https://www.youtube.com/watch?v=TYdkuWvxWOk
Seguir os passos descritos para o Maven em https://www.youtube.com/watch?v=6AVC3X88z6E
A IDE utilizada no projeto foi o Eclipse 2019-03(4.11.0), mas fique a vontade para escolha.

## Tasks
## Task 1 - Construa um parser para o arquivo de log games.log e exponha uma API de consulta.

Pacote Analisadores - Responsável pela análise das informações categorizando as informações de acordo com o especificado no desafio.
Pacote Domain e Enumerator - Classes básicas e tipos de mortes.
Pacote Util: Responsável pela leitura e manipulação do arquivo.
Pacote Service: Responsável pelo serviço de impressão das informações.
Pacote Resource: Responsável pela comunicação da API

Foram implementados testes automatizados onde são verificadas as Classes Divisor e GameParser, ambas responsáveis de forma direta pela execução do projeto.
Para se efetuar os testes, acessar a classe MagaluApplicationTestes.java, que se encontra no pacote com.magalu.desafio dentro da source de testes e executar a mesma.

Para execução do projeto, basta executar a classe 

## Task 2 - Após construir o parser construa uma API que faça a exposição de um método de consulta que retorne um relatório de cada jogo.
A implementação da API se resume na classe QuakeController, onde a mesma seta para a URI api/v1.
Dentro da API existe apenas uma URI com método GET que retorna as informações de LOG do game no formato JSON.


OBS.: O arquivo de log esta em local fixo, localizado na pasta src/main/resources do projeto


## Testes da API
Para testes foi utilizado o Postman.
O mesmo pode ser instalado diretamente como extensão no Google Chrome ou através do download no endereço https://www.getpostman.com/downloads/, estando disponível para Windows, MacOS e Linux


## EXECUÇÃO DA API
Para executar, utilizando o console (cmd):  
- ``` cd <caminho do projeto> ```
- ``` mvn spring-boot:run ``
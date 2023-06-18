CREATE DATABASE registrojogos;

USE registrojogos;

CREATE TABLE plataforma (
	id_plataforma BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(200),
    PRIMARY KEY (id_plataforma)
);

CREATE TABLE empresa (
	id_empresa BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(200),
    cnpj VARCHAR(20),
    PRIMARY KEY (id_empresa)
);

CREATE TABLE jogo_digital (
	id_jogo BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(200),
    ano_lancamento INTEGER(4),
    id_empresa BIGINT,
    id_plataforma BIGINT,
    PRIMARY KEY (id_jogo),
    FOREIGN KEY (id_empresa) REFERENCES empresa(id_empresa),
    FOREIGN KEY (id_plataforma) REFERENCES plataforma(id_plataforma)
);

INSERT INTO plataforma (nome) VALUES ("Console");
INSERT INTO plataforma (nome) VALUES ("Computador");
INSERT INTO plataforma (nome) VALUES ("Celular");

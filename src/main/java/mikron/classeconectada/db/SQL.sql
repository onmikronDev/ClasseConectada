use classeconectada;

CREATE TABLE user (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      nome VARCHAR(255) NOT NULL,
                      cpf VARCHAR(14) UNIQUE NOT NULL,
                      senha VARCHAR(255) NOT NULL,
                      email VARCHAR(255) UNIQUE NOT NULL,
                      telefone varchar(14) NOT NULL,
                      endereco VARCHAR(255) NOT NULL
);

CREATE TABLE diretor (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         nome VARCHAR(255) NOT NULL,
                         cpf VARCHAR(14) UNIQUE NOT NULL,
                         senha VARCHAR(255) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         telefone varchar(14) NOT NULL,
                         endereco VARCHAR(255) NOT NULL,
                         FOREIGN KEY (id) REFERENCES user(id)
);

CREATE TABLE supervisor (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            nome VARCHAR(255) NOT NULL,
                            cpf VARCHAR(14) UNIQUE NOT NULL,
                            senha VARCHAR(255) NOT NULL,
                            email VARCHAR(255) UNIQUE NOT NULL,
                            telefone varchar(14) NOT NULL,
                            endereco VARCHAR(255) NOT NULL,
                            FOREIGN KEY (id) REFERENCES user(id)
);


CREATE TABLE aluno (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       nome VARCHAR(255) NOT NULL,
                       cpf VARCHAR(14) UNIQUE NOT NULL,
                       senha VARCHAR(255) NOT NULL,
                       telefone VARCHAR(14) NOT NULL,
                       endereco VARCHAR(255) NOT NULL,
                       pai VARCHAR(255) NOT NULL,
                       mae VARCHAR(255) NOT NULL,
                       turma_id INT NOT NULL,
                       FOREIGN KEY (id) REFERENCES user(id),
                       FOREIGN KEY (turma_id) REFERENCES turmas(id)
);

CREATE table professor (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           nome VARCHAR(255) NOT NULL,
                           cpf VARCHAR(14) UNIQUE NOT NULL,
                           senha VARCHAR(255) NOT NULL,
                           email VARCHAR(255) UNIQUE NOT NULL,
                           telefone varchar(14) NOT NULL,
                           endereco VARCHAR(255) NOT NULL,
                           formação VARCHAR(255) NOT NULL
);

create table turmas (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nome VARCHAR(255) NOT NULL,
                        ano INT NOT NULL
);


create table chamada (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         aluno_id INT NOT NULL,
                         data DATE NOT NULL,
                         status ENUM('presente', 'ausente', 'justificada') NOT NULL
);

create table calendario (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            data DATE NOT NULL,
                            evento VARCHAR(255) NOT NULL,
                            descricao TEXT NOT NULL,
                            id_turma INT NOT NULL,
                            id_professor INT NOT NULL,
                            foreign key (id_turma) references turmas(id),
                            foreign key (id_professor) references professor(id)
);

create table chamada_aluno (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               chamada_id INT NOT NULL,
                               aluno_id INT NOT NULL,
                               FOREIGN KEY (chamada_id) REFERENCES chamada(id),
                               FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);

create table professor_turmas (
                                  professor_id INT NOT NULL,
                                  turma_id INT NOT NULL,
                                  PRIMARY KEY (professor_id, turma_id),
                                  FOREIGN KEY (professor_id) REFERENCES professor(id),
                                  FOREIGN KEY (turma_id) REFERENCES turmas(id)
);

create table relatorio (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           aluno_id INT NOT NULL,
                           data DATE NOT NULL,
                           conteudo TEXT NOT NULL,
                           FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);

create table nota (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      aluno_id INT NOT NULL,
                      nota DECIMAL(5,2) NOT NULL,
                      data DATE NOT NULL,
                      descricao varchar(255) NOT NULL,
                      FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);


show tables;

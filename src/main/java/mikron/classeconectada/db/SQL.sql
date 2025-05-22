-- Use the target database

create database if not exists classeconectada;


USE classeconectada;



CREATE TABLE user (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      nome VARCHAR(255) NOT NULL,
                      cpf VARCHAR(14) UNIQUE NOT NULL,
                      senha VARCHAR(255) NOT NULL,
                      email VARCHAR(255) UNIQUE NOT NULL,
                      telefone VARCHAR(14) NOT NULL,
                      endereco VARCHAR(255) NOT NULL,
                      observacao VARCHAR(255) NOT NULL,
                      tipo ENUM('diretor', 'supervisor', 'professor', 'aluno') NOT NULL
);

CREATE TABLE turmas (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nome VARCHAR(255) NOT NULL,
                        ano INT NOT NULL
);

CREATE TABLE aluno (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       pai VARCHAR(255) NOT NULL,
                       mae VARCHAR(255) NOT NULL,
                       turma_id INT NOT NULL,
                       recado VARCHAR(255) NOT NULL,
                       FOREIGN KEY (id) REFERENCES user(id),
                       FOREIGN KEY (turma_id) REFERENCES turmas(id)
);

CREATE TABLE professor (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           disciplina VARCHAR(255) NOT NULL,
                           FOREIGN KEY (id) REFERENCES user(id)
);

CREATE TABLE disciplina (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            nome VARCHAR(255) NOT NULL
);

CREATE TABLE professor_disciplina (
                                      professor_id INT NOT NULL,
                                      disciplina_id INT NOT NULL,
                                      PRIMARY KEY (professor_id, disciplina_id),
                                      FOREIGN KEY (professor_id) REFERENCES professor(id),
                                      FOREIGN KEY (disciplina_id) REFERENCES disciplina(id)
);

CREATE TABLE turmas_aluno (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              aluno_id INT NOT NULL,
                              turma_id INT NOT NULL,
                              FOREIGN KEY (aluno_id) REFERENCES aluno(id),
                              FOREIGN KEY (turma_id) REFERENCES turmas(id)
);

CREATE TABLE professor_turmas (
                                  professor_id INT NOT NULL,
                                  turma_id INT NOT NULL,
                                  PRIMARY KEY (professor_id, turma_id),
                                  FOREIGN KEY (professor_id) REFERENCES professor(id),
                                  FOREIGN KEY (turma_id) REFERENCES turmas(id)
);

CREATE TABLE eventos (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         evento VARCHAR(255) NOT NULL,
                         data DATE NOT NULL,
                         descricao TEXT NOT NULL,
                         id_turma INT NOT NULL,
                         FOREIGN KEY (id_turma) REFERENCES turmas(id)
);

CREATE TABLE calendario (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            data DATE NOT NULL,
                            evento VARCHAR(255) NOT NULL,
                            descricao TEXT NOT NULL,
                            id_turma INT NOT NULL,
                            id_professor INT NOT NULL,
                            FOREIGN KEY (id_turma) REFERENCES turmas(id),
                            FOREIGN KEY (id_professor) REFERENCES professor(id)
);

CREATE TABLE chamada (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         aluno_id INT NOT NULL,
                         turma_id INT NOT NULL,
                         data DATE NOT NULL,
                         status ENUM('presente', 'ausente', 'justificada') NOT NULL,
                         FOREIGN KEY (aluno_id) REFERENCES aluno(id),
                         FOREIGN KEY (turma_id) REFERENCES turmas(id)
);

CREATE TABLE chamada_aluno (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               chamada_id INT NOT NULL,
                               aluno_id INT NOT NULL,
                               FOREIGN KEY (chamada_id) REFERENCES chamada(id),
                               FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);

CREATE TABLE observacao (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            aluno_id INT NOT NULL,
                            data DATE NOT NULL,
                            conteudo TEXT NOT NULL,
                            FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);

CREATE TABLE relatorio (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           aluno_id INT NOT NULL,
                           data DATE NOT NULL,
                           conteudo TEXT NOT NULL,
                           FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);

CREATE TABLE nota (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      aluno_id INT NOT NULL,
                      disciplina VARCHAR(255) NOT NULL,
                      nota DECIMAL(5,2) NOT NULL,
                      data DATE NOT NULL,
                      descricao VARCHAR(255) NOT NULL,
                      FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);

INSERT INTO user (nome, cpf, senha, email, telefone, endereco, observacao, tipo) VALUES
                                                                                     ('Diretor', '12345678900', 'senha123', 'diretor@email.com', '123456789', 'Rua A, 123', '', 'diretor'),
                                                                                     ('Supervisor', '12345678903', 'senha123', 'supervisor@email.com', '123456789', 'Rua A, 123', '', 'supervisor'),
                                                                                     ('Professor', '12345678901', 'senha123', 'professor@email.com', '123456789', 'Rua A, 123', '', 'professor'),
                                                                                     ('Aluno', '12345678902', 'senha123', 'aluno@email.com', '123456789', 'Rua A, 123', '', 'aluno');

INSERT INTO professor (id, disciplina) VALUES (3, 'Matemática');

INSERT INTO turmas (nome, ano) VALUES ('Turma A', 2023), ('Turma B', 2023);

INSERT INTO disciplina (nome) VALUES ('Matemática'), ('Português'), ('História'), ('Geografia');

INSERT INTO professor_disciplina (professor_id, disciplina_id) VALUES (3, 1);

INSERT INTO aluno (id, pai, mae, turma_id, recado) VALUES (4, 'Pai', 'Mãe', 1, '');

INSERT INTO turmas_aluno (aluno_id, turma_id) VALUES (4, 1);

INSERT INTO professor_turmas (professor_id, turma_id) VALUES (3, 1);

INSERT INTO nota (aluno_id, disciplina, nota, data, descricao) VALUES
                                                                   (4, 'Matemática', 5.7, '2023-11-01', 'Atividade de Frações'),
                                                                   (4, 'Matemática', 9.3, '2023-12-01', 'Prova Final de Matemática'),
                                                                   (4, 'Matemática', 8.5, '2023-10-01', 'Prova de Matemática'),
                                                                   (4, 'Português', 7.2, '2023-10-05', 'Trabalho de Redação'),
                                                                   (4, 'Português', 8.9, '2023-11-03', 'Prova de Literatura'),
                                                                   (4, 'História', 6.8, '2023-10-10', 'Apresentação sobre Brasil Colônia'),
                                                                   (4, 'História', 7.4, '2023-11-10', 'Seminário sobre Revolução Industrial'),
                                                                   (4, 'Geografia', 6.5, '2023-11-15', 'Trabalho sobre Climas'),
                                                                   (4, 'Geografia', 9.1, '2023-10-12', 'Prova de Mapas');
use classeconectada;

CREATE TABLE user (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      nome VARCHAR(255) NOT NULL,
                      cpf VARCHAR(14) UNIQUE NOT NULL,
                      senha VARCHAR(255) NOT NULL,
                      email VARCHAR(255) UNIQUE NOT NULL,
                      telefone varchar(14) NOT NULL,
                      endereco VARCHAR(255) NOT NULL,
                      tipo ENUM('diretor', 'supervisor', 'professor', 'aluno') NOT NULL
);



CREATE TABLE aluno (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       pai VARCHAR(255) NOT NULL,
                       mae VARCHAR(255) NOT NULL,
                       turma_id INT NOT NULL,
                       FOREIGN KEY (id) REFERENCES user(id),
                       FOREIGN KEY (turma_id) REFERENCES turmas(id)
);

CREATE table professor (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           formação VARCHAR(255) NOT NULL,
                           FOREIGN KEY (id) REFERENCES user(id)

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

insert into user (nome, cpf, senha, email, telefone, endereco, tipo) values ('Diretor', '12345678900', 'senha123', '@gay', '123456789', 'Rua A, 123', 'diretor');
insert into user (nome, cpf, senha, email, telefone, endereco, tipo) values ('Supervisor', '12345678903', 'senha123', '@bixa', '123456789', 'Rua A, 123', 'supervisor');
insert into user (nome, cpf, senha, email, telefone, endereco, tipo) values ('Professor', '12345678901', 'senha123', '@viado', '123456789', 'Rua A, 123', 'professor');
insert into professor (id, formação) values (2, 'Matemática');
insert into user (nome, cpf, senha, email, telefone, endereco, tipo) values ('Aluno', '12345678902', 'senha123', '@homosexual', '123456789', 'Rua A, 123', 'aluno');

insert turmas (nome, ano) values ('Turma A', 2023);
insert into professor_turmas(professor_id, turma_id) values (2, 1);
insert into aluno (id, pai, mae, turma_id) values (3, 'Pai', 'Mãe', 1);
delete from aluno where id = 1;

SELECT aluno.id, aluno.pai, aluno.mae, turmas.nome AS turma_nome
FROM aluno
         JOIN turmas ON aluno.turma_id = turmas.id
WHERE turmas.nome = 'Turma A';

select * from user;

SELECT * FROM aluno WHERE turma_id = 1;

show tables;
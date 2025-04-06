CREATE TABLE cargo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cargo_id INT NOT NULL,
    FOREIGN KEY (cargo_id) REFERENCES cargo(id)
);

CREATE TABLE permissao (
	id INT AUTO_INCREMENT PRIMARY KEY,
    tipo varchar(255) NOT NULL
);

CREATE TABLE cargo_permissao (
	cargo_id INT NOT NULL,
    permissao_id INT NOT NULL,
    PRIMARY KEY (cargo_id, permissao_id),
    FOREIGN KEY (cargo_id) REFERENCES cargo(id),
    FOREIGN KEY (permissao_id) REFERENCES permissao(id)
);

CREATE TABLE log (
	id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
	acao VARCHAR(255) NOT NULL,
    data_hora DATETIME NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE area_agricola (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,          
    usuario_upgrade_id INT,           
    usuario_aprovador_id INT,         
    nome_fazenda VARCHAR(255) NOT NULL,
    cultura VARCHAR(255) NOT NULL,
    produtividade_ano DECIMAL(10,2) NOT NULL,
    area DECIMAL(10,2) NOT NULL,
    tipo_solo VARCHAR(100) NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    vetor_raiz JSON NOT NULL,         -- Vetor inicial cadastrado
    vetor_atualizado JSON NULL,       -- Vetor atualizado durante a análise
    vetor_aprovado JSON NULL,         -- Vetor final aprovado
    status ENUM('pendente', 'aprovado', 'rejeitado') DEFAULT 'pendente',
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_upgrade_id) REFERENCES usuario(id) ON DELETE SET NULL,
    FOREIGN KEY (usuario_aprovador_id) REFERENCES usuario(id) ON DELETE SET NULL
);

CREATE TABLE tipo_solo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo_solo VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE cultura (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE talhao (
    id INT PRIMARY KEY,
    produtividade_ano FLOAT,
    area DOUBLE PRECISION NOT NULL,
    tipo_solo_id INTEGER REFERENCES tipo_solo(id),
    area_agricola_id INTEGER REFERENCES area_agricola(id),
    arquivo_daninha GEOMETRY,
    arquivo_final_daninha GEOMETRY
);

CREATE TABLE safra (
   id INT PRIMARY KEY AUTO_INCREMENT,
   ano INTEGER NOT NULL,
   cultura_id INTEGER REFERENCES cultura(id),
   talhao_id INTEGER REFERENCES talhao(id),
   status ENUM('pendente', 'em_analise', 'aprovado') DEFAULT 'pendente'
);



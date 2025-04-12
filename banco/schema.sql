CREATE TABLE cidade (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE cargo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE permissao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(255) NOT NULL
);

CREATE TABLE tipo_solo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_solo VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE cultura (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) UNIQUE NOT NULL
);

-- Tabela usuario (depende de cargo)

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cargo_id INT NOT NULL,
    FOREIGN KEY (cargo_id) REFERENCES cargo(id)
);

-- Tabela de associação cargo_permissao (depende de cargo e permissao)

CREATE TABLE cargo_permissao (
    cargo_id INT NOT NULL,
    permissao_id INT NOT NULL,
    PRIMARY KEY (cargo_id, permissao_id),
    FOREIGN KEY (cargo_id) REFERENCES cargo(id),
    FOREIGN KEY (permissao_id) REFERENCES permissao(id)
);

-- Tabela log (depende de usuario)

CREATE TABLE log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    acao VARCHAR(255) NOT NULL,
    data_hora DATETIME NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Tabela area_agricola (depende de usuario e cidade)

CREATE TABLE area_agricola (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_fazenda VARCHAR(255) NOT NULL,
    cidade_id INT NOT NULL,
    estado VARCHAR(2) NOT NULL,
    arquivo_fazenda GEOMETRY NOT NULL,
    status ENUM('Pendente', 'Atribuido', 'Aprovado') DEFAULT 'Pendente',
    FOREIGN KEY (cidade_id) REFERENCES cidade(id)
);

-- Tabela talhao (depende de tipo_solo e area_agricola)

CREATE TABLE talhao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    produtividade_ano FLOAT,
    area DOUBLE PRECISION NOT NULL,
    tipo_solo_id INT,
    area_agricola_id INT,
    arquivo_daninha GEOMETRY,
    arquivo_final_daninha GEOMETRY,
    FOREIGN KEY (tipo_solo_id) REFERENCES tipo_solo(id),
    FOREIGN KEY (area_agricola_id) REFERENCES area_agricola(id)
);

-- Tabela safra (depende de cultura e talhao)

CREATE TABLE safra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ano INT NOT NULL,
    cultura_id INT,
    talhao_id INT,
    status ENUM('Pendente', 'Atribuido', 'Aprovado') DEFAULT 'Pendente',
    FOREIGN KEY (cultura_id) REFERENCES cultura(id),
    FOREIGN KEY (talhao_id) REFERENCES talhao(id)
);

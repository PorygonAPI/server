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

INSERT INTO cargo (nome) VALUES ('Consultor'), ('Analista'), ('Administrador');
INSERT INTO usuario (nome, senha, email, cargo_id) VALUES ('admin', '1234', 'admin@visiona.com', 3);
INSERT INTO usuario (nome, senha, email, cargo_id) VALUES ('analista', '1234', 'analista@visiona.com', 2);
INSERT INTO usuario (nome, senha, email, cargo_id) VALUES ('consultor', '1234', 'consultor@visiona.com', 1);

INSERT INTO permissao (tipo) VALUES ("gerenciamento_usuarios"), ("relatorios"), ("upload_arquivos");
INSERT INTO cargo_permissao (cargo_id, permissao_id) VALUES (3,1), (1,3), (3,3);
INSERT INTO log (usuario_id, acao, data_hora) VALUES (1,"cadastro_usuario", now()), (3, "upload_area", "2025-03-15 15:45:00"), (2, "aprovacao", "2025-03-14 08:07:36");
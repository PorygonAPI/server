INSERT INTO cargo (nome) VALUES ('Consultor'), ('Analista'), ('Administrador');
INSERT INTO usuario (nome, senha, email, cargo_id) VALUES ('admin', '1234', 'admin@visiona.com', 3);
INSERT INTO usuario (nome, senha, email, cargo_id) VALUES ('analista', '1234', 'analista@visiona.com', 2);
INSERT INTO usuario (nome, senha, email, cargo_id) VALUES ('consultor', '1234', 'consultor@visiona.com', 1);

INSERT INTO area_agricola (usuario_id, usuario_upgrade_id, usuario_aprovador_id, nome_fazenda, cultura, produtividade_ano, area, tipo_solo, cidade, estado, vetor_raiz, vetor_atualizado, vetor_aprovado, status) 
VALUES 
(1, NULL, NULL, 'Fazenda Exemplo', 'Soja', 3500.50, 150.75, 'Argiloso', 'Ribeirão Preto', 'SP','{"type": "Polygon", "coordinates": [[[30, 10], [40, 40], [20, 40], [10, 20], [30, 10]]]}', NULL,  NULL,'pendente');

INSERT INTO permissao (tipo) VALUES ("gerenciamento_usuarios"), ("relatorios"), ("upload_arquivos");
INSERT INTO cargo_permissao (cargo_id, permissao_id) VALUES (3,1), (1,3), (3,3);
INSERT INTO log (usuario_id, acao, data_hora) VALUES (1,"cadastro_usuario", now()), (3, "upload_area", "2025-03-15 15:45:00"), (2, "aprovacao", "2025-03-14 08:07:36");


INSERT INTO tipo_solo (tipo_solo) VALUES ('Argiloso'), ('Arenoso'), ('Siltoso');
INSERT INTO cultura (nome) VALUES ('Soja'), ('Milho'), ('Cana-de-Açúcar');
INSERT INTO talhao (produtividade_ano, area, tipo_solo_id, area_agricola_id, arquivo_daninha, arquivo_final_daninha) VALUES(3.2, 25.5, 1, 1, ST_GeomFromText('POLYGON((10 10, 20 10, 20 20, 10 20, 10 10))'), ST_GeomFromText('POLYGON((11 11, 21 11, 21 21, 11 21, 11 11))')), (4.0, 30.75, 2, 1, ST_GeomFromText('POLYGON((12 12, 22 12, 22 22, 12 22, 12 12))'), ST_GeomFromText('POLYGON((13 13, 23 13, 23 23, 13 23, 13 13))')), (2.8, 15.0, 3, 1, ST_GeomFromText('POLYGON((14 14, 24 14, 24 24, 14 24, 14 14))'), ST_GeomFromText('POLYGON((15 15, 25 15, 25 25, 15 25, 15 15))'));
INSERT INTO safra (ano, cultura_id, talhao_id, status) VALUES (2022, 1, 1, 'aprovado'), (2023, 2, 1, 'em_analise'), (2022, 3, 2, 'pendente'), (2024, 1, 3, 'aprovado');

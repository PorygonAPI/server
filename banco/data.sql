INSERT INTO cidade (id, nome) VALUES (1, 'São Paulo'), (2, 'Campinas'), (3, 'Piracicaba');

INSERT INTO cargo (nome) VALUES ('Consultor'), ('Analista'), ('Administrador');

INSERT INTO usuario (nome, senha, email, cargo_id) VALUES ('admin', '1234', 'admin@visiona.com', 3), ('analista', '1234', 'analista@visiona.com', 2), ('consultor', '1234', 'consultor@visiona.com', 1);

INSERT INTO area_agricola (id, nome_fazenda, estado, status, cidade_id, arquivo_fazenda) VALUES (1, 'Fazenda Primavera', 'SP', 'Pendente', 1, ST_GeomFromGeoJSON('{"type": "Polygon", "coordinates": [[[-46.625290, -23.533773], [-46.625290, -23.534773], [-46.624290, -23.534773], [-46.624290, -23.533773], [-46.625290, -23.533773]]]}')), (2, 'Fazenda Aurora', 'SP', 'Em análise', 2, ST_GeomFromGeoJSON('{"type": "Polygon", "coordinates": [[[-47.060830, -22.905560], [-47.060830, -22.906560], [-47.059830, -22.906560], [-47.059830, -22.905560], [-47.060830, -22.905560]]]}')), (3, 'Fazenda Horizonte', 'SP', 'Aprovado', 3, ST_GeomFromGeoJSON('{"type": "Polygon", "coordinates": [[[-47.648220, -22.725450], [-47.648220, -22.726450], [-47.647220, -22.726450], [-47.647220, -22.725450], [-47.648220, -22.725450]]]}'));

INSERT INTO permissao (tipo) VALUES ('gerenciamento_usuarios'), ('relatorios'), ('upload_arquivos');

INSERT INTO cargo_permissao (cargo_id, permissao_id) VALUES (3, 1), (1, 3), (3, 3);

INSERT INTO log (usuario_id, acao, data_hora) VALUES (1, 'cadastro_usuario', NOW()), (3, 'upload_area', '2025-03-15 15:45:00'), (2, 'aprovacao', '2025-03-14 08:07:36');
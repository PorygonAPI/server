INSERT INTO cidade (nome) VALUES ('São Paulo'), ('Campinas'), ('Piracicaba');
INSERT INTO cargo (nome) VALUES ('Consultor'), ('Analista'), ('Administrador');
INSERT INTO permissao (tipo) VALUES ('gerenciamento_usuarios'), ('relatorios'), ('upload_arquivos');
INSERT INTO tipo_solo (tipo) VALUES ('Argiloso'), ('Arenoso'), ('Siltoso');
INSERT INTO cultura (nome) VALUES ('Soja'), ('Milho'), ('Cana-de-Açúcar');

INSERT INTO usuario (nome, senha, email, cargo_id) 
VALUES 
  ('admin', '1234', 'admin@visiona.com', 3),
  ('analista', '1234', 'analista@visiona.com', 2),
  ('consultor', '1234', 'consultor@visiona.com', 1);

INSERT INTO cargo_permissao (cargo_id, permissao_id) 
VALUES 
  (3, 1), (1, 3), (3, 3);

INSERT INTO log (usuario_id, acao, data_hora) 
VALUES 
  (1, 'cadastro_usuario', NOW()), 
  (3, 'upload_area', '2025-03-15 15:45:00'), 
  (2, 'aprovacao', '2025-03-14 08:07:36');

INSERT INTO area_agricola (nome_fazenda, estado, status, cidade_id, arquivo_fazenda) 
VALUES 
  ('Fazenda Primavera', 'SP', 'Pendente', 1, ST_GeomFromGeoJSON('{"type": "Polygon","coordinates": [[[-46.625290, -23.533773],[-46.625290, -23.534773],[-46.624290, -23.534773],[-46.624290, -23.533773],[-46.625290, -23.533773]]]}')),
  ('Fazenda Aurora', 'SP', 'Atribuido', 2, ST_GeomFromGeoJSON('{"type": "Polygon","coordinates": [[[-47.060830, -22.905560],[-47.060830, -22.906560],[-47.059830, -22.906560],[-47.059830, -22.905560],[-47.060830, -22.905560]]]}')),
  ('Fazenda Horizonte', 'SP', 'Aprovado', 3, ST_GeomFromGeoJSON('{"type": "Polygon","coordinates": [[[-47.648220, -22.725450],[-47.648220, -22.726450],[-47.647220, -22.726450],[-47.647220, -22.725450],[-47.648220, -22.725450]]]}'));

INSERT INTO talhao (area, tipo_solo_id, area_agricola_id, arquivo_daninha, arquivo_final_daninha) 
VALUES 
  (25.5, 1, 1, ST_GeomFromText('POLYGON((10 10, 20 10, 20 20, 10 20, 10 10))'), ST_GeomFromText('POLYGON((11 11, 21 11, 21 21, 11 21, 11 11))')),
  (30.75, 2, 1, ST_GeomFromText('POLYGON((12 12, 22 12, 22 22, 12 22, 12 12))'), ST_GeomFromText('POLYGON((13 13, 23 13, 23 23, 13 23, 13 13))')),
  (15.0, 3, 1, ST_GeomFromText('POLYGON((14 14, 24 14, 24 24, 14 24, 14 14))'), ST_GeomFromText('POLYGON((15 15, 25 15, 25 25, 15 25, 15 15))'));

INSERT INTO safra (ano, cultura_id, talhao_id, status) 
VALUES 
  (2022, 1, 1, 'Aprovado'),
  (2023, 2, 1, 'Atribuido'),
  (2022, 3, 2, 'Pendente'),
  (2024, 1, 3, 'Aprovado');
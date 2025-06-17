-- Limpa as tabelas existentes
DELETE FROM ACOES_FAVORITAS;
DELETE FROM USUARIOS;

-- Insere usuário de teste
INSERT INTO USUARIOS (USU_LOGIN, USU_SENHA, USU_EMAIL, USU_NOME, USU_ATIVO)
VALUES ('admin', '$2a$10$X7UrH5YxX5YxX5YxX5YxX.5YxX5YxX5YxX5YxX5YxX5YxX5YxX5Yx', 'admin@trademap.com', 'Administrador', true); 
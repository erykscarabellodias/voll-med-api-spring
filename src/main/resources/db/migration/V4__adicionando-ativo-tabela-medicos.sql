ALTER TABLE medicos ADD ativo TINYINT;

UPDATE medicos SET ativo = 1;

ALTER TABLE medicos MODIFY ativo TINYINT NOT NULL;
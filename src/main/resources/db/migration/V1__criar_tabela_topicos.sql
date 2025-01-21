CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('ABERTO', 'FECHADO') DEFAULT 'ABERTO',
    autor VARCHAR(100) NOT NULL,
    curso VARCHAR(100) NOT NULL
);

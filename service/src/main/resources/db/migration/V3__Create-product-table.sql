CREATE TABLE products (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    value DECIMAL(15,2) NOT NULL,
    image_url VARCHAR(255),
    status TINYINT NOT NULL DEFAULT true,
    created_at DATETIME NOT NULL,
    updated_at DATETIME
);
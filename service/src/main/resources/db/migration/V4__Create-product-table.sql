CREATE TABLE products (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    type_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    value DECIMAL(15,2) NOT NULL,
    image_url VARCHAR(255),
    status TINYINT NOT NULL DEFAULT true,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    CONSTRAINT fk_type FOREIGN KEY(type_id) REFERENCES product_type(id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);
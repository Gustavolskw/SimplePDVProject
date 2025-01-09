CREATE TABLE orders (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    consumer_name VARCHAR(150) NOT NULL,
    table_number INT NOT NULL,
    guide BIGINT NOT NULL,
    status TINYINT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    CONSTRAINT fk_guider FOREIGN KEY(guide) REFERENCES users(id)
                    ON DELETE NO ACTION
                    ON UPDATE CASCADE
);
CREATE TABLE users(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(80) NOT NULL,
  email VARCHAR(155) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  status TINYINT NOT NULL DEFAULT true,
  role_id BIGINT NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME,
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES roles(id)
                  ON DELETE NO ACTION
                  ON UPDATE CASCADE
);
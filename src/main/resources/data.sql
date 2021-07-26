DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS tokens;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  userid int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username varchar(255) UNIQUE,
  email varchar(255) UNIQUE,
  password varchar(255) NOT NULL,
  active tinyint(1) DEFAULT 0,
  roles varchar(255) DEFAULT 'ROLE_USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO users (userid, username, email, password, active, roles) VALUES
(1, 'user', 'user@boiler.fr', '{bcrypt}$2a$10$mQkBOQLbXGncAojWzUglFeGcsK/IgbuHaBepOhHUWHpoCG5eR.pPK', 1, 'ROLE_USER'),
(2, 'admin', 'admin@plate.ch', '{bcrypt}$2a$10$tPh0/2HSOi7r6.x4NZqLweaiJwpHaSLu7KVmPkB4Zr1cNuTqSgjsm', 1, 'ROLE_ADMIN');

CREATE TABLE customers (
  customerid int(11) PRIMARY KEY AUTO_INCREMENT,
  username varchar(255) UNIQUE,
  email varchar(255) UNIQUE,
  password varchar(255) NOT NULL,
  active tinyint(1) DEFAULT 0,
  created datetime DEFAULT NULL,
  updated datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO customers (username, email, password) VALUES
('c1', 'c1@boiler.fr', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c2', 'c2@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c3', 'c3@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c4', 'c4@boiler.fr', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c5', 'c5@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c6', 'c6@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c7', 'c7@boiler.fr', '$2a$12$RYvIjo9UwCT6BJ8dHiUcpuC93lIZJBf0SMfmnzIKf3EJcQGx4Fgsy'),
('c8', 'c8@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c9', 'c9@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c10', 'c10@boiler.fr', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c11', 'c11@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c12', 'c12@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c13', 'c13@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c14', 'c14@boiler.fr', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c15', 'c15@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c16', 'c16@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c17', 'c17@boiler.fr', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c18', 'c18@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c19', 'c19@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c20', 'c20@boiler.fr', '$2a$12$RYvIjo9UwCT6BJ8dHiUcpuC93lIZJBf0SMfmnzIKf3EJcQGx4Fgsy'),
('c21', 'c21@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c22', 'c22@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c23', 'c23@boiler.fr', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c24', 'c24@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c25', 'c25@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K'),
('c26', 'c26@plate.ch', '$2a$10$e0paOUnsIcpJRUv6foDvsu75o29zHFXSfcLdVGuyiGjcYikhDzA3K');

CREATE TABLE tokens (
  tokenid int(11) PRIMARY KEY AUTO_INCREMENT,
  customerid int(11) NOT NULL,
  token varchar(255) UNIQUE,
  expires datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE tokens ADD CONSTRAINT tokens_fk1 FOREIGN KEY (customerid)
REFERENCES customers (customerid);

CREATE TABLE items (
  itemid int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  title varchar(255) NOT NULL,
  description text NOT NULL,
  price double NOT NULL,
  locked tinyint(1) DEFAULT 0,
  created datetime DEFAULT NULL,
  updated datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO items (title, description, price) VALUES
('Sunglasses', 'Ergonomic orange sunglasses for skating or biking', 42.95),
('Sed diam', 'Sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 42.95),
('Labore', 'Labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 2.95),
('At vero eos et accusam', 'Sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rconsetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 89.95),
('Stet clita', 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 7.00),
('Consetetur', 'Ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 11.95),
('Invidunt', 'Invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 88.38),
('At vero eos', 'Amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 102.95),
('Dolor sit amet', 'Dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr.', 1000.00),
('At vero eos et accusam', 'Et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 107.00),
('Elitr', 'Elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 0.95),
('Sadipscing', 'Sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 1074.0),
('Justo', 'Justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 3.33),
('Accusam et justo', 'Accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 99.99),
('Amet', 'Amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat.', 2.95),
('Diam nonumy', 'Diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 2.95),
('Kasd gubergren', 'Duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 24.95),
('Dolores et ea rebum', 'Dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 24.95),
('Eos et accusam', 'Eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 98.98),
('Stet clita kasd gubergren', 'Consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor.', 32.00),
('Lorem ipsum dolor sit amet', 'Et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 55.55),
('Sea takimata sanctus', 'Rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, iam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 42.95),
('Rebum', 'Clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.  At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 42.95),
('Nonumy eirmod tempor invidunt', 'Nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 77.77),
('Magna aliquyam eratm', 'Magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 879.00),
('Lorem ipsum', 'Clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 42.95),
('Helmet', 'Resizable soft helmet for all heads', 75.95);

CREATE TRIGGER customer_created_now BEFORE INSERT ON customers FOR EACH ROW
SET NEW.created = NOW();
CREATE TRIGGER customer_updated_now BEFORE UPDATE ON customers FOR EACH ROW
SET NEW.updated = NOW();
CREATE TRIGGER customer_lcase_insert BEFORE INSERT ON customers FOR EACH ROW
SET NEW.email = LOWER(NEW.email);
CREATE TRIGGER customer_lcase_update BEFORE UPDATE ON customers FOR EACH ROW
SET NEW.email = LOWER(NEW.email);
CREATE TRIGGER customer_inactive BEFORE INSERT ON customers FOR EACH ROW
SET NEW.active = 0;
CREATE TRIGGER user_lcase_insert BEFORE INSERT ON users FOR EACH ROW
SET NEW.email = LOWER(NEW.email);
CREATE TRIGGER user_lcase_update BEFORE UPDATE ON users FOR EACH ROW
SET NEW.email = LOWER(NEW.email);
CREATE TRIGGER user_inactive BEFORE INSERT ON users FOR EACH ROW
SET NEW.active = 0;
CREATE TRIGGER item_created_now BEFORE INSERT ON items FOR EACH ROW
SET NEW.created = NOW();
CREATE TRIGGER item_updated_now BEFORE UPDATE ON items FOR EACH ROW
SET NEW.updated = NOW();
CREATE TRIGGER expires_in2h BEFORE INSERT ON tokens FOR EACH ROW
SET NEW.expires = NOW() + INTERVAL 2 HOUR;

COMMIT;

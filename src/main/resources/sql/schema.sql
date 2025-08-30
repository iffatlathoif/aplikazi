CREATE TABLE IF NOT EXISTS users
(
    id                  CHAR(36) PRIMARY KEY,
    username            VARCHAR(20)  NOT NULL,
    email               VARCHAR(100) NOT NULL,
    password            VARCHAR(100)  NOT NULL,
    last_login          DATETIME,
    enabled             BOOLEAN      NOT NULL default false,
    account_non_expired BOOLEAN      NOT NULL,
    account_non_locked  BOOLEAN      NOT NULL,
    login_attempts      INTEGER      NOT NULL DEFAULT 0,
    created_at          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by          VARCHAR(20)  NOT NULL,
    updated_at          TIMESTAMP             DEFAULT NULL,
    updated_by          VARCHAR(20)           DEFAULT NULL,
    UNIQUE KEY unique_username (username),
    UNIQUE KEY unique_email (email)
);

CREATE TABLE IF NOT EXISTS roles
(
    id         CHAR(36) PRIMARY KEY,
    name       VARCHAR(255)                          NOT NULL,
    created_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by VARCHAR(20)                           NOT NULL,
    updated_at TIMESTAMP   DEFAULT NULL,
    updated_by VARCHAR(20) DEFAULT NULL,
    CONSTRAINT check_name CHECK ( name in ('ADMIN', 'STAFF', 'MEMBER') ),
    UNIQUE KEY unique_name (name)
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id CHAR(36) NOT NULL,
    role_id CHAR(36) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS members
(
    id         CHAR(36) PRIMARY KEY,
    user_id    CHAR(36)    DEFAULT NULL,
    first_name VARCHAR(100)                          NOT NULL,
    last_name  VARCHAR(100)                          NOT NULL,
    address    VARCHAR(255)                          NOT NULL,
    status     VARCHAR(50)                           NOT NULL,
    join_at    DATETIME                              NOT NULL,
    expired_at DATETIME                              NOT NULL,
    created_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by VARCHAR(20)                           NOT NULL,
    updated_at TIMESTAMP   DEFAULT NULL,
    updated_by VARCHAR(20) DEFAULT NULL,
    CONSTRAINT check_status CHECK ( status in ('ACTIVE', 'INACTIVE') ),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO roles (id, name, created_at, created_by)
VALUES (UUID(), 'ADMIN', CURRENT_TIMESTAMP, 'DBA');

INSERT INTO roles (id, name, created_at, created_by)
VALUES (UUID(), 'STAFF', CURRENT_TIMESTAMP, 'DBA');

INSERT INTO roles (id, name, created_at, created_by)
VALUES (UUID(), 'MEMBER', CURRENT_TIMESTAMP, 'DBA');

INSERT INTO users (id, username, email, password, account_non_expired, account_non_locked, created_at, created_by)
VALUES (UUID(), 'admin', 'admin@gmail.com', '$2b$12$uvKlEH4J3UwLjxGYRGaTMe6gk09QYQTO4acvdhrfxGeNsNP3OyLoe', true, true,
        CURRENT_TIMESTAMP, 'DBA');

INSERT INTO user_roles(user_id, role_id)
VALUES('0f52c25e-8575-11f0-a00a-d85ed399317e', '93a67360-855c-11f0-a00a-d85ed399317e')

DROP TABLE IF EXISTS health;

CREATE TABLE health
(
    id INT PRIMARY KEY,
    up BOOLEAN
);

INSERT INTO health
    (id, up)
VALUES (1, true);

CREATE TABLE IF NOT EXISTS authorized_user (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id UUID          NOT NULL,
    name VARCHAR(100)     NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255)    NOT NULL UNIQUE,
    rol VARCHAR(50)       NOT NULL,
    status BIGINT         NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_authorized_user_id ON authorized_user(user_id);
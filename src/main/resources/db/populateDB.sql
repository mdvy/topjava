DELETE FROM user_role;
DELETE  FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories)
VALUES (100000, '2024-01-01 08:00:00', 'завтрак USER', 600),
       (100000, '2024-01-01 13:00:00', 'обед USER', 800),
       (100000, '2024-01-01 18:00:00', 'ужин USER', 700),
       (100000, '2024-01-02 08:00:00', 'завтрак USER', 600),
       (100000, '2024-01-02 13:00:00', 'обед USER', 900),
       (100000, '2024-01-02 18:00:00', 'ужин USER', 800),
       (100001, '2024-01-01 08:00:00', 'завтрак ADMIN', 300),
       (100001, '2024-01-01 13:00:00', 'обед ADMIN', 500),
       (100001, '2024-01-01 18:00:00', 'ужин ADMIN', 1200),
       (100001, '2024-01-02 08:00:00', 'завтрак ADMIN', 400),
       (100001, '2024-01-02 13:00:00', 'обед ADMIN', 600),
       (100001, '2024-01-02 18:00:00', 'ужин ADMIN', 1300)
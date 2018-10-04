DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM menu;
DELETE FROM restaurants;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 10000;

INSERT INTO users (name, email, password) VALUES
  ('User0', 'user0@yandex.ru', '{noop}password0'),
  ('User1', 'user1@yandex.ru', '{noop}password1'),
  ('User2', 'user2@yandex.ru', '{noop}password2'),
  ('User3', 'user3@yandex.ru', '{noop}password3'),
  ('User4', 'user4@yandex.ru', '{noop}password4'),
  ('User5', 'user5@yandex.ru', '{noop}password5'),
  ('User6', 'user6@yandex.ru', '{noop}password6'),
  ('User7', 'user7@yandex.ru', '{noop}password7'),
  ('User8', 'user8@yandex.ru', '{noop}password8'),
  ('User9', 'user9@yandex.ru', '{noop}password9'),
  ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 10000),
  ('ROLE_USER', 10001),
  ('ROLE_USER', 10002),
  ('ROLE_USER', 10003),
  ('ROLE_USER', 10004),
  ('ROLE_USER', 10005),
  ('ROLE_USER', 10006),
  ('ROLE_USER', 10007),
  ('ROLE_USER', 10008),
  ('ROLE_USER', 10009),
  ('ROLE_ADMIN', 10010),
  ('ROLE_USER', 10010);

INSERT INTO restaurants (name) VALUES
  ('Столовая №1'),
  ('Shaverma Cafe'),
  ('Бургерная'),
  ('Блинная'),
  ('Едим дома');

INSERT INTO menu (dish, price, restaurant_id) VALUES
  --Столовая
  ('Борщ', 100, 10011),
  ('Солянка', 110, 10011),
  ('Котлета', 140, 10011),
  ('Макароны', 50, 10011),
  ('Компот', 30, 10011),
  --Шавермятная
  ('Шаверма', 120, 10012),
  ('Шаурма', 130, 10012),
  ('Кебаб', 200, 10012),
  ('Doner kebab', 300, 10012),
  ('Попить', 60, 10012),
  --Бургерная
  ('Small mac', 120, 10013),
  ('Small king', 120, 10013),
  ('Box mister', 150, 10013),
  ('Картошка фри', 150, 10013),
  ('Кола 0.5', 100, 10013),
  --Блинная
  ('Блин', 50, 10014),
  ('Сгущенка', 20, 10014),
  ('Мясо', 100, 10014),
  ('Творог', 30, 10014),
  ('Квас 0,5', 50, 10014),
  --Едим дома
  ('Двухэтажный особняк', 300, 10015),
  ('Хрущевка пятиэтажная', 200, 10015),
  ('Брежневка девятиэтажная', 240, 10015),
  ('Домик в деревне', 120, 10015),
  ('Двухэтажка', 150, 10015);

INSERT INTO votes (user_id, restaurant_id) VALUES
  (10010, 10015),
  (10001, 10011),
  (10002, 10011),
  (10003, 10011),
  (10004, 10011),
  (10005, 10011),
  (10006, 10014),
  (10007, 10014),
  (10008, 10014),
  (10009, 10013);
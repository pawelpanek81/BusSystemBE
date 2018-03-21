
-- INSERT ACCOUNTS
INSERT INTO accounts
VALUES (101, FALSE, 'person@mail.pl', 'ExampleName', 'passPerson1', '123456789', NULL, 'ExampleSurname', 'username1');

INSERT INTO accounts
VALUES (102, TRUE, 'admin@mail.pl', 'AdminExampleName', 'passAdmin1', '987654321', NULL, 'AdminExampleSurname', 'admin1');

INSERT INTO accounts
VALUES (103, TRUE, 'bok@mail.pl', 'BOKExampleName', 'passBok1', NULL , NULL, 'BokExampleSurname', 'bok1');

INSERT INTO accounts
VALUES (104, TRUE, 'driver@mail.pl', 'DriverExampleName', 'passDriver1', NULL , NULL, 'DriverExampleSurname', 'driver1');

INSERT INTO accounts
VALUES (105, TRUE, 'user@mail.pl', 'UserExampleName', 'passUser1', '123123123', NULL, 'UserExampleSurname', 'user1');


-- INSERT AUTHORITIES
INSERT INTO authorities VALUES (111, 'group:User', 101);
INSERT INTO authorities VALUES (112, 'group:Admin', 102);
INSERT INTO authorities VALUES (113, 'group:BOK', 103);
INSERT INTO authorities VALUES (114, 'group:Driver', 104);
INSERT INTO authorities VALUES (115, 'group:User', 105);

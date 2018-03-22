INSERT INTO public.accounts(active, email, name, password, phone, photo, surname, username) VALUES  (TRUE, 'admin@admin.pl', 'Jan', '$2a$04$epeg1B52UFs0iBLleXk9y.O2R93KRjRA4XljnE0zIes2kVnpOAXSa', '123123123', '', 'Kowalski', 'admin');
INSERT INTO public.authorities(authority, account) VALUES ('ROLE_BOK', (SELECT id FROM accounts WHERE username='admin'));

INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-08-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-04-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2018-12-30T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2008-01-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);


-- INSERT ACCOUNTS
INSERT INTO public.accounts(active, email, name, password, phone, photo, surname, username) VALUES (FALSE, 'person@mail.pl', 'ExampleName', '$2a$04$6mleckTHMnntW5HRVken5us8nmCA1LcSLDnd.nJztS8.enBy65XRa', '123456789', NULL, 'ExampleSurname', 'username1');
INSERT INTO public.accounts(active, email, name, password, phone, photo, surname, username) VALUES (TRUE, 'admin@mail.pl', 'AdminExampleName', '$2a$04$B6JubabOiHHMMfGPxfiiter2lHkNt4r5UqS.1vEHRTIYy5fV.nghe', '987654321', NULL, 'AdminExampleSurname', 'admin1');
INSERT INTO public.accounts(active, email, name, password, phone, photo, surname, username) VALUES (TRUE, 'bok@mail.pl', 'BOKExampleName', '$2a$04$b69EQ87IsSiKhpUXnY36eezuM3lF7aOliFtNQId/Ji4vPOWXb7BOe', NULL , NULL, 'BokExampleSurname', 'bok1'); --passBok1
INSERT INTO public.accounts(active, email, name, password, phone, photo, surname, username) VALUES (TRUE, 'driver@mail.pl', 'DriverExampleName', '$2a$04$bZtA9GZVkWfh84NpJiJ5qeWlFQgEL6IZ8C8XpCAgdsT6.ZeXyQUTC', NULL , NULL, 'DriverExampleSurname', 'driver1');
INSERT INTO public.accounts(active, email, name, password, phone, photo, surname, username) VALUES (TRUE, 'user@mail.pl', 'UserExampleName', '$2a$04$uaFESGU/6kZNb/PhnNISlO1tgsXipwMLAUuCijgZ1E3XdOhLgmfVe', '123123123', NULL, 'UserExampleSurname', 'user1');


-- INSERT AUTHORITIES
INSERT INTO public.authorities(authority, account) VALUES ('ROLE_USER', (SELECT id FROM accounts WHERE username='username1'));
INSERT INTO public.authorities(authority, account) VALUES ('ROLE_ADMIN', (SELECT id FROM accounts WHERE username='admin1'));
INSERT INTO public.authorities(authority, account) VALUES ('ROLE_BOK', (SELECT id FROM accounts WHERE username='bok1'));
INSERT INTO public.authorities(authority, account) VALUES ('ROLE_DRIVER', (SELECT id FROM accounts WHERE username='driver1'));
INSERT INTO public.authorities(authority, account) VALUES ('ROLE_USER', (SELECT id FROM accounts WHERE username='user1'));

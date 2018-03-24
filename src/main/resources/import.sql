INSERT INTO public.accounts(active, email, name, password, phone, photo, surname, username) VALUES  (TRUE, 'admin@admin.pl', 'Jan', '$2a$04$epeg1B52UFs0iBLleXk9y.O2R93KRjRA4XljnE0zIes2kVnpOAXSa', '123123123', '', 'Kowalski', 'admin');
INSERT INTO public.authorities(authority, account) VALUES ('ROLE_BOK', (SELECT id FROM accounts WHERE username='admin'));

INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu1', '2015-09-01T16:34:02', 'tytul1', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu2', '2015-08-01T16:34:02', 'tytul2', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu3', '2015-09-01T16:34:02', 'tytul3', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu4', '2015-04-01T16:34:02', 'tytul4', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu5', '2015-09-01T16:34:02', 'tytul5', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu6', '2015-09-01T16:34:02', 'tytul6', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu7', '2018-12-30T16:34:02', 'tytul7', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu8', '2015-09-01T16:34:02', 'tytul8', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu9', '2015-09-01T16:34:02', 'tytul9', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu10', '2015-09-01T16:34:02', 'tytul10', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu11', '2015-09-01T16:34:02', 'tytul11', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu12', '2015-09-01T16:34:02', 'tytul12', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu13', '2015-09-01T16:34:02', 'tytul13', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu14', '2015-09-01T16:34:02', 'tytul14', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu15', '2015-09-01T16:34:02', 'tytul15', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu16', '2008-01-01T16:34:02', 'tytul16', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu17', '2015-09-01T16:34:02', 'tytul17', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu18', '2015-09-01T16:34:02', 'tytul18', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu19', '2015-09-01T16:34:02', 'tytul19', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu20', '2015-09-01T16:34:02', 'tytul20', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu21', '2015-09-01T16:34:02', 'tytul21', 1);
INSERT INTO public.news(body, date_time, title, author) VALUES ('cialo artykulu22', '2015-09-01T16:34:02', 'tytul22', 1);


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


INSERT INTO public.bus_stops(city, name, latitude, longitude, address) VALUES ('Tarnobrzeg', 'Dworzec Autobusowy', '50.566024', '21.667687', 'Adama Mickiewicza 40');
INSERT INTO public.bus_stops(city, name, latitude, longitude, address) VALUES ('Nowa Dęba', 'Centrum przystanek 03', '50.413533', '21.754224', 'ul. Rzeszowska 6');
INSERT INTO public.bus_stops(city, name, latitude, longitude, address) VALUES ('Majdan królewski', 'Urząd gminy', '50.376637', '21.746139', 'Plac Rynek 1a');
INSERT INTO public.bus_stops(city, name, latitude, longitude, address) VALUES ('Kolbuszowa', 'Dworzec Główny', '50.246611', '21.783309', 'ks. Ruczki 1');
INSERT INTO public.bus_stops(city, name, latitude, longitude, address) VALUES ('Komorów', 'Kościół 01', '50.350856', '21.727866', null);
INSERT INTO public.bus_stops(city, name, latitude, longitude, address) VALUES ('Komorów', 'Kościół 02', '50.351728', '21.726752', null);
INSERT INTO public.bus_stops(city, name, latitude, longitude, address) VALUES ('Rzeszów', 'Dworzec Główny', '50.042307', '22.003255', 'Artura Grottgera 1');


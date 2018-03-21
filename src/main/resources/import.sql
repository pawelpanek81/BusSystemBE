INSERT INTO public.accounts(id, active, email, name, password, phone, photo, surname, username) VALUES  (1, TRUE, 'admin@admin.pl', 'Jan', '$2a$04$epeg1B52UFs0iBLleXk9y.O2R93KRjRA4XljnE0zIes2kVnpOAXSa', '123123123', '', 'Kowalski', 'admin');
INSERT INTO public.authorities(id, authority, account) VALUES (1, 'group:BOK', 1);

INSERT INTO public.news(id, body, date_time, title, author) VALUES (1, 'cialo artykulu', '2015-09-01T16:34:02', 'tytul', 1);

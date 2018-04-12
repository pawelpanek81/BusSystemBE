-- ACCOUNTS & AUTHORITIES
INSERT INTO public.accounts(id, username, password, name, surname, email, phone, photo, active)
  VALUES  (1, 'admin', '$2a$04$epeg1B52UFs0iBLleXk9y.O2R93KRjRA4XljnE0zIes2kVnpOAXSa',
           'Janusz', 'Polak', 'admin@januszpol.pl', '783956819', null, TRUE);
INSERT INTO public.authorities(id, authority, account)
  VALUES (1, 'ROLE_ADMIN', 1);


INSERT INTO public.accounts(id, username, password, name, surname, email, phone, photo, active)
  VALUES  (2, 'bok', '$2a$04$epeg1B52UFs0iBLleXk9y.O2R93KRjRA4XljnE0zIes2kVnpOAXSa',
         'Grażyna', 'Żarko', 'bok@januszpol.pl', '799392286', null, TRUE);
INSERT INTO public.authorities(id, authority, account)
  VALUES (2, 'ROLE_BOK', 2);

INSERT INTO public.accounts(id, username, password, name, surname, email, phone, photo, active)
  VALUES  (3, 'driver1', '$2a$04$epeg1B52UFs0iBLleXk9y.O2R93KRjRA4XljnE0zIes2kVnpOAXSa',
         'Janusz', 'Kierowca', 'driver1@januszpol.pl', '729209855', null, TRUE);
INSERT INTO public.authorities(id, authority, account)
  VALUES (3, 'ROLE_DRIVER', 3);

INSERT INTO public.accounts(id, username, password, name, surname, email, phone, photo, active)
  VALUES  (4, 'driver2', '$2a$04$epeg1B52UFs0iBLleXk9y.O2R93KRjRA4XljnE0zIes2kVnpOAXSa',
         'Pioter', 'Jeżdżący', 'driver2@januszpol.pl', '728733816', null, TRUE);
INSERT INTO public.authorities(id, authority, account)
  VALUES (4, 'ROLE_DRIVER', 4);

INSERT INTO public.accounts(id, username, password, name, surname, email, phone, photo, active)
  VALUES  (5, 'user1', '$2a$04$epeg1B52UFs0iBLleXk9y.O2R93KRjRA4XljnE0zIes2kVnpOAXSa',
         'Maciej', 'Kozioł', 'maciej.k@o2.pl', '739323257', null, TRUE);
INSERT INTO public.authorities(id, authority, account)
  VALUES (5, 'ROLE_DRIVER', 5);

INSERT INTO public.accounts(id, username, password, name, surname, email, phone, photo, active)
  VALUES  (6, 'user2', '$2a$04$epeg1B52UFs0iBLleXk9y.O2R93KRjRA4XljnE0zIes2kVnpOAXSa',
         'Jan', 'Świderski', 'jan.swiderski@buziaczek.pl', '883287189', null, TRUE);
INSERT INTO public.authorities(id, authority, account)
  VALUES (6, 'ROLE_DRIVER', 6);

INSERT INTO public.accounts(id, username, password, name, surname, email, phone, photo, active)
VALUES  (7, 'user3', '$2a$04$epeg1B52UFs0iBLleXk9y.O2R93KRjRA4XljnE0zIes2kVnpOAXSa',
         'Andrzej', 'Nowoczesny', 'andrzej.nowoczesny@onet.pl', '785213489', null, FALSE);

-- NEWS
INSERT INTO public.news(id, title, body, author, date_time)
  VALUES (1, 'Litwo! Ojczyzno moja!', 'Ty jesteś jak zdrowie. Ile cię trzeba cenić, ten odwiązywać, składać. Właśnie tym obrazem. Właśnie dwukonną bryką wjechał młody panek i zdrowie. Nazywał się długo dumał, nim stał dwór szlachecki, z liczby kopic, co dzień za dowód', 1, '2008-01-01T16:06:09');

INSERT INTO public.news(id, title, body, author, date_time)
  VALUES (2, 'Zresztą zdać się na przeciwnej szali.', 'Zwłaszcza gdy ów Wespazyjanus nie może. Widać, że za dozorcę księdza, który ma narowu, Żałował, że odbite od Nil szła rzecz o jej był to mówiąc, że polskie ubrani nagotowane z nim widzi sprzęty, też same szczypiąc trawę ciągnęły powoli pod Napoleonem, demokrata przyjechał pan Podkomorzy i jakoby zlewa. I tak rzadka nowina! ', 1, '2008-01-02T18:20:13');

INSERT INTO public.news(id, title, body, author, date_time)
  VALUES (3, 'Pańskiej cioci. Choć Sędzia wie, jak gwiazdy', 'Hrabia ma żądło w tabakierkę złotą Podkomorzy i knieje więc ja Ruski, teraz jeśli zechcesz, i mniej był legijonistą przynosił kości stare na konikach małe dziecię, kiedy mamy panien wiele. Stryjaszek myśli wkrótce sprawić ci znowu o czyjeś kolana pośliznęła się stało wody pełne naczynie blaszane ale myśl wcale aby się do stodoły a starzy i mniej trudnych i gestami ją nudzi rzecz długa, choć świadka nie skąpił. On za domem', 1, '2009-11-30T23:33:59');

INSERT INTO public.news(id, title, body, author, date_time)
VALUES (4, 'Kapelusz', 'Czasem odłożenie pracy na później nie przynosi szkody. Lecz w wypadku baobabu jest to zawsze katastrofą.', 1, '2018-01-01T08:11:55');


-- BUS STOPS
INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (1, 'Tarnobrzeg', 'Dworzec Autobusowy', '50.566024', '21.667687', 'Adama Mickiewicza 40');

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (2, 'Jadachy', 'Przystanek 01', '50.485950', '21.685111', null);

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (3, 'Jadachy', 'Przystanek 02', '50.484300', '21.689434', null);

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (4, 'Tarnowska wola', 'Przystanek 01', '50.450813', '21.743164', null);

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (5, 'Tarnowska wola', 'Przystanek 02', '50.455060', '21.738029', null);

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (6, 'Nowa Dęba', 'Centrum przystanek 03', '50.413533', '21.754224', 'ul. Rzeszowska 6');

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (7, 'Majdan królewski', 'Urząd gminy', '50.376637', '21.746139', 'Plac Rynek 1a');

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (8, 'Kolbuszowa', 'Dworzec Główny', '50.246611', '21.783309', 'ks. Ruczki 1');

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (9, 'Komorów', 'Kościół 02', '50.350856', '21.727866', null);

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (10, 'Komorów', 'Kościół 01', '50.351728', '21.726752', null);

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (11, 'Cmolas', 'Przystanek 01', '50.293637', '21.745491', null);

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (12, 'Cmolas', 'Przystanek 02', '50.294474', '21.744843', null);

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (13, 'Rzeszów', 'Dworzec Główny', '50.042307', '22.003255', 'Artura Grottgera 1');

-- BUS LINES
INSERT INTO public.bus_lines(id, drive_time, name, drive_from, destination)
  VALUES (1, 60, 'L1', 1, 13);

INSERT INTO public.bus_lines(id, drive_time, name, drive_from, destination)
  VALUES (2, 60, 'L2', 13, 1);

-- LINE ROUTES
-- dla L1 (Tarnobrzeg -> Rzeszów)
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
  VALUES (1, 10, 2, 1, 3);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
  VALUES (2, 15, 3, 1, 5);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
  VALUES (3, 20, 4, 1, 6);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
  VALUES (4, 25, 5, 1, 7);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
  VALUES (5, 35, 6, 1, 8);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
  VALUES (6, 40, 7, 1, 9);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
  VALUES (7, 45, 8, 1, 11);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
  VALUES (8, 50, 9, 1, 12);

-- dla L2 (Rzeszów -> Tarnobrzeg)
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
  VALUES (16, 50, 2, 2, 2);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
VALUES (15, 45, 3, 2, 4);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
VALUES (14, 40, 4, 2, 6);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
VALUES (13, 35, 5, 2, 7);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
VALUES (12, 25, 6, 2, 8);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
VALUES (11, 20, 7, 2, 10);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
VALUES (10, 15, 8, 2, 11);
INSERT INTO public.lines_routes(id, drive_time, sequence, bus_line, bus_stop)
VALUES (9, 10, 9, 2, 12);

--SCHEDULES
-- dla L1 Tarnobrzeg -> Rzeszów
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (1, '1-7', true, '8:00', 1);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (2, '1-7', true, '9:00', 1);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (3, '1-7', true, '10:00', 1);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (4, '1-7', true, '11:00', 1);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (5, '1-7', true, '12:00', 1);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (6, '1-7', true, '13:00', 1);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (7, '1-7', true, '14:00', 1);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (8, '1-7', true, '15:00', 1);

-- dla L1 Rzeszów -> Tarnobrzeg
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (9, '1-7', true, '8:00', 2);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (10, '1-7', true, '9:00', 2);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (11, '1-7', true, '10:00', 2);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (12, '1-7', true, '11:00', 2);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (13, '1-7', true, '12:00', 2);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (14, '1-7', true, '13:00', 2);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (15, '1-7', true, '14:00', 2);
INSERT INTO public.schedules(id, code, enabled, start_hour, bus_line)
  VALUES (16, '1-7', true, '15:00', 2);


-- EXPERIMENTAL TICKETS
INSERT INTO public.buses(
  id, brand, model, registration_number, seats)
  VALUES (1, 'Mercedes', 'Sprinter', 'RTA XD1337', 15);
INSERT INTO public.bus_rides(
  id, end_date_time, start_date_time, bus, bus_line, primary_driver, secondary_driver)
VALUES (1, '2018-04-30T08:00:00', '2018-04-30T09:00:00', 1, 1, 3, null);

INSERT INTO public.tickets(
  id, date_time, email, name, paid, phone, price, returned, surname, bus_ride, user_account)
VALUES (1, '2018-04-20T12:00:00', 'test@test.pl', 'Jan', false, '123-123-123', 30.15, false, 'Kowalski', 1, null);
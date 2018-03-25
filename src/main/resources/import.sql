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



INSERT INTO public.news(id, title, body, author, date_time)
  VALUES (1, 'Litwo! Ojczyzno moja!', 'Ty jesteś jak zdrowie. Ile cię trzeba cenić, ten odwiązywać, składać. Właśnie tym obrazem. Właśnie dwukonną bryką wjechał młody panek i zdrowie. Nazywał się długo dumał, nim stał dwór szlachecki, z liczby kopic, co dzień za dowód', 1, '2008-01-01T16:06:09');

INSERT INTO public.news(id, title, body, author, date_time)
  VALUES (2, 'Zresztą zdać się na przeciwnej szali.', 'Zwłaszcza gdy ów Wespazyjanus nie może. Widać, że za dozorcę księdza, który ma narowu, Żałował, że odbite od Nil szła rzecz o jej był to mówiąc, że polskie ubrani nagotowane z nim widzi sprzęty, też same szczypiąc trawę ciągnęły powoli pod Napoleonem, demokrata przyjechał pan Podkomorzy i jakoby zlewa. I tak rzadka nowina! ', 1, '2008-01-02T18:20:13');

INSERT INTO public.news(id, title, body, author, date_time)
  VALUES (3, 'Pańskiej cioci. Choć Sędzia wie, jak gwiazdy', 'Hrabia ma żądło w tabakierkę złotą Podkomorzy i knieje więc ja Ruski, teraz jeśli zechcesz, i mniej był legijonistą przynosił kości stare na konikach małe dziecię, kiedy mamy panien wiele. Stryjaszek myśli wkrótce sprawić ci znowu o czyjeś kolana pośliznęła się stało wody pełne naczynie blaszane ale myśl wcale aby się do stodoły a starzy i mniej trudnych i gestami ją nudzi rzecz długa, choć świadka nie skąpił. On za domem', 1, '2009-11-30T23:33:59');

INSERT INTO public.news(id, title, body, author, date_time)
VALUES (4, 'Kapelusz', 'Czasem odłożenie pracy na później nie przynosi szkody. Lecz w wypadku baobabu jest to zawsze katastrofą.', 1, '2018-01-01T08:11:55');



INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (1, 'Tarnobrzeg', 'Dworzec Autobusowy', '50.566024', '21.667687', 'Adama Mickiewicza 40');

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (2, 'Nowa Dęba', 'Centrum przystanek 03', '50.413533', '21.754224', 'ul. Rzeszowska 6');

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (3, 'Majdan królewski', 'Urząd gminy', '50.376637', '21.746139', 'Plac Rynek 1a');

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (4, 'Kolbuszowa', 'Dworzec Główny', '50.246611', '21.783309', 'ks. Ruczki 1');

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (5, 'Komorów', 'Kościół 01', '50.350856', '21.727866', null);

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (6, 'Komorów', 'Kościół 02', '50.351728', '21.726752', null);

INSERT INTO public.bus_stops(id, city, name, latitude, longitude, address)
  VALUES (7, 'Rzeszów', 'Dworzec Główny', '50.042307', '22.003255', 'Artura Grottgera 1');


--Actualizacion de la version 8.0.2 a la version 8.0.3

-- Tablas

-- Tabla para mapeo de los c�digos de tipo de transporte de la normativa de intercambio
-- registal de la normativa sicres 3 con la tabla SCR_TT.
CREATE TABLE scr_ttexreg (
    id integer NOT NULL,
    code varchar2(10) NOT NULL,
    name varchar2(31) NOT NULL,
    id_scr_tt integer not null
);

-- Secuencias

-- Secuencia para el identificador de la tabla scr_ttexreg
CREATE SEQUENCE scr_ttexreg_id_seq
  INCREMENT BY 1;


-- Constraints

-- TABLA scr_tt
ALTER TABLE scr_tt ADD CONSTRAINT pk_scr_tt0 PRIMARY KEY (id);
CREATE UNIQUE INDEX scr_tt0 ON scr_tt (transport) tablespace SIGM_I;

-- TABLA scr_ttexreg
CREATE UNIQUE INDEX scr_ttexreg0 ON scr_ttexreg (name) tablespace SIGM_I;
ALTER TABLE scr_ttexreg ADD CONSTRAINT pk_scr_ttexreg0 PRIMARY KEY (id);
ALTER TABLE scr_ttexreg ADD CONSTRAINT fk_scr_ttexreg0 FOREIGN KEY (id_scr_tt) REFERENCES scr_tt(id) ON DELETE CASCADE;

--Actualizacion de la version 8.0.2 a la version 8.0.3 M4

-- Tablas

-- Nueva tabla mapeo country para intercambio registral con la tabla scr_country
CREATE TABLE scr_countryexreg(
    id integer NOT NULL,
    code varchar(4) NOT NULL,
    name varchar(80) NOT NULL,
    id_country integer NOT NULL
);

-- Secuencias

-- Constraints

-- TABLA scr_country
ALTER TABLE scr_country ADD CONSTRAINT pk_scr_country0 PRIMARY KEY (id);

-- TABLA scr_dom
ALTER TABLE scr_dom ADD CONSTRAINT pk_scr_dom0 PRIMARY KEY (id);

-- TABLA scr_countryexreg
ALTER TABLE scr_countryexreg ADD CONSTRAINT pk_scr_countryexreg0 PRIMARY KEY (id);
CREATE UNIQUE INDEX uk_scr_countryexreg0 ON scr_countryexreg (code) tablespace SIGM_I;
CREATE UNIQUE INDEX uk_scr_countryexreg1 ON scr_countryexreg  (name) tablespace SIGM_I;
ALTER TABLE scr_countryexreg ADD CONSTRAINT fk_scr_countryexreg0 FOREIGN KEY (id_country) REFERENCES scr_country(id) ON DELETE CASCADE;

--Nuevos paises a partir de la version 8.0.3
INSERT INTO scr_country VALUES (241,localtimestamp,  'MM','Birmania');
INSERT INTO scr_country VALUES (242,localtimestamp,  'GG','Guernsey');
INSERT INTO scr_country VALUES (243,localtimestamp,  'IM','Isla de Man');
INSERT INTO scr_country VALUES (244,localtimestamp,  'JE','Jersey');
INSERT INTO scr_country VALUES (245,localtimestamp,  'ME','Montenegro');
INSERT INTO scr_country VALUES (246,localtimestamp,  'BL','San Bartolom�');
INSERT INTO scr_country VALUES (247,localtimestamp,  'MF','San Mart�n');
INSERT INTO scr_country VALUES (248,localtimestamp,  'RS','Serbia');

--Asociacion de los paises de sicres con los paises del DCO
INSERT INTO scr_countryexreg VALUES (1,'004','Afganist�n',1);
INSERT INTO scr_countryexreg VALUES (2,'764','Tailandia',211);
INSERT INTO scr_countryexreg VALUES (3,'248','�land',2);
INSERT INTO scr_countryexreg VALUES (4,'008','Albania',3);
INSERT INTO scr_countryexreg VALUES (5,'276','Alemania',4);
INSERT INTO scr_countryexreg VALUES (6,'020','Andorra',5);
INSERT INTO scr_countryexreg VALUES (7,'024','Angola',6);
INSERT INTO scr_countryexreg VALUES (8,'660','Anguila',7);
INSERT INTO scr_countryexreg VALUES (9,'010','Ant�rtida',8);
INSERT INTO scr_countryexreg VALUES (10,'028','Antigua y Barbuda',9);
INSERT INTO scr_countryexreg VALUES (11,'530','Antillas Neerlandesas',10);
INSERT INTO scr_countryexreg VALUES (12,'682','Arabia Saudita',11);
INSERT INTO scr_countryexreg VALUES (13,'012','Argelia',12);
INSERT INTO scr_countryexreg VALUES (14,'032','Argentina',13);
INSERT INTO scr_countryexreg VALUES (15,'051','Armenia',14);
INSERT INTO scr_countryexreg VALUES (16,'533','Aruba',15);
INSERT INTO scr_countryexreg VALUES (17,'036','Australia',16);
INSERT INTO scr_countryexreg VALUES (18,'040','Austria',17);
INSERT INTO scr_countryexreg VALUES (19,'275','Autoridad Nacional Palestina',169);
INSERT INTO scr_countryexreg VALUES (20,'031','Azerbaiy�n',18);
INSERT INTO scr_countryexreg VALUES (21,'044','Bahamas',19);
INSERT INTO scr_countryexreg VALUES (22,'050','Banglad�s',21);
INSERT INTO scr_countryexreg VALUES (23,'052','Barbados',22);
INSERT INTO scr_countryexreg VALUES (24,'048','Bar�in',20);
INSERT INTO scr_countryexreg VALUES (25,'056','B�lgica',24);
INSERT INTO scr_countryexreg VALUES (26,'084','Belice',25);
INSERT INTO scr_countryexreg VALUES (27,'204','Ben�n',26);
INSERT INTO scr_countryexreg VALUES (28,'060','Bermudas',27);
INSERT INTO scr_countryexreg VALUES (29,'112','Bielorrusia',23);
INSERT INTO scr_countryexreg VALUES (30,'104','Birmania',241);
INSERT INTO scr_countryexreg VALUES (31,'068','Bolivia',29);
INSERT INTO scr_countryexreg VALUES (32,'070','Bosnia y Herzegovina',30);
INSERT INTO scr_countryexreg VALUES (33,'072','Botsuana',31);
INSERT INTO scr_countryexreg VALUES (34,'076','Brasil',33);
INSERT INTO scr_countryexreg VALUES (35,'096','Brun�i',34);
INSERT INTO scr_countryexreg VALUES (36,'100','Bulgaria',35);
INSERT INTO scr_countryexreg VALUES (37,'854','Burkina Faso',36);
INSERT INTO scr_countryexreg VALUES (38,'108','Burundi',37);
INSERT INTO scr_countryexreg VALUES (39,'064','But�n',28);
INSERT INTO scr_countryexreg VALUES (40,'132','Cabo Verde',38);
INSERT INTO scr_countryexreg VALUES (41,'116','Camboya',40);
INSERT INTO scr_countryexreg VALUES (42,'120','Camer�n',41);
INSERT INTO scr_countryexreg VALUES (43,'124','Canad�',42);
INSERT INTO scr_countryexreg VALUES (44,'634','Catar',179);
INSERT INTO scr_countryexreg VALUES (45,'148','Chad',44);
INSERT INTO scr_countryexreg VALUES (46,'152','Chile',46);
INSERT INTO scr_countryexreg VALUES (47,'156','China',47);
INSERT INTO scr_countryexreg VALUES (48,'196','Chipre',48);
INSERT INTO scr_countryexreg VALUES (49,'336','Ciudad del Vaticano',50);
INSERT INTO scr_countryexreg VALUES (50,'170','Colombia',52);
INSERT INTO scr_countryexreg VALUES (51,'174','Comoras',53);
INSERT INTO scr_countryexreg VALUES (52,'408','Corea del Norte',57);
INSERT INTO scr_countryexreg VALUES (53,'410','Corea del Sur',58);
INSERT INTO scr_countryexreg VALUES (54,'384','Costa de Marfil',59);
INSERT INTO scr_countryexreg VALUES (55,'188','Costa Rica',60);
INSERT INTO scr_countryexreg VALUES (56,'191','Croacia',61);
INSERT INTO scr_countryexreg VALUES (57,'192','Cuba',62);
INSERT INTO scr_countryexreg VALUES (58,'208','Dinamarca',63);
INSERT INTO scr_countryexreg VALUES (59,'212','Dominica',64);
INSERT INTO scr_countryexreg VALUES (60,'218','Ecuador',66);
INSERT INTO scr_countryexreg VALUES (61,'818','Egipto',67);
INSERT INTO scr_countryexreg VALUES (62,'222','El Salvador',68);
INSERT INTO scr_countryexreg VALUES (63,'784','Emiratos �rabes Unidos',69);
INSERT INTO scr_countryexreg VALUES (64,'232','Eritrea',70);
INSERT INTO scr_countryexreg VALUES (65,'703','Eslovaquia',71);
INSERT INTO scr_countryexreg VALUES (66,'705','Eslovenia',72);
INSERT INTO scr_countryexreg VALUES (67,'724','Espa�a',73);
INSERT INTO scr_countryexreg VALUES (68,'840','Estados Unidos',75);
INSERT INTO scr_countryexreg VALUES (69,'233','Estonia',76);
INSERT INTO scr_countryexreg VALUES (70,'231','Etiop�a',77);
INSERT INTO scr_countryexreg VALUES (71,'608','Filipinas',79);
INSERT INTO scr_countryexreg VALUES (72,'246','Finlandia',80);
INSERT INTO scr_countryexreg VALUES (73,'242','Fiyi',81);
INSERT INTO scr_countryexreg VALUES (74,'250','Francia',82);
INSERT INTO scr_countryexreg VALUES (75,'266','Gab�n',83);
INSERT INTO scr_countryexreg VALUES (76,'270','Gambia',84);
INSERT INTO scr_countryexreg VALUES (77,'268','Georgia',85);
INSERT INTO scr_countryexreg VALUES (78,'288','Ghana',87);
INSERT INTO scr_countryexreg VALUES (79,'292','Gibraltar',88);
INSERT INTO scr_countryexreg VALUES (80,'308','Granada',89);
INSERT INTO scr_countryexreg VALUES (81,'300','Grecia',90);
INSERT INTO scr_countryexreg VALUES (82,'304','Groenlandia',91);
INSERT INTO scr_countryexreg VALUES (83,'312','Guadalupe',92);
INSERT INTO scr_countryexreg VALUES (84,'316','Guam',93);
INSERT INTO scr_countryexreg VALUES (85,'320','Guatemala',94);
INSERT INTO scr_countryexreg VALUES (86,'254','Guayana Francesa',95);
INSERT INTO scr_countryexreg VALUES (87,'831','Guernsey',242);
INSERT INTO scr_countryexreg VALUES (88,'324','Guinea',96);
INSERT INTO scr_countryexreg VALUES (89,'226','Guinea Ecuatorial',97);
INSERT INTO scr_countryexreg VALUES (90,'624','Guinea-Bissau',98);
INSERT INTO scr_countryexreg VALUES (91,'328','Guyana',99);
INSERT INTO scr_countryexreg VALUES (92,'332','Hait�',100);
INSERT INTO scr_countryexreg VALUES (93,'340','Honduras',102);
INSERT INTO scr_countryexreg VALUES (94,'344','Hong Kong',103);
INSERT INTO scr_countryexreg VALUES (95,'348','Hungr�a',104);
INSERT INTO scr_countryexreg VALUES (96,'356','India',105);
INSERT INTO scr_countryexreg VALUES (97,'360','Indonesia',106);
INSERT INTO scr_countryexreg VALUES (98,'368','Irak',108);
INSERT INTO scr_countryexreg VALUES (99,'364','Ir�n',107);
INSERT INTO scr_countryexreg VALUES (100,'372','Irlanda',109);
INSERT INTO scr_countryexreg VALUES (101,'074','Isla Bouvet',32);
INSERT INTO scr_countryexreg VALUES (102,'833','Isla de Man',243);
INSERT INTO scr_countryexreg VALUES (103,'162','Isla de Navidad',49);
INSERT INTO scr_countryexreg VALUES (104,'352','Islandia',110);
INSERT INTO scr_countryexreg VALUES (105,'136','Islas Caim�n',39);
INSERT INTO scr_countryexreg VALUES (106,'166','Islas Cocos',51);
INSERT INTO scr_countryexreg VALUES (107,'184','Islas Cook',56);
INSERT INTO scr_countryexreg VALUES (108,'234','Islas Feroe',78);
INSERT INTO scr_countryexreg VALUES (109,'239','Islas Georgias del Sur y Sandwich del Sur',86);
INSERT INTO scr_countryexreg VALUES (110,'334','Islas Heard y McDonald',101);
INSERT INTO scr_countryexreg VALUES (111,'238','Islas Malvinas',138);
INSERT INTO scr_countryexreg VALUES (112,'580','Islas Marianas del Norte',139);
INSERT INTO scr_countryexreg VALUES (113,'584','Islas Marshall',141);
INSERT INTO scr_countryexreg VALUES (114,'612','Islas Pitcairn',174);
INSERT INTO scr_countryexreg VALUES (115,'090','Islas Salom�n',186);
INSERT INTO scr_countryexreg VALUES (116,'796','Islas Turcas y Caicos',223);
INSERT INTO scr_countryexreg VALUES (117,'581','Islas ultramarinas de Estados Unidos',74);
INSERT INTO scr_countryexreg VALUES (118,'092','Islas V�rgenes Brit�nicas',234);
INSERT INTO scr_countryexreg VALUES (119,'850','Islas V�rgenes de los Estados Unidos',235);
INSERT INTO scr_countryexreg VALUES (120,'376','Israel',111);
INSERT INTO scr_countryexreg VALUES (121,'380','Italia',112);
INSERT INTO scr_countryexreg VALUES (122,'388','Jamaica',113);
INSERT INTO scr_countryexreg VALUES (123,'392','Jap�n',114);
INSERT INTO scr_countryexreg VALUES (124,'832','Jersey',244);
INSERT INTO scr_countryexreg VALUES (125,'400','Jordania',115);
INSERT INTO scr_countryexreg VALUES (126,'398','Kazajist�n',116);
INSERT INTO scr_countryexreg VALUES (127,'404','Kenia',117);
INSERT INTO scr_countryexreg VALUES (128,'417','Kirguist�n',118);
INSERT INTO scr_countryexreg VALUES (129,'296','Kiribati',119);
INSERT INTO scr_countryexreg VALUES (130,'414','Kuwait',120);
INSERT INTO scr_countryexreg VALUES (131,'418','Laos',121);
INSERT INTO scr_countryexreg VALUES (132,'426','Lesoto',122);
INSERT INTO scr_countryexreg VALUES (133,'428','Letonia',123);
INSERT INTO scr_countryexreg VALUES (134,'422','L�bano',124);
INSERT INTO scr_countryexreg VALUES (135,'430','Liberia',125);
INSERT INTO scr_countryexreg VALUES (136,'434','Libia',126);
INSERT INTO scr_countryexreg VALUES (137,'438','Liechtenstein',127);
INSERT INTO scr_countryexreg VALUES (138,'440','Lituania',128);
INSERT INTO scr_countryexreg VALUES (139,'442','Luxemburgo',129);
INSERT INTO scr_countryexreg VALUES (140,'446','Macao',130);
INSERT INTO scr_countryexreg VALUES (141,'450','Madagascar',132);
INSERT INTO scr_countryexreg VALUES (142,'458','Malasia',133);
INSERT INTO scr_countryexreg VALUES (143,'454','Malaui',134);
INSERT INTO scr_countryexreg VALUES (144,'462','Maldivas',135);
INSERT INTO scr_countryexreg VALUES (145,'466','Mal�',136);
INSERT INTO scr_countryexreg VALUES (146,'470','Malta',137);
INSERT INTO scr_countryexreg VALUES (147,'504','Marruecos',140);
INSERT INTO scr_countryexreg VALUES (148,'474','Martinica',142);
INSERT INTO scr_countryexreg VALUES (149,'480','Mauricio',143);
INSERT INTO scr_countryexreg VALUES (150,'478','Mauritania',144);
INSERT INTO scr_countryexreg VALUES (151,'175','Mayotte',145);
INSERT INTO scr_countryexreg VALUES (152,'484','M�xico',146);
INSERT INTO scr_countryexreg VALUES (153,'583','Micronesia',147);
INSERT INTO scr_countryexreg VALUES (154,'498','Moldavia',148);
INSERT INTO scr_countryexreg VALUES (155,'492','M�naco',149);
INSERT INTO scr_countryexreg VALUES (156,'496','Mongolia',150);
INSERT INTO scr_countryexreg VALUES (157,'499','Montenegro',245);
INSERT INTO scr_countryexreg VALUES (158,'500','Montserrat',151);
INSERT INTO scr_countryexreg VALUES (159,'508','Mozambique',152);
INSERT INTO scr_countryexreg VALUES (160,'516','Namibia',154);
INSERT INTO scr_countryexreg VALUES (161,'520','Nauru',155);
INSERT INTO scr_countryexreg VALUES (162,'524','Nepal',156);
INSERT INTO scr_countryexreg VALUES (163,'558','Nicaragua',157);
INSERT INTO scr_countryexreg VALUES (164,'562','N�ger',158);
INSERT INTO scr_countryexreg VALUES (165,'566','Nigeria',159);
INSERT INTO scr_countryexreg VALUES (166,'570','Niue',160);
INSERT INTO scr_countryexreg VALUES (167,'574','Norfolk',161);
INSERT INTO scr_countryexreg VALUES (168,'578','Noruega',162);
INSERT INTO scr_countryexreg VALUES (169,'540','Nueva Caledonia',163);
INSERT INTO scr_countryexreg VALUES (170,'554','Nueva Zelanda',164);
INSERT INTO scr_countryexreg VALUES (171,'512','Om�n',165);
INSERT INTO scr_countryexreg VALUES (172,'528','Pa�ses Bajos',166);
INSERT INTO scr_countryexreg VALUES (173,'586','Pakist�n',167);
INSERT INTO scr_countryexreg VALUES (174,'585','Palaos',168);
INSERT INTO scr_countryexreg VALUES (175,'591','Panam�',170);
INSERT INTO scr_countryexreg VALUES (176,'598','Pap�a Nueva Guinea',171);
INSERT INTO scr_countryexreg VALUES (177,'600','Paraguay',172);
INSERT INTO scr_countryexreg VALUES (178,'604','Per�',173);
INSERT INTO scr_countryexreg VALUES (179,'258','Polinesia Francesa',175);
INSERT INTO scr_countryexreg VALUES (180,'616','Polonia',176);
INSERT INTO scr_countryexreg VALUES (181,'620','Portugal',177);
INSERT INTO scr_countryexreg VALUES (182,'630','Puerto Rico',178);
INSERT INTO scr_countryexreg VALUES (183,'826','Reino Unido',180);
INSERT INTO scr_countryexreg VALUES (184,'180','Rep. Dem. del Congo',54);
INSERT INTO scr_countryexreg VALUES (185,'732','Rep�blica �rabe Saharaui Democr�tica',185);
INSERT INTO scr_countryexreg VALUES (186,'140','Rep�blica Centroafricana',43);
INSERT INTO scr_countryexreg VALUES (187,'203','Rep�blica Checa',45);
INSERT INTO scr_countryexreg VALUES (188,'807','Rep�blica de Macedonia',131);
INSERT INTO scr_countryexreg VALUES (189,'178','Rep�blica del Congo',55);
INSERT INTO scr_countryexreg VALUES (190,'214','Rep�blica Dominicana',65);
INSERT INTO scr_countryexreg VALUES (191,'638','Reuni�n',181);
INSERT INTO scr_countryexreg VALUES (192,'646','Ruanda',182);
INSERT INTO scr_countryexreg VALUES (193,'642','Ruman�a',183);
INSERT INTO scr_countryexreg VALUES (194,'643','Rusia',184);
INSERT INTO scr_countryexreg VALUES (195,'882','Samoa',187);
INSERT INTO scr_countryexreg VALUES (196,'016','Samoa Estadounidense',188);
INSERT INTO scr_countryexreg VALUES (197,'652','San Bartolom�',246);
INSERT INTO scr_countryexreg VALUES (198,'659','San Crist�bal y Nieves',189);
INSERT INTO scr_countryexreg VALUES (199,'674','San Marino',190);
INSERT INTO scr_countryexreg VALUES (200,'663','San Mart�n',247);
INSERT INTO scr_countryexreg VALUES (201,'666','San Pedro y Miquel�n',191);
INSERT INTO scr_countryexreg VALUES (202,'670','San Vicente y las Granadinas',192);
INSERT INTO scr_countryexreg VALUES (203,'654','Santa Helena, A. y T.',193);
INSERT INTO scr_countryexreg VALUES (204,'662','Santa Luc�a',194);
INSERT INTO scr_countryexreg VALUES (205,'678','Santo Tom� y Pr�ncipe',195);
INSERT INTO scr_countryexreg VALUES (206,'686','Senegal',196);
INSERT INTO scr_countryexreg VALUES (207,'688','Serbia',248);
INSERT INTO scr_countryexreg VALUES (208,'891','Serbia y Montenegro',197);
INSERT INTO scr_countryexreg VALUES (209,'690','Seychelles',198);
INSERT INTO scr_countryexreg VALUES (210,'694','Sierra Leona',199);
INSERT INTO scr_countryexreg VALUES (211,'702','Singapur',200);
INSERT INTO scr_countryexreg VALUES (212,'760','Siria',201);
INSERT INTO scr_countryexreg VALUES (213,'706','Somalia',202);
INSERT INTO scr_countryexreg VALUES (214,'144','Sri Lanka',203);
INSERT INTO scr_countryexreg VALUES (215,'748','Suazilandia',204);
INSERT INTO scr_countryexreg VALUES (216,'710','Sud�frica',205);
INSERT INTO scr_countryexreg VALUES (217,'736','Sud�n',206);
INSERT INTO scr_countryexreg VALUES (218,'752','Suecia',207);
INSERT INTO scr_countryexreg VALUES (219,'756','Suiza',208);
INSERT INTO scr_countryexreg VALUES (220,'740','Surinam',209);
INSERT INTO scr_countryexreg VALUES (221,'744','Svalbard y Jan Mayen',210);
INSERT INTO scr_countryexreg VALUES (222,'158','Taiw�n',212);
INSERT INTO scr_countryexreg VALUES (223,'834','Tanzania',213);
INSERT INTO scr_countryexreg VALUES (224,'762','Tayikist�n',214);
INSERT INTO scr_countryexreg VALUES (225,'086','Territorio Brit�nico del Oc�ano �ndico',215);
INSERT INTO scr_countryexreg VALUES (226,'260','Territorios Australes Franceses',216);
INSERT INTO scr_countryexreg VALUES (227,'626','Timor Oriental',217);
INSERT INTO scr_countryexreg VALUES (228,'768','Togo',218);
INSERT INTO scr_countryexreg VALUES (229,'772','Tokelau',219);
INSERT INTO scr_countryexreg VALUES (230,'776','Tonga',220);
INSERT INTO scr_countryexreg VALUES (231,'780','Trinidad y Tobago',221);
INSERT INTO scr_countryexreg VALUES (232,'788','T�nez',222);
INSERT INTO scr_countryexreg VALUES (233,'795','Turkmenist�n',224);
INSERT INTO scr_countryexreg VALUES (234,'792','Turqu�a',225);
INSERT INTO scr_countryexreg VALUES (235,'798','Tuvalu',226);
INSERT INTO scr_countryexreg VALUES (236,'804','Ucrania',227);
INSERT INTO scr_countryexreg VALUES (237,'800','Uganda',228);
INSERT INTO scr_countryexreg VALUES (238,'858','Uruguay',229);
INSERT INTO scr_countryexreg VALUES (239,'860','Uzbekist�n',230);
INSERT INTO scr_countryexreg VALUES (240,'548','Vanuatu',231);
INSERT INTO scr_countryexreg VALUES (241,'862','Venezuela',232);
INSERT INTO scr_countryexreg VALUES (242,'704','Vietnam',233);
INSERT INTO scr_countryexreg VALUES (243,'876','Wallis y Futuna',236);
INSERT INTO scr_countryexreg VALUES (244,'887','Yemen',237);
INSERT INTO scr_countryexreg VALUES (245,'262','Yibuti',238);
INSERT INTO scr_countryexreg VALUES (246,'894','Zambia',239);
INSERT INTO scr_countryexreg VALUES (247,'716','Zimbabue',240);


--Actualizacion de la version 8.0.2 a la version 8.0.3 M5

-- Tablas

-- TABLA scr_distregstate
ALTER TABLE scr_distregstate ADD  message varchar2(250);

--Actualizacion de la version 8.0.2 a la version 8.0.3 M7

-- Tablas

-- TABLA scr_distregstate
ALTER TABLE scr_distreg ADD  id_dist_father integer;

-- Tabla scr_distribucion_actual
CREATE TABLE scr_distribucion_actual(
    id_dist integer NOT NULL,
    dist_actual clob
);

ALTER TABLE scr_distribucion_actual ADD CONSTRAINT pk_scr_distribucion_actual PRIMARY KEY (id_dist);


--Actualizacion de la version 8.0.2 a la version 8.0.3 M9

-- Eliminar el pa�s Birmania porque est� repetido con Myanmar.
DELETE FROM SCR_COUNTRY where NAME='Birmania';
UPDATE SCR_COUNTRYEXREG SET ID_COUNTRY=153, NAME='Myanmar' WHERE ID=30;

-- A�adimos un nuevo tipo de direcci�n telem�tica
INSERT INTO scr_typeaddress (id, description, code) VALUES (6, 'Comparecencia Electr�nica', 'TE');

-- Se borra la duplicidad de Gijon como ciudad
DELETE FROM SCR_CITIESEXREG WHERE id = 496;
DELETE FROM SCR_CITIES WHERE id = 5015;
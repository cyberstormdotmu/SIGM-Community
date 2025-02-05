-- Eliminar informaci�n de la tabla SCR_DELTAS
DELETE FROM SCR_DELTAS;

-- Actualizar contandores de tablas
UPDATE SCR_CONTADOR SET contador = 2 WHERE tablaid = 'SCR_TYPEOFIC';
UPDATE SCR_CONTADOR SET contador = 8 WHERE tablaid = 'SCR_REPORTS';
UPDATE SCR_CONTADOR SET contador = 0 WHERE tablaid = 'SCR_DELTAS';

-- Actualizar identificadores de informes
UPDATE SCR_REPORTS SET id = 1 WHERE id = 9;
UPDATE SCR_REPORTS SET id = 2 WHERE id = 10;
UPDATE SCR_REPORTS SET id = 3 WHERE id = 11;
UPDATE SCR_REPORTS SET id = 4 WHERE id = 12;
UPDATE SCR_REPORTS SET id = 5 WHERE id = 13;
UPDATE SCR_REPORTS SET id = 6 WHERE id = 14;
UPDATE SCR_REPORTS SET id = 7 WHERE id = 15;
UPDATE SCR_REPORTS SET id = 8 WHERE id = 16;


-- Eliminar tablas obsoletas para la versi�n J2EE y sus correspondientes claves ajenas
ALTER TABLE SCR_RECVDISTREG DROP CONSTRAINT FK_SCRRECVDIST0;

ALTER TABLE SCR_REGORIGDOC DROP CONSTRAINT FK_SCRREGORIFD1;
ALTER TABLE SCR_REGORIGDOC DROP CONSTRAINT FK_SCRREGORIFD2;
ALTER TABLE SCR_REGORIGDOC DROP CONSTRAINT FK_SCRREGORIGD0;

DROP TABLE scr_recvdistreg;
DROP TABLE scr_regdoc;
DROP TABLE scr_regorigdoc;
DROP TABLE scr_typeproc;
DROP TABLE scr_erptaux;
DROP TABLE scr_srptaux;

-- A�adir tablas e �ndices utilizados por las aplicaciones de administraci�n
CREATE TABLE scr_unitadm (
    userid  integer NOT NULL,
    objid integer NOT NULL,
		objtype integer NOT NULL
);

-- TABLA scr_unitadm
CREATE INDEX scr_unitadm0 ON scr_unitadm (userid);
CREATE INDEX scr_unitadm1 ON scr_unitadm (objid);

CREATE TABLE scr_lockitems (
    objtype integer NOT NULL,
		objid integer NOT NULL,
		userid  integer NOT NULL,
		lockdate timestamp NOT NULL		
);

-- TABLA scr_lockitems
CREATE INDEX scrlockitems0 ON scr_lockitems (objid);
CREATE INDEX scrlockitems1 ON scr_lockitems (userid);

CREATE TABLE scr_versionitems (
    objtype integer NOT NULL,
		code varchar(32) NOT NULL,
		objid integer NOT NULL,
		version  integer NOT NULL,
		userid  integer NOT NULL,
		versiondate timestamp NOT NULL		
);

-- TABLA scr_versionitems
CREATE INDEX scrversionitems0 ON scr_versionitems (code);
CREATE INDEX scrversionitems1 ON scr_versionitems (objid);

-- Corregir orden de tabulaci�n de formulario de Nuevo Registro de Salida
update idocfmt 
set data = '"01.05"|"Carpeta",44,8,618,368,2|"Registro",6,32,563,347,"MS Sans Serif",8,32|1001,8,11,86,19,1342177280,130,"N�mero de registro:","",0,0,0,1,2147483646,"IDOC_STATIC1001",""|1002,92,6,192,19,1350631552,129,"","",0,0,0,3,1,"IDOC_EDIT1002",""|1005,202,9,242,19,1342177280,130,"Usuario:","",0,0,0,1,2147483646,"IDOC_STATIC1005",""|1006,244,6,316,19,1350631552,129,"","",0,0,0,3,3,"IDOC_EDIT1006",""|1011,322,9,352,17,1342177280,130,"Estado:","",0,0,0,1,2147483646,"IDOC_STATIC1011",""|1012,354,6,412,19,1350631552,129,"","",0,0,0,3,6,"IDOC_EDIT1012",""|1003,8,27,78,35,1342177280,130,"Fecha de registro:","",0,0,0,1,2147483646,"IDOC_STATIC1003",""|1004,92,24,192,36,1350631552,129,"","",0,0,0,3,2,"IDOC_EDIT1004",""|1007,246,27,326,35,1342177280,130,"Fecha de trabajo:","MS Sans Serif",8,1,0,1,2147483646,"IDOC_STATIC1007",""|1008,324,24,412,36,1350631552,129,"","",0,0,0,3,4,"IDOC_EDIT1008",""|1013,8,43,78,51,1342177280,130,"Origen:","",0,0,0,1,2147483646,"IDOC_STATIC1013",""|1014,92,41,192,54,1350631552,129,"","",0,0,0,3,7,"IDOC_EDIT1014",""|1042,202,41,412,62,1350633604,129,"","",0,0,0,10,7,"IDOC_EDIT1042",""|1015,8,70,78,78,1342177280,130,"Destino:","",0,0,0,1,2147483646,"IDOC_STATIC1015",""|1016,92,67,192,80,1350631552,129,"","",0,0,0,3,8,"IDOC_EDIT1016",""|1043,202,67,412,88,1350633604,129,"","",0,0,0,10,8,"IDOC_EDIT1043",""|1017,8,94,86,102,1342177280,130,"Destinatarios:","",0,0,0,1,2147483646,"IDOC_STATIC1017",""|1018,92,92,412,144,1350631552,129,"","",0,0,0,3,9,"IDOC_EDIT1018",""|1036,8,156,80,164,1342177280,130,"Tipo de asunto:","",0,0,0,1,2147483646,"IDOC_STATIC1036",""|1037,92,153,192,166,1350631552,129,"","",0,0,0,3,12,"IDOC_EDIT1037",""|1046,202,153,412,174,1350633604,129,"","",0,0,0,10,12,"IDOC_EDIT1046",""|1038,8,185,90,193,1342177280,130,"Resumen:","",0,0,0,1,2147483646,"IDOC_STATIC1038",""|1039,92,177,412,217,1350631620,129,"","",0,0,0,3,13,"IDOC_EDIT1039",""|1019,8,225,96,233,1342177280,130,"Tipo de transporte:","",0,0,0,1,2147483646,"IDOC_STATIC1019",""|1020,92,224,226,238,1350631552,129,"","",0,0,0,3,10,"IDOC_EDIT1020",""|1034,236,225,334,233,1342177280,130,"N�mero de transporte:","",0,0,0,1,2147483646,"IDOC_STATIC1034",""|1035,330,224,412,238,1350631552,129,"","",0,0,0,3,11,"IDOC_EDIT1035",""|1040,8,256,80,264,1342177280,130,"Comentario:","",0,0,0,1,2147483646,"IDOC_STATIC1040",""|1041,92,241,412,275,1352728644,129,"","",0,0,0,3,14,"IDOC_EDIT1041",""|1009,8,284,96,292,1342177280,130,"Oficina de registro:","",0,0,0,1,2147483646,"IDOC_STATIC1009",""|1010,8,296,98,307,1350631552,129,"","",0,0,0,3,5,"IDOC_EDIT1010",""|1033,104,296,324,307,1350633604,129,"","",0,0,0,10,5,"IDOC_EDIT1033",""|1,2147483646,""|"Documentos",6,32,563,347,"MS Sans Serif",8,0|2,2147483646,"2,2|0|2,0,50,0,0,0,1,1,50,0,0,50,1|0.000000,0,0"|0|0|"0"|1,0|0,0|0|2147483646|2147483646|0'
,upddate = (SELECT current timestamp FROM sysibm.sysdummy1) 
where id = 12;

-- Corregir rutas de repositorios documentales
update ivolrephdr set info='"01.01"|"127.0.0.1,21,sigem,Ok04ddM=,/home/sigem/000_REPOSITORIO_REGISTRO"|1|3|3|0|0' where id=5;
update ivolrephdr set info='"01.01"|"127.0.0.1,21,sigem,Ok04ddM=,/home/sigem/000_SIGEM_TRAMITACION"|1|3|3|0|0' where id=6;
update ivolrephdr set info='"01.01"|"127.0.0.1,21,sigem,Ok04ddM=,/home/sigem/000_SIGEM_ARCHIVO"|1|3|3|0|0' where id=7;

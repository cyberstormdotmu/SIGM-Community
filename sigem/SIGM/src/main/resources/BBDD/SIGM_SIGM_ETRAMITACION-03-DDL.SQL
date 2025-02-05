

CREATE INDEX FKI_CAT_HOOK_TYPE ON SGMRTCATALOGO_CONECTORES
(TIPO)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_CNX_USR1 ON SGM_CNXUSR
(CNIF)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_CT_FICHHITO1 ON SGM_CTFICHHITO
(CGUIDHITO)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_CT_INTEXP1 ON SGM_CTINTEXP
(CNUMEXP)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_CT_INTEXP2 ON SGM_CTINTEXP
(CNIF)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_NT_INFONOTIF2 ON SGM_NTINFONOTIF
(CNIFDEST)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_NT_INFONOTIF3 ON SGM_NTINFONOTIF
(CAVISADA)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_NT_INFONOTIF4 ON SGM_NTINFONOTIF
(CENTREGADA)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_NT_NOTIF_ESTAD ON SGMNTINFO_NOTIFICACION
(CESTADO)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_RDE_REGISTRY ON SGMRTREGISTRO_DOCUMENTOS
(NUMERO_REGISTRO)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_RDE_REGISTRY_1 ON SGMRTREGISTRO
(EMISOR_ID)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_RDE_REGISTRY_2 ON SGMRTREGISTRO
(FECHA_REGISTRO)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_RDE_REGISTRY_4 ON SGMRTREGISTRO
(ESTADO)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_SGMRTREGISTRO_DOCS_CSV ON SGMRTREGISTRO_DOCS_CSV
(GUID, CSV)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE UNIQUE INDEX IX_CSV_APLICACIONES_CODIGO ON CSV_APLICACIONES
(CODIGO)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE UNIQUE INDEX IX_CSV_DOCUMENTOS_CSV ON CSV_DOCUMENTOS
(CSV)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_ ON SGMRTCATALOGO_DOCSTRAMITE
(DOCUMENTO_ID)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_NT_DOCS_NOTIF ON SGMNTINFO_DOCUMENTO
(CNOTIEXPE, CNOTINIFDEST)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;


CREATE INDEX FKI_SES_INF_HOOKID ON SGMAFSESION_INFO
(CONECTORID)
LOGGING
TABLESPACE SIGMETRAMI
NOPARALLEL;



ALTER TABLE CSV_APLICACIONES ADD (
  CONSTRAINT PK_CSV_APLICACIONES
 PRIMARY KEY
 (ID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE CSV_DOCUMENTOS ADD (
  CONSTRAINT PK_CSV_DOCUMENTOS
 PRIMARY KEY
 (ID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMAFCONECTOR_AUTENTICACION ADD (
  CONSTRAINT SGMAFCON_AUT_PK
 PRIMARY KEY
 (TRAMITEID, CONECTORID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMCERTIFICACION ADD (
  CONSTRAINT SGMCERT_PK
 PRIMARY KEY
 (ID_FICHERO)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMNTINFO_ESTADO_NOTI ADD (
  CONSTRAINT SGMNT_EST_NOTI_PK
 PRIMARY KEY
 (CGUID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMNTINFO_NOTIFICACION ADD (
  CONSTRAINT SGMNTINFO_NOTIF_PK
 PRIMARY KEY
 (CNOTIID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMRDEDOCUMENTOS ADD (
  CONSTRAINT DOCUMENTS_PKEY
 PRIMARY KEY
 (GUID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMRTCATALOGO_CONECTORES ADD (
  CONSTRAINT SGMRDECAT_HOOK_PK
 PRIMARY KEY
 (CONECTORID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMRTCATALOGO_DIASCALENDARIO ADD (
  CONSTRAINT DIASCALENDARIO_PKEY
 PRIMARY KEY
 (ID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMRTCATALOGO_DOCUMENTOS ADD (
  CONSTRAINT SGMRDECAT_DOC_PK
 PRIMARY KEY
 (ID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMRTCATALOGO_ORGANOS ADD (
  CONSTRAINT SGMRDECAT_ORG_PK
 PRIMARY KEY
 (ORGANO)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMRTCATALOGO_TIPOCONECTOR ADD (
  CONSTRAINT SGMRDECAT_HOOKT_PK
 PRIMARY KEY
 (TIPOID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMRTCATALOGO_TRAMITES ADD (
  CONSTRAINT SGMRDECAT_PROC_PK
 PRIMARY KEY
 (ID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMRTREGISTRO ADD (
  CONSTRAINT SGMRDEREGISTRY_PK
 PRIMARY KEY
 (NUMERO_REGISTRO)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMRTREGISTRO_DOCS_CSV ADD (
  CONSTRAINT SGMRTREGISTRO_DOCS_CSV_PKEY
 PRIMARY KEY
 (GUID),
  CONSTRAINT SGMRTREGISTRO_DOCS_CSV_KEY
 UNIQUE (CSV)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGM_AU_USUARIOS ADD (
  CONSTRAINT SGMAFUSUARIOS_PKEY
 PRIMARY KEY
 (USUARIO)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGM_CTHITOESTEXP ADD (
  CONSTRAINT SGM_CTHITOESTEX_PK
 PRIMARY KEY
 (CNUMEXP)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGM_CTINFOEXP ADD (
  CONSTRAINT SGM_CTINFOEXP_PKEY
 PRIMARY KEY
 (CNUMEXP)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGM_CTINTEXP ADD (
  CONSTRAINT SGM_CTINTEXP_CN_PK
 UNIQUE (CNUMEXP, CNIF)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGM_NTFICHNOTIF ADD (
  CONSTRAINT SGM_NTFICHNOTIF_PK
 PRIMARY KEY
 (CGUID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGM_NTINFONOTIF ADD (
  CONSTRAINT SGM_NTINFONOTIF_PK
 PRIMARY KEY
 (CGUID)
    USING INDEX 
    TABLESPACE SIGMETRAMI,
  CONSTRAINT SGM_NTNOTIF_CN_KEY
 UNIQUE (CNUMEXP, CNIFDEST)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGM_PE_AL12NSEC ADD (
  CONSTRAINT PK_SGM_PE_AL12NSEC
 PRIMARY KEY
 (NUMSEC)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGM_PE_AL3NSEC ADD (
  CONSTRAINT PK_SGM_PE_AL3NSEC
 PRIMARY KEY
 (MODELO)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGM_PE_LIQUIDACIONES ADD (
  CONSTRAINT PK_PE_LIQ
 PRIMARY KEY
 (REFERENCIA)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMAFSESION_INFO ADD (
  CONSTRAINT SGMRDESES_INFO_PK
 PRIMARY KEY
 (SESIONID)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE SGMRTCATALOGO_DOCSTRAMITE ADD (
  CONSTRAINT SGMCAT_PROC_DOC_PK
 PRIMARY KEY
 (TRAMITE_ID, DOCUMENTO_ID, CODIGO_DOCUMENTO)
    USING INDEX 
    TABLESPACE SIGMETRAMI);

ALTER TABLE CSV_DOCUMENTOS ADD (
  CONSTRAINT FK_CSV_DOCUMENTOS_APLICACION 
 FOREIGN KEY (ID_APLICACION) 
 REFERENCES CSV_APLICACIONES (ID));

ALTER TABLE SGMNTINFO_NOTIFICACION ADD (
  CONSTRAINT NT_NOTIF_EST_NOTI 
 FOREIGN KEY (CESTADO) 
 REFERENCES SGMNTINFO_ESTADO_NOTI (CGUID));

ALTER TABLE SGMAFSESION_INFO ADD (
  CONSTRAINT RDESES_INFO_HOOKID 
 FOREIGN KEY (CONECTORID) 
 REFERENCES SGMRTCATALOGO_CONECTORES (CONECTORID));

ALTER TABLE SGMNTINFO_DOCUMENTO ADD (
  CONSTRAINT NT_DOCS_NOTI 
 FOREIGN KEY (CNOTIID) 
 REFERENCES SGMNTINFO_NOTIFICACION (CNOTIID));

ALTER TABLE SGMRTCATALOGO_DOCSTRAMITE ADD (
  CONSTRAINT RDECAT_PROC_DOC_ID 
 FOREIGN KEY (DOCUMENTO_ID) 
 REFERENCES SGMRTCATALOGO_DOCUMENTOS (ID),
  CONSTRAINT RDECAT_PROC_DOC_PD 
 FOREIGN KEY (TRAMITE_ID) 
 REFERENCES SGMRTCATALOGO_TRAMITES (ID));

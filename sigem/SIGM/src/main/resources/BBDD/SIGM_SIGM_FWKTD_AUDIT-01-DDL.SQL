CREATE TABLE AUDIT_APPS
(
  APP_DESCRIPTION  VARCHAR2(128 CHAR)           NOT NULL,
  APP_ID           NUMBER                       NOT NULL
);

COMMENT ON COLUMN AUDIT_APPS.APP_DESCRIPTION IS 'Descripci�n de la aplicaci�n que se registra';

COMMENT ON COLUMN AUDIT_APPS.APP_ID IS 'Identificador de la aplicaci�n que se registra';


CREATE TABLE AUDIT_TRAZA
(
  ID                       NUMBER               NOT NULL,
  APP_ID                   NUMBER               NOT NULL,
  APP_DESCRIPTION          VARCHAR2(128 CHAR)       NULL,
  EVENT_TYPE               NUMBER               NOT NULL,
  EVENT_DATE               TIMESTAMP(6)         NOT NULL,
  EVENT_DESCRIPTION        VARCHAR2(512 CHAR)       NULL,
  USER_ID                  VARCHAR2(128 CHAR)   NOT NULL,
  USER_NAME                VARCHAR2(256 CHAR)   NOT NULL,
  USER_HOST_NAME           VARCHAR2(256 CHAR)   NOT NULL,
  USER_IP                  VARCHAR2(256 CHAR)   NOT NULL,
  OBJECT_TYPE              VARCHAR2(128 CHAR)   NOT NULL,
  OBJECT_TYPE_DESCRIPTION  VARCHAR2(512 CHAR)       NULL,
  OBJECT_ID                VARCHAR2(128 CHAR)       NULL,
  OBJECT_FIELD             VARCHAR2(128 CHAR)       NULL,
  OLD_VALUE                VARCHAR2(4000 BYTE)      NULL,
  NEW_VALUE                VARCHAR2(4000 BYTE)      NULL
);

COMMENT ON COLUMN AUDIT_TRAZA.ID IS 'Identificador �nico de traza';

COMMENT ON COLUMN AUDIT_TRAZA.APP_ID IS 'Identificador �nico de la aplicaci�n que lanza la traza';

COMMENT ON COLUMN AUDIT_TRAZA.APP_DESCRIPTION IS 'Descripci�n de la aplicaci�n que lanza la traza';

COMMENT ON COLUMN AUDIT_TRAZA.EVENT_TYPE IS 'Identificador del tipo de evento en el que consiste la traza';

COMMENT ON COLUMN AUDIT_TRAZA.EVENT_DATE IS 'Fecha y hora en la que ocurre la traza';

COMMENT ON COLUMN AUDIT_TRAZA.EVENT_DESCRIPTION IS 'Descripci�n del evento que lanza la traza';

COMMENT ON COLUMN AUDIT_TRAZA.USER_ID IS 'Identificador del usuario que lanza el evento';

COMMENT ON COLUMN AUDIT_TRAZA.USER_NAME IS 'Nombre del usuario que lanza el evento que se traza';

COMMENT ON COLUMN AUDIT_TRAZA.USER_HOST_NAME IS 'Nombre del host del equipo del usuario que lanza el evento que se traza';

COMMENT ON COLUMN AUDIT_TRAZA.USER_IP IS 'Ip del equipo del usuario que lanza el evento que se traza';

COMMENT ON COLUMN AUDIT_TRAZA.OBJECT_TYPE IS 'Identificador del tipo de objeto sobre el que se lanza el evento que se traza';

COMMENT ON COLUMN AUDIT_TRAZA.OBJECT_TYPE_DESCRIPTION IS 'Descripci�n del tipo de objeto sobre el que se lanza el evento que se traza';

COMMENT ON COLUMN AUDIT_TRAZA.OBJECT_ID IS 'Identificador del objeto sobre el que se lanza la traza';

COMMENT ON COLUMN AUDIT_TRAZA.OBJECT_FIELD IS 'Atributo del objeto sobre el que se lanza la traza';

COMMENT ON COLUMN AUDIT_TRAZA.OLD_VALUE IS 'Valor del atributo del objeto antes de lanzar el evento que modifica el valor';

COMMENT ON COLUMN AUDIT_TRAZA.NEW_VALUE IS 'Valor del atributo del objeto despu�s de lanzar el evento que modifica el valor';


CREATE SEQUENCE AUDIT_TRAZA_SEQ
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER;
  
COMMIT;
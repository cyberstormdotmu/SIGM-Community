-- /***************************/
-- /* Versi�n 4.0             */
-- /***************************/

	-- Insertar la versi�n actual de bd
	INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','4.0',getdate());

	-- A�adir fecha de creaci�n a la unidad de instalaci�n
	ALTER TABLE ASGDUINSTALACION ADD FCREACION DATETIME NOT NULL DEFAULT getDate();
	GO

	-- Crear la tabla de unidades de instalaci�n hist�ricas
	CREATE TABLE dbo.ASGDHISTUINSTALACION(
		ID VARCHAR(32) NOT NULL,
		IDARCHIVO VARCHAR(32) NOT NULL,
		IDFORMATO VARCHAR(32) NOT NULL,
		SIGNATURAUI	VARCHAR(16) NOT NULL,
		IDENTIFICACION VARCHAR(254)	NOT NULL,
		FELIMINACION DATETIME NOT NULL,
		MOTIVO	SMALLINT NOT NULL
	);
	GO

	CREATE UNIQUE INDEX ASGDHISTUINSTALACION1 ON ASGDHISTUINSTALACION(ID);
	CREATE INDEX ASGDHISTUINSTALACION2 ON ASGDHISTUINSTALACION(SIGNATURAUI);
	CREATE INDEX ASGDHISTUINSTALACION3 ON ASGDHISTUINSTALACION(ID,FELIMINACION);
	CREATE INDEX ASGDHISTUINSTALACION4 ON ASGDHISTUINSTALACION(IDARCHIVO);
	GO

	-- A�adir la columna visibilidad a los motivos de consulta
	ALTER TABLE ASGPMTVCONSULTA ADD VISIBILIDAD SMALLINT;
	GO

	--Si el tipo de entidad es 1 (Investigador), tiene visibilidad 3 (Ambos)
	UPDATE ASGPMTVCONSULTA SET VISIBILIDAD = 3 WHERE TIPOENTIDAD=1;

	-- Crear identificador para los motivos de consulta
	ALTER TABLE ASGPMTVCONSULTA ADD ID VARCHAR(32);
	GO

	-- Actualizar los registros antiguos con el nuevo ID de motivo de consulta
	UPDATE ASGPMTVCONSULTA SET ID = SUBSTRING('u' + CAST ((TIPOENTIDAD) AS VARCHAR) + CAST ((TIPOCONSULTA) AS VARCHAR) + SUBSTRING(MOTIVO,1,1) + '000000000000000000000000000000000',1,32);
	ALTER TABLE ASGPMTVCONSULTA ALTER COLUMN ID VARCHAR(32) NOT NULL;
	GO

	-- Crear un nuevo �ndice sobre el identificador del motivo de consulta
	CREATE UNIQUE INDEX ASGPMTVCONSULTA2 ON ASGPMTVCONSULTA(ID);
	GO

	-- A�adir a las consultas el identificador de motivo
	ALTER TABLE ASGPCONSULTA ADD IDMOTIVO VARCHAR(32);
	GO

	-- Actualizar registros anteriores en consultas con el identificador del motivo que les corresponde
	UPDATE ASGPCONSULTA SET IDMOTIVO = (
		SELECT ASGPMTVCONSULTA.ID FROM ASGPMTVCONSULTA ASGPMTVCONSULTA
		WHERE ASGPMTVCONSULTA.MOTIVO = ASGPCONSULTA.MOTIVO
		AND ASGPMTVCONSULTA.TIPOENTIDAD = ASGPCONSULTA.TIPOENTCONSULTORA
		AND ASGPMTVCONSULTA.TIPOCONSULTA = ASGPCONSULTA.TIPO
	);

	-- La pantalla de consultas tiene un error y no actualiza los motivos cuando se
	-- cambia el usuario tramitador, deber�a hacerlo ya que los motivos dependen de
	-- si la consulta es directa (tramitador = usuario conectado) o no
	-- (tramitador != usuario conectado)
	UPDATE ASGPCONSULTA SET IDMOTIVO = (
		SELECT ASGPMTVCONSULTA.ID FROM ASGPMTVCONSULTA ASGPMTVCONSULTA
		WHERE ASGPMTVCONSULTA.MOTIVO = ASGPCONSULTA.MOTIVO
		AND ASGPMTVCONSULTA.TIPOENTIDAD = ASGPCONSULTA.TIPOENTCONSULTORA
		AND ASGPMTVCONSULTA.TIPOCONSULTA = 1
	) WHERE IDMOTIVO IS NULL AND TIPO=2;

	UPDATE ASGPCONSULTA SET IDMOTIVO = (
		SELECT ASGPMTVCONSULTA.ID FROM ASGPMTVCONSULTA ASGPMTVCONSULTA
		WHERE ASGPMTVCONSULTA.MOTIVO = ASGPCONSULTA.MOTIVO
		AND ASGPMTVCONSULTA.TIPOENTIDAD = ASGPCONSULTA.TIPOENTCONSULTORA
		AND ASGPMTVCONSULTA.TIPOCONSULTA = 2
	) WHERE IDMOTIVO IS NULL AND TIPO=1;

	-- Establecer el id de motivo como no nulo
	ALTER TABLE ASGPCONSULTA ALTER COLUMN IDMOTIVO VARCHAR(32) NOT NULL;
	GO

	-- Crear tabla de motivos de pr�stamo
	CREATE TABLE dbo.ASGPMTVPRESTAMO (
	  ID            VARCHAR(32) NOT NULL,
	  TIPOUSUARIO   SMALLINT    NOT NULL,
	  MOTIVO        VARCHAR (254)  NOT NULL,
	  VISIBILIDAD   SMALLINT);
	GO

	CREATE UNIQUE INDEX ASGPMTVPRESTAMO1 ON ASGPMTVPRESTAMO(ID);
	GO

	-- Insertar motivos de prestamo para poder actualizar registros anteriores
	INSERT INTO ASGPMTVPRESTAMO VALUES ('n0000000000000000000000000000001',1,'Motivo Interno',3);
	INSERT INTO ASGPMTVPRESTAMO VALUES ('n0000000000000000000000000000002',2,'Motivo Externo',3);

	-- A�adir a la tabla de pr�stamos el identificador del motivo
	ALTER TABLE ASGPPRESTAMO ADD IDMOTIVO VARCHAR(32);
	GO

	-- Actualizar los pr�stamos con el identificador de motivo que corresponde en cada caso
	UPDATE ASGPPRESTAMO SET IDMOTIVO = 'n0000000000000000000000000000001' where IDUSRSOLICITANTE IS NOT NULL;
	UPDATE ASGPPRESTAMO SET IDMOTIVO = 'n0000000000000000000000000000002' where IDUSRSOLICITANTE IS NULL;

	-- Establecer el identificador de motivo en pr�stamos como no nulo
	ALTER TABLE ASGPPRESTAMO ALTER COLUMN IDMOTIVO VARCHAR(32) NOT NULL;
	GO

	-- A�adir identificador a los motivos de rechazo
	ALTER TABLE ASGPMTVRECHAZO ADD ID VARCHAR(32);
	GO

	-- Crear un identificador para los motivos de rechazo
	UPDATE ASGPMTVRECHAZO SET ID = SUBSTRING('u' + CAST ((TIPOSOLICITUD) AS VARCHAR) + SUBSTRING(MOTIVO,1,1) + SUBSTRING(MOTIVO,LEN(MOTIVO),LEN(MOTIVO)) + '0000000000000000000000000000000000',1,32);

	-- Establecer el identificador de los motivos de rechazo como no nulo
	ALTER TABLE ASGPMTVRECHAZO ALTER COLUMN ID VARCHAR(32) NOT NULL;
	GO

	-- Crear �ndice �nico para los motivos de rechazo
	CREATE UNIQUE INDEX ASGPMTVRECHAZO2 ON ASGPMTVRECHAZO(ID);
	GO

	-- Crear en prorrogas un identificador de motivo
	ALTER TABLE ASGPPRORROGA ADD IDMOTIVO VARCHAR(32);
	GO

	UPDATE ASGPPRORROGA SET IDMOTIVO = (
		SELECT ASGPMTVRECHAZO.ID FROM ASGPMTVRECHAZO ASGPMTVRECHAZO
		WHERE ASGPMTVRECHAZO.MOTIVO = ASGPPRORROGA.MOTIVORECHAZO
		AND ASGPMTVRECHAZO.TIPOSOLICITUD = 3
	);

	-- A�adir identificador de motivo de rechazo a las solicitudes
	ALTER TABLE ASGPSOLICITUDUDOC ADD IDMOTIVO VARCHAR(32);
	GO

	-- Actualizar los motivos de rechazo de prestamos y consultas
	UPDATE ASGPSOLICITUDUDOC SET IDMOTIVO = (
		SELECT ASGPMTVRECHAZO.ID FROM ASGPMTVRECHAZO ASGPMTVRECHAZO
		WHERE ASGPMTVRECHAZO.MOTIVO = ASGPSOLICITUDUDOC.MOTIVORECHAZO
		AND ASGPMTVRECHAZO.TIPOSOLICITUD = ASGPSOLICITUDUDOC.TIPOSOLICITUD
	);
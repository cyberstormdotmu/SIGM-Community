Versi�n 1.3.0
-------------
Se modifica el fichero de configuraci�n de logs para que se generen en el directorio de usuario


Versi�n 1.3.1
-------------
Se actualiza la versi�n del framework, para emplear las �ltimas revisiones de las dependencias de spring y http-client de apache.
Se modifica el procedimiento de env�o de ficheros al servidor, creando una copia previa.
Se modifica el procedimiento para comprobar si la aplicaci�n ya termin� de subir los ficheros, buscando un .uploading.

Versi�n 1.3.2
-------------
Se corrige la comprobaci�n de nueva versi�n. Hab�a queado comentada.

Versi�n 1.3.3
-------------
Se modifica la condici�n de comprobaci�n de fichero bloqueado en windows. Se har� en dos pasos, y de forma similar a como se hace en linux, buscando un fichero "lock".
Se aumenta a dos segundos el tiempo de doble check para asegurase de que un fichero ya est� libre.
Se mejoran las trazas de log.
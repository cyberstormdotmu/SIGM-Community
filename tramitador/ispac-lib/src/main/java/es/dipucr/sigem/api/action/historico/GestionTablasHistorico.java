package es.dipucr.sigem.api.action.historico;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.TableDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.search.vo.SearchResultVO;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import es.dipucr.sigem.api.rule.common.utils.DocumentosUtil;
import es.dipucr.sigem.api.rule.common.utils.ExpedientesUtil;
import es.dipucr.sigem.api.rule.common.utils.HitosUtils;
import es.dipucr.sigem.api.rule.common.utils.ParticipantesUtil;
import es.dipucr.sigem.api.rule.common.utils.TramitesUtil;
import es.dipucr.sigem.api.rule.procedures.Constants;

public class GestionTablasHistorico {
	
	private static final Logger LOGGER = Logger.getLogger(GestionTablasHistorico.class);

	private IClientContext cct;
	private TXProcesoDAO proceso;

	public GestionTablasHistorico() {
		this.setCct(null);
		this.setProceso(null);
	}

	public GestionTablasHistorico(IClientContext cs, TXProcesoDAO process) {
		this.setCct(cs);
		this.setProceso(process);
	}


	public GestionTablasHistorico(ClientContext cs, TXProcesoDAO process) {
		this.setCct(cs);
		this.setProceso(process);
	}

	public boolean pasaAHistorico(TXTransactionDataContainer dtc) throws ISPACException{
		boolean correcto = false;
		String numexp = "";

		boolean ongoingTX = cct.ongoingTX();
		
		try {
			dtc.persist();
			numexp = getProceso().getString("NUMEXP");
			
			if (!ongoingTX) {
				cct.beginTX();
			}

			// SPAC_DT_TRAMITES_H
			IItemCollection tramitesCollection = TramitesUtil.getTramites(cct, numexp);
			for(Object tramite: tramitesCollection.toList()){
				try{
					TramitesUtil.copiaDtTramites(cct, Constants.TABLASBBDD.SPAC_DT_TRAMITES_H, (IItem)tramite, numexp);
					((IItem)tramite).delete(getCct());
				}
				catch(ISPACException e){
					LOGGER.error("Error al pasar los trámites del expediente: " + numexp + " al histórico. " + e.getMessage(), e);
					//[Ruben #563146] Lanzo excepcion para parar el paso a Historico en caso de fallo y no quede la BD inconsistente
					throw new Exception("Error al pasar los trámites del expediente: " + numexp + " al histórico. " + e.getMessage(), e);
				}
			}

			// SPAC_DT_DOCUMENTOS_H
			IItemCollection documentosCollection = DocumentosUtil.getDocumentos(cct, numexp);
			for(Object documento: documentosCollection.toList()){
				try{
					DocumentosUtil.copiaDtDocumentos(cct, Constants.TABLASBBDD.SPAC_DT_DOCUMENTOS_H, (IItem)documento, numexp);
					((IItem)documento).delete(getCct());
				}
				catch(ISPACException e){
					LOGGER.error("Error al pasar los documentos del expediente: " + numexp + " al histórico. " + e.getMessage(), e);
					//[Ruben #563146] Lanzo excepcion para parar el paso a Historico en caso de fallo y no quede la BD inconsistente
					throw new Exception("Error al pasar los documentos del expediente: " + numexp + " al histórico. " + e.getMessage(), e);
				}
			}

			// SPAC_DT_INTERVINIENTES_H
			IItemCollection intervinientesCollection = ParticipantesUtil.getParticipantes(cct, numexp);
			for(Object interviniente : intervinientesCollection.toList()){
				try{
					ParticipantesUtil.copiaDtIntervinientes(cct, Constants.TABLASBBDD.SPAC_DT_INTERVINIENTES_H, (IItem)interviniente, numexp);
					((IItem)interviniente).delete(getCct());
				}
				catch(ISPACException e){
					LOGGER.error("Error al pasar los intervinientes del expediente: " + numexp + " al histórico. " + e.getMessage(), e);
					//[Ruben #563146] Lanzo excepcion para parar el paso a Historico en caso de fallo y no quede la BD inconsistente
					throw new Exception("Error al pasar los intervinientes del expediente: " + numexp + " al histórico. " + e.getMessage(), e);
				}
			}
			
			//SPAC_HITOS_H			
			IItemCollection hitosCollection = HitosUtils.getHitos(cct, numexp);		
			for(Object hito : hitosCollection.toList()){
				try{
					if(HitosUtils.copiaHitos(cct, Constants.TABLASBBDD.SPAC_HITOS_H, (IItem)hito, numexp))
						((IItem)hito).delete(getCct());
				}
				catch(Exception e){
					LOGGER.error("Error al pasar los hitos del expediente: " + numexp + " al histórico. " + e.getMessage(), e);
					//[Ruben #563146] Lanzo excepcion para parar el paso a Historico en caso de fallo y no quede la BD inconsistente
					throw new Exception("Error al pasar los hitos del expediente: " + numexp + " al histórico. " + e.getMessage(), e);
				}
			}

			// SPAC_EXPEDIENTES_H
			IItem expediente = ExpedientesUtil.getExpediente(cct, numexp);
			try{
				ExpedientesUtil.copiaExpediente(cct, Constants.TABLASBBDD.SPAC_EXPEDIENTES_H, (IItem)expediente, numexp);
				((IItem)expediente).delete(getCct());
			}
			catch(ISPACException e){
				LOGGER.error("Error al pasar el expediente: " + numexp + " al histórico. " + e.getMessage(), e);
				//[Ruben #563146] Lanzo excepcion para parar el paso a Historico en caso de fallo y no quede la BD inconsistente
				throw new Exception("Error al pasar el expediente: " + numexp + " al histórico. " + e.getMessage(), e);
			}
			
			correcto = true;
			
		} catch (Exception e) {
			LOGGER.error("ERROR al pasar al histórico el expediente: " + numexp + ". " + e.getMessage(), e);
			throw new ISPACException("ERROR al pasar al histórico el expediente: " + numexp + ". " + e.getMessage(), e);
			
		} finally {
			if (!ongoingTX) {
				cct.endTX(correcto);
			}
        }

		return correcto;
	}

	public boolean recuperaDeHistorico() throws ISPACException{
		boolean correcto = false;
		String numexp = "";
		
		boolean ongoingTX = cct.ongoingTX();
		
		try {
			numexp = getProceso().getString("NUMEXP");
			
			if (!ongoingTX) {
				cct.beginTX();
			}

			if(ExpedientesUtil.estaEnHistorico(cct, numexp)){
				//SPAC_EXPEDIENTES_H
				IItem expediente_h = ExpedientesUtil.getExpediente(cct, numexp);
				try{
					ExpedientesUtil.copiaExpediente(cct, Constants.TABLASBBDD.SPAC_EXPEDIENTES, (IItem)expediente_h, numexp);
					((IItem)expediente_h).delete(getCct());
				}
				catch(ISPACException e){
					LOGGER.error("Error al recuperar el expediente: " + numexp + " del histórico. " + e.getMessage(), e);
					//[Ruben #563146] Lanzo excepcion para parar el paso a Historico en caso de fallo y no quede la BD inconsistente
					throw new Exception("Error al recuperar el expediente: " + numexp + " del histórico. " + e.getMessage(), e);
				}			
				
				//SPAC_HITOS_H			
				IItemCollection hitosCollection = HitosUtils.getHitos(cct, numexp);		
				for(Object hito_h : hitosCollection.toList()){
					try{
						if(HitosUtils.copiaHitos(cct, Constants.TABLASBBDD.SPAC_HITOS, (IItem)hito_h, numexp))
							((IItem)hito_h).delete(getCct());
					}
					catch(Exception e){
						LOGGER.error("Error al recuperar los hitos del expediente: " + numexp + " del histórico. " + e.getMessage(), e);
						//[Ruben #563146] Lanzo excepcion para parar el paso a Historico en caso de fallo y no quede la BD inconsistente
						throw new Exception("Error al recuperar los hitos del expediente: " + numexp + " del histórico. " + e.getMessage(), e);
					}
				}
	
				// SPAC_DT_TRAMITES_H
				IItemCollection tramitesCollection = TramitesUtil.getTramites(cct, numexp);
				for(Object tramite_h:tramitesCollection.toList()){
					try{
						TramitesUtil.copiaDtTramites(cct, Constants.TABLASBBDD.SPAC_DT_TRAMITES, (IItem)tramite_h, numexp);
						((IItem)tramite_h).delete(getCct());
					}
					catch(ISPACException e){
						LOGGER.error("Error al recuperar el tramite del expediente: " + numexp + " del histórico. " + e.getMessage(), e);
						//[Ruben #563146] Lanzo excepcion para parar el paso a Historico en caso de fallo y no quede la BD inconsistente
						throw new Exception("Error al recuperar el tramite del expediente: " + numexp + " del histórico. " + e.getMessage(), e);
					}
				}
	
				// SPAC_DT_DOCUMENTOS_H
				IItemCollection documentosCollection = DocumentosUtil.getDocumentos(cct, numexp);
				for(Object documento_h:documentosCollection.toList()){				
					try{
						DocumentosUtil.copiaDtDocumentos(cct, Constants.TABLASBBDD.SPAC_DT_DOCUMENTOS, (IItem)documento_h, numexp);
						((IItem)documento_h).delete(getCct());
					}
					catch(ISPACException e){
						LOGGER.error("Error al recuperar los documentos del expediente: " + numexp + " del histórico. " + e.getMessage(), e);
						//[Ruben #563146] Lanzo excepcion para parar el paso a Historico en caso de fallo y no quede la BD inconsistente
						throw new Exception("Error al recuperar los documentos del expediente: " + numexp + " del histórico. " + e.getMessage(), e);
					}
				}
	
				// SPAC_DT_INTERVINIENTES_H
				IItemCollection intervinientesCollection = ParticipantesUtil.getParticipantes(cct, numexp);
				for(Object interviniente_h : intervinientesCollection.toList()){
					try{
						ParticipantesUtil.copiaDtIntervinientes(cct, Constants.TABLASBBDD.SPAC_DT_INTERVINIENTES, (IItem)interviniente_h, numexp);
						((IItem)interviniente_h).delete(getCct());
					}
					catch(ISPACException e){
						LOGGER.error("Error al recuperar los intervinientes del expediente: " + numexp + " del histórico. " + e.getMessage(), e);
						//[Ruben #563146] Lanzo excepcion para parar el paso a Historico en caso de fallo y no quede la BD inconsistente
						throw new Exception("Error al recuperar los intervinientes del expediente: " + numexp + " del histórico. " + e.getMessage(), e);
					}
				}
			}
			
			correcto = true;
			
		} catch (Exception e) {
			LOGGER.error("ERROR al recuperar del histórico el expediente: " + numexp + ". " + e.getMessage(), e);
			throw new ISPACException("ERROR al recuperar del histórico el expediente: " + numexp + ". " + e.getMessage(), e);
			
		} finally {
			if (!ongoingTX) {
				cct.endTX(correcto);
			}
        }

		return correcto;
	}

	public IClientContext getCct() {
		return cct;
	}

	public void setCct(IClientContext cct) {
		this.cct = cct;
	}

	public TXProcesoDAO getProceso() {
		return proceso;
	}

	public void setProceso(TXProcesoDAO proceso) {
		this.proceso = proceso;
	}

	public ListCollection buscaEnHistorico(DbCnt cnt, String[] tableNames, String fromClause, Property[] columns, String conditions, List<ObjectDAO> lista) {
		return buscaEnHistorico(cnt, tableNames, fromClause, columns, conditions, lista, null, 0, null);
	}
	
	// MQE Buscamos también en la tabla de histórico con los mismos criterios
	public ListCollection buscaEnHistorico(DbCnt cnt, String[] tableNames, String fromClause, Property[] columns, 
			String conditions, List<ObjectDAO> lista, String order, int maximo, SearchResultVO searchResultVO) {

		String[] tableNamesH;
		String fromClauseH;
		Property[] columnsH;
		String conditionsH;
		String orderH;

		if (tableNames != null) {
			tableNamesH = new String[tableNames.length];
			for (int i = 0; i < tableNames.length; i++) {
				tableNamesH[i] = reemplazarTablasH(tableNames[i]);
			}
		} else
			tableNamesH = tableNames;

		fromClauseH = reemplazarTablasH(fromClause);

		if (columns != null) {
			columnsH = new Property[columns.length];

			for (int i = 0; i < columns.length; i++) {
				Property aux = columns[i];
				String auxName = reemplazarTablasH(aux.getName());
				String auxRawName = reemplazarTablasH(aux.getRawName());
				String auxTitle = reemplazarTablasH(aux.getTitle());

				Property aux2 = new Property(aux.getOrdinal(), auxName,
						auxRawName, aux.getType(), aux.getSize(),
						aux.getDecimalDigits(), auxTitle, aux.getIsNullable());

				columnsH[i] = aux2;
			}
		} else
			columnsH = columns;


		conditionsH = reemplazarTablasH(conditions);
		
		//[dipucr-Felipe #847]
		orderH = reemplazarTablasH(order);

		DbResultSet rs = null;
		try {
			CollectionDAO resultsH = TableDAO.newCollectionDAO(TableDAO.class, tableNamesH,	fromClauseH, columnsH);
			
			//INICIO [dipucr-Felipe #847]
			rs = resultsH.querySinToList(cnt, conditionsH, orderH, maximo);
			
			if (null != searchResultVO){
				int numRegistros = searchResultVO.getNumTotalRegistros();
				int numRegistrosHist = resultsH.count(cnt, conditionsH);
				searchResultVO.setNumTotalRegistros(numRegistros + numRegistrosHist);
			}
			//FIN [dipucr-Felipe #847]

			while (rs.getResultSet().next()) {
				Object[] marguments = new Object[4];
				marguments[0] = cnt;
				marguments[1] = tableNames;
				marguments[2] = fromClause;
				marguments[3] = columns;

				Class<?>[] margumentTypes = new Class[marguments.length];
				for (int i = 0; i < marguments.length; i++) {
					if (marguments[i] == null)
						throw new ISPACException("La entidad está mal definida");
					margumentTypes[i] = marguments[i].getClass();
				}

				Constructor<TableDAO> newdao;
				ObjectDAO o = null;
				try {
					newdao = (TableDAO.class).getConstructor(margumentTypes);
					o = (ObjectDAO) newdao.newInstance(marguments);
					o.loadHistorico(rs.getResultSet());
				} catch (NoSuchMethodException e) {
					LOGGER.error("Error al buscar en el histórico. " + e.getMessage(), e);
				} catch (SecurityException e) {
					LOGGER.error("Error al buscar en el histórico. " + e.getMessage(), e);
				}

				lista.add(o);
			}
		} catch (SQLException e) {
			LOGGER.error("Error al buscar en el histórico. " + e.getMessage(), e);
		} catch (InstantiationException e) {
			LOGGER.error("Error al buscar en el histórico. " + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			LOGGER.error("Error al buscar en el histórico. " + e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			LOGGER.error("Error al buscar en el histórico. " + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			LOGGER.error("Error al buscar en el histórico. " + e.getMessage(), e);
		} catch (ISPACException e) {
			LOGGER.error("Error al buscar en el histórico. " + e.getMessage(), e);
		} finally {			
			if (null != rs){
				try {
					rs.close();
				} catch (ISPACException e) {
				}
			}
		}
		ListCollection listaResult = new ListCollection(lista);

		return listaResult;
	}

	/**
	 * [dipucr-Felipe #847]
	 * @param cadena
	 * @return
	 */
	public String reemplazarTablasH(String cadena) {
		String result = cadena;
		if (!StringUtils.isEmpty(result)){
			result = result.toUpperCase();
			result = result.replaceAll(Constants.TABLASBBDD.SPAC_EXPEDIENTES, Constants.TABLASBBDD.SPAC_EXPEDIENTES_H);
			result = result.replaceAll(Constants.TABLASBBDD.SPAC_DT_TRAMITES, Constants.TABLASBBDD.SPAC_DT_TRAMITES_H);
			result = result.replaceAll(Constants.TABLASBBDD.SPAC_DT_DOCUMENTOS, Constants.TABLASBBDD.SPAC_DT_DOCUMENTOS_H);
			result = result.replaceAll(Constants.TABLASBBDD.SPAC_DT_INTERVINIENTES, Constants.TABLASBBDD.SPAC_DT_INTERVINIENTES_H);
			result = result.replaceAll(Constants.TABLASBBDD.SPAC_HITOS, Constants.TABLASBBDD.SPAC_HITOS_H);
		}
		return result;
	}
}

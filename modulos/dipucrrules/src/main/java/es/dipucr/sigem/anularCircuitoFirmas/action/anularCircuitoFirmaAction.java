package es.dipucr.sigem.anularCircuitoFirmas.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.common.constants.SignCircuitStates;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.action.BaseDispatchAction;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.dipucr.sigem.anularCircuitoFirmas.sign.SignCircuitAnular;

public class anularCircuitoFirmaAction extends BaseDispatchAction {

	public ActionForward anularCircuito(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {		
		
		ClientContext cct = session.getClientContext();
		
		//SignForm signForm = (SignForm)form;
		
		EntityForm defaultForm = (EntityForm) form;
		
		// Nombre del campo que contiene el valor de la b�squeda
		String field = request.getParameter("field");

		// N�mero de registro a buscar
		String documentId = defaultForm.getProperty(field);
		
		int documentoId=0;
		try{
			//documentoId = Integer.parseInt(signForm.getDocumentId());
			documentoId = Integer.parseInt(documentId);	
		}
		catch(Exception e){			
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("es.dipucr.error.message.anularCircuitoFirma.error"));
			saveErrors(request,messages);
			return mapping.findForward("failure");
		}
		ISignAPI signAPI = session.getAPI().getSignAPI();
        IItemCollection itemcol = signAPI.getStepsByDocument(documentoId);
        int instanciaId = 0;
        
      //Si ya hay alg�n paso del circuito firmado no podemos anular el circuito de firma
		while (itemcol.next()){
			IItem row = itemcol.value();
			int estado = 0;
			try{
				estado = row.getInt("ESTADO");
				instanciaId = row.getInt("ID_INSTANCIA_CIRCUITO");
			}
			catch(Exception e){
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("es.dipucr.error.message.anularCircuitoFirma.error"));
				saveErrors(request,messages);
				return mapping.findForward("failure");
			}
			if(estado != SignCircuitStates.SIN_INICIAR && estado != SignCircuitStates.PENDIENTE){
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("es.dipucr.error.message.anularCircuitoFirma.error"));
				saveErrors(request,messages);
				return mapping.findForward("failure");
			}
		}
		//Anulamos el circuito de firma
		SignCircuitAnular signCircuitAnular = new SignCircuitAnular(cct);
		boolean circuitoAnulado = signCircuitAnular.anularCircuito(instanciaId);

		if(circuitoAnulado){
			// Situamos el Estado de Firma del documento a PENDIENTE
			IEntitiesAPI entitiesAPI = cct.getAPI().getEntitiesAPI();
			IItem itemDoc = entitiesAPI.getDocument(documentoId);
			itemDoc.set("ESTADOFIRMA", SignStatesConstants.SIN_FIRMA);
			
			//Bloqueamos el documento para la edicion
			itemDoc.set("BLOQUEO", "");
			itemDoc.store(cct);
		}
		else{
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("es.dipucr.error.message.anularCircuitoFirma.error"));
			saveErrors(request,messages);
			return mapping.findForward("failure");
		}		
		return mapping.findForward("success");
	}
}

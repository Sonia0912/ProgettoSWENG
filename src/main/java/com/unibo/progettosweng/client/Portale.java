/**
 * Classe astratta per i portali (studente, docente, admin, segreteria)
 **/
package com.unibo.progettosweng.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.unibo.progettosweng.client.model.Utente;

public abstract class Portale {

    VerticalPanel menuLaterale;
    VerticalPanel spazioDinamico;
    Utente utenteLoggato;

    public void svuotaPagina() {
        RootPanel.get("contenuto").clear();
        RootPanel.get("login").clear();
        ProgettoSWENG.caricaButtonPortale(true);
    }

    public abstract void caricaMenu();
    public abstract void caricaDefault() throws Exception;
    public abstract void salvaCredenziali();


    public void caricaPortale(Utente utenteInserito) throws Exception {
        this.utenteLoggato = utenteInserito;
        salvaCredenziali();

        svuotaPagina();
        HorizontalPanel mainPanel = new HorizontalPanel();
        mainPanel.getElement().setId("mainPanelPortaleId");
        mainPanel.addStyleName("mainInfoStudentePanel");

        menuLaterale = new VerticalPanel();
        menuLaterale.getElement().setId("menuLateraleId");
        menuLaterale.addStyleName("menuLaterale");

        caricaMenu();

        spazioDinamico = new VerticalPanel();
        spazioDinamico.getElement().setId("spazioDinamicoId");
        spazioDinamico.addStyleName("spazioDinamico");

        caricaDefault();

        mainPanel.add(menuLaterale);
        mainPanel.add(spazioDinamico);
        RootPanel.get("contenuto").add(mainPanel);
    }

}

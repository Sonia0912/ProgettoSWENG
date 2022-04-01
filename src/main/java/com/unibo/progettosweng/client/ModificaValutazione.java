package com.unibo.progettosweng.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Utente;
import com.unibo.progettosweng.client.model.Valutazione;

import java.util.ArrayList;

public class ModificaValutazione implements Form {

    private static ValutazioneServiceAsync valutazioneService = GWT.create(ValutazioneService.class);
    private static CorsoServiceAsync corsoSerivce = GWT.create(CorsoService.class);
    Valutazione valutazione;
    FormPanel nuovaValutazione;

    public ModificaValutazione(Valutazione valutazione){
        this.valutazione = valutazione;
    }

    @Override
    public FormPanel getForm() throws Exception {
        nuovaValutazione = new FormPanel();
        nuovaValutazione.addStyleName("formCreazioneUtente");
        nuovaValutazione.setAction("/modificaValutazione");
        nuovaValutazione.setMethod(FormPanel.METHOD_POST);

        VerticalPanel formPanel = new VerticalPanel();

        final Label labelNomeCorso = new Label("Esame di " + valutazione.getNomeCorso());
        labelNomeCorso.getElement().setClassName("label");
        formPanel.add(labelNomeCorso);
        formPanel.add(labelNomeCorso);

        final Label labelUsername = new Label("Studente: " + valutazione.getStudente());
        labelUsername.getElement().setClassName("label");
        formPanel.add(labelUsername);

        final Label labelVoto = new Label("Voto");
        labelVoto.getElement().setClassName("label");
        formPanel.add(labelVoto);
        final TextBox voto = new TextBox();
        voto.setValue(String.valueOf(valutazione.getVoto()));
        formPanel.add(voto);

        Button send = new Button("Modifica");
        send.getElement().setClassName("btn-send");
        send.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                nuovaValutazione.submit();
            }
        });
        formPanel.add(send);

        nuovaValutazione.add(formPanel);

        nuovaValutazione.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent submitEvent) {
                if(voto.getText().trim().length() == 0){
                    Window.alert("Inserire il nuovo voto");
                    submitEvent.cancel();
                }
            }
        });

        nuovaValutazione.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {
                //fare aggiornamento
            }
        });
        return nuovaValutazione;

    }
}

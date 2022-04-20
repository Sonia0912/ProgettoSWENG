/**
 * Classe che fornisce il form per modificare un voto gia' deciso nel portale docente.
 **/
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

        final Label labelNomeCorso = new Label("Esame: " + valutazione.getNomeCorso());
        labelNomeCorso.getElement().setClassName("label");
        labelNomeCorso.addStyleName("infoVoto");
        formPanel.add(labelNomeCorso);
        formPanel.add(labelNomeCorso);

        final Label labelUsername = new Label("Studente: " + valutazione.getStudente());
        labelUsername.getElement().setClassName("label");
        labelUsername.addStyleName("infoVoto");
        formPanel.add(labelUsername);

        final Label labelVoto = new Label("Voto:");
        labelVoto.getElement().setClassName("label");
        labelVoto.addStyleName("infoVoto");
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
                if(Integer.parseInt(voto.getText()) < 18 || Integer.parseInt(voto.getText()) > 30){
                    Window.alert("Il voto deve essere compreso tra 18 e 30");
                    submitEvent.cancel();
                }
            }
        });

        nuovaValutazione.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {
                valutazione.setVoto(Integer.valueOf(voto.getText()));
                try {
                    valutazioneService.aggiorna(valutazione, new AsyncCallback<Void>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Errore nell'aggiornare la valutazione: " + throwable.getMessage());
                        }

                        @Override
                        public void onSuccess(Void v) {
                            Window.alert("Aggionata valutazione di " + valutazione.getStudente() + " con voto " + valutazione.getVoto());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return nuovaValutazione;
    }

}

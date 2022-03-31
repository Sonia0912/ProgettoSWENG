package com.unibo.progettosweng.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Utente;

import java.util.ArrayList;

public class InserimentoValutazione implements Form {

    private static ValutazioneServiceAsync valutazioneService = GWT.create(ValutazioneService.class);
    private static CorsoServiceAsync corsoSerivce = GWT.create(CorsoService.class);
    Utente docente;
    FormPanel nuovaValutazione;

    public InserimentoValutazione(Utente username){
        this.docente = username;
    }

    @Override
    public FormPanel getForm() throws Exception {
        nuovaValutazione = new FormPanel();
        nuovaValutazione.addStyleName("formCreazioneValutazione");
        nuovaValutazione.setAction("/creaNuovaValutazione");
        nuovaValutazione.setMethod(FormPanel.METHOD_POST);

        VerticalPanel formPanel = new VerticalPanel();

        //nomeCorso, usernmeStudente, voto

        final Label labelNomeCorso = new Label("nomeCorso");
        labelNomeCorso.getElement().setClassName("label");
        formPanel.add(labelNomeCorso);
        ListBox nomeCorso = new ListBox();
        nomeCorso.addItem("");
        corsoSerivce.getCorsiDocente(docente.getUsername(), new AsyncCallback<ArrayList<Corso>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Errore nell'ottenere la lista dei corsi: "+ throwable.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Corso> corsi) {
                for(int i = 0; i < corsi.size(); i ++){
                    nomeCorso.addItem(corsi.get(i).getNomeCorso());
                }
            }
        });
        formPanel.add(nomeCorso);

        //IMPLEMENTARE OPERAZIONE GETSTUDENTIISCRITTI NEL DB ISCRIZIONI

        final Label labelUsername = new Label("Username Studente:");
        labelUsername.getElement().setClassName("label");
        formPanel.add(labelUsername);
        final TextBox usernameStudente = new TextBox();
        usernameStudente.getElement().setClassName("input");
        usernameStudente.setName("UsernameStudente");
        formPanel.add(usernameStudente);

        final Label labelVoto = new Label("Voto");
        labelVoto.getElement().setClassName("label");
        final ListBox voto = new ListBox();
        voto.addItem("");
        //voto.addItem("Insufficiente");
        for (int i = 0; i <=30; i++) {
            voto.addItem(String.valueOf(i));
        }
        //voto.addItem("30L");
        formPanel.add(voto);

        Button send = new Button("Inserisci");
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
                if(nomeCorso.getSelectedItemText().equals("") || usernameStudente.getText().length() == 0 || voto.getSelectedItemText().equals("")){
                    Window.alert("Compilare tutti i campi");
                    submitEvent.cancel();
                }
            }
        });

        nuovaValutazione.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {

                String [] info = {nomeCorso.getSelectedItemText(), usernameStudente.getText(), voto.getSelectedItemText(), String.valueOf(false)};

                valutazioneService.add(info, new AsyncCallback<String>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                Window.alert("Fallimento nell'inserimento della valutazione: " + throwable.getMessage());
                            }

                            @Override
                            public void onSuccess(String s) {
                                Window.alert(s);
                            }
                        }
                );
            }
        });
        return nuovaValutazione;

    }
}

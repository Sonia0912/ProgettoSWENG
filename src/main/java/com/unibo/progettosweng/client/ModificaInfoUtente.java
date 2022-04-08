package com.unibo.progettosweng.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.unibo.progettosweng.client.model.Utente;

public class ModificaInfoUtente implements Form {

    Utente utente;
    FormPanel editUtente;
    private static UtenteServiceAsync service = GWT.create(UtenteService.class);

    public ModificaInfoUtente(Utente utente){
        this.utente = utente;
    }

    @Override
    public FormPanel getForm(){
        editUtente = new FormPanel();
        editUtente.addStyleName("formCreazioneUtente");
        editUtente.setAction("/modificaUtente");
        editUtente.setMethod(FormPanel.METHOD_POST);

        VerticalPanel formPanel = new VerticalPanel();
        final Label labelNome = new Label("Nome:");
        labelNome.getElement().setClassName("label");
        formPanel.add(labelNome);
        final TextBox nome = new TextBox();
        nome.getElement().setClassName("input");
        nome.setValue(utente.getNome());
        nome.setName("Nome");
        formPanel.add(nome);

        final Label labelCognome = new Label("Cognome:");
        labelCognome.getElement().setClassName("label");
        formPanel.add(labelCognome);
        final TextBox cognome = new TextBox();
        cognome.getElement().setClassName("input");
        cognome.setValue(utente.getCognome());
        cognome.setName("Cognome");
        formPanel.add(cognome);

//        final Label labelEmail = new Label("Email:");
//        labelEmail.getElement().setClassName("label");
//        formPanel.add(labelEmail);
//        final TextBox email = new TextBox();
//        email.getElement().setClassName("input");
//        email.setValue(utente.getUsername());
//        email.setName("Email");
//        formPanel.add(email);

        final Label labelPassword = new Label("Password:");
        labelPassword.getElement().setClassName("label");
        formPanel.add(labelPassword);
        final PasswordTextBox password = new PasswordTextBox();
        password.getElement().setClassName("input");
        password.setValue(utente.getPassword());
        password.setName("Password");
        formPanel.add(password);
/*
        final Label labelTipo = new Label("Tipo di utente*:");
        labelTipo.getElement().setClassName("label");
        formPanel.add(labelTipo);
        ListBox tipo = new ListBox();
        tipo.getElement().setClassName("input");
        tipo.addItem("");
        tipo.addItem("Docente");
        tipo.addItem("Studente");
        tipo.addItem("Segreteria");
        tipo.addItem("Admin");

        formPanel.add(tipo);

        final Label labelPassword = new Label("Password*:");
        labelPassword.getElement().setClassName("label");
        formPanel.add(labelPassword);
        final PasswordTextBox password = new PasswordTextBox();
        password.getElement().setClassName("input");
        password.setName("Password");
        formPanel.add(password);*/

        Button send = new Button("Modifica");
        send.getElement().setClassName("btn-send");
        send.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                editUtente.submit();
            }
        });
        formPanel.add(send);

        editUtente.add(formPanel);

        editUtente.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent submitEvent) {
                if (nome.getText().trim().length() == 0 || cognome.getText().trim().length() == 0 || password.getText().trim().length() == 0) {
                    Window.alert("Compilare tutti i campi");
                    submitEvent.cancel();
                }
            }
        });

        editUtente.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {
                utente.setCognome(cognome.getText());
                utente.setNome(nome.getText());
                utente.setPassword(password.getText());
                try {
                    service.aggiorna(utente, utente.getUsername(), new AsyncCallback<Utente>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Failuire to update user's information: " + throwable.getMessage());
                        }
                        @Override
                        public void onSuccess(Utente output) {
                            Window.alert("L'utente " + output.getUsername() + " è stato modificato con successo.");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return editUtente;
    }





}

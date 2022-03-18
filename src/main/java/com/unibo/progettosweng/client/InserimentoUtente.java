package com.unibo.progettosweng.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class InserimentoUtente implements Pagina{
    String titleString = "<h1 class=\"titoletto\">Inserisci un nuovo utente</h2>";
    FormPanel nuovoUtente;

    @Override
    public void aggiungiContenuto(){
        VerticalPanel vp = new VerticalPanel();
        vp.getElement().getStyle().setPadding(18, Style.Unit.PX);
        vp.add(new HTML(titleString));
        createForm();
        vp.add(nuovoUtente);
        RootPanel.get("contenuto").add(vp);
    }

    private void createForm(){
        nuovoUtente = new FormPanel();
        nuovoUtente.setAction("/creaNuovoUtente");
        nuovoUtente.setMethod(FormPanel.METHOD_POST);

        VerticalPanel formPanel = new VerticalPanel();
        final Label labelNome = new Label("Nome*:");
        labelNome.getElement().setClassName("label");
        formPanel.add(labelNome);
        final TextBox nome = new TextBox();
        nome.getElement().setClassName("input");
        nome.setName("Nome");
        formPanel.add(nome);

        final Label labelCognome = new Label("Cognome*:");
        labelCognome.getElement().setClassName("label");
        formPanel.add(labelCognome);
        final TextBox cognome = new TextBox();
        cognome.getElement().setClassName("input");
        cognome.setName("Cognome");
        formPanel.add(cognome);

        final Label labelEmail = new Label("Email*:");
        labelEmail.getElement().setClassName("label");
        formPanel.add(labelEmail);
        final TextBox email = new TextBox();
        email.getElement().setClassName("input");
        email.setName("Email");
        formPanel.add(email);

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

        Button send = new Button("Inserisci");
        send.getElement().setClassName("sendButton");
        send.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                nuovoUtente.submit();
            }
        });
        formPanel.add(send);

        nuovoUtente.add(formPanel);

        nuovoUtente.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent submitEvent) {
                if (nome.getText().length() == 0 || cognome.getText().length() == 0 || tipo.getSelectedItemText().equals("") || email.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi");
                    submitEvent.cancel();
                }
            }
        });

        nuovoUtente.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {
                //to do
            }
        });
    }





}

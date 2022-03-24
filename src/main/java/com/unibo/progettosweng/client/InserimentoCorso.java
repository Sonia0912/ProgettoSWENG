package com.unibo.progettosweng.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;

public class InserimentoCorso implements Form{
    FormPanel nuovoCorso;

    @Override
    public FormPanel getForm(){
        nuovoCorso = new FormPanel();
        nuovoCorso.addStyleName("formCreazioneUtente");
        nuovoCorso.setAction("/creaNuovoCorso");
        nuovoCorso.setMethod(FormPanel.METHOD_POST);

        VerticalPanel formPanel = new VerticalPanel();
        final Label labelNome = new Label("Nome del Corso*:");
        labelNome.getElement().setClassName("label");
        formPanel.add(labelNome);
        final TextBox nome = new TextBox();
        nome.getElement().setClassName("input");
        nome.setName("Nome");
        formPanel.add(nome);

        final Label labelInizio = new Label("Data di inizio*:");
        labelInizio.getElement().setClassName("label");
        formPanel.add(labelInizio);
        final DatePicker inizio = new DatePicker();
        inizio.getElement().setClassName("input");
        formPanel.add(inizio);

        final Label labelFine = new Label("Data di fine*:");
        labelFine.getElement().setClassName("label");
        formPanel.add(labelFine);
        final DatePicker fine = new DatePicker();
        fine.getElement().setClassName("input");
        formPanel.add(fine);

        final Label labelDescr = new Label("Breve descrizione del corso*:");
        labelDescr.getElement().setClassName("label");
        formPanel.add(labelDescr);
        final TextBox descr = new TextBox();
        descr.getElement().setClassName("input");
        descr.setName("Descrizione");
        formPanel.add(descr);


        final Label labelCoDoc = new Label("Co-docente:");
        labelCoDoc.getElement().setClassName("label");
        formPanel.add(labelCoDoc);
        formPanel.add(labelCoDoc);
        ListBox tipo = new ListBox();
        tipo.getElement().setClassName("input");
        tipo.addItem("");
        //prendere i nomi dei docenti dal db

        formPanel.add(tipo);

        Button send = new Button("Inserisci");
        send.getElement().setClassName("btn-send");
        send.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                nuovoCorso.submit();
            }
        });
        formPanel.add(send);

        nuovoCorso.add(formPanel);

        nuovoCorso.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent submitEvent) {
                if (nome.getText().length() == 0 || inizio.getValue().toString().length() == 0 || fine.getValue().toString().length() == 0 || descr.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi");
                    submitEvent.cancel();
                }
            }
        });

        nuovoCorso.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {
                //to do
            }
        });
        return nuovoCorso;
    }





}

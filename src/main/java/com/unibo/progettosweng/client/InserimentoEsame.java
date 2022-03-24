package com.unibo.progettosweng.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.unibo.progettosweng.client.model.Utente;

public class InserimentoEsame implements Form{
    FormPanel nuovoEsame;

    private final EsameServiceAsync serice = GWT.create(EsameService.class);
    private Utente docente;

    public InserimentoEsame(Utente docente){
        this.docente = docente;
    }

    @Override
    public FormPanel getForm(){
        nuovoEsame = new FormPanel();
        nuovoEsame.addStyleName("formCreazioneUtente");
        nuovoEsame.setAction("/creaNuovoCorso");
        nuovoEsame.setMethod(FormPanel.METHOD_POST);

        VerticalPanel formPanel = new VerticalPanel();

        final Label labelData = new Label("Data*:");
        labelData.getElement().setClassName("label");
        formPanel.add(labelData);
        final DatePicker data = new DatePicker();
        data.getElement().setClassName("input");
        formPanel.add(data);

        final Label labelOrario = new Label("Orario*:");
        labelOrario.getElement().setClassName("label");
        formPanel.add(labelOrario);
        final ListBox orario = new ListBox();
        orario.getElement().setClassName("input");
        orario.addItem("");
        for (int h = 0; h < 24; h++){
            for (int m = 0; m < 60; m+=30){
                if(h < 10){
                    if (m < 10){
                        orario.addItem("0"+h+":0"+m);
                    }else {
                        orario.addItem("0" + h + ":" + m);
                    }
                }else {
                    if (m < 10){
                        orario.addItem(h+":0"+m);
                    }else {
                        orario.addItem(h+":"+m);
                    }
                }

            }
        }

        formPanel.add(orario);

        final Label labelHardness = new Label("Hardness*:");
        labelHardness.getElement().setClassName("label");
        formPanel.add(labelHardness);
        final ListBox hardness = new ListBox();
        hardness.getElement().setClassName("input");
        hardness.addItem("");
        hardness.addItem("Facile");
        hardness.addItem("Medio");
        hardness.addItem("Difficile");
        formPanel.add(hardness);


        final Label labelAula = new Label("Aula*:");
        labelAula.getElement().setClassName("label");
        formPanel.add(labelAula);
        formPanel.add(labelAula);
        final TextBox aula = new TextBox();
        aula.getElement().setClassName("input");
        aula.setName("Aula");
        formPanel.add(aula);

        final Label labelNomeCorso = new Label("Corso*:");
        labelNomeCorso.getElement().setClassName("label");
        formPanel.add(labelNomeCorso);
        formPanel.add(labelNomeCorso);
        final TextBox corso = new TextBox();
        corso.getElement().setClassName("input");
        corso.setName("Corso");
        formPanel.add(corso);



        Button send = new Button("Inserisci");
        send.getElement().setClassName("btn-send");
        send.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                nuovoEsame.submit();
            }
        });
        formPanel.add(send);

        nuovoEsame.add(formPanel);

        nuovoEsame.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent submitEvent) {
                if (data.getValue().toString().length() == 0 || orario.getSelectedItemText().length() == 0 || hardness.getSelectedItemText().length() == 0 || aula.getText().length() == 0 || corso.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi");
                    submitEvent.cancel();
                }
            }
        });

        nuovoEsame.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {
                String[] input ={data.getValue().toString(),orario.getSelectedItemText().toString(),hardness.getSelectedItemText().toString(), aula.getText(), corso.getText()};
                serice.add(input, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore nell'insimento esame: "+ throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        Window.alert("user docente: " + docente.getUsername() + " " + s);
                    }
                });
            }
        });
        return nuovoEsame;
    }





}

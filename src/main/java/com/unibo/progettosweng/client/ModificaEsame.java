package com.unibo.progettosweng.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.unibo.progettosweng.client.model.Esame;
import com.unibo.progettosweng.client.model.Utente;

public class ModificaEsame implements Form{
    FormPanel editEsame;
    Esame esame;

    public ModificaEsame (Esame esame){
        this.esame = esame;
    }

    @Override
    public FormPanel getForm(){
        editEsame = new FormPanel();
        editEsame.addStyleName("formCreazioneUtente");
        editEsame.setAction("/modificaEsame");
        editEsame.setMethod(FormPanel.METHOD_POST);

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
        int i = 0;
        for (int h = 0; h < 24; h++){
            for (int m = 0; m < 60; m+=30){
                i++;
                if(h < 10){
                    if (m < 10){
                        String orarioText = "0"+h+":0"+m;
                        orario.addItem(orarioText);
                        if(esame.getOra().equals(orarioText)){orario.setItemSelected(i,true);}
                    }else {
                        String orarioText = "0" + h + ":" + m;
                        orario.addItem(orarioText);
                        if (esame.getOra().equals(orarioText)){orario.setItemSelected(i,true);}
                    }
                }else {
                    if (m < 10){
                        String orarioText = h+":0"+m;
                        orario.addItem(orarioText);
                        if (esame.getOra().equals(orarioText)){orario.setItemSelected(i,true);}
                    }else {
                        String orarioText = h+":"+m;
                        orario.addItem(h+":"+m);
                        if (esame.getOra().equals(orarioText)){orario.setItemSelected(i,true);}
                    }
                }
            }
        }

        formPanel.add(orario);

        final Label labelHardness = new Label("Difficoltà*:");
        labelHardness.getElement().setClassName("label");
        formPanel.add(labelHardness);
        final ListBox hardness = new ListBox();
        hardness.getElement().setClassName("input");
        hardness.addItem("");
        hardness.addItem("Facile");
        hardness.addItem("Medio");
        hardness.addItem("Difficile");
        switch (esame.getDifficolta()){
            case "Facile":
                hardness.setItemSelected(1,true);
                break;
            case "Medio":
                hardness.setItemSelected(2,true);
                break;
            case "Difficile":
                hardness.setItemSelected(3,true);
        }
        formPanel.add(hardness);


        final Label labelAula = new Label("Aula*:");
        labelAula.getElement().setClassName("label");
        formPanel.add(labelAula);
        formPanel.add(labelAula);
        final TextBox aula = new TextBox();
        aula.getElement().setClassName("input");
        aula.setValue(esame.getAula());
        aula.setName("Aula");
        formPanel.add(aula);

        final Label labelNomeCorso = new Label("Corso*:");
        labelNomeCorso.getElement().setClassName("label");
        formPanel.add(labelNomeCorso);
        formPanel.add(labelNomeCorso);
        final TextBox corso = new TextBox();
        corso.getElement().setClassName("input");
        corso.setValue(esame.getNomeCorso());
        corso.setName("Corso");
        formPanel.add(corso);



        Button send = new Button("Modifica");
        send.getElement().setClassName("btn-send");
        send.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                editEsame.submit();
            }
        });
        formPanel.add(send);

        editEsame.add(formPanel);

        editEsame.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent submitEvent) {
                if (data.getValue().toString().length() == 0 || orario.getSelectedItemText().length() == 0 || hardness.getSelectedItemText().length() == 0 || aula.getText().length() == 0 || corso.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi");
                    submitEvent.cancel();
                }
            }
        });

        editEsame.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {
                //to do
            }
        });
        return editEsame;
    }





}

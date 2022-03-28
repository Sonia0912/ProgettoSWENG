package com.unibo.progettosweng.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Utente;

import java.util.ArrayList;

public class InserimentoEsame implements Form{
    FormPanel nuovoEsame;

    private final EsameServiceAsync serviceEsami = GWT.create(EsameService.class);
    private final CorsoServiceAsync serviceCorsi = GWT.create(CorsoService.class);

    private Utente docente;
    private String nomeCorso;

    public InserimentoEsame(Utente docente, String nomeCorso){
        this.docente = docente;
        this.nomeCorso = nomeCorso;
    }

    @Override
    public FormPanel getForm() throws Exception {
        nuovoEsame = new FormPanel();
        nuovoEsame.addStyleName("formCreazioneUtente");
        nuovoEsame.setAction("/creaNuovoCorso");
        nuovoEsame.setMethod(FormPanel.METHOD_POST);

        VerticalPanel formPanel = new VerticalPanel();

        final Label labelData = new Label("Data*:");
        labelData.getElement().setClassName("label");
        formPanel.add(labelData);
        final DateBox data = new DateBox();
        DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
        data.setFormat(new DateBox.DefaultFormat(format));
        data.getElement().setClassName("input");
        formPanel.add(data);

        final Label labelOrario = new Label("Orario*:");
        labelOrario.getElement().setClassName("label");
        formPanel.add(labelOrario);
        final ListBox orario = new ListBox();
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

        final Label labelHardness = new Label("Difficoltà*:");
        labelHardness.getElement().setClassName("label");
        formPanel.add(labelHardness);
        final ListBox hardness = new ListBox();
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
        final ListBox corso = new ListBox();
        corso.setName(this.nomeCorso);
        corso.setName("Corso");
        corso.addItem("");
        //corso.setValue(this.nomeCorso);


        serviceCorsi.getCorsiDocente(docente.getUsername(), new AsyncCallback<ArrayList<Corso>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Errore in getCorsiDocente: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Corso> corsiDocente) {
                for (int i = 0; i < corsiDocente.size(); i++){
                    if(!corsiDocente.get(i).getEsameCreato()) {
                        corso.addItem(corsiDocente.get(i).getNomeCorso());
                    }
                }
                try {
                    serviceCorsi.getCorsiCoDocente(docente.getUsername(), new AsyncCallback<ArrayList<Corso>>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Errore in getCorsiDocente: " + throwable.getMessage());
                        }

                        @Override
                        public void onSuccess(ArrayList<Corso> corsiCoDocente) {
                            for (int i = 0; i < corsiCoDocente.size(); i++){
                                if(!corsiCoDocente.get(i).getEsameCreato()) {
                                    corso.addItem(corsiCoDocente.get(i).getNomeCorso());
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


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
                if (data.getValue() == null || orario.getSelectedItemText().length() == 0 || hardness.getSelectedItemText().length() == 0 || aula.getText().length() == 0 || corso.getItemCount() == 0) {
                    if(corso.getSelectedItemText().equals("")){
                        Window.alert("Devi prima selezionare un corso per registrare l'esame ");
                        submitEvent.cancel();
                    }else {
                        Window.alert("Compilare tutti i campi");
                        submitEvent.cancel();
                    }

                }
            }
        });

        nuovoEsame.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {
                String[] input ={format.format(data.getValue()).toString(),orario.getSelectedItemText(),hardness.getSelectedItemText(), aula.getText(), corso.getSelectedItemText()};
                serviceEsami.add(input, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore nell'inserimento dell'esame: "+ throwable.getMessage());
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

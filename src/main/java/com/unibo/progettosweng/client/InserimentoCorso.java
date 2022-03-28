package com.unibo.progettosweng.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.unibo.progettosweng.client.model.Utente;

import java.util.ArrayList;
import java.util.Queue;

public class InserimentoCorso implements Form {
    FormPanel nuovoCorso;
    Utente docente;
    private static UtenteServiceAsync service = GWT.create(UtenteService.class);
    private static CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);
    VerticalPanel spazioDinamico ;

    final static String[] DIPARTIMENTI = {"Matematica", "Fisica", "Chimica", "Informatica", "Ingegneria"};

    public InserimentoCorso(Utente docente, VerticalPanel spazioDinamico){
        this.docente = docente;
        this.spazioDinamico = spazioDinamico;
    }

    @Override
    public FormPanel getForm() throws Exception {
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
        final DateBox inizio = new DateBox();
        DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
        inizio.setFormat(new DateBox.DefaultFormat(format));
        inizio.getElement().setClassName("input");
        formPanel.add(inizio);

        final Label labelFine = new Label("Data di fine*:");
        labelFine.getElement().setClassName("label");
        formPanel.add(labelFine);
        final DateBox fine = new DateBox();
        fine.setFormat(new DateBox.DefaultFormat(format));
        fine.getElement().setClassName("input");
        formPanel.add(fine);

        final Label labelDescr = new Label("Breve descrizione del corso*:");
        labelDescr.getElement().setClassName("label");
        formPanel.add(labelDescr);
        final TextBox dipdescr = new TextBox();
        dipdescr.getElement().setClassName("input");
        dipdescr.setName("Descrizione");
        formPanel.add(dipdescr);

        final Label labelDip = new Label("Dipartimento*:");
        labelDip.getElement().setClassName("label");
        formPanel.add(labelDip);
        ListBox dip = new ListBox();
        dip.addItem("");
        for (int i = 0; i < DIPARTIMENTI.length; i++) {
            dip.addItem(DIPARTIMENTI[i]);
        }
        formPanel.add(dip);

        final Label labelCoDoc = new Label("Co-docente:");
        labelCoDoc.getElement().setClassName("label");
        formPanel.add(labelCoDoc);
        ListBox codoc = new ListBox();
        codoc.addItem("");
        service.getCodocenti(docente.getUsername(), new AsyncCallback<ArrayList<Utente>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Utente> codocenti) {
                for (int i = 0; i < codocenti.size(); i++){
                    codoc.addItem(codocenti.get(i).getUsername());
                }
            }
        });
        formPanel.add(codoc);

        formPanel.add(new HTML("<br />"));

        final CheckBox checkBoxEsame = new CheckBox("Crea esame");
        checkBoxEsame.addStyleName("checkBoxEsame");
        formPanel.add(checkBoxEsame);

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
                if (nome.getText().trim().length() == 0 || inizio.getValue() == null || fine.getValue() == null || dipdescr.getText().trim().length() == 0 || dip.getSelectedItemText() == "") {
                    Window.alert("Compilare tutti i campi");
                    submitEvent.cancel();
                }
            }
        });

        nuovoCorso.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {

                String[] info = {nome.getText(), format.format(inizio.getValue()).toString(), format.format(fine.getValue()).toString(),dipdescr.getText(), dip.getSelectedItemText(), docente.getUsername(), codoc.getSelectedItemText(), String.valueOf(checkBoxEsame.getValue())};
                serviceCorso.add(info, new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Errore nell'inserimento del corso "+ throwable.getMessage());
                        }
                        @Override
                        public void onSuccess(String s) {
                            Window.alert(s);
                            if( checkBoxEsame.getValue() ){
                                spazioDinamico.clear();
                                spazioDinamico.add(new HTML("<div class=\"titolettoPortale\"> Inserisci esame </div>"));
                                try {
                                    spazioDinamico.add((new InserimentoEsame(docente, nome.getText() )).getForm());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });


            }
        });
        return nuovoCorso;
    }





}

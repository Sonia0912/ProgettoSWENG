package com.unibo.progettosweng.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Utente;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ModificaCorso implements Form{
    Utente docente;
    Corso corso;
    FormPanel editCorso;
    private static UtenteServiceAsync serviceUtente = GWT.create(UtenteService.class);
    private static CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);

    public ModificaCorso(Utente docente, Corso corso){
        this.docente = docente;
        this.corso = corso;
    }

    @Override
    public FormPanel getForm(){
        editCorso = new FormPanel();
        editCorso.addStyleName("formCreazioneUtente");
        editCorso.setAction("/modificaCorso");
        editCorso.setMethod(FormPanel.METHOD_POST);


        VerticalPanel formPanel = new VerticalPanel();
         /*
        final Label labelNome = new Label("Nome del Corso*:");
        labelNome.getElement().setClassName("label");
        formPanel.add(labelNome);
        final TextBox nome = new TextBox();
        nome.getElement().setClassName("input");
        nome.setValue(corso.getNomeCorso());
        nome.setName("Nome");
        formPanel.add(nome);
         */

        final Label labelInizio = new Label("Data di inizio*:");
        labelInizio.getElement().setClassName("label");
        formPanel.add(labelInizio);
        final DateBox inizio = new DateBox();
        DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
        inizio.setFormat(new DateBox.DefaultFormat(format));
        Date dataInizio = format.parse(corso.getDataInizio());
        inizio.setValue(dataInizio);
        inizio.getElement().setClassName("input");
        formPanel.add(inizio);

        final Label labelFine = new Label("Data di fine*:");
        labelFine.getElement().setClassName("label");
        formPanel.add(labelFine);
        final DateBox fine = new DateBox();
        fine.setFormat(new DateBox.DefaultFormat(format));
        Date dataEsame = format.parse(corso.getDataFine());
        fine.setValue(dataEsame);
        fine.getElement().setClassName("input");
        formPanel.add(fine);

        final Label labelDescr = new Label("Breve descrizione del corso*:");
        labelDescr.getElement().setClassName("label");
        formPanel.add(labelDescr);
        final TextBox descr = new TextBox();
        descr.getElement().setClassName("input");
        descr.setValue(corso.getDescrizione());
        descr.setName("Descrizione");
        formPanel.add(descr);


        final Label labelCoDoc = new Label("Co-docente:");
        labelCoDoc.getElement().setClassName("label");
        formPanel.add(labelCoDoc);
        formPanel.add(labelCoDoc);
        ListBox codoc = new ListBox();
        codoc.addItem("");
        try {
            serviceUtente.getCodocenti(corso.getDocente(), new AsyncCallback<ArrayList<Utente>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    Window.alert("Failure: " + throwable.getMessage());
                }

                @Override
                public void onSuccess(ArrayList<Utente> codocenti) {
                    for (int i = 0; i < codocenti.size(); i++){
                        String nomeCodocente = codocenti.get(i).getUsername();
                        codoc.addItem(nomeCodocente);
                        if(corso.getCodocente().equals(nomeCodocente)){codoc.setItemSelected(i+1,true);}
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        formPanel.add(codoc);

        Button send = new Button("Modifica");
        send.getElement().setClassName("btn-send");
        send.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                editCorso.submit();
            }
        });
        formPanel.add(send);

        editCorso.add(formPanel);

        editCorso.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent submitEvent) {
                if (inizio.getValue().toString().length() == 0 || fine.getValue().toString().length() == 0 || descr.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi");
                    submitEvent.cancel();
                }
            }
        });

        editCorso.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {

                corso.setDataInizio(format.format(inizio.getValue()));
                corso.setDataFine(format.format(fine.getValue()));
                corso.setDescrizione( descr.getText() );
                corso.setCodocente( codoc.getSelectedItemText());

                try {
                        serviceCorso.aggiorna(corso, new AsyncCallback<Void>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Errore durante la modifica del corso");
                        }

                        @Override
                        public void onSuccess(Void v) {
                            Window.alert("Esame di "+ corso.getNomeCorso()+ " aggiornato corretamente");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return editCorso;
    }





}

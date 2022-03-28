package com.unibo.progettosweng.client;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Utente;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PortaleAdmin extends Portale {

    Utente admin = null;
    private static UtenteServiceAsync service = GWT.create(UtenteService.class);
    private static CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);

    /*private static ArrayList<Utente>  listaStudenti = new ArrayList<Utente>(Arrays.asList(
            new Utente("Luca", "Bianchi","lucabianchi@mail.com","123","studente"),
            new Utente("Sofia", "Neri","sofianeri@mail.com","0000","studente"),
            new Utente("Francesco", "Verdi","francescoverdi@mail.com","qwerty","studente")
    ));
    private static ArrayList<Utente>  listaDocenti = new ArrayList<Utente>(Arrays.asList(
            new Utente("Mario", "Rossi","mariorossi@mail.com","5678","docente"),
            new Utente("Giulia", "Gallo","giuliagallo@mail.com","acbde","docente"),
            new Utente("Tommaso", "Neri","tommasoneri@mail.com","asdfg","docente")
    ));*/

    @Override
    public void salvaCredenziali() {
        admin = super.utenteLoggato;
    }

    @Override
    public void caricaMenu() {
        Button btnStudenti = new Button("Studenti");
        Button btnDocenti = new Button("Docenti");
        Button btnCreaAccount = new Button("Crea account");
        btnStudenti.addStyleName("buttonMenuLaterale");
        btnDocenti.addStyleName("buttonMenuLaterale");
        btnCreaAccount.addStyleName("buttonMenuLaterale");
        btnStudenti.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaDefault();
            }
        });
        btnDocenti.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                try {
                    caricaDocenti();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnCreaAccount.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                try {
                    caricaCreaAccount();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        menuLaterale.add(btnStudenti);
        menuLaterale.add(btnDocenti);
        menuLaterale.add(btnCreaAccount);
    }

    @Override
    public void caricaDefault() {
        try {
            caricaStudenti();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void caricaStudenti() throws Exception {
        spazioDinamico.clear();
        service.getStudenti(new AsyncCallback<ArrayList<Utente>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Utente> studenti) {
                CellTable<Utente> tableStudenti = creaTabellaUtenti(studenti, "Non ci sono studenti registrati.");
                spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Studenti</div>"));
                spazioDinamico.add(tableStudenti);
            }
        });
    }

    public void caricaDocenti() throws Exception {
        spazioDinamico.clear();
        service.getDocenti(new AsyncCallback<ArrayList<Utente>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Utente> docenti) {
                CellTable<Utente> tableDocenti = creaTabellaUtenti(docenti, "Non ci sono docenti registrati.");
                spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Docenti</div>"));
                spazioDinamico.add(tableDocenti);
            }
        });
    }

    public void caricaCreaAccount() throws Exception {
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Inserisci un nuovo utente</div>"));
        spazioDinamico.add((new InserimentoUtente()).getForm());

    }

    public CellTable<Utente> creaTabellaUtenti(List<Utente> listaUtenti, String messaggioVuoto) {
        CellTable<Utente> tableStudenti = new CellTable<Utente>();
        tableStudenti.addStyleName("tablePortale");
        tableStudenti.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tableStudenti.setEmptyTableWidget(new Label(messaggioVuoto));

        TextColumn<Utente> nomeCol = new TextColumn<Utente>() {
            @Override
            public String getValue(Utente object) {
                return object.getNome();
            }
        };
        tableStudenti.addColumn(nomeCol, "Nome");

        TextColumn<Utente> cognomeCol = new TextColumn<Utente>() {
            @Override
            public String getValue(Utente object) {
                return object.getCognome();
            }
        };
        tableStudenti.addColumn(cognomeCol, "Cognome");

        TextColumn<Utente> usernameCol = new TextColumn<Utente>() {
            @Override
            public String getValue(Utente object) {
                return object.getUsername();
            }
        };
        tableStudenti.addColumn(usernameCol, "Username");

        ButtonCell corsiCell = new ButtonCell();
        Column<Utente, String> corsiCol = new Column<Utente, String>(corsiCell) {
            @Override
            public String getValue(Utente object) {
                return "Visualizza corsi";
            }
        };
        tableStudenti.addColumn(corsiCol, "");
        corsiCol.setCellStyleNames("btnTableStandard longer");
        corsiCol.setFieldUpdater(new FieldUpdater<Utente, String>() {
            @Override
            public void update(int index, Utente object, String value) {
                try {
                    visualizzaCorsi(object.getUsername(), object.getTipo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ButtonCell esamiCell = new ButtonCell();
        Column<Utente, String> esamiCol = new Column<Utente, String>(esamiCell) {
            @Override
            public String getValue(Utente object) {
                return "Visualizza esami";
            }
        };
        tableStudenti.addColumn(esamiCol, "");
        esamiCol.setCellStyleNames("btnTableStandard longer");
        esamiCol.setFieldUpdater(new FieldUpdater<Utente, String>() {
            @Override
            public void update(int index, Utente object, String value) {
                visualizzaEsami(object.getUsername(), object.getTipo());
            }
        });

        ButtonCell modificaCell = new ButtonCell();
        Column<Utente, String> modificaCol = new Column<Utente, String>(modificaCell) {
            @Override
            public String getValue(Utente object) {
                return "Modifica";
            }
        };
        tableStudenti.addColumn(modificaCol, "");
        modificaCol.setCellStyleNames("btnTableStandard");
        modificaCol.setFieldUpdater(new FieldUpdater<Utente, String>() {
            @Override
            public void update(int index, Utente object, String value) {
                spazioDinamico.clear();
                spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Modifica info utente</div>"));
                spazioDinamico.add((new ModificaInfoUtente(object)).getForm());

            }
        });

        tableStudenti.setRowCount(listaUtenti.size(), true);
        tableStudenti.setRowData(0, listaUtenti);
        return tableStudenti;
    }



    /*private FormPanel createForm(){
        FormPanel nuovoUtente = new FormPanel();
        nuovoUtente.addStyleName("formCreazioneUtente");
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

        final Label labelPassword = new Label("Password*:");
        labelPassword.getElement().setClassName("label");
        formPanel.add(labelPassword);
        final PasswordTextBox password = new PasswordTextBox();
        password.getElement().setClassName("input");
        password.setName("Password");
        formPanel.add(password);

        Button send = new Button("Inserisci");
        send.getElement().setClassName("btn-send");
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
                if (nome.getText().length() == 0 || cognome.getText().length() == 0 || tipo.getSelectedItemText().equals("") || email.getText().length() == 0 || password.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi");
                    submitEvent.cancel();
                }
            }
        });

        nuovoUtente.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {

                String [] info = {nome.getText(), cognome.getText(), email.getText(),password.getText(),tipo.getSelectedItemText()};
                service.add(info, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("faluire to create user " + throwable.getMessage());
                    }
                    @Override
                    public void onSuccess(String s) {
                        Window.alert(s);
                    }
                });
            }
        });

        return nuovoUtente;
    }*/

    private void visualizzaCorsi(String username, String tipo) throws Exception {
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Corsi</div>"));
        if(tipo.equalsIgnoreCase("studente")) {
            spazioDinamico.add(new HTML("<div class=\"listaPortaleIntro\"><b>" + username + "</b> è iscritto/a ai seguenti corsi: </div>"));
            String[] corsi = new String[]{"Algebra", "Analisi I", "Sistemi Operativi"};
            for(int i = 0; i < corsi.length; i++) {
                spazioDinamico.add(new HTML("<div class=\"listaPortale\"> - " + corsi[i] + "</div>"));
            }
            spazioDinamico.add(new HTML("</div>"));
        } else {
            spazioDinamico.add(new HTML("<div class=\"listaPortaleIntro\"><b>" + username + "</b> è un docente dei seguenti corsi: </div>"));
            serviceCorso.getCorsiDocente(username, new AsyncCallback<ArrayList<Corso>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    Window.alert("Failure: " + throwable.getMessage());
                }

                @Override
                public void onSuccess(ArrayList<Corso> listaCorsi) {
                    for (int i = 0; i < listaCorsi.size(); i++) {
                        spazioDinamico.add(new HTML("<div class=\"listaPortale\"> - " + listaCorsi.get(i).getNomeCorso() + "</div>"));
                    }
                    spazioDinamico.add(new HTML("<div class=\"listaPortaleIntro\"><b>" + username + "</b> è un co-docente dei seguenti corsi: </div>"));
                    try {
                        serviceCorso.getCorsiCoDocente(username, new AsyncCallback<ArrayList<Corso>>() {
                            @Override
                            public void onFailure(Throwable throwable) {

                            }

                            @Override
                            public void onSuccess(ArrayList<Corso> listaCorsi) {
                                for (int i = 0; i < listaCorsi.size(); i++) {
                                    spazioDinamico.add(new HTML("<div class=\"listaPortale\"> - " + listaCorsi.get(i).getNomeCorso() + "</div>"));
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }

    private void visualizzaEsami(String username, String tipo) {
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Esami</div>"));
        String[] esami;
        if(tipo.equalsIgnoreCase("studente")) {
            spazioDinamico.add(new HTML("<div class=\"listaPortaleIntro\"><b>" + username + "</b> si è registrato/a ai seguenti esami: </div>"));
            esami = new String[]{"Chimica", "Fisica nucleare", "Sistemi Operativi"};
        } else {
            spazioDinamico.add(new HTML("<div class=\"listaPortaleIntro\"><b>" + username + "</b> ha creato i seguenti esami: </div>"));
            esami = new String[]{"Fisica nucleare", "Sistemi Operativi"};
        }
        for(int i = 0; i < esami.length; i++) {
            spazioDinamico.add(new HTML("<div class=\"listaPortale\"> - " + esami[i] + "</div>"));
        }
    }

}

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
import com.unibo.progettosweng.client.model.*;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PortaleAdmin extends Portale {

    Utente admin = null;
    private static UtenteServiceAsync service = GWT.create(UtenteService.class);
    private static CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);
    private static EsameServiceAsync serviceEsame = GWT.create(EsameService.class);
    private static IscrizioneServiceAsync serviceIscrizione = GWT.create(IscrizioneService.class);
    private static RegistrazioneServiceAsync serviceRegistrazione = GWT.create(RegistrazioneService.class);

    @Override
    public void salvaCredenziali() {
        admin = super.utenteLoggato;
    }

    @Override
    public void caricaDati(String username) throws Exception {}

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
                try {
                    visualizzaEsami(object.getUsername(), object.getTipo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    private void visualizzaCorsi(String username, String tipo) throws Exception {
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Corsi</div>"));
        if(tipo.equalsIgnoreCase("studente")) {
            spazioDinamico.add(new HTML("<div class=\"listaPortaleIntro\"><b>" + username + "</b> è iscritto/a ai seguenti corsi: </div>"));
            serviceIscrizione.getIscrizioniStudente(username, new AsyncCallback<ArrayList<Iscrizione>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    Window.alert("Failure on getIscrizioniStudente: " + throwable.getMessage());
                }
                @Override
                public void onSuccess(ArrayList<Iscrizione> iscrizioni) {
                    for(int i = 0; i < iscrizioni.size(); i++) {
                        spazioDinamico.add(new HTML("<div class=\"listaPortale\"> - " + iscrizioni.get(i).getCorso() + "</div>"));
                    }
                    spazioDinamico.add(new HTML("</div>"));
                }
            });
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
                                Window.alert("Failure: " + throwable.getMessage());
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

    private void visualizzaEsami(String username, String tipo) throws Exception {
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Esami</div>"));
        if(tipo.equalsIgnoreCase("studente")) {
            spazioDinamico.add(new HTML("<div class=\"listaPortaleIntro\"><b>" + username + "</b> si è registrato/a ai seguenti esami: </div>"));
            //esami = new String[]{"Chimica", "Fisica nucleare", "Sistemi Operativi"};
            serviceRegistrazione.getRegistrazioniStudente(username, new AsyncCallback<ArrayList<Registrazione>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    Window.alert("Failure: " + throwable.getMessage());
                }

                @Override
                public void onSuccess(ArrayList<Registrazione> registrazioni) {
                    for (int i = 0; i < registrazioni.size(); i++) {
                        spazioDinamico.add(new HTML("<div class=\"listaPortale\"> - " + registrazioni.get(i).getCorso() + "</div>"));
                    }
                }
            });
        } else {
            spazioDinamico.add(new HTML("<div class=\"listaPortaleIntro\"><b>" + username + "</b> è assegnato come docente ai seguenti esami: </div>"));
            //esami = new String[]{"Fisica nucleare", "Sistemi Operativi"};
            serviceCorso.getCorsiDocente(username, new AsyncCallback<ArrayList<Corso>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    Window.alert("Failure: " + throwable.getMessage());
                }

                @Override
                public void onSuccess(ArrayList<Corso> corsi) {
                    try {
                        serviceEsame.getEsamiFromCorsi(corsi, new AsyncCallback<ArrayList<Esame>>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                Window.alert("Failure: " + throwable.getMessage());
                            }

                            @Override
                            public void onSuccess(ArrayList<Esame> esamiDocente) {
                                for (int i = 0; i < esamiDocente.size(); i++) {
                                    spazioDinamico.add(new HTML("<div class=\"listaPortale\"> - " + esamiDocente.get(i).getNomeCorso() + "</div>"));
                                }

                                try {
                                    serviceCorso.getCorsiCoDocente(username, new AsyncCallback<ArrayList<Corso>>() {
                                        @Override
                                        public void onFailure(Throwable throwable) {
                                            Window.alert("Failure: " + throwable.getMessage());
                                        }

                                        @Override
                                        public void onSuccess(ArrayList<Corso> corsiCodocente) {
                                            spazioDinamico.add(new HTML("<div class=\"listaPortaleIntro\"><b>" + username + "</b> è assegnato come co-docente ai seguenti esami: </div>"));
                                            try {
                                                serviceEsame.getEsamiFromCorsi(corsiCodocente, new AsyncCallback<ArrayList<Esame>>() {
                                                    @Override
                                                    public void onFailure(Throwable throwable) {
                                                        Window.alert("Failure: " + throwable.getMessage());
                                                    }

                                                    @Override
                                                    public void onSuccess(ArrayList<Esame> esamiCodocente) {
                                                        for (int i = 0; i < esamiCodocente.size(); i++) {
                                                            spazioDinamico.add(new HTML("<div class=\"listaPortale\"> - " + esamiCodocente.get(i).getNomeCorso() + "</div>"));
                                                        }
                                                    }
                                                });
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
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

}

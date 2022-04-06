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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.unibo.progettosweng.client.model.Iscrizione;
import com.unibo.progettosweng.client.model.Registrazione;
import com.unibo.progettosweng.client.model.Utente;
import com.unibo.progettosweng.client.model.Valutazione;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PortaleSegreteria extends Portale {

    Utente segreteria = null;
    private static UtenteServiceAsync service = GWT.create(UtenteService.class);
    private static IscrizioneServiceAsync serviceIscrizione = GWT.create(IscrizioneService.class);
    private static RegistrazioneServiceAsync serviceRegistrazione = GWT.create(RegistrazioneService.class);
    private static ValutazioneServiceAsync serviceValutazioni = GWT.create(ValutazioneService.class);

    //private static ArrayList<String[]> listaDaInserire;
    /*
            new ArrayList<String[]>(Arrays.asList(
            new String[]{"Sistemi Operativi", "lucabianchi@mail.com", "28"},
            new String[]{"Sistemi Operativi", "sofianeri@mail.com", "25"},
            new String[]{"Sistemi Operativi", "francescoverdi@mail.com", "30"},
            new String[]{"Algebra lineare", "lucabianchi@mail.com", "19"},
            new String[]{"Algebra lineare", "francescoverdi@mail.com", "23"}
    ));
     */

    private static ArrayList<String[]> listaDaPubblicare ;
    /*= new ArrayList<String[]>(Arrays.asList(
            new String[]{"Chimica", "lucabianchi@mail.com", "24"},
            new String[]{"Chimica", "francescoverdi@mail.com", "24"},
            new String[]{"Chimica", "sofianeri@mail.com", "24"},
            new String[]{"Algebra lineare", "sofianeri@mail.com", "26"}
    ));
     */

    @Override
    public void salvaCredenziali() {
        segreteria = super.utenteLoggato;
    }

    @Override
    public void caricaMenu() {
        Button btnStudenti = new Button("Studenti");
        Button btnInserimento = new Button("Inserisci voti");
        Button btnPubblicazione = new Button("Pubblica voti");
        btnStudenti.addStyleName("buttonMenuLaterale");
        btnInserimento.addStyleName("buttonMenuLaterale");
        btnPubblicazione.addStyleName("buttonMenuLaterale");
        btnStudenti.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                try {
                    caricaDefault();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnInserimento.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaInserimento();
            }
        });
        btnPubblicazione.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaPubblicazione();
            }
        });
        menuLaterale.add(btnStudenti);
        menuLaterale.add(btnInserimento);
        menuLaterale.add(btnPubblicazione);
    }

    @Override
    public void caricaDefault() throws Exception {
        caricaStudenti();
    }

    private void caricaStudenti() throws Exception {
        spazioDinamico.clear();
        service.getStudenti(new AsyncCallback<ArrayList<Utente>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Utente> studenti) {
                CellTable<Utente> tableStudenti = creaTabellaStudenti(studenti, "Non ci sono studenti registrati.");
                spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Studenti</div>"));
                spazioDinamico.add(tableStudenti);
            }
        });
    }

    private void caricaInserimento() {
        try {
            serviceValutazioni.getValutazioniDaInserire(new AsyncCallback<ArrayList<Valutazione>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    Window.alert("Errore durante ottenimento delle valutazioni da inserire");
                }

                @Override
                public void onSuccess(ArrayList<Valutazione> val) {
                    CellTable<Valutazione> tableVotiDaInserire = creaTabellaVoti(val, "Non ci sono voti da inserire, aspetta che un docente li invii.", true);
                    spazioDinamico.clear();
                    spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Voti da inserire</div>"));
                    spazioDinamico.add(tableVotiDaInserire);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void caricaPubblicazione() {

        try {
            serviceValutazioni.getValutazioniDaPubblicare(new AsyncCallback<ArrayList<Valutazione>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    Window.alert("Errore durante l'ottenimento della valutazioni da pubblicare");
                }

                @Override
                public void onSuccess(ArrayList<Valutazione> valPub) {

                    CellTable<Valutazione> tableVotiDaPubblicare = creaTabellaVoti(valPub, "Non ci sono voti da pubblicare, ricordati che prima devi inserirli.", false);
                    spazioDinamico.clear();
                    spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Voti da pubblicare</div>"));
                    spazioDinamico.add(tableVotiDaPubblicare);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public CellTable<Utente> creaTabellaStudenti(List<Utente> listaUtenti, String messaggioVuoto) {
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
                visualizzaCorsi(object.getUsername());
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
                visualizzaEsami(object.getUsername());
            }
        });

        ButtonCell votiCell = new ButtonCell();
        Column<Utente, String> votiCol = new Column<Utente, String>(votiCell) {
            @Override
            public String getValue(Utente object) {
                return "Visualizza voti";
            }
        };
        tableStudenti.addColumn(votiCol, "");
        votiCol.setCellStyleNames("btnTableStandard longer");
        votiCol.setFieldUpdater(new FieldUpdater<Utente, String>() {
            @Override
            public void update(int index, Utente object, String value) {
                visualizzaVoti(object.getUsername());
            }
        });

        tableStudenti.setRowCount(listaUtenti.size(), true);
        tableStudenti.setRowData(0, listaUtenti);
        return tableStudenti;
    }

    // Da modificare quando avremo l'oggetto Voto
    private CellTable<Valutazione> creaTabellaVoti(List<Valutazione> listaVoti, String messaggioVuoto, boolean daInserire) {
        CellTable<Valutazione> tableVoti = new CellTable<Valutazione>();
        tableVoti.addStyleName("tablePortale");
        tableVoti.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tableVoti.setEmptyTableWidget(new Label(messaggioVuoto));

        TextColumn<Valutazione> corsoCol = new TextColumn<Valutazione>() {
            @Override
            public String getValue(Valutazione object) {
                return object.getNomeCorso();
            }
        };
        tableVoti.addColumn(corsoCol, "Nome del corso");

        TextColumn<Valutazione> studenteCol = new TextColumn<Valutazione>() {
            @Override
            public String getValue(Valutazione object) {
                return object.getStudente();
            }
        };
        tableVoti.addColumn(studenteCol, "Username studente");

        TextColumn<Valutazione> votoCol = new TextColumn<Valutazione>() {
            @Override
            public String getValue(Valutazione object) {
                return String.valueOf(object.getVoto());
            }
        };
        tableVoti.addColumn(votoCol, "Voto");

        if(daInserire) {
            ButtonCell azioneCell = new ButtonCell();
            Column<Valutazione, String> azioneCol = new Column<Valutazione, String>(azioneCell) {
                @Override
                public String getValue(Valutazione object) {
                    return "Inserisci";
                }
            };
            tableVoti.addColumn(azioneCol, "");
            azioneCol.setCellStyleNames("btnTableStandard");
            azioneCol.setFieldUpdater(new FieldUpdater<Valutazione, String>() {
                @Override
                public void update(int index, Valutazione object, String value) {
                    try {
                        object.setStato(1);
                        serviceValutazioni.aggiorna(object, new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                Window.alert("Errore durante il cambiamento di stato");
                            }

                            @Override
                            public void onSuccess(Void val) {
                                Window.alert("Stato cambiato correttamente ");
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    caricaInserimento();
                }
            });
        } else {
            ButtonCell azioneCell = new ButtonCell();
            Column<Valutazione, String> azioneCol = new Column<Valutazione, String>(azioneCell) {
                @Override
                public String getValue(Valutazione object) {
                    return "Pubblica";
                }
            };
            tableVoti.addColumn(azioneCol, "");
            azioneCol.setCellStyleNames("btnTableStandard");
            azioneCol.setFieldUpdater(new FieldUpdater<Valutazione, String>() {
                @Override
                public void update(int index, Valutazione object, String value) {

                    try {
                        object.setStato(2);
                        serviceValutazioni.aggiorna(object, new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                Window.alert("Errore durante il cambiamento di stato");
                            }

                            @Override
                            public void onSuccess(Void v) {
                                Window.alert("Stato cambiato correttamente ");
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    caricaPubblicazione();
                }
            });
        }

        tableVoti.setRowCount(listaVoti.size(), true);
        tableVoti.setRowData(0, listaVoti);
        return tableVoti;
    }

    private void visualizzaCorsi(String username) {
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Corsi</div>"));
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
    }

    private void visualizzaEsami(String username) {
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Esami</div>"));
        spazioDinamico.add(new HTML("<div class=\"listaPortaleIntro\"><b>" + username + "</b> si è registrato/a ai seguenti esami: </div>"));
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
    }

    private void visualizzaVoti(String username) {
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Voti</div>"));
        spazioDinamico.add(new HTML("<div class=\"listaPortaleIntro\"><b>" + username + "</b> ha ottenuto i seguenti voti: </div>"));
        String[][] voti = {{"Algebra", "18"}, {"Sistemi Operativi", "23"}};
        for(int i = 0; i < voti.length; i++) {
            spazioDinamico.add(new HTML("<div class=\"listaPortale\"> - " + voti[i][0] + ": " + voti[i][1] + "</div>"));
        }
    }

}

package com.unibo.progettosweng.client;

import com.google.gwt.cell.client.*;
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
import org.checkerframework.checker.units.qual.C;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PortaleStudente extends Portale {

    private Utente studente = null;
    String nome = null;
    String cognome = null;
    String email = null;

    private static IscrizioneServiceAsync serviceIscrizione = GWT.create(IscrizioneService.class);
    private static CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);
    private static EsameServiceAsync serviceEsame = GWT.create(EsameService.class);
    private static RegistrazioneServiceAsync serviceRegistrazione = GWT.create(RegistrazioneService.class);

    private static ArrayList<String[]> listaVoti = new ArrayList<String[]>(Arrays.asList(
            new String[]{"Sistemi Operativi", "28"},
            new String[]{"Algebra lineare", "29"}
    ));

    @Override
    public void salvaCredenziali() {
        studente = super.utenteLoggato;
        nome = studente.getNome();
        cognome = studente.getCognome();
        email = studente.getUsername();
    }

    @Override
    public void caricaMenu() {
        Button btnProfilo = new Button("Profilo");
        Button btnMieiCorsi = new Button("I miei corsi");
        Button btnMieiEsami = new Button("I miei esami");
        Button btnCorsi = new Button("Esplora corsi");
        Button btnEsami = new Button("Prenota esame");
        Button btnVoti = new Button("Voti");
        btnProfilo.addStyleName("buttonMenuLaterale");
        btnMieiCorsi.addStyleName("buttonMenuLaterale");
        btnMieiEsami.addStyleName("buttonMenuLaterale");
        btnCorsi.addStyleName("buttonMenuLaterale");
        btnEsami.addStyleName("buttonMenuLaterale");
        btnVoti.addStyleName("buttonMenuLaterale");
        btnProfilo.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                try {
                    caricaDefault();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnMieiCorsi.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                try {
                    caricaMieiCorsi();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnMieiEsami.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                try {
                    caricaMieiEsami();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnCorsi.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                try {
                    caricaEsploraCorsi();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnEsami.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                try {
                    caricaPrenotaEsame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnVoti.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaVoti();
            }
        });
        menuLaterale.add(btnProfilo);
        menuLaterale.add(btnMieiCorsi);
        menuLaterale.add(btnMieiEsami);
        menuLaterale.add(btnCorsi);
        menuLaterale.add(btnEsami);
        menuLaterale.add(btnVoti);
    }

    @Override
    public void caricaDefault() throws Exception {
        caricaProfilo();
    }

    private void caricaProfilo() {
        spazioDinamico.clear();
        HTML infoPersonali = new HTML("<div class=\"infoPersonali\"><b>Nome: </b>" + nome
                + "<br /><b>Cognome: </b>" + cognome
                + "<br /><b>E-mail: </b>" + email + "</div>");
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Informazioni personali</div>"));
        spazioDinamico.add(infoPersonali);
    }

    private void caricaMieiCorsi() {
        spazioDinamico.clear();
        // Prendo le iscrizioni dello studente.
        serviceIscrizione.getIscrizioniStudente(email, new AsyncCallback<ArrayList<Iscrizione>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getIscrizioniStudente: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(ArrayList<Iscrizione> iscrizioni) {
                ArrayList<String> nomeCorsi = new ArrayList<>();
                for (int i = 0; i < iscrizioni.size(); i++){
                    nomeCorsi.add(iscrizioni.get(i).getCorso());
                }
                try {
                    // Prendo le informazioni dei corsi a cui e' iscritto.
                    serviceCorso.getListaCorsiIscrizioni(nomeCorsi, new AsyncCallback<ArrayList<Corso>>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Failure on getListaCorsiIscrizioni: " + throwable.getMessage() + " I corsi a cui è iscritto: " + nomeCorsi.toString());
                        }
                        @Override
                        public void onSuccess(ArrayList<Corso> corsiIscrittoOutput) {
                            CellTable<Corso> tableCorsi = creaTabellaCorsi(corsiIscrittoOutput, "Non sei iscritto a nessun corso.", false);
                            spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei corsi</div>"));
                            spazioDinamico.add(tableCorsi);                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void caricaMieiEsami() {
        spazioDinamico.clear();
        // Prendo gli esami a cui lo studente e' registrato.
        serviceRegistrazione.getRegistrazioniStudente(studente.getUsername(), new AsyncCallback<ArrayList<Registrazione>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getRegistrazioniStudente: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(ArrayList<Registrazione> registrazioni) {
                ArrayList<String> nomeCorsi = new ArrayList<>();
                for (int i = 0; i < registrazioni.size(); i++){
                    nomeCorsi.add(registrazioni.get(i).getCorso());
                }
                try {
                    // Prendo le informazioni sui corsi a cui e' registrato.
                    serviceCorso.getListaCorsiIscrizioni(nomeCorsi, new AsyncCallback<ArrayList<Corso>>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Failure on getListaCorsiIscrizioni: " + throwable.getMessage());
                        }
                        @Override
                        public void onSuccess(ArrayList<Corso> corsi) {
                            try {
                                // Prendo le informazioni sugli esami a cui e' registrato.
                                serviceEsame.getEsamiFromCorsi(corsi, new AsyncCallback<ArrayList<Esame>>() {
                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        Window.alert("Failure: " + throwable.getMessage());
                                    }
                                    @Override
                                    public void onSuccess(ArrayList<Esame> listaEsami) {
                                        CellTable<Esame> tableEsami = creaTabellaEsami(listaEsami, "Non hai prenotato nessun esame.", false);
                                        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei esami</div>"));
                                        spazioDinamico.add(tableEsami);
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

    private void caricaEsploraCorsi() throws Exception {
        serviceCorso.getCorsi(new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getCorsi: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Corso[] corsiTutti) {
                serviceIscrizione.getIscrizioniStudente(email, new AsyncCallback<ArrayList<Iscrizione>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Failure on getIscrizioniStudente: " + throwable.getMessage());
                    }
                    @Override
                    public void onSuccess(ArrayList<Iscrizione> iscrizioniStudente) {
                        List<Corso> corsiEsplorabili = new ArrayList<>(Arrays.asList(corsiTutti));
                        for (int i = 0; i < corsiTutti.length; i++){
                            for (int j = 0; j < iscrizioniStudente.size(); j++){
                                if(iscrizioniStudente.get(j).getCorso().equals(corsiTutti[i].getNomeCorso())){
                                    String daRimuovere = corsiTutti[i].getNomeCorso();
                                    corsiEsplorabili.removeIf(esame -> esame.getNomeCorso().equals(daRimuovere));
                                }
                            }
                        }
                        CellTable<Corso> tableCorsi = creaTabellaCorsi(corsiEsplorabili, "Non sono presenti corsi a cui puoi iscriverti.", true);
                        spazioDinamico.clear();
                        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Esplora i corsi</div>"));
                        spazioDinamico.add(tableCorsi);
                    }
                });
            }
        });
    }

    private void caricaPrenotaEsame() throws Exception {
        // Prendo tutti gli esami esistenti.
        serviceEsame.getEsami(new AsyncCallback<ArrayList<Esame>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(ArrayList<Esame> esamiTutti){
                // Prendo i corsi a cui lo studente e' iscritto.
                serviceIscrizione.getIscrizioniStudente(studente.getUsername(), new AsyncCallback<ArrayList<Iscrizione>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Failure on getIscrizioniStudente: " + throwable.getMessage());
                    }
                    @Override
                    public void onSuccess(ArrayList<Iscrizione> iscrizioniStudente) {
                        // Rimuovo da tutti gli esami quelli a cui lo studente non e' registrato.
                        ArrayList<Esame> esamiPrenotabili = new ArrayList<>();
                        for (int i = 0; i < esamiTutti.size(); i++) {
                            for (int j = 0; j < iscrizioniStudente.size(); j++) {
                                if(esamiTutti.get(i).getNomeCorso().equals(iscrizioniStudente.get(j).getCorso())) {
                                    esamiPrenotabili.add(esamiTutti.get(i));
                                }
                            }
                        }
                        // Prendo gli esami ai quali lo studente e' gia' registrato.
                        serviceRegistrazione.getRegistrazioniStudente(studente.getUsername(), new AsyncCallback<ArrayList<Registrazione>>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                Window.alert("Failure: " + throwable.getMessage());
                            }
                            @Override
                            public void onSuccess(ArrayList<Registrazione> esamiStudente) {
                                // Rimuovo dagli esami prenotabili quelli a cui lo studente e' gia' registrato.
                                List<Esame> esamiVisibili = new ArrayList<>(esamiPrenotabili);
                                for (int i = 0; i < esamiPrenotabili.size(); i++){
                                    for (int j = 0; j < esamiStudente.size(); j++){
                                        if(esamiStudente.get(j).getCorso().equals(esamiPrenotabili.get(i).getNomeCorso())){
                                            String daRimuovere = esamiPrenotabili.get(i).getNomeCorso();
                                            esamiVisibili.removeIf(esame -> esame.getNomeCorso().equals(daRimuovere));
                                        }
                                    }
                                }
                                CellTable<Esame> tableEsami = creaTabellaEsami(esamiVisibili, "Non sono presenti esami prenotabili (prova ad iscriverti prima ad un corso).", true);
                                spazioDinamico.clear();
                                spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Prenota un esame</div>"));
                                spazioDinamico.add(tableEsami);
                            }
                        });
                    }
                });
            }
        });
    }

    private void caricaVoti() {
        CellTable<String[]> tableVoti = creaTabellaVoti(listaVoti, "Non hai ancora nessun voto.");
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei voti</div>"));
        spazioDinamico.add(tableVoti);
    }

    private CellTable<Corso> creaTabellaCorsi(List<Corso> LISTCORSI, String messaggioVuoto, boolean selezionabile) {
        CellTable<Corso> tableCorsi = new CellTable<Corso>();
        tableCorsi.addStyleName("tablePortale");
        tableCorsi.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tableCorsi.setEmptyTableWidget(new Label(messaggioVuoto));

        TextColumn<Corso> nomeCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getNomeCorso();
            }
        };
        tableCorsi.addColumn(nomeCol, "Nome");

        TextColumn<Corso> periodoCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDataInizio() + " - " + object.getDataFine();
            }
        };
        tableCorsi.addColumn(periodoCol, "Periodo");

        TextColumn<Corso> descrizioneCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDescrizione();
            }
        };
        tableCorsi.addColumn(descrizioneCol, "Descrizione");

        TextColumn<Corso> dipCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDipartimento();
            }
        };
        tableCorsi.addColumn(dipCol, "Dipartimento");

        TextColumn<Corso> docCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDocente();
            }
        };
        tableCorsi.addColumn(docCol, "Docente");

        TextColumn<Corso> codocCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getCodocente();
            }
        };
        tableCorsi.addColumn(codocCol, "Co-docente");

        if(selezionabile) {
            ButtonCell iscrizioneCell = new ButtonCell();
            Column<Corso, String> iscrizioneCol = new Column<Corso, String>(iscrizioneCell) {
                @Override
                public String getValue(Corso object) {
                    return "Iscriviti";
                }
            };
            tableCorsi.addColumn(iscrizioneCol, "");
            iscrizioneCol.setCellStyleNames("btnTableStandard");
            iscrizioneCol.setFieldUpdater(new FieldUpdater<Corso, String>() {
                @Override
                public void update(int index, Corso object, String value) {
                    serviceIscrizione.add(email, object.getNomeCorso(), new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Failure on add Iscrizione: " + throwable.getMessage());
                        }
                        @Override
                        public void onSuccess(String output) {
                            Window.alert(output);
                            if(!output.equals("Ti sei già iscritto a questo corso")) {
/*                                corsiEsplorabili.remove(index);
                                corsiIscritto.add(object);*/
                                try {
                                    caricaEsploraCorsi();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            });
        }

        tableCorsi.setRowCount(LISTCORSI.size(), true);
        tableCorsi.setRowData(0, LISTCORSI);
        return tableCorsi;
    }

    private CellTable<Esame> creaTabellaEsami(List<Esame> listaEsami, String messaggioVuoto, boolean selezionabile) {
        CellTable<Esame> tableEsami = new CellTable<Esame>();
        tableEsami.addStyleName("tablePortale");
        tableEsami.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tableEsami.setEmptyTableWidget(new Label(messaggioVuoto));

        TextColumn<Esame> corsoCol = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getNomeCorso();
            }
        };
        tableEsami.addColumn(corsoCol, "Nome del corso");

        TextColumn<Esame> dataCol = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getData();
            }
        };
        tableEsami.addColumn(dataCol, "Data");

        TextColumn<Esame> oraCol = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getOra();
            }
        };
        tableEsami.addColumn(oraCol, "Ora");

        TextColumn<Esame> aulaCol = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getAula();
            }
        };
        tableEsami.addColumn(aulaCol, "Aula");

        TextColumn<Esame> diffCol = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getDifficolta();
            }
        };
        tableEsami.addColumn(diffCol, "Difficoltà");

        if(selezionabile) {
            ButtonCell prenotazioneCell = new ButtonCell();
            Column<Esame, String> prenotazioneCol = new Column<Esame, String>(prenotazioneCell) {
                @Override
                public String getValue(Esame object) {
                    return "Prenota";
                }
            };
            tableEsami.addColumn(prenotazioneCol, "");
            prenotazioneCol.setCellStyleNames("btnTableStandard");
            prenotazioneCol.setFieldUpdater(new FieldUpdater<Esame, String>() {
                @Override
                public void update(int index, Esame object, String value) {
                    serviceRegistrazione.add(studente.getUsername(), object.getNomeCorso(), new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Failure on add Esame: " + throwable.getMessage());
                        }
                        @Override
                        public void onSuccess(String s) {
                            Window.alert("Hai prenotato con successo l'esame di " + object.getNomeCorso());
                        }
                    });
                    try {
                        caricaPrenotaEsame();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        tableEsami.setRowCount(listaEsami.size(), true);
        tableEsami.setRowData(0, listaEsami);
        return tableEsami;
    }

    // Da modificare quando avremo l'oggetto Voto
    private CellTable<String[]> creaTabellaVoti(List<String[]> listaVoti, String messaggioVuoto) {
        CellTable<String[]> tableVoti = new CellTable<String[]>();
        tableVoti.addStyleName("tablePortale");
        tableVoti.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tableVoti.setEmptyTableWidget(new Label(messaggioVuoto));

        TextColumn<String[]> corsoCol = new TextColumn<String[]>() {
            @Override
            public String getValue(String[] object) {
                return object[0];
            }
        };
        tableVoti.addColumn(corsoCol, "Nome del corso");

        TextColumn<String[]> votoCol = new TextColumn<String[]>() {
            @Override
            public String getValue(String[] object) {
                return object[1];
            }
        };
        tableVoti.addColumn(votoCol, "Voto");

        tableVoti.setRowCount(listaVoti.size(), true);
        tableVoti.setRowData(0, listaVoti);
        return tableVoti;
    }

}

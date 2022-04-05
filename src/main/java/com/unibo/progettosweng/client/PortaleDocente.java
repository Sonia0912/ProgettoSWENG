package com.unibo.progettosweng.client;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextInputCell;
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

import com.google.gwt.user.client.ui.VerticalPanel;
import com.unibo.progettosweng.client.model.*;


import java.util.ArrayList;
import java.util.List;

public class PortaleDocente extends Portale {

    Utente docente = null;
    String nome = null;
    String cognome = null;
    String email = null;

    private static CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);
    private static EsameServiceAsync serviceEsame = GWT.create(EsameService.class);
    private static RegistrazioneServiceAsync serviceRegistrazione = GWT.create(RegistrazioneService.class);
    private static ValutazioneServiceAsync serviceValutazione = GWT.create(ValutazioneService.class);

    @Override
    public void salvaCredenziali() {
        docente = super.utenteLoggato;
        nome = docente.getNome();
        cognome = docente.getCognome();
        email = docente.getUsername();
    }

    @Override
    public void caricaMenu() {
        Button btnProfilo = new Button("Profilo");
        Button btnCorsi = new Button("Corsi");
        Button btnEsami = new Button("Esami");
        Button btnValutazioni = new Button("Valutazioni");
        btnValutazioni.addStyleName("buttonMenuLaterale");
        btnProfilo.addStyleName("buttonMenuLaterale");
        btnCorsi.addStyleName("buttonMenuLaterale");
        btnEsami.addStyleName("buttonMenuLaterale");
        btnProfilo.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaDefault();
            }
        });
        btnCorsi.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                try {
                    caricaCorsi();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnEsami.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                try {
                    caricaEsami();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        menuLaterale.add(btnProfilo);
        menuLaterale.add(btnCorsi);
        menuLaterale.add(btnEsami);
    }

    @Override
    public void caricaDefault() {
        caricaProfilo();
    }

    public void caricaProfilo() {
        HTML infoPersonali = new HTML("<div class=\"infoPersonali\"><b>Nome: </b>" + nome
                + "<br /><b>Cognome: </b>" + cognome
                + "<br /><b>E-mail: </b>" + email + "</div>");
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Informazioni personali</div>"));
        spazioDinamico.add(infoPersonali);
    }

    public void caricaCorsi() throws Exception {
        spazioDinamico.clear();
        serviceCorso.getCorsiDocente(docente.getUsername(), new AsyncCallback<ArrayList<Corso>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getCorsiDocente: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(ArrayList<Corso> listaCorsi) {
                spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei corsi da docente</div>"));
                CellTable<Corso> tableCorsi = creaTabellaCorsi(listaCorsi, "Non hai ancora creato nessun corso.", false);
                spazioDinamico.add(tableCorsi);
                try {
                    spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei corsi da co-docente</div>"));
                    serviceCorso.getCorsiCoDocente(docente.getUsername(), new AsyncCallback<ArrayList<Corso>>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Failure on getCorsiCoDocente: " + throwable.getMessage());
                        }
                        @Override
                        public void onSuccess(ArrayList<Corso> listaCorsi) {
                            CellTable<Corso> tableCoCorsi = creaTabellaCorsi(listaCorsi, "Non sei stato assegnato come co-docente a nessun corso.", true);
                            spazioDinamico.add(tableCoCorsi);
                            Button btnCreaCorso = new Button("Crea corso");
                            btnCreaCorso.addStyleName("btnCreazione");
                            btnCreaCorso.addClickHandler(new ClickHandler() {
                                @Override
                                public void onClick(ClickEvent clickEvent) {
                                    spazioDinamico.clear();
                                    spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Crea un nuovo corso</div>"));
                                    try {
                                        spazioDinamico.add((new InserimentoCorso(docente, spazioDinamico)).getForm());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            spazioDinamico.add(btnCreaCorso);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void caricaEsami() throws Exception {
        spazioDinamico.clear();
        serviceCorso.getCorsiDocente(docente.getUsername(), new AsyncCallback<ArrayList<Corso>>() {
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
                        public void onSuccess(ArrayList<Esame> listaEsami) {
                            CellTable<Esame> tableEsamiDocente = creaTabellaEsami(listaEsami, "Non hai ancora creato nessun esame.");
                            spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei esami da docente</div>"));
                            spazioDinamico.add(tableEsamiDocente);
                            try {
                                serviceCorso.getCorsiCoDocente(docente.getUsername(), new AsyncCallback<ArrayList<Corso>>() {
                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        Window.alert("Failure: " + throwable.getMessage());
                                    }

                                    @Override
                                    public void onSuccess(ArrayList<Corso> corsiCodocente) {
                                        try {
                                            serviceEsame.getEsamiFromCorsi(corsiCodocente, new AsyncCallback<ArrayList<Esame>>() {
                                                @Override
                                                public void onFailure(Throwable throwable) {
                                                    Window.alert("Failure: " + throwable.getMessage());
                                                }

                                                @Override
                                                public void onSuccess(ArrayList<Esame> listaEsamiCoDoc) {
                                                    CellTable<Esame> tableEsamiDocente = creaTabellaEsami(listaEsamiCoDoc, "Non hai ancora creato nessun esame.");
                                                    spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei esami da co-docente</div>"));
                                                    spazioDinamico.add(tableEsamiDocente);

                                                    Button btnCreaEsame = new Button("Crea esame");
                                                    btnCreaEsame.addClickHandler(new ClickHandler() {
                                                        @Override
                                                        public void onClick(ClickEvent clickEvent) {
                                                            spazioDinamico.clear();
                                                            spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Crea un nuovo esame</div>"));
                                                            try {
                                                                spazioDinamico.add((new InserimentoEsame(docente, "").getForm()));
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    });
                                                    btnCreaEsame.addStyleName("btnCreazione");
                                                    spazioDinamico.add(btnCreaEsame);
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

    private CellTable<Corso> creaTabellaCorsi(List<Corso> LISTCORSI, String messaggioVuoto, boolean codocente) {
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

        if(!codocente) {
            TextColumn<Corso> codocCol = new TextColumn<Corso>() {
                @Override
                public String getValue(Corso object) {
                    return object.getCodocente();
                }
            };
            tableCorsi.addColumn(codocCol, "Co-Docente");
        } else {
            TextColumn<Corso> docCol = new TextColumn<Corso>() {
                @Override
                public String getValue(Corso object) {
                    return object.getDocente();
                }
            };
            tableCorsi.addColumn(docCol, "Docente");
        }

        ButtonCell modificaCell = new ButtonCell();
        Column<Corso, String> modificaCol = new Column<Corso, String>(modificaCell) {
            @Override
            public String getValue(Corso object) {
                return "Modifica";
            }
        };
        tableCorsi.addColumn(modificaCol, "");
        modificaCol.setCellStyleNames("btnTableStandard");
        modificaCol.setFieldUpdater(new FieldUpdater<Corso, String>() {
            @Override
            public void update(int index, Corso object, String value) {
                spazioDinamico.clear();
                spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Modifica corso</div>"));
                spazioDinamico.add((new ModificaCorso(docente, object)).getForm());

            }
        });

        ButtonCell eliminaCell = new ButtonCell();
        Column<Corso, String> eliminaCol = new Column<Corso, String>(eliminaCell) {
            @Override
            public String getValue(Corso object) {
                return "Cancella";
            }
        };
        tableCorsi.addColumn(eliminaCol, "");
        eliminaCol.setCellStyleNames("btnElimina");
        eliminaCol.setFieldUpdater(new FieldUpdater<Corso, String>() {
            @Override
            public void update(int index, Corso object, String value) {
                serviceCorso.remove(object.getNomeCorso(), new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore durante l'eliminazione del corso " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        Window.alert(s);
                        try {
                            caricaCorsi();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        tableCorsi.setRowCount(LISTCORSI.size(), true);
        tableCorsi.setRowData(0, LISTCORSI);
        return tableCorsi;
    }

    private CellTable<Esame> creaTabellaEsami(List<Esame> listaEsami, String messaggioVuoto) {
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

        ButtonCell votiCell = new ButtonCell();
        Column<Esame, String> votiCol = new Column<Esame, String>(votiCell) {
            @Override
            public String getValue(Esame object) {
                return "Gestisci voti";
            }
        };
        tableEsami.addColumn(votiCol, "");
        votiCol.setCellStyleNames("btnTableStandard long");
        votiCol.setFieldUpdater(new FieldUpdater<Esame, String>() {
            @Override
            public void update(int index, Esame object, String value) {
                serviceRegistrazione.getRegistrazioniFromEsame(object.getNomeCorso(), new AsyncCallback<ArrayList<Registrazione>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Failure: " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(ArrayList<Registrazione> registrazioni) {
                        serviceValutazione.getValutazioniFromEsame(object.getNomeCorso(), new AsyncCallback<ArrayList<Valutazione>>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                Window.alert("Failure: " + throwable.getMessage());
                            }

                            @Override
                            public void onSuccess(ArrayList<Valutazione> valutazioni) {
                                spazioDinamico.clear();
                                VerticalPanel tableValutazioni = creaTabellaValutazioni(registrazioni, valutazioni, "Non ci sono iscritti da valutare per questo esame.");
                                spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Inserisci valutazioni per l'esame di " + object.getNomeCorso() +"</div>"));
                                spazioDinamico.add(tableValutazioni);
                                CellTable<Valutazione> tableModificaValutazioni = creaTabellaModificaValutazioni(valutazioni,"Non ci sono valutazioni da modificare");
                                spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Modifica voti</div>"));
                                spazioDinamico.add(tableModificaValutazioni);
                            }
                        });
                    }
                });
            }
        });

        ButtonCell modificaCell = new ButtonCell();
        Column<Esame, String> modificaCol = new Column<Esame, String>(modificaCell) {
            @Override
            public String getValue(Esame object) {
                return "Modifica";
            }
        };
        tableEsami.addColumn(modificaCol, "");
        modificaCol.setCellStyleNames("btnTableStandard");
        modificaCol.setFieldUpdater(new FieldUpdater<Esame, String>() {
            @Override
            public void update(int index, Esame object, String value) {
                spazioDinamico.clear();
                spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Modifica esame</div>"));
                spazioDinamico.add((new ModificaEsame(object)).getForm());
            }
        });

        ButtonCell eliminaCell = new ButtonCell();
        Column<Esame, String> eliminaCol = new Column<Esame, String>(eliminaCell) {
            @Override
            public String getValue(Esame object) {
                return "Cancella";
            }
        };
        tableEsami.addColumn(eliminaCol, "");
        eliminaCol.setCellStyleNames("btnElimina");
        eliminaCol.setFieldUpdater(new FieldUpdater<Esame, String>() {
            @Override
            public void update(int index, Esame object, String value) {
                serviceEsame.remove(object.getNomeCorso(), new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore durante l'eliminazione dell'esame di " + object.getNomeCorso() + " -> "+ throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        Window.alert(s);
                        try {
                            serviceCorso.getCorso(object.getNomeCorso(), new AsyncCallback<Corso>() {
                                @Override
                                public void onFailure(Throwable throwable) {
                                    Window.alert("Errore durante ottenimento del corso " + throwable.getMessage());
                                }

                                @Override
                                public void onSuccess(Corso corso) {
                                    corso.setEsameCreato(false);

                                    try {
                                        serviceCorso.aggiorna(corso, new AsyncCallback<Void>() {
                                            @Override
                                            public void onFailure(Throwable throwable) {
                                                Window.alert("Errore durante aggiornamento corso " + throwable.getMessage());
                                            }

                                            @Override
                                            public void onSuccess(Void unused) {
                                                try {
                                                    caricaEsami();
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
        });

        tableEsami.setRowCount(listaEsami.size(), true);
        tableEsami.setRowData(0, listaEsami);
        return tableEsami;
    }

    private VerticalPanel creaTabellaValutazioni(List<Registrazione> listaRegistrazioni, List<Valutazione> valutazioniOld, String messaggioVuoto) {
        VerticalPanel vp = new VerticalPanel();
        CellTable<Registrazione> tableRegistrazioni = new CellTable<Registrazione>();
        for (int i = 0; i < listaRegistrazioni.size(); i++){
            for (int j = 0; j < valutazioniOld.size(); j++){
                if (listaRegistrazioni.get(i).getStudente().equals(valutazioniOld.get(j).getStudente()) && listaRegistrazioni.get(i).getCorso().equals(valutazioniOld.get(j).getNomeCorso())) {
                    listaRegistrazioni.remove(listaRegistrazioni.get(i));
                }
            }
        }
        ArrayList<String[]> valutazioni = new ArrayList<>();
        tableRegistrazioni.addStyleName("tablePortale");
        tableRegistrazioni.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tableRegistrazioni.setEmptyTableWidget(new Label(messaggioVuoto));

        TextColumn<Registrazione> usernameCol = new TextColumn<Registrazione>() {
            @Override
            public String getValue(Registrazione object) {
                return object.getStudente();
            }
        };
        tableRegistrazioni.addColumn(usernameCol, "Username studente");

        final TextInputCell valCell = new TextInputCell();
        Column<Registrazione, String> nameColumn = new Column<Registrazione, String>(valCell) {
            @Override
            public String getValue(Registrazione object) {
                // Return the name as the value of this column.
                return "";
            }
        };
        tableRegistrazioni.addColumn(nameColumn, "Valutazione");
        nameColumn.setFieldUpdater(new FieldUpdater<Registrazione, String>() {
            @Override
            public void update(int index, Registrazione object, String value) {
                if (Integer.parseInt(value) < 18 || Integer.parseInt(value) > 30){
                    Window.alert("Il voto deve essere compreso tra 18 e 30");
                }
                valutazioni.add(new String[]{object.getCorso(), object.getStudente(), value, "0"});
            }
        });

        Button btnInviaVoti = new Button("Invia voti");
        btnInviaVoti.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                serviceValutazione.addMore(valutazioni, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore durante l'invio dei voti " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        Window.alert(s);
                        try {
                            caricaEsami();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        btnInviaVoti.addStyleName("btnCreazione");

        tableRegistrazioni.setRowCount(listaRegistrazioni.size(), true);
        tableRegistrazioni.setRowData(0, listaRegistrazioni);

        vp.add(tableRegistrazioni);
        vp.add(btnInviaVoti);
        return vp;
    }

    private CellTable<Valutazione> creaTabellaModificaValutazioni(List<Valutazione> listaValutazioni, String messaggioVuoto) {
        CellTable<Valutazione> tableValutazioni = new CellTable<Valutazione>();
        tableValutazioni.addStyleName("tablePortale");
        tableValutazioni.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tableValutazioni.setEmptyTableWidget(new Label(messaggioVuoto));

        TextColumn<Valutazione> usernameCol = new TextColumn<Valutazione>() {
            @Override
            public String getValue(Valutazione object) {
                return object.getStudente();
            }
        };
        tableValutazioni.addColumn(usernameCol, "Username studente");

        TextColumn<Valutazione> votoCol = new TextColumn<Valutazione>() {
            @Override
            public String getValue(Valutazione object) {
                return String.valueOf(object.getVoto());
            }
        };
        tableValutazioni.addColumn(votoCol, "Voto");

        ButtonCell modificaCell = new ButtonCell();
        Column<Valutazione, String> modificaCol = new Column<Valutazione, String>(modificaCell) {
            @Override
            public String getValue(Valutazione object) {
                return "Modifica";
            }
        };
        tableValutazioni.addColumn(modificaCol, "");
        modificaCol.setCellStyleNames("btnTableStandard");
        modificaCol.setFieldUpdater(new FieldUpdater<Valutazione, String>() {
            @Override
            public void update(int index, Valutazione object, String value) {
                spazioDinamico.clear();
                spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Modifica voto</div>"));
                for(int i = 0; i < listaValutazioni.size(); i++){
                    if(object.getNomeCorso().equals(listaValutazioni.get(i).getNomeCorso()) && object.getStudente().equals(listaValutazioni.get(i).getStudente())){
                        try {
                            spazioDinamico.add((new ModificaValutazione(listaValutazioni.get(i)).getForm()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

        tableValutazioni.setRowCount(listaValutazioni.size(), true);
        tableValutazioni.setRowData(0, listaValutazioni);

        return tableValutazioni;
    }
}

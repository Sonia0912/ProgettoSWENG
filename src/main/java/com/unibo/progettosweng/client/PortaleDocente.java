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

import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Esame;
import com.unibo.progettosweng.client.model.Utente;


import java.util.ArrayList;
import java.util.List;

public class PortaleDocente extends Portale {

    Utente docente = null;
    String nome = null;
    String cognome = null;
    String email = null;

    private static CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);
    private static EsameServiceAsync serviceEsame = GWT.create(EsameService.class);

    /*private static ArrayList<Corso> listaCorsi = new ArrayList<Corso>(Arrays.asList(
            new Corso("Sistemi Operativi", "24/04/2022", "06/06/2022", "Un corso sull'informatica.","info","doc","c", false),
            new Corso("Analisi I", "26/04/2022", "15/06/2022", "Logaritmi e derivate.","info","doc","c", false),
            new Corso("Algebra lineare", "12/03/2022", "17/05/2022", "Tutto sulle matrici.","info","doc","c", false)));
*/
    /*private static List<Esame> listaEsami = new ArrayList<Esame>(Arrays.asList(
            new Esame("17/06/2022", "15:30", "Medio", "Aula Tonelli", "Sistemi Operativi"),
            new Esame("22/06/2022", "09:40", "Difficile", "Aula Verdi", "Analisi I")));
*/
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

        btnValutazioni.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                try {
                    caricaValutazioni();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        menuLaterale.add(btnProfilo);
        menuLaterale.add(btnCorsi);
        menuLaterale.add(btnEsami);
        menuLaterale.add(btnValutazioni);
    }

    private void caricaValutazioni() {

        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Crea una nuova valutazioni </div>"));
        try {
            spazioDinamico.add((new InserimentoValutazione(docente)).getForm());
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                    Window.alert("Vuoi eliminare il corso di " + object.getNomeCorso());
                }
            });
        } else {
            TextColumn<Corso> docCol = new TextColumn<Corso>() {
                @Override
                public String getValue(Corso object) {
                    return object.getDocente();
                }
            };
            tableCorsi.addColumn(docCol, "Docente");
        }

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
                return "Inserisci voti";
            }
        };
        tableEsami.addColumn(votiCol, "");
        votiCol.setCellStyleNames("btnTableStandard long");
        votiCol.setFieldUpdater(new FieldUpdater<Esame, String>() {
            @Override
            public void update(int index, Esame object, String value) {
                Window.alert("Vuoi inserire i voti dell'esame di " + object.getNomeCorso());
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
                Window.alert("Hai eliminato con successo l'esame di " + object.getNomeCorso());
            }
        });

        tableEsami.setRowCount(listaEsami.size(), true);
        tableEsami.setRowData(0, listaEsami);
        return tableEsami;
    }
}

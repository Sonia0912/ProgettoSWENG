package com.unibo.progettosweng.client;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.unibo.progettosweng.client.model.Utente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PortaleSegreteria extends Portale {

    Utente segreteria = null;

    private static ArrayList<Utente> listaStudenti = new ArrayList<Utente>(Arrays.asList(
            new Utente("Luca", "Bianchi","lucabianchi@mail.com","123","studente"),
            new Utente("Sofia", "Neri","sofianeri@mail.com","0000","studente"),
            new Utente("Francesco", "Verdi","francescoverdi@mail.com","qwerty","studente")
    ));

    private static ArrayList<String[]> listaDaInserire = new ArrayList<String[]>(Arrays.asList(
            new String[]{"Sistemi Operativi", "lucabianchi@mail.com", "28"},
            new String[]{"Sistemi Operativi", "sofianeri@mail.com", "25"},
            new String[]{"Sistemi Operativi", "francescoverdi@mail.com", "30"},
            new String[]{"Algebra lineare", "lucabianchi@mail.com", "19"},
            new String[]{"Algebra lineare", "francescoverdi@mail.com", "23"}
    ));

    private static ArrayList<String[]> listaDaPubblicare = new ArrayList<String[]>(Arrays.asList(
            new String[]{"Chimica", "lucabianchi@mail.com", "24"},
            new String[]{"Chimica", "francescoverdi@mail.com", "24"},
            new String[]{"Chimica", "sofianeri@mail.com", "24"},
            new String[]{"Algebra lineare", "sofianeri@mail.com", "26"}
    ));

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
                caricaDefault();
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
    public void caricaDefault() {
        caricaStudenti();
    }

    private void caricaStudenti() {
        CellTable<Utente> tableStudenti = creaTabellaStudenti(listaStudenti, "Non ci sono studenti registrati.");
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Studenti</div>"));
        spazioDinamico.add(tableStudenti);
    }

    private void caricaInserimento() {
        CellTable<String[]> tableVotiDaInserire = creaTabellaVoti(listaDaInserire, "Non ci sono voti da inserire, aspetta che un docente li invii.", true);
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Voti da inserire</div>"));
        spazioDinamico.add(tableVotiDaInserire);
    }

    private void caricaPubblicazione() {
        CellTable<String[]> tableVotiDaPubblicare = creaTabellaVoti(listaDaPubblicare, "Non ci sono voti da pubblicare, ricordati che prima devi inserirli.", false);
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Voti da pubblicare</div>"));
        spazioDinamico.add(tableVotiDaPubblicare);
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
                Window.alert("Vuoi vedere i corsi di " + object.getUsername());
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
                Window.alert("Vuoi vedere gli esami di " + object.getUsername());
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
                Window.alert("Vuoi visualizzare i voti di " + object.getUsername());
            }
        });

        tableStudenti.setRowCount(listaUtenti.size(), true);
        tableStudenti.setRowData(0, listaUtenti);
        return tableStudenti;
    }

    // Da modificare quando avremo l'oggetto Voto
    private CellTable<String[]> creaTabellaVoti(List<String[]> listaVoti, String messaggioVuoto, boolean daInserire) {
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

        TextColumn<String[]> studenteCol = new TextColumn<String[]>() {
            @Override
            public String getValue(String[] object) {
                return object[1];
            }
        };
        tableVoti.addColumn(studenteCol, "Username studente");

        TextColumn<String[]> votoCol = new TextColumn<String[]>() {
            @Override
            public String getValue(String[] object) {
                return object[2];
            }
        };
        tableVoti.addColumn(votoCol, "Voto");

        if(daInserire) {
            ButtonCell azioneCell = new ButtonCell();
            Column<String[], String> azioneCol = new Column<String[], String>(azioneCell) {
                @Override
                public String getValue(String[] object) {
                    return "Inserisci";
                }
            };
            tableVoti.addColumn(azioneCol, "");
            azioneCol.setCellStyleNames("btnTableStandard");
            azioneCol.setFieldUpdater(new FieldUpdater<String[], String>() {
                @Override
                public void update(int index, String[] object, String value) {
                    //Window.alert("Vuoi inserire il voto di " + object[1] + " per l'esame di " + object[0]);
                    String[] selezionato = {object[0], object[1], object[2]};
                    listaDaInserire.removeIf(esame -> esame[0].equals(object[0]) && esame[1].equals(object[1]) && esame[2].equals(object[2]));
                    listaDaPubblicare.add(selezionato);
                    caricaInserimento();
                }
            });
        } else {
            ButtonCell azioneCell = new ButtonCell();
            Column<String[], String> azioneCol = new Column<String[], String>(azioneCell) {
                @Override
                public String getValue(String[] object) {
                    return "Pubblica";
                }
            };
            tableVoti.addColumn(azioneCol, "");
            azioneCol.setCellStyleNames("btnTableStandard");
            azioneCol.setFieldUpdater(new FieldUpdater<String[], String>() {
                @Override
                public void update(int index, String[] object, String value) {
                    //Window.alert("Vuoi pubblicare il voto di " + object[1] + " per l'esame di " + object[0]);
                    String[] selezionato = {object[0], object[1], object[2]};
                    listaDaPubblicare.removeIf(esame -> esame[0].equals(object[0]) && esame[1].equals(object[1]) && esame[2].equals(object[2]));
                    caricaPubblicazione();
                }
            });
        }

        tableVoti.setRowCount(listaVoti.size(), true);
        tableVoti.setRowData(0, listaVoti);
        return tableVoti;
    }

}

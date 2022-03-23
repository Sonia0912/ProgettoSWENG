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

    private static ArrayList<Utente> listaStudenti = new ArrayList<Utente>(Arrays.asList(
            new Utente("Luca", "Bianchi","lucabianchi@mail.com","123","studente"),
            new Utente("Sofia", "Neri","sofianeri@mail.com","0000","studente"),
            new Utente("Francesco", "Verdi","francescoverdi@mail.com","qwerty","studente")
    ));

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
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Voti da inserire</div>"));
    }

    private void caricaPubblicazione() {
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Voti da pubblicare</div>"));
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



}

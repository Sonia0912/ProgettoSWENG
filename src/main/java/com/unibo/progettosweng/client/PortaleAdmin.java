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
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Esame;
import com.unibo.progettosweng.client.model.Utente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PortaleAdmin extends Portale {

    private static ArrayList<Utente>  listaStudenti = new ArrayList<Utente>(Arrays.asList(
            new Utente("Luca", "Bianchi","lucabianchi@mail.com","123","studente"),
            new Utente("Sofia", "Neri","sofianeri@mail.com","0000","studente"),
            new Utente("Francesco", "Verdi","francescoverdi@mail.com","qwerty","studente")
    ));

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
                caricaDocenti();
            }
        });
        btnCreaAccount.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaCreaAccount();
            }
        });
        menuLaterale.add(btnStudenti);
        menuLaterale.add(btnDocenti);
        menuLaterale.add(btnCreaAccount);
    }

    @Override
    public void caricaDefault() {
        caricaStudenti();
    }

    public void caricaStudenti() {
        spazioDinamico.clear();
        CellTable<Utente> tableStudenti = creaTabellaStudenti(listaStudenti, "Non ci sono studenti registrati.");
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Studenti</div>"));
        spazioDinamico.add(tableStudenti);
    }

    public void caricaDocenti() {
        spazioDinamico.clear();
    }

    public void caricaCreaAccount() {
        spazioDinamico.clear();

    }

    public CellTable<Utente> creaTabellaStudenti(List<Utente> listaStudenti, String messaggioVuoto) {
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
                Window.alert("Vuoi modificare i dati di " + object.getUsername());
            }
        });

        tableStudenti.setRowCount(listaStudenti.size(), true);
        tableStudenti.setRowData(0, listaStudenti);
        return tableStudenti;
    }


}

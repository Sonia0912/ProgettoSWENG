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

public class PortaleDocente extends Portale {

    Utente docente = null;
    String nome = null;
    String cognome = null;
    String email = null;

    private static ArrayList<Corso> listaCorsi = new ArrayList<Corso>(Arrays.asList(
            new Corso("Sistemi Operativi", "24/04/2022", "06/06/2022", "Un corso sull'informatica.", "Informatica"),
            new Corso("Analisi I", "26/04/2022", "15/06/2022", "Logaritmi e derivate.", "Matematica"),
            new Corso("Algebra lineare", "12/03/2022", "17/05/2022", "Tutto sulle matrici.", "Matematica")));

    private static List<Esame> listaEsami = new ArrayList<Esame>(Arrays.asList(
            new Esame("17/06/2022", "15:30", "Medio", "Aula Tonelli", "Sistemi Operativi"),
            new Esame("22/06/2022", "09:40", "Difficile", "Aula Verdi", "Analisi I")));

    @Override
    public void caricaMenu() {
        Button btnProfilo = new Button("Profilo");
        Button btnCorsi = new Button("Corsi");
        Button btnEsami = new Button("Esami");
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
                caricaCorsi();
            }
        });
        btnEsami.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaEsami();
            }
        });
        menuLaterale.add(btnProfilo);
        menuLaterale.add(btnCorsi);
        menuLaterale.add(btnEsami);

        docente = super.ut;
        nome = docente.getNome();
         cognome = docente.getCognome();
         email = docente.getUsername();

    }

    @Override
    public void caricaDefault() {
        spazioDinamico.clear();
        HTML infoPersonali = new HTML("<div class=\"infoPersonali\"><b>Nome: </b>" + nome
                + "<br /><b>Cognome: </b>" + cognome
                + "<br /><b>E-mail: </b>" + email + "</div>");
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Informazioni personali</div>"));
        spazioDinamico.add(infoPersonali);
    }



    public void caricaCorsi() {
        spazioDinamico.clear();
        CellTable<Corso> tableCorsi = creaTabellaCorsi(listaCorsi, "Non hai ancora creato nessun corso.");
        Button btnCreaCorso = new Button("Crea corso");
        btnCreaCorso.addStyleName("btnCreazione");
        btnCreaCorso.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                InserimentoCorso ic = new InserimentoCorso();
                ic.aggiungiContenuto();
            }
        });
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei corsi</div>"));
        spazioDinamico.add(tableCorsi);
        spazioDinamico.add(btnCreaCorso);
    }

    public void caricaEsami() {
        spazioDinamico.clear();
        CellTable<Esame> tableEsami = creaTabellaEsami(listaEsami, "Non hai ancora creato nessun esame.");
        Button btnCreaEsame = new Button("Crea esame");
        btnCreaEsame.addStyleName("btnCreazione");
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei esami</div>"));
        spazioDinamico.add(tableEsami);
        spazioDinamico.add(btnCreaEsame);
    }

    private CellTable<Corso> creaTabellaCorsi(List<Corso> LISTCORSI, String messaggioVuoto) {
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

        TextColumn<Corso> inizioCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDataInizio();
            }
        };
        tableCorsi.addColumn(inizioCol, "Data di inizio");

        TextColumn<Corso> fineCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDataFine();
            }
        };
        tableCorsi.addColumn(fineCol, "Data di fine");

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
                Window.alert("Vuoi modificare il corso di " + object.getNomeCorso());
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
                Window.alert("Hai eliminato con successo il corso di " + object.getNomeCorso());
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
                Window.alert("Vuoi modificare l'esame di " + object.getNomeCorso());
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

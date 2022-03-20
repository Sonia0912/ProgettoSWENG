package com.unibo.progettosweng.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.unibo.progettosweng.shared.model.Corso;
import com.unibo.progettosweng.shared.model.Esame;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class InfoStudente implements Pagina {

    private static final List<Corso> CORSI = Arrays.asList(
            new Corso("Sistemi Operativi", "24/04/2022", "06/06/2022", "Un corso sull'informatica.", "Informatica"),
            new Corso("Analisi I", "26/04/2022", "15/06/2022", "Logaritmi e derivate.", "Matematica"),
            new Corso("Algebra lineare", "12/03/2022", "17/05/2022", "Tutto sulle matrici.", "Matematica"));

    private static final List<Esame> ESAMI = Arrays.asList(
            new Esame("17/06/2022", "15:30", "Medio", "Aula Tonelli", "Sistemi Operativi"),
            new Esame("22/06/2022", "09:40", "Difficile", "Aula Verdi", "Analisi I"));

    String nome = "Sonia";
    String cognome = "Nicoletti";
    String email = "sonianicoletti@unitech.com";
    String[][] esami = {
            {"Sistemi Operativi", "17/06/2022", "15:30", "Medio", "Aula Tonelli"},
            {"Analisi I", "22/06/2022", "09:40", "Difficile", "Aula Verdi"}};

    @Override
    public void aggiungiContenuto() {
        RootPanel.get("contenuto").clear();
        RootPanel.get("pannelloMenu").clear();
        RootPanel.get("pannelloMenu").setHeight("0");

        HorizontalPanel mainPanel = new HorizontalPanel();
        mainPanel.addStyleName("mainInfoStudentePanel");

        // Menu laterale con le varie sezioni
        VerticalPanel menuLaterale = new VerticalPanel();
        menuLaterale.addStyleName("menuLaterale");
        Button btnInfo = new Button("Profilo");
        Button btnCorsi = new Button("Esplora corsi");
        Button btnEsami = new Button("Prenota esame");
        Button btnVoti = new Button("Voti");
        btnInfo.addStyleName("buttonMenuLaterale");
        btnCorsi.addStyleName("buttonMenuLaterale");
        btnEsami.addStyleName("buttonMenuLaterale");
        btnVoti.addStyleName("buttonMenuLaterale");
        menuLaterale.add(btnInfo);
        menuLaterale.add(btnCorsi);
        menuLaterale.add(btnEsami);
        menuLaterale.add(btnVoti);

        // Spazio principale dinamico
        VerticalPanel spazioDinamico = new VerticalPanel();
        spazioDinamico.getElement().setId("spazioDinamicoStudente");
        spazioDinamico.addStyleName("spazioDinamico");

        // Informazioni personali
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Informazioni personali</div>"));
        HTML infoPersonali = new HTML("<div class=\"infoPersonali\"><b>Nome: </b>" + nome
                + "<br /><b>Cognome: </b>" + cognome
                + "<br /><b>E-mail: </b>" + email + "</div>");
        spazioDinamico.add(infoPersonali);

        // I miei corsi
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei corsi</div>"));

        CellTable<Corso> tableCorsi = new CellTable<Corso>();
        tableCorsi.addStyleName("tableCorsi");
        tableCorsi.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        // Set the message to display when the table is empty.
        tableCorsi.setEmptyTableWidget(new Label("Non ci sono corsi a cui sei iscritto."));

        // Colonna per il nome
        TextColumn<Corso> nomeCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getNomeCorso();
            }
        };
        tableCorsi.addColumn(nomeCol, "Nome");
        // Colonna per la data inizio
        TextColumn<Corso> inizioCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDataInizio();
            }
        };
        tableCorsi.addColumn(inizioCol, "Data di inizio");
        // Colonna per la data fine
        TextColumn<Corso> fineCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDataFine();
            }
        };
        tableCorsi.addColumn(fineCol, "Data di fine");
        // Colonna per la descrizione
        TextColumn<Corso> descrizioneCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDescrizione();
            }
        };
        tableCorsi.addColumn(descrizioneCol, "Descrizione");
        // Colonna per il dipartimento
        TextColumn<Corso> dipCol = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDipartimento();
            }
        };
        tableCorsi.addColumn(dipCol, "Dipartimento");
        // Add a selection model to handle user selection.
        final SingleSelectionModel<Corso> selectionModel = new SingleSelectionModel<Corso>();
        tableCorsi.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                Corso selected = selectionModel.getSelectedObject();
                if (selected != null) {
                    Window.alert("You selected: " + selected.getNomeCorso());
                }
            }
        });
        // Set the total row count
        tableCorsi.setRowCount(CORSI.size(), true);
        // Push the data into the widget.
        tableCorsi.setRowData(0, CORSI);
        spazioDinamico.add(tableCorsi);



        // I miei esami
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei esami</div>"));

        CellTable<Esame> tableEsami = new CellTable<Esame>();
        tableEsami.addStyleName("tableEsami");
        tableEsami.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        // Set the message to display when the table is empty.
        tableEsami.setEmptyTableWidget(new Label("Non ci sono corsi a cui sei iscritto."));

        // Colonna per il nome
        TextColumn<Esame> corsoCol = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getNomeCorso();
            }
        };
        tableEsami.addColumn(corsoCol, "Nome del corso");

        // Colonna per la data
        TextColumn<Esame> dataCol = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getData();
            }
        };
        tableEsami.addColumn(dataCol, "Data");

        // Colonna per l'ora
        TextColumn<Esame> oraCol = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getOra();
            }
        };
        tableEsami.addColumn(oraCol, "Ora");

        // Colonna per l'aula
        TextColumn<Esame> aulaCol = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getAula();
            }
        };
        tableEsami.addColumn(aulaCol, "Aula");

        // Colonna per la difficolta'
        TextColumn<Esame> diffCol = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getDifficolta();
            }
        };
        tableEsami.addColumn(diffCol, "Difficoltà");

        // Add a selection model to handle user selection.
        final SingleSelectionModel<Esame> selectionModelEsami = new SingleSelectionModel<Esame>();
        tableEsami.setSelectionModel(selectionModelEsami);
        selectionModelEsami.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                Esame selected = selectionModelEsami.getSelectedObject();
                if (selected != null) {
                    Window.alert("You selected: " + selected.getNomeCorso());
                }
            }
        });
        // Set the total row count
        tableEsami.setRowCount(ESAMI.size(), true);
        // Push the data into the widget.
        tableEsami.setRowData(0, ESAMI);
        spazioDinamico.add(tableEsami);

        mainPanel.add(menuLaterale);
        mainPanel.add(spazioDinamico);
        mainPanel.getWidget(0).addStyleName("menuLaterale");
        mainPanel.getWidget(1).addStyleName("spazioDinamico");

        RootPanel.get("contenuto").add(mainPanel);
    }
}

package com.unibo.progettosweng.client;

import com.google.gwt.cell.client.*;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Esame;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PortaleStudente extends Portale {

    private static ArrayList<Corso> CORSI = new ArrayList<Corso>(Arrays.asList(
            new Corso("Sistemi Operativi", "24/04/2022", "06/06/2022", "Un corso sull'informatica.", "Informatica"),
            new Corso("Analisi I", "26/04/2022", "15/06/2022", "Logaritmi e derivate.", "Matematica"),
            new Corso("Algebra lineare", "12/03/2022", "17/05/2022", "Tutto sulle matrici.", "Matematica")
    ));

    private static ArrayList<Esame> ESAMI = new ArrayList<Esame>(Arrays.asList(
            new Esame("17/06/2022", "15:30", "Medio", "Aula Tonelli", "Sistemi Operativi"),
            new Esame("22/06/2022", "09:40", "Difficile", "Aula Verdi", "Analisi I")
    ));

    String nome = "Sonia";
    String cognome = "Nicoletti";
    String email = "sonianicoletti@unitech.com";

    private static List<Corso> TUTTICORSI = Arrays.asList(
        new Corso("Sistemi Operativi", "24/04/2022", "06/06/2022", "Un corso sull'informatica.", "Informatica"),
        new Corso("Analisi I", "26/04/2022", "15/06/2022", "Logaritmi e derivate.", "Matematica"),
        new Corso("Basi di dati", "13/11/2022", "15/02/2023", "Impareremo i database relazionali e non.", "Informatica"),
        new Corso("Fisica nucleare", "26/04/2022", "15/06/2022", "Dagli atomi all'universo.", "Fisica"),
        new Corso("Chimica applicata", "17/02/2022", "29/03/2022", "Lezioni di chimica applicata.", "Chimica"),
        new Corso("Algebra lineare", "12/03/2022", "17/05/2022", "Tutto sulle matrici.", "Matematica")
    );

    private static List<Esame> TUTTIESAMI = Arrays.asList(
        new Esame("17/06/2022", "15:30", "Medio", "Aula Tonelli", "Sistemi Operativi"),
        new Esame("22/06/2022", "09:40", "Difficile", "Aula Verdi", "Analisi I"),
        new Esame("14/07/2022", "10:00", "Facile", "Aula Magna", "Chimica applicata"),
        new Esame("03/04/2022", "09:55", "Difficile", "Aula M1", "Algebra lineare")
    );

    @Override
    public void caricaMenu() {
        Button btnProfilo = new Button("Profilo");
        Button btnCorsi = new Button("Esplora corsi");
        Button btnEsami = new Button("Prenota esame");
        Button btnVoti = new Button("Voti");
        btnProfilo.addStyleName("buttonMenuLaterale");
        btnCorsi.addStyleName("buttonMenuLaterale");
        btnEsami.addStyleName("buttonMenuLaterale");
        btnVoti.addStyleName("buttonMenuLaterale");
        btnProfilo.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaDefault();
            }
        });
        btnCorsi.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaEsploraCorsi();
            }
        });
        btnEsami.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaPrenotaEsame();
            }
        });
        btnVoti.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaVoti();
            }
        });
        menuLaterale.add(btnProfilo);
        menuLaterale.add(btnCorsi);
        menuLaterale.add(btnEsami);
        menuLaterale.add(btnVoti);
    }

    @Override
    public void caricaDefault() {
        caricaProfilo();
    }

    private void caricaProfilo() {
        HTML infoPersonali = new HTML("<div class=\"infoPersonali\"><b>Nome: </b>" + nome
                + "<br /><b>Cognome: </b>" + cognome
                + "<br /><b>E-mail: </b>" + email + "</div>");
        CellTable<Corso> tableCorsi = creaTabellaCorsi(CORSI, "Non sei iscritto a nessun corso.", false);
        CellTable<Esame> tableEsami = creaTabellaEsami(ESAMI, "Non hai prenotato nessun esame.", false);
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Informazioni personali</div>"));
        spazioDinamico.add(infoPersonali);
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei corsi</div>"));
        spazioDinamico.add(tableCorsi);
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei esami</div>"));
        spazioDinamico.add(tableEsami);
    }

    private void caricaEsploraCorsi() {
        List<Corso> corsiVisibili = new ArrayList<>(TUTTICORSI);
        for(int i = 0; i < TUTTICORSI.size(); i++) {
            for(int j = 0; j < CORSI.size(); j++) {
                if(CORSI.get(j).getNomeCorso().equals(TUTTICORSI.get(i).getNomeCorso())) {
                    String daRimuovere = TUTTICORSI.get(i).getNomeCorso();
                    corsiVisibili.removeIf(corso -> corso.getNomeCorso().equals(daRimuovere));
                }
            }
        }
        CellTable<Corso> tableCorsi = creaTabellaCorsi(corsiVisibili, "Non sono presenti corsi a cui puoi iscriverti.", true);
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Esplora i corsi</div>"));
        spazioDinamico.add(tableCorsi);
    }

    private void caricaPrenotaEsame() {
        List<Esame> esamiVisibili = new ArrayList<>(TUTTIESAMI);
        for(int i = 0; i < TUTTIESAMI.size(); i++) {
            for(int j = 0; j < ESAMI.size(); j++) {
                if(ESAMI.get(j).getNomeCorso().equals(TUTTIESAMI.get(i).getNomeCorso())) {
                    String daRimuovere = TUTTIESAMI.get(i).getNomeCorso();
                    esamiVisibili.removeIf(esame -> esame.getNomeCorso().equals(daRimuovere));
                }
            }
        }
        CellTable<Esame> tableEsami = creaTabellaEsami(esamiVisibili, "Non sono presenti esami prenotabili (prova ad iscriverti prima ad un corso).", true);
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Prenota un esame</div>"));
        spazioDinamico.add(tableEsami);
    }

    private void caricaVoti() {
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei voti</div>"));
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
                    Window.alert("Ti sei iscritto con successo al corso di " + object.getNomeCorso());
                    CORSI.add(object);
                    caricaEsploraCorsi();
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
                    Window.alert("Hai prenotato con successo l'esame di " + object.getNomeCorso());
                    ESAMI.add(object);
                    caricaPrenotaEsame();
                }
            });
        }

        tableEsami.setRowCount(listaEsami.size(), true);
        tableEsami.setRowData(0, listaEsami);
        return tableEsami;
    }

}

package com.unibo.progettosweng.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class Dipartimenti implements Pagina {

    final static String dipTitolo = "<div class=\"titoloDipartimenti\">I dipartimenti</div>";

    final static String[] nomiDipartimenti = {"Matematica", "Fisica", "Informatica"};
    final static HTML[] descrizioneDipartimento = {
        new HTML("<div class=\"titolettoDipartimenti\">Matematica</div><div class=\"descrizioneDipartimento\">Il dipartimento di Matematica ha come obiettivo la formazione di professionisti in grado di affrontare l’analisi e la modellazione di sistemi complessi che richiedono competenze provenienti da differenti discipline, armonizzando solide conoscenze scientifiche di base con la padronanza di metodologie e tecnologie avanzate.</div><div class=\"titolettoDipartimenti\">Esplora i corsi</div>"),
        new HTML("<div class=\"titolettoDipartimenti\">Fisica</div><div class=\"descrizioneDipartimento\">Il Dipartimento di Fisica è stato costituito con l'obiettivo di svolgere attività di ricerca scientifica sperimentale e teorica nel settore della Fisica della Materia e di contribuire allo sviluppo delle applicazioni di tale settore in Fisica, Ingegneria e Medicina.</div><div class=\"titolettoDipartimenti\">Esplora i corsi</div>"),
        new HTML("<div class=\"titolettoDipartimenti\">Informatica</div><div class=\"descrizioneDipartimento\">Il dipartimento di Informatica ha l'obiettivo di formare ingegneri capaci di applicare i metodi di indagine e di progettazione tipici dell'ingegneria in tutti i contesti in cui l'informatica è l'ingrediente fondamentale per affrontare e risolvere problemi complessi.</div><div class=\"titolettoDipartimenti\">Esplora i corsi</div>")};

    final static String[] corsiMatematica = {"Matematica Pura", "Matematica Applicata"};
    final static String[] corsiFisica = {"Fisica"};
    final static String[] corsiInformatica = {"Informatica", "Informatica per il Management", "Ingengeria informatica"};

    final static String[][] materieMate = {
            {"Algebra lineare", "Analisi I", "Analisi II", "Geometria I", "Geometria II", "Analisi III"},
            {"Algebra lineare Applicata", "Analisi I Applicata", "Analisi II App", "Geometria App"}
    };
    final static String[][] materieFisica = {
            {"Elettromagnetismo", "Fisica relativistica", "Astrofisica", "Meccanica quantistica", "Fisica nucleare"}
    };
    final static String[][] materieInfo = {
            {"Programmazione", "Reti", "Cybersecurity", "Sistemi Operativi", "Basi di dati"},
            {"Programmazione", "Finanza", "Economia", "Basi di dati"},
            {"Elettrotecnica", "Meccanica", "Cybersecurity", "Telecomunicazioni", "AI"}
    };

    final static String[][] dettagli = {{"Luigi Bianchi", "Algebra lineare"},
            {"Sara Neri", "Analisi I"},
            {"Giuseppe Verdi", "Analisi II"},
            {"Marco Rossi", "Geometria"},
            {"Giulia Gialli", "Statistica"},
            {"Federico Blu", "Elettromagnetismo"},
            {"Francesca Viola", "Fisica relativistica"},
            {"Tommaso Bianchi", "Astrofisica"},
            {"Caterina Neri", "Meccanica quantistica"},
            {"Davide Verdi", "Fisica nucleare"},
            {"Nicole Rossi", "Programmazione"},
            {"Daniele Gialli", "Reti"},
            {"Lucia Blu", "Cybersecurity"},
            {"Luca Viola", "Sistemi Operativi"},
            {"Stefania Bianchi", "Basi di dati"},
            {null, "Matematica Pura", "2010"},
            {null, "Informatica", "2007"},
            {null, "Fisica", "1990"},
    };

    private ScrollPanel popolaTree(Tree staticTree, String[] corsi, String[][] materiePerCorso, String dipID) {
        staticTree.setAnimationEnabled(true);
        staticTree.ensureDebugId("cwTree-staticTree");
        ScrollPanel staticTreeWrapper = new ScrollPanel(staticTree);
        staticTreeWrapper.ensureDebugId("cwTree-staticTree-Wrapper");
        staticTreeWrapper.setSize("600px", "400px");

        for(int i = 0; i < corsi.length; i++) {
            TreeItem corso = staticTree.addTextItem(corsi[i]);
            for(int j = 0; j < materiePerCorso[i].length; j++) {
                corso.addTextItem(materiePerCorso[i][j]);
            }
        }

        staticTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                TreeItem item = event.getSelectedItem();
                String id = "dettagliCorsoMateria".concat(dipID);
                //Window.alert(RootPanel.get("dettagliCorsoMateria").toString());
                RootPanel.get(id).clear();
                boolean trovato = false;
                for(int i = 0; i < dettagli.length; i++) {
                    if(dettagli[i][1].equalsIgnoreCase(item.getText())) {
                        trovato = true;
                        if(dettagli[i][0] != null) {
                            //Window.alert("dettagliCorsoMateria".concat(dipID));
                            RootPanel.get(id).add(new HTML("<div class=\"paragrafroDipartimenti\">Il docente del corso è " + dettagli[i][0] + ".</div>"));
                        } else {
                            //Window.alert("Il corso di " + item.getText() + " è stato fondato nel " + dettagli[i][2]);
                            RootPanel.get(id).add(new HTML("<div class=\"paragrafroDipartimenti\">Il corso è stato fondato nel " + dettagli[i][2] + ".</div>"));
                        }
                    }
                }
                if(!trovato) {
                    //Window.alert("Nessuna info trovata.");
                    RootPanel.get(id).add(new HTML("<div class=\"paragrafroDipartimenti\">Nessuna informazione trovata.</div>"));
                }
            }
        });
        return staticTreeWrapper;
    }

    @Override
    public void aggiungiContenuto() {
        RootPanel.get("contenuto").clear();

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.addStyleName("mainDipartimentiPanel");

        // Titolo
        mainPanel.add(new HTML(dipTitolo));

        // TabPanel
        TabLayoutPanel tabPanelDipartimenti = new TabLayoutPanel(2.2, Style.Unit.EM);
        tabPanelDipartimenti.addStyleName("tabPanelDipartimenti");
        tabPanelDipartimenti.setAnimationDuration(1000);

        // Contenuto dei tab va in un vertical panel
        VerticalPanel[] vPanelDipartimenti = {new VerticalPanel(), new VerticalPanel(), new VerticalPanel()};
        HorizontalPanel[] hPanelCorso = {new HorizontalPanel(), new HorizontalPanel(), new HorizontalPanel()};
        Tree[] treeCorsiDipartimento = {new Tree(), new Tree(), new Tree()};
        ScrollPanel[] scrollTree = {new ScrollPanel(), new ScrollPanel(), new ScrollPanel()};
        HTML[] infoCorso = {
                new HTML("<div class=\"titolettoDipartimentiDettagli\">Dettagli</div><div id=\"dettagliCorsoMateria1\"></div>"),
                new HTML("<div class=\"titolettoDipartimentiDettagli\">Dettagli</div><div id=\"dettagliCorsoMateria2\"></div>"),
                new HTML("<div class=\"titolettoDipartimentiDettagli\">Dettagli</div><div id=\"dettagliCorsoMateria3\"></div>")};

        // Matematica
        vPanelDipartimenti[0].addStyleName("vPanelDipartimento");
        vPanelDipartimenti[0].add(descrizioneDipartimento[0]);
        hPanelCorso[0].addStyleName("hPanelDipartimentoCorsi");
        treeCorsiDipartimento[0].addStyleName("treeCorsiDipartimento");
        scrollTree[0] = popolaTree(treeCorsiDipartimento[0], corsiMatematica, materieMate, "1");
        hPanelCorso[0].add(scrollTree[0]);
        infoCorso[0].addStyleName("infoCorsoDinamico");
        hPanelCorso[0].add(infoCorso[0]);
        vPanelDipartimenti[0].add(hPanelCorso[0]);
        // Fisica
        vPanelDipartimenti[1].addStyleName("vPanelDipartimento");
        vPanelDipartimenti[1].add(descrizioneDipartimento[1]);
        hPanelCorso[1].addStyleName("hPanelDipartimentoCorsi");
        treeCorsiDipartimento[1].addStyleName("treeCorsiDipartimento");
        scrollTree[1] = popolaTree(treeCorsiDipartimento[1], corsiFisica, materieFisica, "2");
        hPanelCorso[1].add(scrollTree[1]);
        infoCorso[1].addStyleName("infoCorsoDinamico");
        hPanelCorso[1].add(infoCorso[1]);
        vPanelDipartimenti[1].add(hPanelCorso[1]);
        // Informatica
        vPanelDipartimenti[2].addStyleName("vPanelDipartimento");
        vPanelDipartimenti[2].add(descrizioneDipartimento[2]);
        hPanelCorso[2].addStyleName("hPanelDipartimentoCorsi");
        treeCorsiDipartimento[2].addStyleName("treeCorsiDipartimento");
        scrollTree[2] = popolaTree(treeCorsiDipartimento[2], corsiInformatica, materieInfo, "3");
        hPanelCorso[2].add(scrollTree[2]);
        infoCorso[2].addStyleName("infoCorsoDinamico");
        hPanelCorso[2].add(infoCorso[2]);
        vPanelDipartimenti[2].add(hPanelCorso[2]);

        // Aggiungo i tab e il contenuto (vPanel) per ogni dipartimento al TabPanel
        for (int i = 0; i < nomiDipartimenti.length; i++) {
            tabPanelDipartimenti.add(vPanelDipartimenti[i], nomiDipartimenti[i]);
        }

        tabPanelDipartimenti.selectTab(0);
        mainPanel.add(tabPanelDipartimenti);

        RootPanel.get("contenuto").add(mainPanel);

        tabPanelDipartimenti.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                if (event.getSelectedItem() == 0) {
                    RootPanel.get("dettagliCorsoMateria1").add(new HTML(" "));
                } else if (event.getSelectedItem() == 1) {
                    RootPanel.get("dettagliCorsoMateria2").add(new HTML(" "));
                } else if (event.getSelectedItem() == 2) {
                    RootPanel.get("dettagliCorsoMateria3").add(new HTML(" "));
                }
            }
        });

        RootPanel.get("dettagliCorsoMateria1").add(new HTML(" "));
        /*RootPanel.get("dettagliCorsoMateria2").add(new HTML(" "));
        RootPanel.get("dettagliCorsoMateria3").add(new HTML(" "));*/




        //RootPanel.get("dettagliCorsoMateria").add(new HTML("<div class=\"paragrafroDipartimenti\">Seleziona un corso o una materia per vedere i dettagli.</div>"));

    }


}

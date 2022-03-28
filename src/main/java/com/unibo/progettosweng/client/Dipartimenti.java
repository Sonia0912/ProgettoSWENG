package com.unibo.progettosweng.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.unibo.progettosweng.client.model.Corso;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class Dipartimenti implements Pagina {

    final static String[] DIPARTIMENTI = {"Matematica", "Fisica", "Chimica", "Informatica", "Ingegneria"};

    final static HTML[] DESCRIZIONIDIPARTIMENTI = {
            new HTML("<div class=\"titolettoDipartimenti\">Matematica</div><div class=\"descrizioneDipartimento\">Il dipartimento di Matematica ha come obiettivo la formazione di professionisti in grado di affrontare l’analisi e la modellazione di sistemi complessi che richiedono competenze provenienti da differenti discipline, armonizzando solide conoscenze scientifiche di base con la padronanza di metodologie e tecnologie avanzate.</div><div class=\"titolettoDipartimenti\">Esplora i corsi</div>"),
            new HTML("<div class=\"titolettoDipartimenti\">Fisica</div><div class=\"descrizioneDipartimento\">Il Dipartimento di Fisica è stato costituito con l'obiettivo di svolgere attività di ricerca scientifica sperimentale e teorica nel settore della Fisica della Materia e di contribuire allo sviluppo delle applicazioni di tale settore in Fisica, Ingegneria e Medicina.</div><div class=\"titolettoDipartimenti\">Esplora i corsi</div>"),
            new HTML("<div class=\"titolettoDipartimenti\">Chimica</div><div class=\"descrizioneDipartimento\">Il nuovo dipartimento di chimica si propone di formare figure professionali dotate delle basi scientifiche e tecnologiche indispensabili sia per un pronto inserimento nel mondo del lavoro che per la prosecuzione degli studi ai livelli superiori. Il Corso garantirà, oltre a una solida preparazione nelle discipline matematiche e fisiche, un’adeguata conoscenza di base, teorica, applicata e sperimentale nei principali settori della chimica.</div><div class=\"titolettoDipartimenti\">Esplora i corsi</div>"),
            new HTML("<div class=\"titolettoDipartimenti\">Informatica</div><div class=\"descrizioneDipartimento\">Il dipartimento di Informatica ha l'obiettivo di formare ingegneri capaci di applicare i metodi di indagine e di progettazione tipici dell'ingegneria in tutti i contesti in cui l'informatica è l'ingrediente fondamentale per affrontare e risolvere problemi complessi.</div><div class=\"titolettoDipartimenti\">Esplora i corsi</div>"),
            new HTML("<div class=\"titolettoDipartimenti\">Ingegneria</div><div class=\"descrizioneDipartimento\">Il Dipartimento di Ingegneria dell'Unitech si trova nel Campus del Polo Scientifico Tecnologico, un'ampia area verde dove si trovano aule, laboratori, sale studio, biblioteca e mensa. I nostri punti di forza sono lo stretto rapporto di collaborazione con le imprese, i numerosi accordi con le Università estere (per Erasmus e Doppi titoli), un ottimo rapporto studente-docente.</div><div class=\"titolettoDipartimenti\">Esplora i corsi</div>"),
    };

    Corso[] CORSI = {
            new Corso("Matematica Applicata", "05/04/2022", "17/06/2022", "Un corso molto bello sulla matematica applicata.", "Matematica","doc","c", false),
            new Corso("Analisi I", "12/05/2022", "27/06/2022", "Un corso molto bello sui logaritmi e gli integrali.", "Matematica","doc","c", false),
            new Corso("Chimica dei materiali", "07/04/2022", "17/04/2022", "Di cosa sono fatti i materiali che usiamo?", "Chimica","doc","c", false),
            new Corso("Sistemi Operativi", "12/05/2022", "17/06/2022", "Tutto sui calcolatori.", "Informatica","doc","c", false),
            new Corso("Meccanica", "13/06/2022", "17/07/2022", "Impareremo il funzionamento dei motori.", "Ingegneria","doc","c", false),
            new Corso("Probablità", "26/07/2022", "17/08/2022", "Più che il lancio di una moneta.", "Matematica","doc","c", false),
            new Corso("Gestione dei progetti", "25/03/2022", "17/07/2022", "Come gestire progetti complessi.", "Ingegneria","doc","c", false),
            new Corso("Basi di dati", "30/04/2022", "17/06/2022", "DB relazionali e non.", "Informatica","doc","c", false),
            new Corso("Fisica dei fluidi", "13/04/2022", "22/06/2022", "Tante informazioni sulla fisica dei fluidi.", "Fisica","doc","c", false)
    };

    ArrayList<Corso>[] CORSISUDDIVISI = new ArrayList[DIPARTIMENTI.length];

    //private static CorsoServiceAsync service = GWT.create(CorsoService.class);


    private void suddividiCorsiPerDipartimento(Corso[] tuttiCorsi) {
        Arrays.setAll(CORSISUDDIVISI, element -> new ArrayList<>());
        for(int i = 0; i < tuttiCorsi.length; i++) {
            switch (tuttiCorsi[i].getDipartimento()) {
                case "Matematica":
                    CORSISUDDIVISI[0].add(tuttiCorsi[i]);
                    break;
                case "Fisica":
                    CORSISUDDIVISI[1].add(tuttiCorsi[i]);
                    break;
                case "Chimica":
                    CORSISUDDIVISI[2].add(tuttiCorsi[i]);
                    break;
                case "Informatica":
                    CORSISUDDIVISI[3].add(tuttiCorsi[i]);
                    break;
                case "Ingegneria":
                    CORSISUDDIVISI[4].add(tuttiCorsi[i]);
                    break;
            }
         }
    }

    private ScrollPanel popolaTree(Tree staticTree, ArrayList<Corso> corsi, String dipID) {
        staticTree.setAnimationEnabled(true);
        staticTree.ensureDebugId("cwTree-staticTree");
        ScrollPanel staticTreeWrapper = new ScrollPanel(staticTree);
        staticTreeWrapper.ensureDebugId("cwTree-staticTree-Wrapper");
        staticTreeWrapper.setSize("600px", "400px");
        for(int i = 0; i < corsi.size(); i++) {
            TreeItem corso = staticTree.addTextItem(corsi.get(i).getNomeCorso());
        }
        staticTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                TreeItem item = event.getSelectedItem();
                String id = "dettagliCorsoMateria".concat(dipID);
                RootPanel.get(id).clear();
                for(int i = 0; i < CORSI.length; i++) {
                    if(corsi.get(i).getNomeCorso().equals(item.getText())) {
                        RootPanel.get(id).add(new HTML("<div class=\"paragrafroDipartimenti\">" + corsi.get(i).getDescrizione() + "</div>"));
                    }
                }
            }
        });
        return staticTreeWrapper;
    }

    @Override
    public void aggiungiContenuto() throws Exception {
        RootPanel.get("contenuto").clear();
        suddividiCorsiPerDipartimento(CORSI);


/*        service.getCorsi(new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Errore getCorsi: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Corso[] output) {
                CORSI = output;
            }
        });*/

        suddividiCorsiPerDipartimento(CORSI);

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.addStyleName("mainDipartimentiPanel");

        // Titolo
        mainPanel.add(new HTML("<div class=\"titoloDipartimenti\">I dipartimenti</div>"));

        // TabPanel
        TabLayoutPanel tabPanelDipartimenti = new TabLayoutPanel(2.2, Style.Unit.EM);
        tabPanelDipartimenti.addStyleName("tabPanelDipartimenti");
        tabPanelDipartimenti.setAnimationDuration(1000);

        VerticalPanel[] vPanelDipartimenti = new VerticalPanel[DIPARTIMENTI.length];
        HorizontalPanel[] hPanelCorso = new HorizontalPanel[DIPARTIMENTI.length];
        Tree[] treeCorsiDipartimento = new Tree[DIPARTIMENTI.length];
        ScrollPanel[] scrollTree = new ScrollPanel[DIPARTIMENTI.length];
        HTML[] infoCorso = new HTML[DIPARTIMENTI.length];

        // Aggiungo i tab e il contenuto (vPanel) per ogni dipartimento al TabPanel
        for (int i = 0; i < DIPARTIMENTI.length; i++) {
            String id = String.valueOf(i);
            vPanelDipartimenti[i] = new VerticalPanel();
            hPanelCorso[i] = new HorizontalPanel();
            treeCorsiDipartimento[i] = new Tree();
            infoCorso[i] = new HTML("<div class=\"titolettoDipartimentiDettagli\">Dettagli</div><div id=\"dettagliCorsoMateria" + id + "\"></div>");
            infoCorso[i].addStyleName("infoCorsoDinamico");
            vPanelDipartimenti[i].addStyleName("vPanelDipartimento");
            vPanelDipartimenti[i].add(DESCRIZIONIDIPARTIMENTI[i]);
            hPanelCorso[i].addStyleName("hPanelDipartimentoCorsi");
            treeCorsiDipartimento[i].addStyleName("treeCorsiDipartimento");
            scrollTree[i] = popolaTree(treeCorsiDipartimento[i], CORSISUDDIVISI[i], id);
            hPanelCorso[i].add(scrollTree[i]);
            hPanelCorso[i].add(infoCorso[i]);
            vPanelDipartimenti[i].add(hPanelCorso[i]);
            tabPanelDipartimenti.add(vPanelDipartimenti[i], DIPARTIMENTI[i]);
        }

        tabPanelDipartimenti.selectTab(0);
        mainPanel.add(tabPanelDipartimenti);

        RootPanel.get("contenuto").add(mainPanel);

        // Per inizializzare il div quando si cambia tab, altrimenti il primo click non lo sente.
        tabPanelDipartimenti.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                if (event.getSelectedItem() == 0) {
                    RootPanel.get("dettagliCorsoMateria0").add(new HTML(" "));
                } else if (event.getSelectedItem() == 1) {
                    RootPanel.get("dettagliCorsoMateria1").add(new HTML(" "));
                } else if (event.getSelectedItem() == 2) {
                    RootPanel.get("dettagliCorsoMateria2").add(new HTML(" "));
                } else if (event.getSelectedItem() == 3) {
                    RootPanel.get("dettagliCorsoMateria3").add(new HTML(" "));
                } else if (event.getSelectedItem() == 4) {
                    RootPanel.get("dettagliCorsoMateria4").add(new HTML(" "));
                }
            }
        });
        RootPanel.get("dettagliCorsoMateria0").add(new HTML(" "));
    }
}

/*    private ScrollPanel popolaTree(Tree staticTree, String[] corsi, String[][] docentiPerCorso, String dipID) {
        staticTree.setAnimationEnabled(true);
        staticTree.ensureDebugId("cwTree-staticTree");
        ScrollPanel staticTreeWrapper = new ScrollPanel(staticTree);
        staticTreeWrapper.ensureDebugId("cwTree-staticTree-Wrapper");
        staticTreeWrapper.setSize("600px", "400px");
        for(int i = 0; i < corsi.length; i++) {
            TreeItem corso = staticTree.addTextItem(corsi[i]);
            for(int j = 0; j < 2; j++) {
                corso.addTextItem(docentiPerCorso[i][j]);
            }
        }
        staticTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                TreeItem item = event.getSelectedItem();
                String id = "dettagliCorsoMateria".concat(dipID);
                RootPanel.get(id).clear();
                boolean trovato = false;
                for(int i = 0; i < dettagli.length; i++) {
                    if(dettagli[i][1].equalsIgnoreCase(item.getText())) {
                        trovato = true;
                        if(dettagli[i][0] != null) {
                            RootPanel.get(id).add(new HTML("<div class=\"paragrafroDipartimenti\">Il docente del corso è " + dettagli[i][0] + ".</div>"));
                        } else {
                            RootPanel.get(id).add(new HTML("<div class=\"paragrafroDipartimenti\">Il corso è stato fondato nel " + dettagli[i][2] + ".</div>"));
                        }
                    }
                }
                if(!trovato) {
                    RootPanel.get(id).add(new HTML("<div class=\"paragrafroDipartimenti\">Nessuna informazione trovata.</div>"));
                }
            }
        });
        return staticTreeWrapper;
    }*/

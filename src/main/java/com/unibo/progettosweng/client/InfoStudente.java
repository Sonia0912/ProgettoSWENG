package com.unibo.progettosweng.client;

import com.google.gwt.user.client.ui.*;

public class InfoStudente implements Pagina {

    String nome = "Sonia";
    String cognome = "Nicoletti";
    String email = "sonianicoletti@unitech.com";
    String[] corsi = {"Informatica", "Analisi I", "Meccanica"};
    String[] esami = {"Informatica", "Analisi I"};

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
        Button btnInfo = new Button("Informazioni personali");
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

        // Di default mostro le informazioni personali
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">Informazioni personali</div>"));
        HTML infoPersonali = new HTML("<div class=\"infoPersonali\">Nome: " + nome + "<br />Cognome: " + cognome + "<br />E-mail: " + email + "</div>");
        spazioDinamico.add(infoPersonali);
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei corsi</div>"));
        for(int i = 0; i < corsi.length; i++) {
            spazioDinamico.add(new HTML(corsi[i] + "<br />"));
        }
        spazioDinamico.add(new HTML("<div class=\"titolettoPortale\">I miei esami</div>"));
        for(int i = 0; i < esami.length; i++) {
            spazioDinamico.add(new HTML(esami[i] + "<br />"));
        }

        mainPanel.add(menuLaterale);
        mainPanel.add(spazioDinamico);
        mainPanel.getWidget(0).addStyleName("menuLaterale");
        mainPanel.getWidget(1).addStyleName("spazioDinamico");

        RootPanel.get("contenuto").add(mainPanel);
    }
}

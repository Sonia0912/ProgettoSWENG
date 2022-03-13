package com.unibo.progettosweng.client;

import com.google.gwt.dom.client.*;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;

public class Homepage implements Pagina {

    final static String[] titoliSezioni = {"FUTURI STUDENTI", "PROGRAMMI", "AGGIORNAMENTI 2021-2022", "TOUR VIRTUALE DEL CAMPUS"};
    final static String[] paragrafiSezioni = {"Unitech’s unique personality rests on the bedrock values of academic excellence, inclusivity, and social responsibility.",
                                                "A Unitech education fosters intellectual inquiry and reflection, personal growth, and a commitment to the world beyond oneself.",
                                                "For the latest Academic Year 2021-22 updates, please click below.",
                                                "Take a virtual tour, learn about admission and financial aid, and speak with current students."};
    final static Image fotoP1 = new Image();
    final static Image fotoP2 = new Image();
    final static HTML titolo1 = new HTML("La nostra storia");
    final static HTML paragrafo1 = new HTML("Lorem ipsum...");
    final static HTML titolo2 = new HTML("I nostri obiettivi");
    final static HTML paragrafo2 = new HTML("Lorem ipsum...");

    final static HTML divImgHp = new HTML("<div id=\"divHp\"> <img src=\"img/campusBuilding.jpg\" class=\"uniFotoSfondo\"> <div class=\"centeredAbove\">UNITECH</div> <div class=\"centeredBelow\">Scienza, tecnologia e innovazione</div> </div>");

    @Override
    public void aggiungiContenuto() {
        RootPanel.get("contenuto").clear();

        VerticalPanel hpPanel = new VerticalPanel();
        hpPanel.addStyleName("hpPanel");

        // Sezione 1
        hpPanel.add(divImgHp);
        // Sezione 2
        HorizontalPanel hPanel4Sezioni = new HorizontalPanel();
        hPanel4Sezioni.addStyleName("hPanel4Sezioni");
        HTML sezione1 = new HTML("<img class=\"icona\" src=\"img/iconaCappello.png\" /><div class=\"titoloSezione\">"+ titoliSezioni[0] + "</div><div class=\"linea\"></div><div class=\"paragrafoSezione\">" + paragrafiSezioni[0] + "</div>");
        HTML sezione2 = new HTML("<img class=\"icona\" src=\"img/iconaLampadina.png\" /><div class=\"titoloSezione\">"+ titoliSezioni[1] + "</div><div class=\"linea\"></div><div class=\"paragrafoSezione\">" + paragrafiSezioni[1] + "</div>");
        HTML sezione3 = new HTML("<img class=\"icona\" src=\"img/iconaCalendario.png\" /><div class=\"titoloSezione\">"+ titoliSezioni[2] + "</div><div class=\"linea\"></div><div class=\"paragrafoSezione\">" + paragrafiSezioni[2] + "</div>");
        HTML sezione4 = new HTML("<img class=\"icona\" src=\"img/iconaMappa.png\" /><div class=\"titoloSezione\">"+ titoliSezioni[3] + "</div><div class=\"linea\"></div><div class=\"paragrafoSezione\">" + paragrafiSezioni[3] + "</div>");
        sezione1.addStyleName("sezione1");
        sezione2.addStyleName("sezione2");
        sezione3.addStyleName("sezione3");
        sezione4.addStyleName("sezione4");
        hPanel4Sezioni.add(sezione1);
        hPanel4Sezioni.add(sezione2);
        hPanel4Sezioni.add(sezione3);
        hPanel4Sezioni.add(sezione4);
        hpPanel.add(hPanel4Sezioni);
        // Sezione 3
        HorizontalPanel hPanelObiettivi = new HorizontalPanel();
        hPanelObiettivi.addStyleName("pannelloHP");
        VerticalPanel vPanelObiettivi = new VerticalPanel();
        fotoP2.addStyleName("fotoParagrafoHp");
        fotoP2.setUrl("img/CSclassroom.jpg");
        titolo2.addStyleName("titoletto");
        paragrafo2.addStyleName("paragrafo");
        vPanelObiettivi.add(titolo2);
        vPanelObiettivi.add(paragrafo2);
        hPanelObiettivi.add(fotoP2);
        hPanelObiettivi.add(vPanelObiettivi);
        hpPanel.add(hPanelObiettivi);

        RootPanel.get("contenuto").add(hpPanel);
    }
}

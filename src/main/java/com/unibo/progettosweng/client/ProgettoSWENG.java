/**
 * HOMEPAGE
 */
package com.unibo.progettosweng.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Utente;
import org.checkerframework.checker.units.qual.C;

public class ProgettoSWENG implements EntryPoint {

    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);

    final String[] menuSections = {"Home", "Dipartimenti"};
    final Button[] menuButtons = new Button[menuSections.length];
    final String contattiString = "123-456-789 <br /> Via delle Stelle, 56 Bologna <br /> unitech@mail.com";
    final String mappeString = "Mappa del campus <br /> Direzioni <br /> Area circostante <br /> Tour virtuale del campus";
    final String lavoroString = "Risorse umane";

    public void onModuleLoad() {

        caricaHeader();

        caricaMenu();

        // Di default mostro il contenuto della homepage
        try {
            Homepage hp = new Homepage();
            hp.aggiungiContenuto();
        } catch (Exception e) {
            e.printStackTrace();
        }

        caricaButtonPortale(false);

        caricaContatti();

    }

    public void caricaHeader() {
        Button unitech = new Button("UNITECH");
        unitech.getElement().setId("unitech");
        RootPanel.get("unitechDiv").add(unitech);
        unitech.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Homepage hp = new Homepage();
                RootPanel.get("contenuto").clear();
                RootPanel.get("pannelloMenu").clear();
                RootPanel.get("login").clear();
                caricaMenu();
                RootPanel.get("pannelloMenu").setHeight("45px");
                try {
                    hp.aggiungiContenuto();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                caricaButtonPortale(false);
            }
        });
    }

    public void caricaMenu() {
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.setSpacing(5);
        for (int i = 0; i < menuSections.length; i++) {
            menuButtons[i] = new Button(menuSections[i]);
            hPanel.add(menuButtons[i]);
        }
        RootPanel.get("pannelloMenu").add(hPanel);
        RootPanel.get("menuId").setHeight("45px");

        // Se clicco su Home
        menuButtons[0].addClickHandler(event -> {
            try {
                Homepage hp = new Homepage();
                hp.aggiungiContenuto();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Se clicco su Dipartimenti
        menuButtons[1].addClickHandler(event -> {
            try {
                serviceCorso.getCorsi(new AsyncCallback<Corso[]>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore getCorsi: " + throwable.getMessage());
                    }
                    @Override
                    public void onSuccess(Corso[] output) {
                        try {
                            Dipartimenti dip = new Dipartimenti();
                            dip.aggiungiCorsi(output);
                            dip.aggiungiContenuto();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void rimuoviMenu() {
        RootPanel.get("pannelloMenu").clear();
        RootPanel.get("pannelloMenu").setHeight("0");
        RootPanel.get("menuId").setHeight("0");
    }

    public static void caricaButtonPortale(boolean loggato) {
        String testo = loggato ? "<span>Esci</span>" : "<span>Il mio portale</span>";
        final Button login = new Button(testo);
        login.addStyleName("btnPortale");
        RootPanel.get("login").add(login);
        login.addClickHandler(clickEvent -> {
            Login l = new Login();
            l.aggiungiContenuto();
        });
    }

    public void caricaContatti() {
        final HTML contatti = new HTML(contattiString);
        final HTML mappe = new HTML(mappeString);
        final HTML lavoro = new HTML(lavoroString);
        RootPanel.get("contattiInfo").add(contatti);
        RootPanel.get("mappeInfo").add(mappe);
        RootPanel.get("lavoraInfo").add(lavoro);
    }

}
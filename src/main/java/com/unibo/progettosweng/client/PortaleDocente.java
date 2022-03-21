package com.unibo.progettosweng.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;

public class PortaleDocente extends Portale {

    @Override
    public void caricaMenu() {
        Button btnProva = new Button("Prova");
        Button btnProva2 = new Button("Prova2");
        btnProva.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaDefault();
            }
        });
        btnProva2.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                caricaProva2();
            }
        });
        menuLaterale.add(btnProva);
        menuLaterale.add(btnProva2);
    }

    @Override
    public void caricaDefault() {
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("Sono in Prova"));
    }

    public void caricaProva2() {
        spazioDinamico.clear();
        spazioDinamico.add(new HTML("Sono in Prova2"));
    }
}

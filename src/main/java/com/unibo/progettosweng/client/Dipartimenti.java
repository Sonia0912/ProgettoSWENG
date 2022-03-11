package com.unibo.progettosweng.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class Dipartimenti implements Pagina {

    final static String dipString = "<div class=\"dipartimentiHomepage\"><div class=\"contenuto\"><span class=\"titoletto\">I dipartimenti</span></div></div>";
    final static String[] nomiDipartimenti = {"Matematica", "Fisica", "Informatica"};
    final static String[] infoDipartimenti = {"Info dipartimento matematica", "Info dipartimento fisica", "Info dipartimento informatica"};

    final static Button btnEsempio = new Button("ciao");

    @Override
    public void aggiungiContenuto() {
        RootPanel.get("contenuto").clear();

        TabLayoutPanel tabPanel = new TabLayoutPanel(2.2, Style.Unit.EM);
        tabPanel.setAnimationDuration(1000);
        tabPanel.getElement().getStyle().setMarginBottom(10.0, Style.Unit.PX);
        tabPanel.getElement().getStyle().setHeight(420.0, Style.Unit.PX);
        tabPanel.getElement().getStyle().setBackgroundColor("white");

        HTML[] infoDipHTML = {};
        for (int i = 0; i < infoDipartimenti.length; i++) {
            infoDipHTML[i] = new HTML(infoDipartimenti[i]);
            tabPanel.add(infoDipHTML[i], nomiDipartimenti[i]);
        }
        tabPanel.selectTab(0);

        tabPanel.add(btnEsempio, "Esempio");

        btnEsempio.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Window.alert("ciao");
            }
        });


        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setWidth("100%");

        vPanel.add(new HTML(dipString));
        vPanel.add(tabPanel);
        RootPanel.get("contenuto").add(vPanel);
    }

}

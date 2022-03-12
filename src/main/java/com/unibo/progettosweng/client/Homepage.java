package com.unibo.progettosweng.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;

public class Homepage implements Pagina {

    final static String uniNome = "UNITECH";
    final static String uniDescrizione = "Scienza, tecnologia e innovazione";
    final static Image uniFoto = new Image();
    final static String titolo1String = "La nostra storia";
    final static HTML titolo1 = new HTML(titolo1String);
    final static String paragrafo1String = "Lorem ipsum...";
    final static HTML paragrafo1 = new HTML(paragrafo1String);

    @Override
    public void aggiungiContenuto() {
        RootPanel.get("contenuto").clear();

        uniFoto.addStyleName("uniFoto");
        uniFoto.setUrl("img/uniFoto.jpg");
        titolo1.addStyleName("titoletto");
        paragrafo1.addStyleName("paragrafo");

        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.addStyleName("pannelloHP");

        VerticalPanel vPanelStoria = new VerticalPanel();
        vPanelStoria.add(titolo1);
        vPanelStoria.add(paragrafo1);

        hPanel.add(vPanelStoria);
        hPanel.add(uniFoto);

        RootPanel.get("contenuto").add(hPanel);

    }
}

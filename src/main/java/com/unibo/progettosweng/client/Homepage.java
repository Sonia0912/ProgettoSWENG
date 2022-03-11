package com.unibo.progettosweng.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Homepage {

    final static String uniInfoString = "<div class=\"infoUniHomepage\"><div class=\"contenuto\"><span class=\"titoletto\">La nostra storia</span><p id=\"universitaInfo\">Lorem ipsum...</p></div></div>";

    public static void aggiungiContenuto() {
        RootPanel.get("contenuto").clear();
        RootPanel.get("contenuto").add(new HTML(uniInfoString));
    }
}

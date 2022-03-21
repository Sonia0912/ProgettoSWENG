package com.unibo.progettosweng.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;
import sun.tools.jconsole.TimeComboBox;

public class InserimentoEsame implements Pagina{
    String titleString = "<h1 class=\"titoletto\">Inserisci un nuovo esame</h1>";
    FormPanel nuovoEsame;

    @Override
    public void aggiungiContenuto(){
        RootPanel.get("contenuto").clear();
        VerticalPanel vp = new VerticalPanel();
        vp.getElement().getStyle().setPadding(18, Style.Unit.PX);
        vp.add(new HTML(titleString));
        createForm();
        vp.add(nuovoEsame);
        RootPanel.get("contenuto").add(vp);
    }

    private void createForm(){
        nuovoEsame = new FormPanel();
        nuovoEsame.setAction("/creaNuovoCorso");
        nuovoEsame.setMethod(FormPanel.METHOD_POST);

        VerticalPanel formPanel = new VerticalPanel();

        final Label labelData = new Label("Data*:");
        labelData.getElement().setClassName("label");
        formPanel.add(labelData);
        final DatePicker data = new DatePicker();
        data.getElement().setClassName("input");
        formPanel.add(data);

        final Label labelOrario = new Label("Orario*:");
        labelOrario.getElement().setClassName("label");
        formPanel.add(labelOrario);
        final TextBox orario = new TextBox();
        orario.getElement().setClassName("input");
        orario.setName("Orario");
        formPanel.add(orario);


        final Label labelHardness = new Label("Hardness*:");
        labelHardness.getElement().setClassName("label");
        formPanel.add(labelHardness);
        final ListBox hardness = new ListBox();
        hardness.getElement().setClassName("input");
        hardness.addItem("");
        hardness.addItem("Facile");
        hardness.addItem("Medio");
        hardness.addItem("Difficile");
        formPanel.add(hardness);


        final Label labelAula = new Label("Aula*:");
        labelAula.getElement().setClassName("label");
        formPanel.add(labelAula);
        formPanel.add(labelAula);
        final TextBox aula = new TextBox();
        aula.getElement().setClassName("input");
        aula.setName("Aula");
        formPanel.add(aula);

        Button send = new Button("Inserisci");
        send.getElement().setClassName("btn-send");
        send.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                nuovoEsame.submit();
            }
        });
        formPanel.add(send);

        nuovoEsame.add(formPanel);

        nuovoEsame.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent submitEvent) {
                if (data.getValue().toString().length() == 0 || orario.getText().length() == 0 || hardness.getSelectedItemText().length() == 0 || aula.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi");
                    submitEvent.cancel();
                }
            }
        });

        nuovoEsame.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {
                //to do
            }
        });
    }





}

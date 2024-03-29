/**
 * Classe che implementa l'interfaccia Pagina e mostra il form di login per accedere al portale.
 **/
package com.unibo.progettosweng.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Iscrizione;
import com.unibo.progettosweng.client.model.Utente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Login implements Pagina {
    FormPanel login;
    Image logo = new Image();
    private final UtenteServiceAsync utente = GWT.create(UtenteService.class);

    @Override
    public void aggiungiContenuto(){
        RootPanel.get("contenuto").clear();
        ProgettoSWENG.rimuoviMenu();
        RootPanel.get("login").clear();
        VerticalPanel formContainer = new VerticalPanel();
        formContainer.getElement().setClassName("formContainer");

        VerticalPanel userCard = new VerticalPanel();
        userCard.getElement().setClassName("userCard");

        VerticalPanel logoContainer = new VerticalPanel();
        logoContainer.getElement().setClassName("logoContainer");

        VerticalPanel brandLogoContainer = new VerticalPanel();
        brandLogoContainer.getElement().setClassName("brandLogoContainer");
        logo.addStyleName("brandLogo");
        logo.setUrl("img/logoBianco.png");
        brandLogoContainer.add(logo);
        logoContainer.add(brandLogoContainer);
        userCard.add(logoContainer);

        VerticalPanel loginContainer = new VerticalPanel();
        loginContainer.getElement().setClassName("loginContainer");
        createForm();
        loginContainer.add(login);
        userCard.add(loginContainer);
        formContainer.add(userCard);
        RootPanel.get("contenuto").add(formContainer);
    }

    private void createForm(){
        login = new FormPanel();
        login.setAction("/login");
        login.setMethod(FormPanel.METHOD_POST);

        VerticalPanel formPanel = new VerticalPanel();
        final Label labelUsername = new Label("Username:");
        labelUsername.getElement().setClassName("label");
        formPanel.add(labelUsername);
        final TextBox username = new TextBox();
        username.getElement().setClassName("input");
        username.setName("Username");
        formPanel.add(username);

        final Label labelPassword = new Label("Password:");
        labelPassword.getElement().setClassName("label");
        formPanel.add(labelPassword);
        final PasswordTextBox password = new PasswordTextBox();
        password.getElement().setClassName("input");
        password.setName("Password");
        formPanel.add(password);

        Button send = new Button("Login");
        send.getElement().setClassName("btn-login");
        send.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                login.submit();
            }
        });
        formPanel.add(send);

        login.add(formPanel);

        login.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent submitEvent) {
                if (username.getText().length() == 0 || password.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi.");
                    submitEvent.cancel();
                }
            }
        });

        login.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {

                utente.login(username.getText(), password.getText(), new
                        AsyncCallback<Utente>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                Window.alert("Login failure: " + throwable.getMessage());
                            }
                            @Override
                            public void onSuccess(Utente utente) {
                                if(utente!= null){
                                   String tipo =utente.getTipo();
                                    switch(tipo) {
                                        case "Studente":
                                            try {
                                                PortaleStudente ps = new PortaleStudente();
                                                ps.caricaPortale(utente);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case "Docente":
                                            try {
                                                PortaleDocente pd = new PortaleDocente();
                                                pd.caricaPortale(utente);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case "Admin":
                                            try {
                                                PortaleAdmin pa = new PortaleAdmin();
                                                pa.caricaPortale(utente);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case "Segreteria":
                                            try {
                                                PortaleSegreteria psg = new PortaleSegreteria();
                                                psg.caricaPortale(utente);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                    }
                                }else{
                                    Window.alert("Username o password incorretti.");
                                }
                            }
                        });

            }
        });
    }

}

package com.unibo.progettosweng.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class Login implements Pagina{
    FormPanel login;
    Image logo = new Image();

    @Override
    public void aggiungiContenuto(){
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
        send.getElement().setClassName("btn_login");
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
                    Window.alert("Compilare tutti i campi");
                    submitEvent.cancel();
                }
            }
        });

        login.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {
                //to do
            }
        });
    }





}

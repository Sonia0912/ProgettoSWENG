/**
 * HOMEPAGE
 */
package com.unibo.progettosweng.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;
import com.unibo.progettosweng.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ProgettoSWENG implements EntryPoint {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
          + "attempting to contact the server. Please check your network "
          + "connection and try again.";

  /**
   * Create a remote service proxy to talk to the server-side Greeting service.
   */
  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    final String uniInfoString = "" +
            "<div class=\"infoUniHomepage\">" +
            " <div class=\"contenuto\">" +
            " <span class=\"titoletto\">La nostra storia</span>" +
            " <p id=\"universitaInfo\">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt\"" +
            "   ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex \"" +
            "   ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\"" +
            "   Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum." +
            " </p>" +
            "</div>"+
            "</div>";
    final String infoDipString = "" +
            "<div id=\"dipartimenti\" class=\"dipartimentiHomepage\">\n" +
            " <div class=\"contenuto\">" +
            "   <span class=\"titoletto\">I dipartimenti</span>\n" +
            "   <div id=\"dipInfo\"></div>\n" +
            " </div>"+
            "</div>";
    final String[] nomiDipartimenti = {"Matematica", "Fisica", "Informatica"};
    final String[] infoDipartimenti = {"Info 1", "Info 2", "Info 3"};
    final String testoPulsante = "<span>Il mio portale</span>";
    final String contattiString = "Telefono: 123-456-789 <br /> Indirizzo: Via delle Stelle, 56 Bologna <br /> E-mail: unitech@mail.com";

    // Informazioni universita'
    final HTML uniInfo = new HTML(uniInfoString);
    RootPanel.get("container").add(uniInfo);

    final HTML infoDip = new HTML(infoDipString);
    RootPanel.get("container").add(infoDip);

    // Dipartimenti
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
//    RootPanel.get("dipInfo").add(tabPanel);
    HTMLPanel container = new HTMLPanel("container");
    container.add(tabPanel, "dipInfo");

    // Login
    final Button login = new Button(testoPulsante);
    RootPanel.get("login").add(login);

    login.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        RootPanel.get("container").clear();
        InserimentoUtente newUser = new InserimentoUtente();
        RootPanel.get("container").add(newUser.getFormContainer());
      }
    });

    // Contatti
    final HTML contatti = new HTML(contattiString);
    RootPanel.get("contattiInfo").add(contatti);



/*    final Button sendButton = new Button("Send");
    final TextBox nameField = new TextBox();
    nameField.setText("GWT User");
    final Label errorLabel = new Label();

    // We can add style names to widgets
    sendButton.addStyleName("sendButton");

    // Add the nameField and sendButton to the RootPanel
    // Use RootPanel.get() to get the entire body element
    RootPanel.get("nameFieldContainer").add(nameField);
    RootPanel.get("sendButtonContainer").add(sendButton);
    RootPanel.get("errorLabelContainer").add(errorLabel);

    // Focus the cursor on the name field when the app loads
    nameField.setFocus(true);
    nameField.selectAll();

    // Create the popup dialog box
    final DialogBox dialogBox = new DialogBox();
    dialogBox.setText("Remote Procedure Call");
    dialogBox.setAnimationEnabled(true);
    final Button closeButton = new Button("Close");
    // We can set the id of a widget by accessing its Element
    closeButton.getElement().setId("closeButton");
    final Label textToServerLabel = new Label();
    final HTML serverResponseLabel = new HTML();
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");
    dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
    dialogVPanel.add(textToServerLabel);
    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
    dialogVPanel.add(serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    dialogVPanel.add(closeButton);
    dialogBox.setWidget(dialogVPanel);

    // Add a handler to close the DialogBox
    closeButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        sendButton.setEnabled(true);
        sendButton.setFocus(true);
      }
    });

    // Create a handler for the sendButton and nameField
    class MyHandler implements ClickHandler, KeyUpHandler {
      *//**
     * Fired when the user clicks on the sendButton.
     *//*
      public void onClick(ClickEvent event) {
        sendNameToServer();
      }

      *//**
     * Fired when the user types in the nameField.
     *//*
      public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          sendNameToServer();
        }
      }

      *//**
     * Send the name from the nameField to the server and wait for a response.
     *//*
      private void sendNameToServer() {
        // First, we validate the input.
        errorLabel.setText("");
        String textToServer = nameField.getText();
        if (!FieldVerifier.isValidName(textToServer)) {
          errorLabel.setText("Please enter at least four characters");
          return;
        }
        
        // Then, we send the input to the server.
        sendButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");
        greetingService.greetServer(textToServer, new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            // Show the RPC error message to the user
            dialogBox.setText("Remote Procedure Call - Failure");
            serverResponseLabel.addStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(SERVER_ERROR);
            dialogBox.center();
            closeButton.setFocus(true);
          }

          public void onSuccess(String result) {
            dialogBox.setText("Remote Procedure Call");
            serverResponseLabel.removeStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(result);
            dialogBox.center();
            closeButton.setFocus(true);
          }
        });
      }
    }

    // Add a handler to send the name to the server
    MyHandler handler = new MyHandler();
    sendButton.addClickHandler(handler);
    nameField.addKeyUpHandler(handler);*/
  }
}
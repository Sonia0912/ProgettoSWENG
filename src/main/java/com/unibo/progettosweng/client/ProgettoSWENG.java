/**
 * HOMEPAGE
 */
package com.unibo.progettosweng.client;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.unibo.progettosweng.shared.model.Utente;

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

    final String[] menuSections = {"Home", "Dipartimenti"};
    final Button[] menuButtons = new Button[menuSections.length];
    final String testoPulsante = "<span>Il mio portale</span>";
    final String contattiString = "123-456-789 <br /> Via delle Stelle, 56 Bologna <br /> unitech@mail.com";
    final String mappeString = "Mappa del campus <br /> Direzioni <br /> Area circostante <br /> Tour virtuale del campus";
    final String lavoroString = "Risorse umane";

    // Menu
    HorizontalPanel hPanel = new HorizontalPanel();
    hPanel.setSpacing(5);
    for (int i = 0; i < menuSections.length; i++) {
      menuButtons[i] = new Button(menuSections[i]);
      hPanel.add(menuButtons[i]);
    }
    RootPanel.get("pannelloMenu").add(hPanel);

    // Di default mostro il contenuto della homepage
    Homepage hp = new Homepage();
    hp.aggiungiContenuto();

    // Se clicco su Home
    menuButtons[0].addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        Homepage hp = new Homepage();
        hp.aggiungiContenuto();
      }
    });

    // Se clicco su Dipartimenti
    menuButtons[1].addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        Dipartimenti dip = new Dipartimenti();
        dip.aggiungiContenuto();
      }
    });

    // Login
    final Button login = new Button(testoPulsante);
    RootPanel.get("login").add(login);

    String tipo = "studente";

    login.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        switch(tipo) {
          case "studente":
            PortaleStudente ps = new PortaleStudente();
            ps.caricaPortale();
            break;
          case "docente":
            PortaleDocente pd = new PortaleDocente();
            pd.caricaPortale();
            break;
          case "admin":
            //PortaleAdmin portale = new PortaleAdmin();
            break;
          case "segreteria":
            //PortaleSegreteria portale = new PortaleSegreteria();
            break;
        }
      }
    });

    // Contatti
    final HTML contatti = new HTML(contattiString);
    final HTML mappe = new HTML(mappeString);
    final HTML lavoro = new HTML(lavoroString);
    RootPanel.get("contattiInfo").add(contatti);
    RootPanel.get("mappeInfo").add(mappe);
    RootPanel.get("lavoraInfo").add(lavoro);


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
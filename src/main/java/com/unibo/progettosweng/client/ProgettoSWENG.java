package com.unibo.progettosweng.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
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
    VerticalPanel root = new VerticalPanel();

    //Header
    HorizontalPanel header = new HorizontalPanel();
    final String headerTitleString = "<span id=\"nome\">Università della Scientia</span>\n";
    final HTML headerTitle = new HTML(headerTitleString);
    header.add(headerTitle);
    //Pulsante nuovo utente
    final Button insUtente = new Button("Nuovo Utente");
    insUtente.setStyleName("headerButton");
    header.add(insUtente);
    header.getElement().getStyle().setPaddingTop(50, Style.Unit.PX);
    header.getElement().getStyle().setPaddingBottom(50, Style.Unit.PX);
    // Pulsante log in
    final Button login = new Button("Il mio portale");
    login.setStyleName("headerButton");
    header.add(login);
    header.setWidth("100%");
    header.setCellWidth(headerTitle,"85%");
    root.add(header);

    HorizontalPanel content = new HorizontalPanel();
    // Informazioni universita'
    final String uniInfoString = "" +
            "<div class=\"infoUniHomepage\">\n" +
            "   <span class=\"titoletto\">La nostra storia</span>\n" +
            "   <p id=\"universitaInfo\">"+
            "     Lorem ipsum dolor sit amet, " +
            "     consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
            "     ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
            "     quis nostrud exercitation ullamco laboris nisi ut aliquip ex " +
            "     ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
            "     voluptate velit esse cillum dolore eu fugiat nulla pariatur." +
            "     Excepteur sint occaecat cupidatat non proident, sunt in culpa " +
            "     qui officia deserunt mollit anim id est laborum."+
            "   </p>\n"+
            "</div>";
    final HTML uniInfo = new HTML(uniInfoString);
    content.add(uniInfo);
    // Informazioni dipartimenti
    final String dipInfoString = "" +
            "<div id=\"dipartimenti\" class=\"dipartimentiHomepage\">\n" +
            "   <p id=\"dipInfo\">" +
            "     Qui ci sono i dipartimenti" +
            "   </p>\n" +
            "</div>";
    final HTML dipInfo = new HTML(dipInfoString);
    content.add(dipInfo);
    content.setWidth("100%");
    content.setCellWidth(uniInfo,"30%");
    content.setCellWidth(dipInfo, "100%");
    root.add(content);

    // Contatti
    final String contattiString = "" +
            "<div id=\"contatti\" class=\"contattiHomepage\">" +
            " <span class=\"titoletto\">Per contattarci</span>\n" +
            " <p id=\"contattiInfo\">" +
            "    Telefono: 123-456-789 <br /> Indirizzo: Via delle Stelle, 56 Bologna <br />" +
            "    E-mail: uniscientia@mail.com" +
            " </p>"+
            "</div>";
    final HTML contatti = new HTML(contattiString);
    root.add(contatti);

    RootPanel.get().add(root);

    insUtente.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        root.remove(1);
        root.insert(getFormInserimentoUtente(),1);
        RootPanel.get().add(root);
      }
    });

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

  public FormPanel getFormInserimentoUtente(){
    final FormPanel nuovoUtente = new FormPanel();
    nuovoUtente.setAction("/creaNuovoUtente");
    nuovoUtente.setMethod(FormPanel.METHOD_POST);

    VerticalPanel formPanel = new VerticalPanel();
    final Label labelNome = new Label("Nome*:");
    formPanel.add(labelNome);
    final TextBox nome = new TextBox();
    nome.setName("Nome");
    formPanel.add(nome);

    final Label labelCognome = new Label("Cognome*:");
    formPanel.add(labelCognome);
    final TextBox cognome = new TextBox();
    cognome.setName("Cognome");
    formPanel.add(cognome);

    final Label labelEmail = new Label("Email*:");
    formPanel.add(labelEmail);
    final TextBox email = new TextBox();
    email.setName("Email");
    formPanel.add(email);

    final Label labelTipo = new Label("Tipo di utente*:");
    formPanel.add(labelTipo);
    ListBox tipo = new ListBox();
    tipo.addItem("");
    tipo.addItem("Docente");
    tipo.addItem("Studente");
    tipo.addItem("Segreteria");
    tipo.addItem("Admin");

    formPanel.add(tipo);

    formPanel.add(new Button("Inserisci", new ClickHandler() {
      public void onClick(ClickEvent event) {
        nuovoUtente.submit();
      }
    }));

    nuovoUtente.add(formPanel);

    nuovoUtente.addSubmitHandler(new FormPanel.SubmitHandler() {
      @Override
      public void onSubmit(FormPanel.SubmitEvent submitEvent) {
        if (nome.getText().length() == 0 || cognome.getText().length() == 0 || tipo.getSelectedItemText().equals("") || email.getText().length() == 0) {
          Window.alert("Compilare tutti i campi");
          submitEvent.cancel();
        }
      }
    });

    nuovoUtente.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
      @Override
      public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {
        //to do
      }
    });

    return nuovoUtente;
  }
}

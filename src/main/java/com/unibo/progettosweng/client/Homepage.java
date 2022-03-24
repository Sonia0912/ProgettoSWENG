package com.unibo.progettosweng.client;

import com.google.gwt.user.client.ui.*;

public class Homepage implements Pagina {

    final static String[] titoliSezioni = {"FUTURI STUDENTI", "PROGRAMMI", "AGGIORNAMENTI 2021-2022", "TOUR VIRTUALE DEL CAMPUS"};
    final static String[] paragrafiSezioni = {"Unitech’s unique personality rests on the bedrock values of academic excellence, inclusivity, and social responsibility.",
                                                "A Unitech education fosters intellectual inquiry and reflection, personal growth, and a commitment to the world beyond oneself.",
                                                "For the latest Academic Year 2021-22 updates, please click below.",
                                                "Take a virtual tour, learn about admission and financial aid, and speak with current students."};
    final static String[] titoloContatore = {"Studenti", "Corsi", "Dipartimenti", "Docenti"};
    final static int[] numeriContatore = {3400, 75, 14, 230};
    final static String[] motivoTitoli = {"Apprendimento flessibile", "Prospetti lavorativi", "Un mondo di opportunità"};
    final static String[] motivoParagrafo = {"University doesn’t necessarily involve several years of full-time study in one place. Flexible study options let you choose how and when you learn. These include part-time study, distance and online learning, work-based learning and study abroad options.",
                                                "If you know what you want to do after graduating, a vocational degree can significantly improve your job prospects at the same time as giving you professional skills and knowledge relevant to your future career.",
                                                "At university, you’ll have the opportunity to broaden your horizons by meeting new people from all over the world, facing new challenges and making use of the huge range of new activities, clubs and societies on offer. You may even be able to take part of your course overseas and learn a new language and culture."};
    final static Image fotoP1 = new Image();
    final static Image fotoP2 = new Image();
    final static HTML titolo1 = new HTML("L'istruzione superiore comincia qui");
    final static HTML paragrafo1 = new HTML("<i>Commitment is something that comes from understanding that everything has its price and then having the willingness to pay that price.</i> <br /><br /> The other virtues practice in succession by Franklin were silence, order, resolution, frugality, industry, sincerity, Justice, moderation, cleanliness, tranquility, chastity and humility. For the summary order he followed a little scheme of employing his time each day.");
    final static HTML titolo2 = new HTML("Vita vera. Conoscenza vera. Persone vere.");
    final static HTML paragrafo2 = new HTML("<i>Smart is an acronym for Specific, Measurable, Realistic and Time Sensitive.</i> <br /><br /> The first thing to remember about success is that it is a process – nothing more, nothing less. There is really no magic to it and it’s not reserved only for a select few people. As such, success really has nothing to do with luck, coincidence or fate. It really comes down to understanding the steps in the process and then executing on those steps.");
    final static HTML divImgHp = new HTML("<div id=\"divHp\"> <img src=\"img/campusBuilding.jpg\" class=\"uniFotoSfondo\"> <div class=\"centeredAbove\">UNITECH</div> <div class=\"centeredBelow\">Scienza, tecnologia e innovazione</div> </div>");
    final static HTML contatore1 = new HTML("<div class=\"contatore\"><div class=\"contatoreColonne\"><img class=\"iconaContatore\" src=\"img/iconaStudenti.png\" /><div class=\"colonnaNumeri\"><div class=\"numeroContatore\">" + numeriContatore[0] + "</div><div class=\"titoloContatore\">" + titoloContatore[0] + "</div></div></div></div>");
    final static HTML contatore2 = new HTML("<div class=\"contatore\"><div class=\"contatoreColonne\"><img class=\"iconaContatore\" src=\"img/iconaCorsi.png\" /><div class=\"colonnaNumeri\"><div class=\"numeroContatore\">" + numeriContatore[1] + "</div><div class=\"titoloContatore\">" + titoloContatore[1] + "</div></div></div></div>");
    final static HTML contatore3 = new HTML("<div class=\"contatore\"><div class=\"contatoreColonne\"><img class=\"iconaContatore\" src=\"img/iconaDipartimenti.png\" /><div class=\"colonnaNumeri\"><div class=\"numeroContatore\">" + numeriContatore[2] + "</div><div class=\"titoloContatore\">" + titoloContatore[2] + "</div></div></div></div>");
    final static HTML contatore4 = new HTML("<div class=\"contatore\"><div class=\"contatoreColonne\"><img class=\"iconaContatore\" src=\"img/iconaProfessore.png\" /><div class=\"colonnaNumeri\"><div class=\"numeroContatore\">" + numeriContatore[3] + "</div><div class=\"titoloContatore\">" + titoloContatore[3] + "</div></div></div></div>");

    final static HTML divStudentiHp = new HTML("<div class=\"titoloStudenti\">Incontra i nostri studenti</div><div class=\"sottotitoloStudenti\">Unitech students tell their own stories of what life at Unitech means to them.</div>");
    final static HTML studente1 = new HTML("<div class=\"immagineStudenteDiv\"><img src=\"img/studente1.jpg\" class=\"immagineStudente\"/><div class=\"nomeStudente\">Luca Rossi</div><div class=\"facoltaStudente\">Fisica</div></div>");
    final static HTML studente2 = new HTML("<div class=\"immagineStudenteDiv\"><img src=\"img/studente2.jpg\" class=\"immagineStudente\"/><div class=\"nomeStudente\">Sara Verdi</div><div class=\"facoltaStudente\">Matematica</div></div>");
    final static HTML studente3 = new HTML("<div class=\"immagineStudenteDiv\"><img src=\"img/studente3.jpg\" class=\"immagineStudente\"/><div class=\"nomeStudente\">Giulia Bianchi</div><div class=\"facoltaStudente\">Informatica</div></div>");

    @Override
    public void aggiungiContenuto() {
        RootPanel.get("contenuto").clear();

        VerticalPanel hpPanel = new VerticalPanel();
        hpPanel.addStyleName("hpPanel");

        // Hero
        hpPanel.add(divImgHp);

        // Quattro pannelli verticali
        HorizontalPanel hPanel4Sezioni = new HorizontalPanel();
        hPanel4Sezioni.addStyleName("hPanel4Sezioni");
        HTML sezione1 = new HTML("<img class=\"icona\" src=\"img/iconaCappello.png\" /><div class=\"titoloSezione\">"+ titoliSezioni[0] + "</div><div class=\"linea\"></div><div class=\"paragrafoSezione\">" + paragrafiSezioni[0] + "</div>");
        HTML sezione2 = new HTML("<img class=\"icona\" src=\"img/iconaLampadina.png\" /><div class=\"titoloSezione\">"+ titoliSezioni[1] + "</div><div class=\"linea\"></div><div class=\"paragrafoSezione\">" + paragrafiSezioni[1] + "</div>");
        HTML sezione3 = new HTML("<img class=\"icona\" src=\"img/iconaCalendario.png\" /><div class=\"titoloSezione\">"+ titoliSezioni[2] + "</div><div class=\"linea\"></div><div class=\"paragrafoSezione\">" + paragrafiSezioni[2] + "</div>");
        HTML sezione4 = new HTML("<img class=\"icona\" src=\"img/iconaMappa.png\" /><div class=\"titoloSezione\">"+ titoliSezioni[3] + "</div><div class=\"linea\"></div><div class=\"paragrafoSezione\">" + paragrafiSezioni[3] + "</div>");
        sezione1.addStyleName("sezione1");
        sezione2.addStyleName("sezione2");
        sezione3.addStyleName("sezione3");
        sezione4.addStyleName("sezione4");
        hPanel4Sezioni.add(sezione1);
        hPanel4Sezioni.add(sezione2);
        hPanel4Sezioni.add(sezione3);
        hPanel4Sezioni.add(sezione4);
        hpPanel.add(hPanel4Sezioni);

        // Foto e paragrafo 1 (foto a sinistra)
        HorizontalPanel hPanel1 = new HorizontalPanel();
        hPanel1.addStyleName("pannelloHP");
        VerticalPanel vPanel1 = new VerticalPanel();
        fotoP1.addStyleName("fotoParagrafoHp1");
        fotoP1.setUrl("img/graduationHats.jpg");
        titolo1.addStyleName("titoletto1");
        paragrafo1.addStyleName("paragrafo1");
        vPanel1.add(titolo1);
        vPanel1.add(paragrafo1);
        hPanel1.add(fotoP1);
        hPanel1.add(vPanel1);
        hpPanel.add(hPanel1);

        // Cifre
        HorizontalPanel hPanelCifre = new HorizontalPanel();
        hPanelCifre.addStyleName("hPanelCifre");
        hPanelCifre.add(contatore1);
        hPanelCifre.add(contatore2);
        hPanelCifre.add(contatore3);
        hPanelCifre.add(contatore4);
        hpPanel.add(hPanelCifre);

        // Foto e paragrafo 2 (foto a destra)
        HorizontalPanel hPanel2 = new HorizontalPanel();
        hPanel2.addStyleName("pannelloHP");
        VerticalPanel vPanel2 = new VerticalPanel();
        fotoP2.addStyleName("fotoParagrafoHp2");
        fotoP2.setUrl("img/classroom1.jpg");
        titolo2.addStyleName("titoletto2");
        paragrafo2.addStyleName("paragrafo2");
        vPanel2.add(titolo2);
        vPanel2.add(paragrafo2);
        hPanel2.add(vPanel2);
        hPanel2.add(fotoP2);
        hpPanel.add(hPanel2);

        // Perche' Unitech
        hpPanel.add(new HTML("<div class=\"motiviTitolo\">Perché scegliere Unitech?</div>"));
        Grid grid = new Grid(1, 3);
        grid.addStyleName("gridMotivi");
        grid.setWidget(0, 0, new HTML("<div class=\"motivo\"><div class=\"motivoTitolo\">" + motivoTitoli[0] + "</div><div class=\"motivoParagrafo\">" + motivoParagrafo[0] + "</div></div>"));
        grid.setWidget(0, 1, new HTML("<div class=\"motivo\"><div class=\"motivoTitolo\">" + motivoTitoli[1] + "</div><div class=\"motivoParagrafo\">" + motivoParagrafo[1] + "</div></div>"));
        grid.setWidget(0, 2, new HTML("<div class=\"motivo\"><div class=\"motivoTitolo\">" + motivoTitoli[2] + "</div><div class=\"motivoParagrafo\">" + motivoParagrafo[2] + "</div></div>"));
        hpPanel.add(grid);

        // Studenti
        hpPanel.add(divStudentiHp);
        HorizontalPanel hPanelStudenti = new HorizontalPanel();
        hPanelStudenti.addStyleName("hPanelStudenti");
        hPanelStudenti.add(studente1);
        hPanelStudenti.add(studente2);
        hPanelStudenti.add(studente3);
        hpPanel.add(hPanelStudenti);


        RootPanel.get("contenuto").add(hpPanel);
    }
}

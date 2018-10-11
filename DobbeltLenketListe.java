package no.oslomet.cs.algdat;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Group on 9/30/2018.
 * Amina Shahzad
 * xxxx
 * Harith Elamin s316494
 */
//double linked list
//Klassen DobbeltLenketListe er generisk
//https://www.cs.hioa.no/~ulfu/appolonius/kildekode/DobbeltlenketHashTabell.html
//https://github.com/Harithelamin/DDL.gi
public class DobbeltLenketListe <T> implements Liste<T> {

    private static final class Node<T> {
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi) {
            this(verdi, null, null);
        }
    }
    //
    private Node<T> hode;           //peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen




    // konstruktør
    public DobbeltLenketListe()
    {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    //Opgave 0;
    //Flytt grensesnittene Beholder, Liste og skjelettet til klassen DobbeltLenketListe

    //Opgave 1
    //

    // Den første skal returnere antallet verdier i listen
    @Override
    public int antall() {
        return antall;
    }

    //den andre skal returnere true/false avhengig av om listen er tom eller ikke
    @Override
    public boolean tom() {
        return antall == 0;
    }

    //Lag så konstruktøren
    // Den skal lage en dobbeltlenket liste med verdiene fra tabellen a
    public DobbeltLenketListe(T[] a) {
        // Hvis a er null, skal det kastes en NullPointerException med teksten "Tabellen a er null!"
        // (bruk f.eks. en requireNonNull-metode fra klassen Objects).
        Objects.requireNonNull(a, "Tabellen a er null!");
        //forrige og hale.neste være null
        hode = hale = new Node<>(null); // en tom list.

        for (T verdi : a) {
            if (verdi != null) {
                //Her må du passe på at hode peker til den første i listen og hale til den siste
                hale = hale.neste = new Node(verdi, hale, null);
                antall++;
            }
        }
        //Hvis alle verdiene i a er null, får vi en tom liste.
        if (antall == 0) hode = hale = null;
        else {
            // både hode.forrige og hale.neste være null.
            (hode = hode.neste).forrige = null;
        }
    }

    //******************************************************************************************************************
    //Oppgave 2- a

    //Den skal returnere en tegnstreng med listens verdier.
    // Hvis listen f.eks. inneholder tallene 1, 2 og 3,
    // skal metoden returnere strengen "[1, 2, 3]" og kun "[]" hvis listen er tom.
    // Du skal bruke en StringBuilder, or StringJoiner.
    @Override
    public String toString() {
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder("," + "," + " [ " + "]! ");

        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (Node node = hode; node != null; node = node.neste) {
            // stringBuilder.append(node.verdi.toString());
            stringJoiner.add(node.verdi.toString());
        }

        // return stringBuilder.toString();
        return stringJoiner.toString();
    }

    // Den skal returnere en tegnstreng på samme form som den toString() gir,
    // men verdiene skal komme i omvendt rekkefølge
    // // Utskrift: [] [A] [A, B] [] [A] [B, A]
    public String omvendtString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (Node<T> p = hale; p != null; p = p.forrige) sj.add(p.verdi.toString());
        return sj.toString();
    }

    //Oppgave 2- b
    // Null-verdier er ikke tillatt
    // Start derfor med en sjekk (bruk en requireNonNull-metode fra klassen Objects).
    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Null-verdier er ikke tillatt");
        //Innleggingsmetoden skal legge en ny node med oppgitt verdi
        //Her må du skille mellom to tilfeller: 1) at listen på forhånd er tom og 2) at den ikke er tom.
        // I en tom liste skal både hode og hale være null (og antall lik 0)

        //(både forrige-peker og neste-peker i noden skal da være null)
        Node node = new Node(verdi, hale, null);
        hale = tom() ? (hode = node) : (hale.neste = node);
        //Husk at antallet må økes etter en innlegging
        // Det samme med variabelen endringer
        antall++;
        endringer++;
        return true;
    }

    //******************************************************************************************************************

    //Opgave 3-a
    //Lag den private hjelpemetoden
    // Den skal returnere noden med den gitte indeksen/posisjonen. Hvis indeks er mindre enn antall/2,
    // så skal letingen etter noden starte fra hode og gå mot høyre ved hjelp av neste-pekere.
    // Hvis ikke, skal letingen starte fra halen og gå mot venstre ved hjelp av forrige-pekere. Lag
    private Node<T> finnNode(int indeks) {
        Node node;
        if (indeks < antall / 2) {
            node = hode;
            for (int i = 0; i < indeks; i++) {
                node = node.neste;
            }
        } else {
            node = hale;
            for (int i = antall - 1; i > indeks; i--) {
                node = node.forrige;
            }
        }
        return node;
    }

    //Oppgave 3-b
    //bruke finnNode()
    //Bruk metoden indeksKontroll() ???
    //fra Liste (bruk false som parameter). Lag også metoden T oppdater(int indeks, T nyverdi).
    // Den skal erstatte verdien på plass indeks med nyverdi og returnere det som lå der fra før
    // Husk at indeks må sjekkes, at null-verdier ikke skal kunne legges inn og at variabelen endringer skal økes.
    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    // Den skal returnere en liste (en instans av klassen DobbeltLenketListe) som
    // inneholder verdiene fra intervallet [fra:til> i «vår» liste.
    //
    //Her må det først sjekkes om indeksene fra og til er lovlige
    //Hvis ikke, skal det kastes unntak slik som i metoden fratilKontroll().
    //Legg derfor den inn som en privat metode i klassen DobbeltLenketListe og bytt ut
    // ArrayIndexOutOfBoundsException med IndexOutOfBoundsException siden vi ikke har noen tabell (array) her.
    //Bytt også ut ordet tablengde med ordet antall.
    // Denne kontrollmetoden kan da kalles med antall, fra og til som argumenter.
    // Husk at et tomt intervall er lovlig. Det betyr at vi får en tom liste
    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(antall, fra, til);
        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();  // ny liste
        Node<T> p = finnNode(fra);        // finner noden med indeks lik fra
        for (int i = fra; i < til; i++)   // henter verdiene i [fra:til>
        {
            liste.leggInn(p.verdi);
            p = p.neste;
        }

        return liste;
    }

    @Override
    public T oppdater(int indeks, T v) {
        Objects.requireNonNull(v, "Ikke tilltat null verdi");
        indeksKontroll(indeks, false);
        Node node = finnNode(indeks);
        T t = (T) node.verdi;
        node.verdi = v;
        endringer++;
        return t;
    }

    //Flg. metode, som skal legges i samleklassen Tabell, tester om et halvåpent tabellintervall, dvs. intervallet a[fra:til>, er lovlig:
    public static void fratilKontroll(int tablengde, int fra, int til) {
        if (fra < 0)                                  // fra er negativ
            throw new ArrayIndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > tablengde)                          // til er utenfor tabellen
            throw new ArrayIndexOutOfBoundsException
                    ("til(" + til + ") > tablengde(" + tablengde + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    //Oppgave 4 -a
    // Den skal returnere indeksen/posisjonen til verdi hvis den finnes i listen og returnere -1 hvis den ikke finnes.
    //Her skal det ikke kastes unntak hvis verdi er null.Metoden skal isteden returnere -1

    @Override
    public int indeksTil(T verdi) {
        Node<T> node = hode;
        //Her sjekker jeg om verdien ligger i første indeks
        if (verdi == null) return -1;

        //Her går vi gjennom hele liste, og sjekker om verdien til hver enkel node er lik neste verdi
        for (int i = 0; i < antall; node = node.neste)
        {

            if (node.verdi.equals(verdi))  return i;
            i++;
        }
        return -1;
    }

    //Oppgave 4 -b
    //Den skal returnere true hvis listen inneholder verdi og returnere false ellers
    //Her lønner det seg å bruke et kall på metoden indeksTil som en del av koden.
    @Override
    public boolean inneholder(T verdi) {
        //Finner indeksen til gitte verdien, hvis indeksen er forskjellig
        //fra -1, da finnes den. Ellers ikke
        int indeks = indeksTil(verdi);
        if (indeks == -1) {
            return false;
        }
        else{
            return true;
        }
    }



    //
    //Oppgave 5
    // Den skal legge verdi inn i listen slik at den får indeks/posisjon indeks.
    //Husk at negative indekser og indekser større enn antall er ulovlige (
    // indekser fra og med 0 til og med antall er lovlige).
    //Her må du passe på de fire tilfellene
    // 1) listen er tom,
    // 2) verdien skal legges først,
    // 3) verdien skal legges bakerst og
    // 4) verdien skal legges mellom to andre verdier
    // Spesielt skal forrige-peker i den første noden være null og neste-peker i den siste noden være null.
    @Override
    public void leggInn(int indeks, T verdi){
        Objects.requireNonNull(verdi, "verdien kan ikke være null");
        //Negative indekser og indekser større enn null er ulovlige
        if(indeks < 0) throw new IndexOutOfBoundsException("indeks " + indeks + "er negativ");
        else if(indeks > antall) throw new IndexOutOfBoundsException("Indeks " + indeks +  " > antall(" + antall + ") noder!");

        //listen er tom
        if(antall == 0 & indeks == 0) hode = hale = new Node<T>(verdi, null, null);
            //verdien skal legges først
        else if ( indeks == 0) {
            hode = new Node<T>(verdi, null, hode);
            hode.neste.forrige = hode;
            //verdien skal legges bakerst
        }
        else if (indeks == antall) {
            hale = new Node<T>(verdi, hale, null);
            hale.forrige.neste = hale;
        }
        //legges i midten
        else {
            Node<T> p = hode;
            for(int i = 0; i < indeks-1; i++)      {
                p = p.neste;
            }
            Node<T> q = new Node<>(verdi, p, p.neste );
            p.neste = q;
            q.neste = p.neste;
            q.forrige = p;
            q.neste.neste = q;

        }

        antall ++;
    }
    //
    //Oppgave 6
    //Den første skal fjerne (og returnere) verdien på posisjon indeks (som først må sjekkes).
    //Hvis det er flere forekomster av verdier det den første av dem (fra venstre) som skal fjernes.
    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks,false);
        Node node=finnNode(indeks);
        fjernNode(node);
        return (T) node.verdi;
    }


    //Den andre skal fjerne verdi fra listen og så returnere true.
    ////Hvis verdi ikke er i listen, skal metoden returnere false.
    //Her skal det ikke kastes unntak hvis verdi er null
    //Metoden skal isteden returnere false.
    @Override
    public boolean fjern(T verdi) {
        if (verdi==null)return false;
        for (Node node=hode; node!= null; node=node.neste)
        {
            while (node.verdi.equals(verdi));
            fjernNode(node);
            return true;
        }
        return false;
    }

    public void fjernNode(Node noede){
        if (noede==hode){
            if (antall==1)
            {
                hode=hale=null;
            }else
            {
                (hode=hode.neste).forrige= null;
            }
        }
        else if (noede != hale) {
            (noede.forrige.neste = noede.neste).forrige = noede.forrige;
        } else {
            (hale=hale.forrige).neste=null;


        }
        endringer++;
        antall--;
    }

    //Oppgave 7
    //Den skal «tømme» listen og nulle alt slik at «søppeltømmeren» kan hente alt som ikke lenger brukes.
    @Override
    public void nullstill() {
        Node node = hode;
        while (node != null)
        {
            Node temp = node.neste;
            node.verdi = null;
            node.forrige = null;
            node.forrige = null;
            node = temp;
        }

        hode = hale = null;

        antall = 0;
        endringer++;
    }


    @Override
    public Iterator<T> iterator() {
        return null;
    }


    //Oppgave 8
    //Metoden boolean hasNext()
    // og konstruktøren public DobbeltLenketListeIterator()
    // i klassen DobbeltLenketListeIterator er ferdigkodet og skal ikke endres.
    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }


        @Override
        public boolean hasNext() {
            return denne != null;  // denne koden skal ikke endres!
        }

        ////////


        //Oppgave 8-a
        //Den skal først sjekke om iteratorendringer er lik endringer.
        // Hvis ikke, kastes en ConcurrentModificationException.
        // Så en NoSuchElementException hvis det ikke er flere igjen i listen (dvs. hvis hasNext() ikke er sann/true).
        // Deretter settes fjernOK til sann/true, verdien til denne returneres og denne flyttes til den neste node.
        @Override
        public T next() {
            if (!hasNext())throw new NoSuchElementException("det ikke er flere igjen i listen");
            T t=denne.verdi;
            denne=denne.neste;
            fjernOK=true;
            return t;


        }


        //Oppgave 8-b
        // Den skal returnere en instans av iteratorklassen.
        public Iterator<T> iterator() throws NullPointerException {
            return new DobbeltLenketListeIterator();

        }


        //Oppgave 8-c
        // Den skal sette pekeren denne til den noden som hører til den oppgitte indeksen.
        // Resten skal være som i den konstruktøren som er ferdigkodet.
        private DobbeltLenketListeIterator(int indeks) {
            denne=finnNode(indeks);
            fjernOK=false;
            iteratorendringer=endringer;

        }

        //Oppgave 8-d
        //Det må først sjekkes at indeksen er lovlig.
        // Bruk metoden indeksKontroll().
        // Deretter skal den ved hjelp av konstruktøren i punkt c) returnere en instans av iteratorklassen.
        Iterator<T> iterator(int indeks) {
            indeksKontroll(indeks,false);
            return new DobbeltLenketListeIterator(indeks);
        }

        //Oppagve 9
        //Hvis det ikke er tillatt å kalle denne metoden, skal det kastes en IllegalStateException.
        // Hvis endringer og iteratorendringer er forskjellige, kastes en ConcurrentModificationException.
        // Hvis disse hindrene passeres, settes fjernOK til usann/false.
        // Så skal noden rett til venstre for p fjernes.
        // Den finner vi lett siden det går en peker dit. Her må en passe på alle tilfellene
        //
        //1. Hvis den som skal fjernes er eneste verdi (antall == 1), så nulles hode og hale.
        // 2. Hvis den siste skal fjernes (denne == null), så må hale oppdateres.
        // 3. Hvis den første skal fjernes (denne.forrige == hode), så må hode oppdateres.
        // 4. Hvis en node inne i listen skal fjernes (noden denne.forrige), så må pekerne i nodene på hver side oppdateres.
        //Til slutt reduseres antall og både endringer og iteratorendringer økes.
        @Override
        public void remove() {
            if (fjernOK == false) { //hvis det ikke er mulig å kalle på denne metoden så sendes en Exception
                throw new IllegalStateException("Ikke lov å kalle på denne metoden");
            }
            if (endringer != iteratorendringer) {
                throw new UnsupportedOperationException("endringer er forskjellig fra interatorendringer");
            }
            fjernOK = false;

            if (antall == 1) {
                hale = null;
                hode = null;
            }
            if (denne == null) {//denne er hode
                Node<T> p = denne.forrige;
                Node<T> q = denne.forrige.forrige;
                hale = null;
                p.verdi = null;
                p = null;
                hale = q;
            } else if (denne.forrige == hode) {
                Node<T> p = hode;
                hode = denne;
                hode.forrige = null;
            } else {

            }
            antall--;
            endringer++;
            iteratorendringer++;

        }


        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            String[] navn = {"Lars", "Anders", "Bodil", "Kari", "Per", "Berit"};
            Liste<String> liste = new DobbeltLenketListe<>(navn);
            liste.forEach(s -> System.out.print(s + " "));
            System.out.println();
            for (String s : liste) System.out.print(s + " ");

            // Utskrift:    // Lars Anders Bodil Kari Per Berit   // Lars Anders Bodil Kari Per Berit

        }
    }





    //Oppgave 10
    //Den skal sortere liste ved hjelp av komparatoren c.
    //Her vil det selvfølgelig være fristende å kopiere alle verdiene over i en tabell
    // sortere tabellen og så kopiere dem tilbake i listen.
    public static  <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        for (int n = liste.antall(); n > 0; n--)
        {
            Iterator<T> iterator = liste.iterator();
            int m = 0;
            T t = iterator.next();
            int i=0;
             while (i<n) {
             }
        }
    }


}




















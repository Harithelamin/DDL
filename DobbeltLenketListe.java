package no.oslomet.cs.algdat;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created by Harith on 9/30/2018.
 */
//double linked list
//Klassen DobbeltLenketListe er generisk
//https://github.com/Harithelamin/DDL.git
public class DobbeltLenketListe <T> implements Liste<T>{

    //Klassen inneholder instansvariablene antall og endringer
    int antall;   // Variabelen antall skal økes med 1 for hver innlegging og reduseres med 1 for hver fjerning
    int endringer;  //Variabelen endringer skal økes med 1 for hver endring i listen (innlegging, oppdatering, fjerning, nullstill).

    //
    private Node head;
    private Node tail;

    public DobbeltLenketListe() {
        head=tail=null;
        antall=0;
        endringer=0;
    }

    //define a node
    //En node har både en forrige-peker og en neste-peker
    // i den siste noden og forrige-peker i den første noden er begge null.
    public class Node<T>{
        T element;
        Node forrigePeker;
        Node nestePeker;

        public Node(T element, Node forrigePeker, Node nestePeker) {
            this.element=element;
            this.forrigePeker=forrigePeker;
            this.nestePeker=nestePeker;
        }
        public Node(T element){
            this(element, null, null);
        }
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
        return antall==0;
    }

    //Lag så konstruktøren
    // Den skal lage en dobbeltlenket liste med verdiene fra tabellen a
    public DobbeltLenketListe(T[] a){
        // Hvis a er null, skal det kastes en NullPointerException med teksten "Tabellen a er null!"
        // (bruk f.eks. en requireNonNull-metode fra klassen Objects).
        Objects.requireNonNull(a,"Tabellen a er null!");

        // Hvis alle verdiene i a er null, får vi en tom liste.
        for (T element:a)
        {
            if (element !=null)
            {
                //Her må du passe på at hode peker til den første i listen og hale til den siste
                head=tail=new Node(element,head, tail);
                antall++;
            }
            //forrige og hale.neste være null
            head=tail= new Node<> (null);
        }

    }


    //Oppgave 2- a

    //Den skal returnere en tegnstreng med listens verdier.
    // Hvis listen f.eks. inneholder tallene 1, 2 og 3,
    // skal metoden returnere strengen "[1, 2, 3]" og kun "[]" hvis listen er tom.
    // Du skal bruke en StringBuilder
    @Override
    public String toString() {
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder("," + "," + " [ " + "] ");
        for (Node node = head; node!=null; node=node.nestePeker)
        {
            stringBuilder.append(node.element.toString());
        }

        return stringBuilder.toString();
    }

    // Den skal returnere en tegnstreng på samme form som den toString() gir,
    // men verdiene skal komme i omvendt rekkefølge
    // // Utskrift: [] [A] [A, B] [] [A] [B, A]
    String omvendtString(){
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder("," + "," + " [ " + "] ");
        for (Node node = head; node!=null; node=node.forrigePeker)
        {
            stringBuilder.append(node.element.toString());
        }

        return stringBuilder.toString();
    }

    //Oppgave 2- b
    // Null-verdier er ikke tillatt
    // Start derfor med en sjekk (bruk en requireNonNull-metode fra klassen Objects).
    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi,"Null-verdier er ikke tillatt" );
        //Innleggingsmetoden skal legge en ny node med oppgitt verdi
        //Her må du skille mellom to tilfeller: 1) at listen på forhånd er tom og 2) at den ikke er tom.
        // I en tom liste skal både hode og hale være null (og antall lik 0)

        //(både forrige-peker og neste-peker i noden skal da være null)
        Node node= new Node(verdi,head,null);
        //Husk at antallet må økes etter en innlegging
        // Det samme med variabelen endringer
        antall++;
        endringer++;
        return true;
    }

    //

    //Opgave 3-a
    //Lag den private hjelpemetoden
    // Den skal returnere noden med den gitte indeksen/posisjonen. Hvis indeks er mindre enn antall/2,
    // så skal letingen etter noden starte fra hode og gå mot høyre ved hjelp av neste-pekere.
    // Hvis ikke, skal letingen starte fra halen og gå mot venstre ved hjelp av forrige-pekere. Lag
    Node<T> finnNode(int indeks){
            return finnNode(indeks);
    }
    
    //oppgave 5
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
        q.neste.forrige = q;

        }

        antall ++;
    }

    @Override
    //Oppgave 4
    public boolean inneholder(T verdi){
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
    
    @Override
    public T hent(int indeks) {
        return null;
    }
    
    //oppgave4
    @Override
    public int indeksTil(T verdi){
        //Her sjekker jeg om verdien ligger i første indeks
        if( verdi == hode.verdi)
            return 0;
        //Her går vi gjennom hele liste, og sjekker om verdien til hver enkel node er lik neste verdi
        else{
            for (int i = 1; i <antall ; i++) {
                Node<T> p = hode.neste;
                if (p.verdi == verdi) {
                    return i;
                }
                else{
                    p = p.neste;
                }

            }
        }
        return -1;
    }


    @Override
    public T oppdater(int indeks, T verdi) {
        return null;
    }

    @Override
    public boolean fjern(T verdi) {
        return false;
    }

    @Override
    public T fjern(int indeks) {
        return null;
    }



    @Override
    public void nullstill() {

    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    public static <T> void sorter(DobbeltLenketListe<String> liste, Comparator<T> tComparator) {
    }
}

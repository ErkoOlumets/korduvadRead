/****************************************
 * Programmeerimisharjutused. LTAT.03.007
 * 2020/2021 kevadsemester
 *
 * Kodutöö. Ülesanne nr 3b
 *
 * Teema: Korduvad read n*n maatriksis
 *
 * Autor: Erko Olumets
 ***************************************/

import java.util.Arrays;
import java.util.Collections;

public class Mall3b {

    public static void main(String[] args) {
        // Näidis
        System.out.println("Kodutöö nr 3b.                          Programmi väljund");
        System.out.println("=========================================================:");
        //Siia lisada testimine
        int n = 10;
        for (int i = 0; i < 3; i++) {
            System.out.println("\nn="+n);
            int[][] mass = juhuslik_2d_massiiv(n, 1, 5);
            System.out.println("Täisarvude massiiv: ");
            for (int[] elem: mass){
                System.out.println(Arrays.toString(elem));
            }//näidismassiivi genereerimine
            System.out.println("\nIgas reas korduvad elemendid on:");
            System.out.println(Arrays.toString(korduvadRead(mass)));
        }

        System.out.println("\n=========================================================.");
        System.out.println("Erko Olumets                      " + new java.sql.Timestamp(System.currentTimeMillis()));
    }

    //ABIMEETOD - Sobilik kasutada testimiseks
    public static int[][] juhuslik_2d_massiiv(int n, int a, int b){
        //Antud: n - elementide arv, poollõik [a,b)
        //Tagastab: n x n -elemendilise juhuslike täisarvudega järjendi, elemendid läigult [a,b)
        int[][] x=new int[n][n];
        for(int i=0;i<n;i++)
            for (int j = 0; j < x.length; j++) {
                x[i][j]=juhuslik(a,b);
            }
        return x;
    }

    //ABIMEETOD
    public static int juhuslik(int a, int b){
        //Antud: poollõik [a,b)
        //Tagastab: juhusliku täisarvu sellelt lõigult
        return (int)(Math.round(Math.random()*(b-a)+a));
    }


    /**
     * Antud on n*n täisarvude massiiv, elemendid int-tüüpi.
     * Ülesandeks on leida arvud, mis esinevad igas reas.
     * Tulemusmassiiv peab olema mittekahanevalt sorteeritud ja selles leidub mingit arvu täpselt k
     * korda, kui algse massiivi igas reas on seda arvu vähemalt k korda.
     */
    /**
     * Meetod korduvadRead
     * Antud parameetriks on kahekordne n*n massiiv ehk n-järku ruutmaatriks
     * Tagastatakse täisarvude massiiv, kus igas reas leiduvad arvud on mittekahanevas järjekorras
     *
     * Meetod kasutab kahte abimeetodit "leidub" ja "hulk".
     *
     *
     */
    public static int[] korduvadRead(int[][] a){

        int pikkus = a.length;
        int[] korduvMassiiv = a[0];
        Arrays.sort(korduvMassiiv);
        int[][] hulk = hulk(korduvMassiiv);

        //Kasutan meetodit hulk, et luua massiiv, mille 1-indeksil on nö hulk maatriksi a esimesest reast ja 0-indeksil korduste arv,
        //palju vastavaid elemente esimeses reas on. Hakkan for-tsüklitega võrdlema iga maatriksi a rida ning muutma maatriksi hulk esimeses reas
        //korduvate elementide arvu. Kui läbivaadatavas reas on sama palju elemente või rohkem elemente siis jääb see arv samaks.
        //Kui on vähem elemente, siis muudetakse maatriksi hulk esimeses reas vastava elemendi korduste arvu vastavalt.


        int loendur = 0;

        for (int i = 1; i < pikkus; i++) {
            for (int j = 0; j < hulk[1].length; j++) {
                loendur = 0;
                if (hulk[0][j]>0&&leidub(hulk[1][j], a[i])){
                    for (int elem: a[i]){
                        if (hulk[1][j] == elem) loendur++;
                    }
                    if (loendur < hulk[0][j]) hulk[0][j] = loendur;
                }
                else {
                    hulk[0][j] = 0;
                }
            }
        }
        int summa = 0;
        int indeks = 0;
        for (int elem: hulk[0]) summa += elem;

        if (summa == 0) return new int[0];//tagastamiseks vaikeväärtus
        else {
            int[] tagastus = new int[summa];
            for (int i = 0; i < hulk[0].length ; i++) {
                for (int j = hulk[0][i]; j > 0; j--) {
                    tagastus[indeks++] = hulk[1][i];
                }
            }
            return tagastus;
        }
    }

    /**
     * Abimeetod leidub, mida kasutame meetodis korduvadRead
     *
     * Esimene parameeter: a on täisarv ehk element, mille olemasolu reas otsime.
     * Teine parameeter: b on täisarvumassiiv ehk maatriksi rida kust otsime täisarvu a.
     *
     * Tagastatakse tõeväärtus ehk kas a leidub reas b või ei.
     * Sarnane pythonis funktsiooniga "in"
     */
    public static boolean leidub(int a, int[] b){
        for (int elem: b){
            if (a==elem) return true;
        }
        return false;
    }


    /**
     * Abimeetod hulk, mida kasutame meetodis korduvadRead
     *
     * Parameeter: a on täisarvumassiiv, esialgse maatriksi esimene rida
     *
     * Meetodi eesmärgiks on leida unikaalsed arvud massiivis a ning nende korduste arv.
     *
     * Massiivi hulk salvestatakse teisele reale massiivi a nö "hulk" ehk kõik arvud ühekordselt.
     * Esimesele reale vastavatele indeksitele võrreldes teise reaga salvestatakse arvude kordused massiivis a.
     *
     * Tagastatakse massiiv hulk.
     */
    public static int[][] hulk(int[] a){
        int loendur = 1;

        for (int i = 1; i < a.length; i++) {
            if (a[i] != a[i-1]) loendur++;
        }
        int[][] hulk = new int[2][loendur];

        int arv = 0;
        hulk[0][0] = 1;
        hulk[1][0] = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] != a[i-1]){
                arv++;
                hulk[0][arv]++;
                hulk[1][arv]=a[i];
            }
            else hulk[0][arv]++;
        }

        return hulk;
    }



}

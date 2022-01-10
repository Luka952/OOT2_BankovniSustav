package model;

public class TransakcijaModel {

    private String Sifra_transakcije;
    private String Datum_transakcije;
    private String Vrsta_transakcije;
    private Float Kolicina_transakcije;
    private String Sifra_racuna;
    private String Broj_racuna_uplate;

    public void setSifraTransakcije(String transakcija) {
        this.Sifra_transakcije = transakcija;
    }

    public void setDatumTransakcije(String datum) {
        this.Datum_transakcije = datum;
    }

    public void setVrstaTransakcije(String vrsta) {
        this.Vrsta_transakcije = vrsta;
    }

    public void setKolicinaTransakcije(Float kolicina) {
        this.Kolicina_transakcije = kolicina;
    }

    public void setSifraRacuna(String racun) {
        this.Sifra_racuna = racun;
    }
    
    public void setBrojRacuna(String broj) {
        this.Sifra_racuna = broj;
    }

    public String getSifraTransakcije() {
        return this.Sifra_transakcije;
    }

    public String getDatumTransakcije() {
        return this.Datum_transakcije;
    }

    public String getVrstaTransakcije() {
        return this.Vrsta_transakcije;
    }

    public Float getKolicinaTransakcije() {
        return this.Kolicina_transakcije;
    }

    public String getSifraRacuna() {
        return this.Sifra_racuna;
    }
    
    public String getBrojRacuna() {
        return this.Broj_racuna_uplate;
    }

}

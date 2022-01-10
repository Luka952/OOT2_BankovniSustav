package model;

public class BankovniRacunModel {

    private String Broj_racuna;
    private Float Kolicina_novca;
    private String OIB_korisnika;


    public void setBrojRacuna(String broj) {
        this.Broj_racuna = broj;
    }

    public void setKolicinaNovca(Float kolicina) {
        this.Kolicina_novca = kolicina;
    }

    public void setOIBKorisnika(String oib) {
        this.OIB_korisnika = oib;
    }


    public String getBrojRacuna() {
        return this.Broj_racuna;
    }

    public Float getKolicinaNovca() {
        return this.Kolicina_novca;
    }

    public String getOIBKorisnika() {
        return this.OIB_korisnika;
    }

}

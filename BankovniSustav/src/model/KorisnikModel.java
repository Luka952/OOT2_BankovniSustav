package model;

public class KorisnikModel {

    private String Ime;
    private String Prezime;
    private String OIB_korisnika;

    public void setIme(String ime) {
        this.Ime = ime;
    }

    public void setPrezime(String prezime) {
        this.Prezime = prezime;
    }

    public void setOIB(String OIB) {
        this.OIB_korisnika = OIB;
    }

    public String getIme() {
        return this.Ime;
    }

    public String getPrezime() {
        return this.Prezime;
    }

    public String getOIB() {
        return this.OIB_korisnika;
    }

}

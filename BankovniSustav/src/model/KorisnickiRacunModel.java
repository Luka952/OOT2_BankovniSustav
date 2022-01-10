package model;

public class KorisnickiRacunModel {

    private String Korisnicko_ime;
    private String Lozinka;
    private String OIB_korisnika;

    public void setIme(String ime) {
        this.Korisnicko_ime = ime;
    }

    public void setLozinka(String lozinka) {
        this.Lozinka = lozinka;
    }

    public void setOIB(String oib) {
        this.OIB_korisnika = oib;
    }

    public String getIme() {
        return this.Korisnicko_ime;
    }

    public String getLozinka() {
        return this.Lozinka;
    }

    public String getOIB() {
        return this.OIB_korisnika;
    }
   
}

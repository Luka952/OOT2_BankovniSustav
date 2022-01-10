package controller;

import view.KorisnickiRacunView;
import model.KorisnickiRacunModel;
import model.KorisnikModel;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.BankovniRacunModel;
import model.TransakcijaModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lukab
 */
public class Controller {

    private KorisnikModel k_model;
    private KorisnickiRacunModel kr_model;
    private BankovniRacunModel br_model;
    private TransakcijaModel t_model;
    private KorisnickiRacunView kr_view;

    public Controller(KorisnikModel k_model, KorisnickiRacunModel kr_model, BankovniRacunModel br_model, TransakcijaModel t_model, KorisnickiRacunView kr_view) {
        this.k_model = k_model;
        this.kr_model = kr_model;
        this.br_model = br_model;
        this.t_model = t_model;
        this.kr_view = kr_view;
    }

    public boolean login(String korisnickoIme, String lozinka) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("");
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM KorisnickiRacun WHERE Korisnicko_ime='" + korisnickoIme + "' AND Lozinka='" + lozinka + "'";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                this.kr_model.setIme(rs.getString(1));
                this.kr_model.setLozinka(rs.getString(2));
                this.kr_model.setOIB(rs.getString(3));

                con.close();
                return true;

            } else {
                con.close();
                return false;

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(kr_view, "Greška!");
        }

        return false;
    }

    public void prikazPodataka() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("");
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM Korisnik WHERE OIB_korisnika ='" + this.kr_model.getOIB() + "'";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                this.k_model.setIme(rs.getString(2));
                this.k_model.setPrezime(rs.getString(3));
                this.k_model.setOIB(rs.getString(1));
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(kr_view, "Greška!");
        }
    }

    public void prikazBankovnogRacuna() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("");
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM BankovniRacun WHERE OIB_korisnika ='" + this.k_model.getOIB() + "'";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                this.br_model.setBrojRacuna(rs.getString(1));
                this.br_model.setKolicinaNovca(rs.getFloat(2));
                this.br_model.setOIBKorisnika(rs.getString(3));
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(kr_view, "Greška!");
        }
    }

    public void transakcija() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("");
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM Transakcija WHERE Broj_racuna ='" + this.br_model.getBrojRacuna() + "'";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                this.t_model.setSifraTransakcije(rs.getString(1));
                this.t_model.setDatumTransakcije(rs.getString(2));
                this.t_model.setVrstaTransakcije(rs.getString(3));
                this.t_model.setKolicinaTransakcije(rs.getFloat(4));
                this.t_model.setSifraRacuna(rs.getString(5));
                this.t_model.setBrojRacuna(rs.getString(6));
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(kr_view, "Greška!");
        }
    }

    public String zadnjaTransakcija() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("");
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM Transakcija WHERE Datum_transakcije=(SELECT MAX(Datum_transakcije) FROM Transakcija WHERE Broj_racuna ='" + this.br_model.getBrojRacuna() + "')";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getDate(2).toString();
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(kr_view, "Greška!");
        }

        return "/";
    }

    public boolean provjeraRacuna(String brojRacuna) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("");
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM BankovniRacun WHERE Broj_racuna ='" + brojRacuna + "'";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                con.close();
                return true;
            }

            con.close();
            return false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(kr_view, "Greška!");
        }
        return false;
    }

    public boolean uplata(String brojRacuna, String kolicina) {

        if (brojRacuna.equals("") || kolicina.equals("")) {
            return false;
        } else if (Float.compare(this.br_model.getKolicinaNovca(), Float.valueOf(kolicina)) < 0) {
            return false;
        } else if (!(this.provjeraRacuna(brojRacuna))) {
            return false;
        } else if (brojRacuna.equals(this.br_model.getBrojRacuna())) {
            Float dodaj = 0f;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("");
                Statement stmt = con.createStatement();

                String sql = "SELECT * FROM BankovniRacun WHERE Broj_racuna= '" + brojRacuna + "'";

                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    Float kol_baza = rs.getFloat(2);
                    dodaj = kol_baza + Float.valueOf(kolicina);
                }

                con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(kr_view, "Greška!");
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("");
                Statement stmt = con.createStatement();

                String sql = "UPDATE BankovniRacun SET Kolicina_novca=" + dodaj + " WHERE Broj_racuna='" + brojRacuna + "'";

                stmt.executeUpdate(sql);
                this.br_model.setKolicinaNovca(dodaj);

                con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(kr_view, "Greška!");
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("");

                String sql = "INSERT INTO Transakcija(Datum_transakcije, Vrsta_transakcije, Kolicina_transakcije, Broj_racuna, Broj_racuna_uplate) VALUES(?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(sql);

                java.util.Date javaDate = new java.util.Date();
                java.sql.Date mySQLDate = new java.sql.Date(javaDate.getTime());

                pstmt.setDate(1, mySQLDate);
                pstmt.setString(2, "Uplata");
                pstmt.setFloat(3, Float.valueOf(kolicina));
                pstmt.setString(4, this.br_model.getBrojRacuna());
                pstmt.setString(5, brojRacuna);

                pstmt.execute();

                con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(kr_view, e);
            }
        } else {

            Float oduzmi = 0f;
            oduzmi = this.br_model.getKolicinaNovca() - Float.valueOf(kolicina);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("");
                Statement stmt = con.createStatement();

                String sql = "UPDATE BankovniRacun SET Kolicina_novca=" + oduzmi + " WHERE Broj_racuna='" + this.br_model.getBrojRacuna() + "'";

                stmt.executeUpdate(sql);
                this.br_model.setKolicinaNovca(oduzmi);

                con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(kr_view, "Greška!");
            }

            Float dodaj = 0f;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("");
                Statement stmt = con.createStatement();

                String sql = "SELECT * FROM BankovniRacun WHERE Broj_racuna= '" + brojRacuna + "'";

                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    Float kol_baza = rs.getFloat(2);
                    dodaj = kol_baza + Float.valueOf(kolicina);
                }

                con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(kr_view, "Greška!");
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("");
                Statement stmt = con.createStatement();

                String sql = "UPDATE BankovniRacun SET Kolicina_novca=" + dodaj + " WHERE Broj_racuna='" + brojRacuna + "'";

                stmt.executeUpdate(sql);

                con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(kr_view, "Greška!");
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("");

                String sql = "INSERT INTO Transakcija(Datum_transakcije, Vrsta_transakcije, Kolicina_transakcije, Broj_racuna, Broj_racuna_uplate) VALUES(?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(sql);

                java.util.Date javaDate = new java.util.Date();
                java.sql.Date mySQLDate = new java.sql.Date(javaDate.getTime());

                pstmt.setDate(1, mySQLDate);
                pstmt.setString(2, "Uplata");
                pstmt.setFloat(3, Float.valueOf(kolicina));
                pstmt.setString(4, this.br_model.getBrojRacuna());
                pstmt.setString(5, brojRacuna);

                pstmt.execute();

                con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(kr_view, e);
            }
        }
        return true;
    }

    public boolean isplata(String kolicina) {
        if (kolicina.equals("")) {
            return false;
        } else if (Float.compare(this.br_model.getKolicinaNovca(), Float.valueOf(kolicina)) < 0) {
            return false;
        } else {

            Float oduzmi = 0f;
            oduzmi = this.br_model.getKolicinaNovca() - Float.valueOf(kolicina);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("");
                Statement stmt = con.createStatement();

                String sql = "UPDATE BankovniRacun SET Kolicina_novca=" + oduzmi + " WHERE Broj_racuna='" + this.br_model.getBrojRacuna() + "'";

                stmt.executeUpdate(sql);
                this.br_model.setKolicinaNovca(oduzmi);

                con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(kr_view, "Greška!");
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("");

                String sql = "INSERT INTO Transakcija(Datum_transakcije, Vrsta_transakcije, Kolicina_transakcije, Broj_racuna, Broj_racuna_uplate) VALUES(?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(sql);

                java.util.Date javaDate = new java.util.Date();
                java.sql.Date mySQLDate = new java.sql.Date(javaDate.getTime());

                pstmt.setDate(1, mySQLDate);
                pstmt.setString(2, "Isplata");
                pstmt.setFloat(3, Float.valueOf(kolicina));
                pstmt.setString(4, this.br_model.getBrojRacuna());
                pstmt.setString(5, "/");

                pstmt.execute();

                con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(kr_view, "Greška!");
            }

            return true;
        }
    }

    public void prikazTransakcija(JTable table) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("");
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM Transakcija WHERE Broj_racuna='" + this.br_model.getBrojRacuna() + "'";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String datum = rs.getDate(2).toString();
                String vrsta = rs.getString(3);
                String kolicina = rs.getString(4);
                String racun = rs.getString(6);

                String tbData[] = {datum, vrsta, kolicina, racun};
                DefaultTableModel tbModel = (DefaultTableModel) table.getModel();

                tbModel.addRow(tbData);
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(table, "Greška!");
        }
    }

    public void prikazUplate(JTable table) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("");
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM Transakcija WHERE Broj_racuna='" + this.br_model.getBrojRacuna() + "' AND Vrsta_transakcije ='Uplata'";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String datum = rs.getDate(2).toString();
                String vrsta = rs.getString(3);
                String kolicina = rs.getString(4);
                String racun = rs.getString(6);

                String tbData[] = {datum, vrsta, kolicina, racun};
                DefaultTableModel tbModel = (DefaultTableModel) table.getModel();

                tbModel.addRow(tbData);
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(table, "Greška!");
        }
    }

    public void prikazIsplate(JTable table) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("");
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM Transakcija WHERE Broj_racuna='" + this.br_model.getBrojRacuna() + "' AND Vrsta_transakcije ='Isplata'";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String datum = rs.getDate(2).toString();
                String vrsta = rs.getString(3);
                String kolicina = rs.getString(4);
                String racun = rs.getString(6);

                String tbData[] = {datum, vrsta, kolicina, racun};
                DefaultTableModel tbModel = (DefaultTableModel) table.getModel();

                tbModel.addRow(tbData);
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(table, "Greška!");
        }
    }

}

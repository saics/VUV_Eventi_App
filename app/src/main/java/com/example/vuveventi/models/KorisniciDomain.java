package com.example.vuveventi.models;

public class KorisniciDomain {

    private int id;
    private String ime;
    private String prezime;
    private String email;
    private String lozinka;
    private String UlogaID;
    private String DatumKreiranja;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getUlogaID() {
        return UlogaID;
    }

    public void setUlogaID(String ulogaID) {
        UlogaID = ulogaID;
    }

    public String getDatumKreiranja() {
        return DatumKreiranja;
    }

    public void setDatumKreiranja(String datumKreiranja) {
        DatumKreiranja = datumKreiranja;
    }
}

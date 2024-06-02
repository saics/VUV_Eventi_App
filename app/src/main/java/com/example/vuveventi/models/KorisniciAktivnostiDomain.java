package com.example.vuveventi.models;

import java.util.List;

public class KorisniciAktivnostiDomain {
    private int id;
    private int korisnikId;
    private int eventId;
    private List<Integer> aktivnostiIds;
    private byte[] qrKod;
    private String korisnikName;
    private String korisnikEmail;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public List<Integer> getAktivnostiIds() {
        return aktivnostiIds;
    }

    public void setAktivnostiIds(List<Integer> aktivnostiIds) {
        this.aktivnostiIds = aktivnostiIds;
    }

    public byte[] getQrKod() {
        return qrKod;
    }

    public void setQrKod(byte[] qrKod) {
        this.qrKod = qrKod;
    }

    public String getKorisnikName() {
        return korisnikName;
    }

    public void setKorisnikName(String korisnikName) {
        this.korisnikName = korisnikName;
    }

    public String getKorisnikEmail() {
        return korisnikEmail;
    }

    public void setKorisnikEmail(String korisnikEmail) {
        this.korisnikEmail = korisnikEmail;
    }
}

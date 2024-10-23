package beans;

public class Pracownik {
    private int id;
    private String nazwisko;
    private float pensja;
    private String firma;

    public int getId() {
        return id;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public float getPensja() {
        return pensja;
    }

    public String getFirma() {
        return firma;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setPensja(float pensja) {
        this.pensja = pensja;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }
    
}
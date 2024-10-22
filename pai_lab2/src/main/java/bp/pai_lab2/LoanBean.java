package bp.pai_lab2;
import java.io.Serializable;

public class LoanBean implements Serializable {
    private double kwota;
    private double oprocentowanieRoczne;
    private int liczbaRat;

    public LoanBean() {}

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getOprocentowanieRoczne() {
        return oprocentowanieRoczne;
    }

    public void setOprocentowanieRoczne(double oprocentowanieRoczne) {
        this.oprocentowanieRoczne = oprocentowanieRoczne;
    }

    public int getLiczbaRat() {
        return liczbaRat;
    }

    public void setLiczbaRat(int liczbaRat) {
        this.liczbaRat = liczbaRat;
    }

    public double getRata() {
        double p = (oprocentowanieRoczne / 100) / 12;
        return (kwota * p) / (1 - Math.pow(1 + p, -liczbaRat));
    }
}


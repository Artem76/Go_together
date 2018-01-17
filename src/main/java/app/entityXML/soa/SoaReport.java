package app.entityXML.soa;

import app.entity.Soa;

import java.util.List;

/**
 * Created by Олег on 05.11.2017.
 */
public class SoaReport {
    private Double startBalance;

    private Double endBalance;

    private List<Soa> list;

    public SoaReport() {

    }

    public SoaReport(Double startBalance, Double endBalance, List<Soa> list) {
        this.startBalance = startBalance;
        this.endBalance = endBalance;
        this.list = list;
    }

    public Double getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(Double startBalance) {
        this.startBalance = startBalance;
    }

    public Double getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(Double endBalance) {
        this.endBalance = endBalance;
    }

    public List<Soa> getList() {
        return list;
    }

    public void setList(List<Soa> list) {
        this.list = list;
    }
}

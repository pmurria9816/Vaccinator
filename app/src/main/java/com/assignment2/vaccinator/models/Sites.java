package com.assignment2.vaccinator.models;

public class Sites {

    private int total;

    private int govt;

    private int pvt;

    public Sites() {
    }

    public Sites(int total, int govt, int pvt) {
        this.total = total;
        this.govt = govt;
        this.pvt = pvt;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getGovt() {
        return govt;
    }

    public void setGovt(int govt) {
        this.govt = govt;
    }

    public int getPvt() {
        return pvt;
    }

    public void setPvt(int pvt) {
        this.pvt = pvt;
    }

    @Override
    public String toString() {
        return "Sites{" +
                "total=" + total +
                ", governmentSites=" + govt +
                ", privateSites=" + pvt +
                '}';
    }
}

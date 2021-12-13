package com.assignment2.vaccinator.modules;

public class Sites {

    private int total;
    private int governmentSites;
    private int privateSites;

    public Sites() {
    }

    public Sites(int total, int governmentSites, int privateSites) {
        this.total = total;
        this.governmentSites = governmentSites;
        this.privateSites = privateSites;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getGovernmentSites() {
        return governmentSites;
    }

    public void setGovernmentSites(int governmentSites) {
        this.governmentSites = governmentSites;
    }

    public int getPrivateSites() {
        return privateSites;
    }

    public void setPrivateSites(int privateSites) {
        this.privateSites = privateSites;
    }

    @Override
    public String toString() {
        return "Sites{" +
                "total=" + total +
                ", governmentSites=" + governmentSites +
                ", privateSites=" + privateSites +
                '}';
    }
}

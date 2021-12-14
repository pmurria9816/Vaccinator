package com.assignment2.vaccinator.models;

import com.google.gson.annotations.SerializedName;

public class Vaccination {

    private int total;
    private int today;
    private int covishield;
    private int covaxin;
    private int sputnik;

    @SerializedName("tot_dose_1")
    private int totalDose1;

    @SerializedName("tot_dose_2")
    private int totalDose2;

    public Vaccination() {
    }

    public Vaccination(int total, int today, int covishield, int covaxin, int sputnik, int totalDose1, int totalDose2) {
        this.total = total;
        this.today = today;
        this.covishield = covishield;
        this.covaxin = covaxin;
        this.sputnik = sputnik;
        this.totalDose1 = totalDose1;
        this.totalDose2 = totalDose2;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getCovishield() {
        return covishield;
    }

    public void setCovishield(int covishield) {
        this.covishield = covishield;
    }

    public int getCovaxin() {
        return covaxin;
    }

    public void setCovaxin(int covaxin) {
        this.covaxin = covaxin;
    }

    public int getSputnik() {
        return sputnik;
    }

    public void setSputnik(int sputnik) {
        this.sputnik = sputnik;
    }

    public int getTotalDose1() {
        return totalDose1;
    }

    public void setTotalDose1(int totalDose1) {
        this.totalDose1 = totalDose1;
    }

    public int getTotalDose2() {
        return totalDose2;
    }

    public void setTotalDose2(int totalDose2) {
        this.totalDose2 = totalDose2;
    }

    @Override
    public String toString() {
        return "Vaccination{" +
                "total=" + total +
                ", today=" + today +
                ", covishield=" + covishield +
                ", covaxin=" + covaxin +
                ", sputnik=" + sputnik +
                ", totalDose1=" + totalDose1 +
                ", totalDose2=" + totalDose2 +
                '}';
    }
}

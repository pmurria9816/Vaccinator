package com.assignment2.vaccinator.modules;

public class Registration {


    private int total;
    private int today;
    private int citizenBetween18And45;
    private int citizenAbove45;

    public Registration() {
    }

    public Registration(int total, int today, int citizenBetween18And45, int citizenAbove45) {
        this.total = total;
        this.today = today;
        this.citizenBetween18And45 = citizenBetween18And45;
        this.citizenAbove45 = citizenAbove45;
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

    public int getCitizenBetween18And45() {
        return citizenBetween18And45;
    }

    public void setCitizenBetween18And45(int citizenBetween18And45) {
        this.citizenBetween18And45 = citizenBetween18And45;
    }

    public int getCitizenAbove45() {
        return citizenAbove45;
    }

    public void setCitizenAbove45(int citizenAbove45) {
        this.citizenAbove45 = citizenAbove45;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "total=" + total +
                ", today=" + today +
                ", Citizens Between 18 And 45 years old =" + citizenBetween18And45 +
                ", Citizens Above 45 years old =" + citizenAbove45 +
                '}';
    }
}

package com.assignment2.vaccinator.models;

public class TopBlock {

    private Vaccination vaccination;

    private Registration registration;

    private Sites sites;

    public Vaccination getVaccination() {
        return vaccination;
    }

    public void setVaccination(Vaccination vaccination) {
        this.vaccination = vaccination;
    }

    public Sites getSites() {
        return sites;
    }

    public void setSites(Sites sites) {
        this.sites = sites;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    @Override
    public String toString() {
        return "TopBlock{" +
                "vaccination=" + vaccination +
                ", registration=" + registration +
                ", sites=" + sites +
                '}';
    }
}
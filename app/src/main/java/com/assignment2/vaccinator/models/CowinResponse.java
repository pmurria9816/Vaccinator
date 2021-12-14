package com.assignment2.vaccinator.models;

public class CowinResponse {

    private TopBlock topBlock;

    public TopBlock getTopBlock() {
        return topBlock;
    }

    public void setTopBlock(TopBlock topBlock) {
        this.topBlock = topBlock;
    }

    @Override
    public String toString() {
        return "CowinResponse{" +
                "topBlock=" + topBlock +
                '}';
    }
}
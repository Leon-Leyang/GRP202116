package com.grp202116.consumerserver.service.statistics;

public class Tag {

    String name;
    int number;

    public Tag(String name){
        this.name = name;
        this.number = 1;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

}

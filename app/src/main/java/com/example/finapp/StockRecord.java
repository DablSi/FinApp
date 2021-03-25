package com.example.finapp;

public class StockRecord {
    private String companyName;
    private String companyTicket;
    private String price;
    private String difference;

    public StockRecord(String companyName, String companyTicket, String price, String difference) {
        this.companyName = companyName;
        this.companyTicket = companyTicket;
        this.price = price;
        this.difference = difference;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyTicket() {
        return companyTicket;
    }

    public void setCompanyTicket(String companyTicket) {
        this.companyTicket = companyTicket;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }
}
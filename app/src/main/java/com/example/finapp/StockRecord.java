package com.example.finapp;

import yahoofinance.Stock;

import java.io.Serializable;

public class StockRecord implements Serializable{
    private String companyName;
    private String companyTicket;
    private String price;
    private String difference;
    private boolean favorite;

    public StockRecord(Stock stock, boolean favorite) {
        try {
            this.companyName = stock.getName();
            this.companyTicket = stock.getSymbol();
            this.price = stock.getQuote().getPrice().toString() + " " + stock.getCurrency();
            this.difference = stock.getQuote().getChange() + " (" + stock.getQuote().getChangeInPercent() + "%)";
            this.favorite = favorite;
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public StockRecord(StockRecord other){
        this.companyName = other.getCompanyName();
        this.companyTicket = other.getCompanyTicket();
        this.price = other.getPrice();
        this.difference = other.getDifference();
        this.favorite = other.isFavorite();
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
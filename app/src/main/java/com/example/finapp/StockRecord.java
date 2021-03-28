package com.example.finapp;

import yahoofinance.Stock;

public class StockRecord {
    private String companyName;
    private String companyTicket;
    private String price;
    private String difference;
    private boolean favorite;

    public StockRecord(Stock stock) {
        try {
            this.companyName = stock.getName();
            this.companyTicket = stock.getSymbol();
            this.price = stock.getQuote().getPrice().toString() + " " + stock.getCurrency();
            this.difference = stock.getQuote().getChange() + " (" + stock.getQuote().getChangeInPercent() + "%)";
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
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
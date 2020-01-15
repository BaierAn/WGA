package com.example.wgapp.models;

import com.example.wgapp.MainActivity;

import java.io.Serializable;
import java.util.UUID;

public class Stock implements Serializable {

        private int totalAmount;
        private int leftAmount;
        private float totalCost;
        private StockCreationTypes StockType;
        private String stockName;
        private String rommmateId;
        private String userName;
        private String ID;

        public Stock(int totalAmount, int leftAmount, float totalCost, StockCreationTypes stockType, String stockName) {
            this.totalAmount = totalAmount;
            this.leftAmount = leftAmount;
            this.totalCost = totalCost;
            StockType = stockType;
            this.stockName = stockName;
            this.rommmateId = MainActivity.getLocalUser().getId();
            this.userName =  MainActivity.getLocalUser().getDisplayName();
            this.ID = UUID.randomUUID().toString();
        }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRommmateId() {
            return rommmateId;
        }
        public int getTotalAmount() {
            return totalAmount;
        }

        public float getTotalCost() {
            return totalCost;
        }

        public StockCreationTypes getStockType() {
            return StockType;
        }

        public String getStockName() {
            return stockName;
        }

    public int getLeftAmount() {
        return leftAmount;
    }

    public void takeAmount(){
            this.leftAmount =- 1;
        }
        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
        }

        public void setTotalCost(int totalCost) {
            this.totalCost = totalCost;
        }

        public void setStockType(StockCreationTypes stockType) {
            StockType = stockType;
        }

        public void setStockName(String stockName) {
            this.stockName = stockName;
        }


    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public void setRommmateId(String rommmateId) {
        this.rommmateId = rommmateId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}

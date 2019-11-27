package com.example.wgapp.models;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class Stock implements Serializable {

        private int totalAmount;
        private float totalCost;
        private StockCreationTypes StockType;
        private String name;



    private String rommmateId;

        public Stock(int totalAmount, float totalCost, StockCreationTypes stockType, String name) {
            this.totalAmount = totalAmount;
            this.totalCost = totalCost;
            StockType = stockType;
            this.name = name;
            this.rommmateId = FirebaseAuth.getInstance().getCurrentUser().getUid();
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

        public String getName() {
            return name;
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

        public void setName(String name) {
            this.name = name;
        }



}

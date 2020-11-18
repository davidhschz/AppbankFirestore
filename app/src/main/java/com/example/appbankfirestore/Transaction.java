package com.example.appbankfirestore;

public class Transaction {
    private String amount;
    private String date;
    private String origin_account_number;
    private String target_account_number;
    private String transaction_number;

    public void Transaction() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrigin_account_number() {
        return origin_account_number;
    }

    public void setOrigin_account_number(String origin_account_number) {
        this.origin_account_number = origin_account_number;
    }

    public String getTarget_account_number() {
        return target_account_number;
    }

    public void setTarget_account_number(String target_account_number) {
        this.target_account_number = target_account_number;
    }

    public String getTransaction_number() {
        return transaction_number;
    }

    public void setTransaction_number(String transaction_number) {
        this.transaction_number = transaction_number;
    }
}

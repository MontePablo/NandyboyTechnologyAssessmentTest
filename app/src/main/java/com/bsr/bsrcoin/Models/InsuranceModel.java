package com.bsr.bsrcoin.Models;

public class InsuranceModel {
    public String insuranceId;
    public String instatus;
    public String inamt;
    public String wallet;
    public String duration;
    public String type;
    public String currency;

    public String getInsuranceId() {
        return insuranceId;
    }

    public String getInstatus() {
        return instatus;
    }

    public String getInamt() {
        return inamt;
    }

    public String getWallet() {
        return wallet;
    }

    public String getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }

    public String getCurrency() {
        return currency;
    }

    public InsuranceModel(String insuranceId,String instatus, String inamt, String wallet, String duration, String type, String currency) {
        this.instatus = instatus;
        this.inamt = inamt;
        this.wallet = wallet;
        this.duration = duration;
        this.type = type;
        this.currency = currency;
        this.insuranceId=insuranceId;
    }

}

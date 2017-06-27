package com.example.manojpun.camc;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//import org.apache.commons.lang.builder.ToStringBuilder;

public class Uids {

    @SerializedName("confidence")
    @Expose
    private Double confidence;
    @SerializedName("prediction")
    @Expose
    private String prediction;
    @SerializedName("uid")
    @Expose
    private String uid;

    /**
     * No args constructor for use in serialization
     *
     */
    public Uids() {
    }

    /**
     *
     * @param uid
     * @param prediction
     * @param confidence
     */
    public Uids(Double confidence, String prediction, String uid) {
        super();
        this.confidence = confidence;
        this.prediction = prediction;
        this.uid = uid;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return uid;
        //return ToStringBuilder.reflectionToString(this);
    }

}


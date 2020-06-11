package com.example.taskmassages.classes;

public class Massage {
    private String textMassage;
    private Long timeStamp;
    private String receiverPhone;
    private String senderName;
    private String senderPhone;
    private String photoPath;

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }


    public Massage(String textMassage, Long timeStamp, String receiverPhone, String senderName, String senderPhone, String photoPath) {
        this.textMassage = textMassage;
        this.timeStamp = timeStamp;
        this.receiverPhone = receiverPhone;
        this.senderName = senderName;
        this.senderPhone = senderPhone;
        this.photoPath = photoPath;
    }

    public Massage() {
    }

    public String getTextMassage() {
        return textMassage;
    }

    public void setTextMassage(String textMassage) {
        this.textMassage = textMassage;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}

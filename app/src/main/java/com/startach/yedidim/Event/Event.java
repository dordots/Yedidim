package com.startach.yedidim.Event;

/**
 * Created by Michael on 06/09/2017.
 */

public class Event {


    private String nameAskingForHelp;
    private String phoneAskingForHelp;
    private String addressOfEvent;
    private String placeDescription;
    private String kindOfEvent;
    private String extendedEventInformation;



    public Event() {
    }


    public String getNameAskingForHelp() {
        return nameAskingForHelp;
    }

    public void setNameAskingForHelp(String nameAskingForHelp) {
        this.nameAskingForHelp = nameAskingForHelp;
    }

    public String getPhoneAskingForHelp() {
        return phoneAskingForHelp;
    }

    public void setPhoneAskingForHelp(String phoneAskingForHelp) {
        this.phoneAskingForHelp = phoneAskingForHelp;
    }

    public String getAddressOfEvent() {
        return addressOfEvent;
    }

    public void setAddressOfEvent(String addressOfEvent) {
        this.addressOfEvent = addressOfEvent;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getKindOfEvent() {
        return kindOfEvent;
    }

    public void setKindOfEvent(String kindOfEvent) {
        this.kindOfEvent = kindOfEvent;
    }
    public String getExtendedEventInformation() {
        return extendedEventInformation;
    }

    public void setExtendedEventInformation(String extendedEventInformation) {
        this.extendedEventInformation = extendedEventInformation;
    }
}

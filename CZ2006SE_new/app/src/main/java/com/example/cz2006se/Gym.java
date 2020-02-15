package com.example.cz2006se;

import android.os.Parcel;
import android.os.Parcelable;

public class Gym implements Parcelable{
    private String address;
    private String contact;
    private String image;
    private String name;
    private String openingHour;
    private String latitude;
    private String longitude;

    public Gym() {
    }

    public Gym(String address, String contact, String image, String name, String openingHour, String latitude, String longitude) {
        this.address = address;
        this.contact = contact;
        this.image = image;
        this.name = name;
        this.openingHour = openingHour;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Gym(Parcel in) {
        address = in.readString();
        contact = in.readString();
        image = in.readString();
        name = in.readString();
        openingHour = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<Gym> CREATOR = new Creator<Gym>() {
        @Override
        public Gym createFromParcel(Parcel in) {
            return new Gym(in);
        }

        @Override
        public Gym[] newArray(int size) {
            return new Gym[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeString(contact);
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeString(openingHour);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
    }
}
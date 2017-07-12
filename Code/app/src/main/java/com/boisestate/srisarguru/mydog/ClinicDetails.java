package com.boisestate.srisarguru.mydog;

/**
 * Created by Srisarguru on 4/14/2015.
 */
public class ClinicDetails {
    String name, phone,address;
    String latitude,longitude;

    public ClinicDetails(String name,String phone,String latitude,String longitude,String address){
        this.name=name;
        this.phone =phone;
        this.latitude=latitude;
        this.longitude=longitude;
        this.address=address;
    }
}

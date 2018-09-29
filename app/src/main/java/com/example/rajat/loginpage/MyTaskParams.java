package com.example.rajat.loginpage;

import org.altbeacon.beacon.Beacon;

import java.util.Collection;

public class MyTaskParams {

    String cls;
    String div;
    String batch;
    Collection<Beacon> beacon;

    MyTaskParams(String cls,String div,String batch, Collection<Beacon> beacon)
    {
        this.cls = cls;
        this.div = div;
        this.batch = batch;
        this.beacon = beacon;
    }

}

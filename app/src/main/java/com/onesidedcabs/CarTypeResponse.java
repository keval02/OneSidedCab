package com.onesidedcabs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEO on 9/6/2017.
 */

public class CarTypeResponse {

    int status = 0;
    String message = "";

    List<CarType> cartype = new ArrayList<>();


    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<CarType> getCartype() {
        return cartype;
    }
}

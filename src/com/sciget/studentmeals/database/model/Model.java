package com.sciget.studentmeals.database.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import android.content.Context;

import com.sciget.studentmeals.database.Database;

public class Model extends Database {
    public Model(Context context) {
        super(context);
    }
    
    public Model(Context context, boolean open) {
        super(context, open);
    }
    
    protected Timestamp time() {
        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }
    
    protected String todayDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DATE);
    }
}

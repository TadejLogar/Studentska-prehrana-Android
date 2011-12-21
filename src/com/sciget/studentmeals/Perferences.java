package com.sciget.studentmeals;

public class Perferences {
    private static int userId;
    
    public static int getUserId() throws Exception {
        if (userId == 0) {
            throw new Exception("User id has not been set.");
        }
        return userId;
    }
    
    public static void setUserId(int userId0) {
        userId = userId0;
    }
}

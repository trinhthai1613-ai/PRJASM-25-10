package com.company.leavemgmt.util;

public final class Config {
    private static String dbUrl  = "jdbc:sqlserver://localhost:1433;databaseName=LeaveManagementDB;encrypt=false;trustServerCertificate=true;";
    private static String dbUser = "sa";
    private static String dbPass = "123456";
    private static boolean demoIgnorePassword = true;

    private Config(){}

    // Called by AppConfigListener at startup
    static void init(String url, String user, String pass, boolean ignorePw){
        if (url != null && !url.isEmpty()) dbUrl = url;
        if (user != null) dbUser = user;
        if (pass != null) dbPass = pass;
        demoIgnorePassword = ignorePw;
    }

    public static String  getDbUrl(){ return dbUrl; }
    public static String  getDbUser(){ return dbUser; }
    public static String  getDbPassword(){ return dbPass; }
    public static boolean isDemoIgnorePassword(){ return demoIgnorePassword; }
}

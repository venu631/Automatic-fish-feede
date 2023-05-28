package com.example.automaticfishfeeder;

public class dataholder {
    String FEED,user_name,password,C_FEED;



    public String getFEED() {
        return FEED;
    }

    public void setFEED(String FEED) {
        this.FEED = FEED;
    }

    public String getuser_name() {
        return user_name;
    }

    public void setUser(String user_name) {
        this.user_name = user_name;
    }


    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getC_FEED() {
        return C_FEED;
    }

    public void setC_FEED(String C_FEED) {
        this.C_FEED = C_FEED;
    }


    public dataholder( String FEED, String user_name, String password, String C_FEED){

        this.FEED=FEED;
        this.user_name=user_name;
        this.password=password;
        this.C_FEED=C_FEED;
    }
}

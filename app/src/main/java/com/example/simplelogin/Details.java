package com.example.simplelogin;

public class Details {

    private String Username;
    private String Mobile;
    private String Email;
    private String Password;

    Details(String username, String mobile, String email, String password){
        this.Username=username;
        this.Mobile = mobile;
        this.Email = email;
        this.Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

     public void setUsername(String username) {
        Username = username;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

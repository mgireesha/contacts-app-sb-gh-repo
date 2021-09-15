package com.capp.springboot.object;

public class Contact {
    
        private String name;
		private String email;
		private String phone;
		private String gender;
		private String city;
		private String address;
        private String state;
		private String id;
        private String dp;  // added to for insert and retrieve image to database
                
    public Contact(){
        }
    public Contact(String name, String email, String phone, String gender, String city, String address, String state) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.city = city;
        this.address = address;
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
    
    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getDp() {
        return dp;
    }
    
    @Override
    public String toString() {
            return "A [name=" + name + ", email=" + email + ", phone=" + phone + ", gender=" + gender + ", city=" + city
                            + ", address=" + address + ", state=" + state + ", id=" + id + "]";
    }
}

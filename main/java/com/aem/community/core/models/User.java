package com.aem.community.core.models;

public class User {

    private String id;
    private String nume;
    private String mail;
    private Address address;
    private String phone;
    private String website;
    private String companyName;
    private String companyCatchPhrase;

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getMail() {
        return mail;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyCatchPhrase() {
        return companyCatchPhrase;
    }

    public static class Builder {

        private String id;
        private String nume;
        private String mail;
        private Address address;
        private String phone;
        private String website;
        private String companyName;
        private String companyCatchPhrase;

        public Builder setId(String id) {
            this.id = id;

            return this;
        }

        public Builder setNume(String nume) {
            this.nume = nume;

            return this;
        }

        public Builder setMail(String mail) {
            this.mail = mail;

            return this;
        }

        public Builder setAddress(Address address) {
            this.address = address;

            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;

            return this;
        }

        public Builder setWebsite(String website) {
            this.website = website;

            return this;
        }

        public Builder setCompanyName(String companyName) {
            this.companyName = companyName;

            return this;
        }

        public Builder setCompanyCatchPhrase(String companyCatchPhrase) {
            this.companyCatchPhrase = companyCatchPhrase;

            return this;
        }

        public User build(){
            User user = new User();
            user.id = this.id;
            user.mail = this.mail;
            user.nume = this.nume;
            user.address = this.address;
            user.phone = this.phone;
            user.website = this.website;
            user.companyName = this.companyName;
            user.companyCatchPhrase = this.companyCatchPhrase;

            return user;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", nume='" + nume + '\'' +
                    ", mail='" + mail + '\'' +
                    ", address=" + address +
                    ", phone='" + phone + '\'' +
                    ", website='" + website + '\'' +
                    ", companyName='" + companyName + '\'' +
                    ", companyCatchPhrase='" + companyCatchPhrase + '\'' +
                    '}';
        }
    }
}

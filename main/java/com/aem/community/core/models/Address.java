package com.aem.community.core.models;

public class Address {
    private String street;
    private String suite;
    private String city;
    private String lat;
    private String lng;

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public String getSuite() {
        return suite;
    }

    public String getCity() {
        return city;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public static class Builder {
        private String street;
        private String suite;
        private String city;
        private String lat;
        private String lng;

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setSuite(String suite) {
            this.suite = suite;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setLat(String lat) {
            this.lat = lat;
            return this;
        }

        public Builder setLng(String lng) {
            this.lng = lng;
            return this;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", suite='" + suite + '\'' +
                    ", city='" + city + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    '}';
        }

        public Address build(){
            Address address = new Address();
            address.street = this.street;
            address.suite = this.suite;
            address.city = this.city;
            address.lat = this.lat;
            address.lng = this.lng;

            return address;
        }
    }
}

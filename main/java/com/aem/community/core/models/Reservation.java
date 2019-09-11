package com.aem.community.core.models;

public class Reservation {
    private String name;
    private String date;
    private String hour;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }

    public static class Builder {
        private String name;
        private String date;
        private String hour;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }

        public Builder setHour(String hour) {
            this.hour = hour;
            return this;
        }

        public Reservation build(){
            Reservation reservation = new Reservation();

            reservation.name = this.name;
            reservation.date = this.date;
            reservation.hour = this.hour;

            return reservation;
        }
    }
}

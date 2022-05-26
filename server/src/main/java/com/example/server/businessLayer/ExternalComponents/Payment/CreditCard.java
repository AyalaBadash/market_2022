package com.example.server.businessLayer.ExternalComponents.Payment;

public class CreditCard implements PaymentMethod{

        private String number;
        private String month;
        private String year;
        private String cvv;
        private String holder;
        private String id;


        public CreditCard(String number, String month, String year, String cvv, String holder, String id) {
                this.number = number;
                this.month = month;
                this.year = year;
                this.cvv = cvv;
                this.holder = holder;
                this.id = id;
        }

        public String getNumber() {
                return number;
        }

        public void setNumber(String number) {
                this.number = number;
        }

        public String getMonth() {
                return month;
        }

        public void setMonth(String month) {
                this.month = month;
        }

        public String getYear() {
                return year;
        }

        public void setYear(String year) {
                this.year = year;
        }

        public String getCvv() {
                return cvv;
        }

        public void setCvv(String cvv) {
                this.cvv = cvv;
        }

        public String getHolder() {
                return holder;
        }

        public void setHolder(String holder) {
                this.holder = holder;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }
}

package com.narola.finalproject.model;

public class CardDetails {
        private boolean emi;
        private String last4;
        private String sub_type;
        private String name;
        private String id;
        private String token_iin = null;
        private String type;
        private boolean international;
        private String entity;
        private String issuer;
        private String network;


        // Getter Methods

        public boolean getEmi() {
            return emi;
        }

        public String getLast4() {
            return last4;
        }

        public String getSub_type() {
            return sub_type;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getToken_iin() {
            return token_iin;
        }

        public String getType() {
            return type;
        }

        public boolean getInternational() {
            return international;
        }

        public String getEntity() {
            return entity;
        }

        public String getIssuer() {
            return issuer;
        }

        public String getNetwork() {
            return network;
        }

        // Setter Methods

        public void setEmi(boolean emi) {
            this.emi = emi;
        }

        public void setLast4(String last4) {
            this.last4 = last4;
        }

        public void setSub_type(String sub_type) {
            this.sub_type = sub_type;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setToken_iin(String token_iin) {
            this.token_iin = token_iin;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setInternational(boolean international) {
            this.international = international;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public void setNetwork(String network) {
            this.network = network;
        }
    }

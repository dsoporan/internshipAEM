package com.aem.community.core.models;

public class Ticket {
    private String ticketName;
    private String description;
    private String status;
    private String createdBy;
    private String assignTo;
    private String attachments;

    public Ticket() {
    }

    public Ticket(String ticketName, String description, String status, String createdBy, String assignTo, String attachments) {
        this.ticketName = ticketName;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.assignTo = assignTo;
        this.attachments = attachments;
    }

    public String getTicketName() {
        return ticketName;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public String getAttachments() {
        return attachments;
    }

    public static class Builder {
        private String ticketName;
        private String description;
        private String status;
        private String createdBy;
        private String assignTo;
        private String attachments;


        public Builder() {
        }


        public Builder setTicketName(String ticketName) {
            this.ticketName = ticketName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder setAssignTo(String assignTo) {
            this.assignTo = assignTo;
            return this;
        }

        public Ticket build(){
            Ticket ticket = new Ticket();
            ticket.ticketName = this.ticketName;
            ticket.description = this.description;
            ticket.status = this.status;
            ticket.createdBy = this.createdBy;
            ticket.assignTo = this.assignTo;
            ticket.attachments = this.attachments;
            return ticket;
        }

        public Builder setAttachments(String attachments) {
            this.attachments = attachments;
            return this;
        }
    }
}

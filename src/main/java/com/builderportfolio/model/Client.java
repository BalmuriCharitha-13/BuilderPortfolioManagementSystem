package com.builderportfolio.model;

/**
 * Represents a client associated with a construction project.
 * Each client has a unique ID, name, email, and phone number.
 */
public class Client {
    /** Static counter to generate unique client IDs */
    private static long lastClientId = 0L;

    /** Unique ID for the client */
    private long clientId;

    /** Name of the client */
    private String clientName;

    /** Email address of the client */
    private String clientEmail;

    /** Phone number of the client */
    private String clientPhoneNo;


    /**
     * Creates a new Client with the given details.
     *
     * @param clientName Name of the client (cannot be null or empty)
     * @param clientEmail Email of the client (cannot be null or empty)
     * @param clientPhoneNo Phone number of the client (cannot be null or empty)
     * @throws IllegalArgumentException if any of the parameters are null or empty
     */
    public Client(String clientName, String clientEmail, String clientPhoneNo) {
        if (clientName == null || clientName.isEmpty())
            throw new IllegalArgumentException("Client name cannot be null or empty");
        if (clientEmail == null || clientEmail.isEmpty())
            throw new IllegalArgumentException("Client email cannot be null or empty");
        if (clientPhoneNo == null || clientPhoneNo.isEmpty())
            throw new IllegalArgumentException("Client phone number cannot be null or empty");

        this.clientId = ++lastClientId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientPhoneNo = clientPhoneNo;
    }

    /** @return the unique client ID */
    public long getClientId() {
        return clientId;
    }

    /** @return the client's name */
    public String getClientName() {
        return clientName;
    }


    /** @param clientName the new name to set for the client */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /** @return the client's email */
    public String getClientEmail() {
        return clientEmail;
    }

    /** @param clientEmail the new email to set for the client */
    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    /** @return the client's phone number */
    public String getClientPhoneNo() {
        return clientPhoneNo;
    }

    /** @param clientPhoneNo the new phone number to set for the client */
    public void setClientPhoneNo(String clientPhoneNo) {
        this.clientPhoneNo = clientPhoneNo;
    }

    /**
     * Returns a string representation of the client with all details.
     *
     * @return formatted string containing client ID, name, email, and phone number
     */
    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", clientPhoneNo='" + clientPhoneNo + '\'' +
                '}';
    }
}

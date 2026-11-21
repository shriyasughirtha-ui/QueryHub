package com.example.dto;

public class EmailDomainCountDTO {

    private String domain;
    private long count;

    public EmailDomainCountDTO(
            String domain,
            long count) {

        this.domain = domain;
        this.count = count;
    }

    public String getDomain() {
        return domain;
    }

    public long getCount() {
        return count;
    }
}
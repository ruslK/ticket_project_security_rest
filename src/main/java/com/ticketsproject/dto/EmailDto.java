package com.ticketsproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailDto {
    private String emailTo;
    private String emailFrom;
    private String message;
    private String token;
    private String subject;
    private String url;
}

package com.emailService.emailService.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonSerialize
public class EmailDTO implements Serializable {
    private String to;
    private String from;
    private String message;
    private String subject;
}

package com.apress.catalog.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class AuditDTO implements Serializable {

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

     
}
package com.example.infoloko.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
@Data
public class SummaryDocument {
    @Id
    private String id;
    private String tanggal;
    private String status;
    private int jumlah;
}

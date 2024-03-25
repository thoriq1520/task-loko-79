package com.example.infoloko.model;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Document(collection = "info-loko")
public class InfoLoko {
    @Id
    private UUID kodeLoko;
    private String namaLoko;
    private String dimensiLoko;
    private String status;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate tanggal;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime jam;

    @Override
    public String toString() {
        return "InfoLoko{" +
                "kodeLoko=" + kodeLoko +
                ", namaLoko='" + namaLoko + '\'' +
                ", dimensiLoko='" + dimensiLoko + '\'' +
                ", status='" + status + '\'' +
                ", tanggal=" + tanggal +
                ", jam=" + jam +
                '}';
    }
}
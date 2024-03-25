package com.example.infoloko.service;

import com.example.infoloko.model.InfoLoko;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class InfoLokomotifService {
    //Init Variable Static
    private static ApiClientService apiService;

    //Daftar Statis yang akan dibuat sebagai logging 10 detik
    private static final List<String> initNama = List.of("Ekonomi", "Bisnis", "Eksekutif", "Luxury", "Whoosh");
    private static final List<String> initDimensi = List.of("1000x2000", "2000x2000", "4000x2000", "5000x2000", "6000x2000");
    private static final List<String> initStatus = List.of("Sedang Berjalan", "Tidak Berjalan");

    //Mencatat Log
    private static final Logger LOGGER = LoggerFactory.getLogger(InfoLokomotifService.class);

    //Karena satuan milidetik jadi fixed rate 10000
    @Scheduled(fixedRate = 10000)
    public static InfoLoko createDummyData(){

        InfoLoko infoLokomotif = new InfoLoko();

        infoLokomotif.setKodeLoko(UUID.randomUUID());
        infoLokomotif.setNamaLoko(randomName());
        infoLokomotif.setDimensiLoko(randomDimensi());
        infoLokomotif.setStatus(randomStatus());
        infoLokomotif.setTanggal(LocalDate.now());
        infoLokomotif.setJam(LocalTime.now());

//        code dibawah ini untuk memanggil api post dari node js
//        apabila api belum dibuat tetap berikan comment
        ApiClientService.callAPI(infoLokomotif);

        LOGGER.info(String.format("Data => %s", infoLokomotif.toString()));
        return infoLokomotif;
    }

    private static String randomStatus() {
        Random random = new Random();

        int randomIndex = random.nextInt(initStatus.size());
        return initStatus.get(randomIndex);
    }

    private static String randomDimensi() {
        Random random = new Random();

        int randomIndex = random.nextInt(initDimensi.size());
        return initDimensi.get(randomIndex);
    }

    private static String randomName() {
        Random random = new Random();

        int randomIndex = random.nextInt(initNama.size());
        return initNama.get(randomIndex);
    }
}

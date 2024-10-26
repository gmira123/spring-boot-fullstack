package com.dani.dcl.parcauto;

import com.dani.dcl.parcauto.model.Vehicule;
import com.dani.dcl.parcauto.repository.VehiculeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class ParcautoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParcautoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(VehiculeRepository repository){
        return args -> {
            Random random = new Random();
            Vehicule v1 = Vehicule.builder()
                    .matricule("J178130").marque("Dacia Duster 4X4").type("Service").anneeCirculation(random.nextInt(2000, 2024))
                    .build();
            Vehicule v2 = Vehicule.builder()
                    .matricule("J179056").marque("HYUNDAI").type("Ambulance").anneeCirculation(random.nextInt(2000, 2024))
                    .build();
            Vehicule v3 = Vehicule.builder()
                    .matricule("J0185634").marque("FIAT DUCATO").type("Transport Scolaire").anneeCirculation(random.nextInt(2000, 2024))
                    .build();

            List<Vehicule> vehicules = List.of(v1, v2, v3);
            //repository.saveAll(vehicules);
        };
    }


}

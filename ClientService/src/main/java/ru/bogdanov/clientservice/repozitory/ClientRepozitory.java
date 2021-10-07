package ru.bogdanov.clientservice.repozitory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bogdanov.clientservice.entity.Client;


public interface ClientRepozitory extends JpaRepository<Client,Long> {
}

package ru.bogdanov.proxyserver.repozitory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bogdanov.proxyserver.entity.User;

public interface UserRepository extends JpaRepository<User,String> {
}

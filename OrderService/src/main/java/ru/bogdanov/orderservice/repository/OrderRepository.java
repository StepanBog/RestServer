package ru.bogdanov.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bogdanov.orderservice.entity.ClientOrder;
import ru.bogdanov.orderservice.entity.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<ClientOrder,Long> {
    List<ClientOrder> findAllByClient(Long client_id);
    List<ClientOrder> findAllByStatusAndClient(OrderStatus status,Long id);
}

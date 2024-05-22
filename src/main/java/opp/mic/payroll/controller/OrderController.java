package opp.mic.payroll.controller;


import opp.mic.payroll.model.Order;
import opp.mic.payroll.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/orders")
    public Page<Order> all(int page){
        return orderService.allOrders(page);
    }
}

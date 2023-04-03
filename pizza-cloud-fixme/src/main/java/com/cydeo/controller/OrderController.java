package com.cydeo.controller;

import com.cydeo.exception.PizzaNotFoundException;
import com.cydeo.model.Pizza;
import com.cydeo.model.PizzaOrder;
import com.cydeo.repository.PizzaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final PizzaRepository pizzaRepository;

    public OrderController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping("/current")
    public String orderForm(@RequestParam UUID pizzaId, Model model) {
        //creating an object of PizzaOrder
        PizzaOrder pizzaOrder = new PizzaOrder();
        //getPizza() method below should be fixed first, b/c the id might be null
        pizzaOrder.setPizza(getPizza(pizzaId));
        model.addAttribute("pizzaOrder", pizzaOrder);
        return "/orderForm";
    }

    @PostMapping("/{pizzaId}")//need to have @PathVariable
    public String processOrder(@PathVariable("pizzaId") UUID pizzaId, PizzaOrder pizzaOrder) {
        // Save the order, sets the filtered and found id as the endpoint of the ordered pizza
        pizzaOrder.setPizza(getPizza(pizzaId));
        return "redirect:/home";
    }

    private Pizza getPizza(UUID pizzaId) {
        // Get the pizza from repository based on its id, if the id isn't the same or not found
        //it should throw exception. The designed and the ordered pizza must have the same id
        return pizzaRepository.readAll().stream()
                .filter(p ->p.getId().equals(pizzaId))
                .findFirst().orElseThrow(() -> new PizzaNotFoundException("Pizza not Found!"));
    }
}

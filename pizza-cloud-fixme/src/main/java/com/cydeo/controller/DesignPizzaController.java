package com.cydeo.controller;

import com.cydeo.bootstrap.DataGenerator;
import com.cydeo.model.Pizza;
import com.cydeo.repository.PizzaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/design") //4th error, design endpoint added to display Design your pizza! view
public class DesignPizzaController {

    private PizzaRepository pizzaRepository;

    //5th error, a constructor should be added to inject it by constructor,
    // then @Component should be added on the PizzaRepository
    public DesignPizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping //6th error, to retrieve @GetMapping is used not @PostMapping
    public String showDesignForm(Model model) {
        //7th error: to display objects Pizza object must be added
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("cheeses", DataGenerator.cheeseTypeList);
        model.addAttribute("sauces", DataGenerator.sauceTypeList);
        model.addAttribute("toppings", DataGenerator.toppingTypeList);
        return "/design";
    }

    @PostMapping("/createPizza")
    public String processPizza(Pizza pizza) {
        //might be null, so the same endpoint designed pizza id must be used to order it,
        //therefore, in the OrderController, the pizzaId path and the getPizza() method must be fixed
        pizza.setId(UUID.randomUUID());
        pizzaRepository.createPizza(pizza);
        return "redirect:/orders/current?pizzaId=" + pizza.getId();
    }
}

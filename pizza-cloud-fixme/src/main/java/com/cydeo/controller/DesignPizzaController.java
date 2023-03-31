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
@RequestMapping("/design") //1st error, endpoint added
public class DesignPizzaController {

    private PizzaRepository pizzaRepository;

    //2nd error, inject by constructor, then @Component on PizzaRepository
    public DesignPizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping //3rd error, to retrieve GetMapping not PostMapping
    public String showDesignForm(Model model) {

        model.addAttribute("pizza", new Pizza());//4th error, Pizza object added
        model.addAttribute("cheeses", DataGenerator.cheeseTypeList);
        model.addAttribute("sauces", DataGenerator.sauceTypeList);
        model.addAttribute("toppings", DataGenerator.toppingTypeList);

        return "/design";

    }

    @PostMapping("/createPizza")
    public String processPizza(Pizza pizza) {

        pizza.setId(UUID.randomUUID());//might be null
        pizzaRepository.createPizza(pizza);

        return "redirect:/orders/current?pizzaId=" + pizza.getId();

    }

}

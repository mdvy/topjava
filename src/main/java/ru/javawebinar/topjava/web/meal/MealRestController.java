package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.service.MealService;

@Controller
public class MealRestController extends MealController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    public MealRestController(MealService service) {
        super(service);
    }
}
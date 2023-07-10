package org.openmrs.module.Quiz.controller;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.Quiz.api.QuizService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//https://YOUR_IP_ADDRESS/openmrs/ws/rest/v1/quiz/test/add_item
@Controller
@RequestMapping(value = "/rest/"+ RestConstants.VERSION_1+"/quiz/test")
public class MyQuizController extends BaseRestController {

    private final Log log = LogFactory.getLog(this.getClass());

    /*
       GUIZ 2
        */
    @RequestMapping(value="/add_item",  method = RequestMethod.POST)
    @ResponseBody
    public String addPersonalDetails(@RequestBody String detailPayload)
    {
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.addItemObject(detailPayload);
    }
}

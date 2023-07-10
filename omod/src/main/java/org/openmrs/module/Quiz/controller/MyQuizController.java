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
@RequestMapping(value = "/rest/"+ RestConstants.VERSION_1+"/moh/inventory/")
public class MyQuizController extends BaseRestController {

    private final Log log = LogFactory.getLog(this.getClass());

    //moh inventory functions start here
    @RequestMapping(value ="/new/device/type", method = RequestMethod.POST)
    @ResponseBody
    public String addDeviceTypeObject(@RequestBody String deviceTypeBody){
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.addDeviceTypeObject(deviceTypeBody);
    }

    @RequestMapping(value ="/new/device/type", method = RequestMethod.POST)
    @ResponseBody
    public String updateDeviceTypeObject(@RequestBody String deviceTypeBody){
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.addDeviceTypeObject(deviceTypeBody);
    }
    //moh inventory functions end here







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

    //for create attribute names
    @RequestMapping(value="/add_attribute_names",  method = RequestMethod.POST)
    @ResponseBody
    public String addAttributeNames(@RequestBody String names)
    {
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.addAttributeNames(names);
    }

    //for update attribute names
    @RequestMapping(value="/update_attribute_name",  method = RequestMethod.POST)
    @ResponseBody
    public String updateAttributeName(@RequestBody String name)
    {
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.updateAttributeName(name);
    }
}

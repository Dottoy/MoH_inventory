package org.openmrs.module.Quiz.controller;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.Quiz.api.QuizService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

//https://YOUR_IP_ADDRESS/openmrs/ws/rest/v1/quiz/test/add_item
@Controller
@RequestMapping(value = "/rest/"+ RestConstants.VERSION_1+"/moh/inventory/")
public class MyQuizController extends BaseRestController {

    private final Log log = LogFactory.getLog(this.getClass());

    //moh inventory functions start here

    @RequestMapping(value ="/verify/nida/number", method = RequestMethod.POST)
    @ResponseBody
    public String verifyNiN(@RequestBody String nidaNumber){
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.verifyUserNidNumber(nidaNumber);
    }

    @RequestMapping(value ="/new/device/type", method = RequestMethod.POST)
    @ResponseBody
    public String addDeviceTypeObject(@RequestBody String deviceTypeBody){
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.addDeviceTypeObject(deviceTypeBody);
    }

    @RequestMapping(value ="/add/device/movement", method = RequestMethod.POST)
    @ResponseBody
    public String addDeviceMovementObject(@RequestBody String deviceMovementBody){
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.addDeviceMovementObject(deviceMovementBody);
    }

//    @RequestMapping(value ="/update/device/type", method = RequestMethod.POST)
    @RequestMapping(value ="/update/device/type", method = RequestMethod.PUT)
    @ResponseBody
    public String updateDeviceTypeObject(@RequestBody String deviceTypeBody){
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.updateDeviceTypeObject(deviceTypeBody);
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

    @RequestMapping(value="/update_attribute_name",  method = RequestMethod.PUT)
    @ResponseBody
    public String updateAttributeName(@RequestBody String name)
    {
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.updateAttributeName(name);
    }

    //for get list of attribute name
    @RequestMapping(value="/get_attribute_name", method = RequestMethod.GET)
    @ResponseBody
    public String getAttributeName()
    {
        QuizService quizService = Context.getService(QuizService.class);
        List attributeName =  quizService.getAttributeName();
        String response;
        if(attributeName!=null)
        {
            response=new Gson().toJson(attributeName);
        }
        else{
            List empty = new ArrayList();
            response = new Gson().toJson(empty);
        }
        return response;
    }

    //for create moh_device_status table
    @RequestMapping(value="/add_device_status",  method = RequestMethod.POST)
    @ResponseBody
    public String addDeviceStatus(@RequestBody String status)
    {
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.addDeviceStatus(status);
    }
    //update device status
    @RequestMapping(value="/update_device_status",  method = RequestMethod.PUT)
    @ResponseBody
    public String updateDeviceStatus(@RequestBody String status)
    {
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.updateDeviceStatus(status);
    }

    //list all available status
    @RequestMapping(value="/get_device_status", method = RequestMethod.GET)
    @ResponseBody
    public String getDeviceStatus()
    {
        QuizService quizService = Context.getService(QuizService.class);
        List statusList =  quizService.getDeviceStatus();
        String response;
        if(statusList!=null)
        {
            response=new Gson().toJson(statusList);
        }
        else{
            List empty = new ArrayList();
            response = new Gson().toJson(empty);
        }
        return response;
    }
    //for moh_device_inventory_attribute_answer table
    @RequestMapping(value ="/add_device_inventory_answer",  method = RequestMethod.POST)
    @ResponseBody
    public String addDeviceInventoryAnswer(@RequestBody String answers)
    {
        QuizService quizService = Context.getService(QuizService.class);
        return quizService.addDeviceInventoryAnswer(answers);
    }
    @RequestMapping(value = "/get_device_inventory_answer" , method = RequestMethod.GET)
    @ResponseBody
    public String deviceInventoryAnswers()
    {
        QuizService quizService = Context.getService(QuizService.class);
        List answers =  quizService.getDeviceInventoryAnswers();
        String response;
        if(answers!=null)
        {
            response=new Gson().toJson(answers);
        }
        else{
            List empty = new ArrayList();
            response = new Gson().toJson(empty);
        }
        return response;
    }
    @RequestMapping(value = "/all_location", method = RequestMethod.GET)
    @ResponseBody
    public String getAllLocation()
    {
        LocationService location=Context.getLocationService();
       String response = new  Gson().toJson(location.getAllLocations());
       return response;
    }

}


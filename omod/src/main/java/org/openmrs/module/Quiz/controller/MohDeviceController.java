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

//https://YOUR_IP_ADDRESS/openmrs/ws/rest/v1/moh/inventory/device
@Controller
@RequestMapping(value = "/rest/"+ RestConstants.VERSION_1+"/moh/inventory/device")
public class MohDeviceController extends BaseRestController {
    private final Log log = LogFactory.getLog(this.getClass());
    @RequestMapping(value="/add_device",  method = RequestMethod.POST)
    @ResponseBody
    public String addDevice(@RequestBody String detailPayload)
    {
        QuizService deviceService = Context.getService(QuizService.class);
        return deviceService.addDevice(detailPayload);
    }

    @RequestMapping(value="/update_device",  method = RequestMethod.POST)
    @ResponseBody
    public String updateDevice(@RequestBody String detailPayload)
    {
        QuizService deviceService = Context.getService(QuizService.class);
        return deviceService.updateDevice(detailPayload);
    }

    @RequestMapping(value="/device_list",  method = RequestMethod.GET)
    public String deviceList()
    {
        QuizService deviceService = Context.getService(QuizService.class);
        return deviceService.deviceList();
    }
}

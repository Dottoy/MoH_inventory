package org.openmrs.module.Quiz.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.Quiz.api.QuizService;

import java.util.Date;

public class BedTestTask /*extends AbstractTask*/ {
    private Log log = LogFactory.getLog(BedTestTask.class);
   /* @Override*/
    public void execute() {
        QuizService quizService = Context.getService(QuizService.class);
        try {
//            quizService.processDebtsFeed();
        }catch (Exception e){
            log.error("Test process failed at "+new Date().toString());
        }
    }
}

package log4jdemo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Log4jDemo {
    private static final Logger LOGGER = LogManager.getLogger(Log4jDemo.class);


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            LOGGER.debug("running loop " + i);
        }

        calculateTax(12345, true);
        calculateTax(-123, true);
    }

    static double calculateTax(double salary, boolean isMarried) {
        LOGGER.info("Received request for calculating tax");
        LOGGER.debug("Persons name salary " + salary + " marriage status " + isMarried);
        if (salary > 0) {
            if (salary < 35000 && isMarried) {
                LOGGER.debug("tax = " + (salary * 0.1));
                return salary * 0.1;
            }
        } else
            LOGGER.error("Salary can not be negative");
        return 0;
        //more conditions here
}

//Logs in all programming languages follow the sane conventions for level:
//DEBUG - we put very detailed info with very detailed data in debug level
//INFO - we put general info in INGO level
//WARN - we put possible issues in warn level
//ERROR - when error occurs we put it in error level
//FATAL - when smth went horrible wrong we put in fatal level
}

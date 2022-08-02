package step_defs;

import org.junit.runners.Parameterized;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class CalendarClass {
    public static void main(String[] args) {
        //to get todays date

        //=====================
        ZoneId zonedId = ZoneId.of("America/Montreal");
        LocalDate today = LocalDate.now(zonedId);
        System.out.println("today : " + today);

        //========================
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));

        //==========================
        String mydate = DateFormat.getDateTimeInstance().
                format(Calendar.getInstance().getTime());
        System.out.println(mydate);

        //===============

    }

//    @Parameterized.Parameters(name = "browser, version, os data")
//    public static Collection sauceLabDataProvider() {
//        return Arrays.asList(new Object[][]{
//                {"chrome", "latest", "windows 10"},
//                {"firefox", "latest", "macOs 10.14"},
//                //{"edge", "92", "windows 10"}
//        });
    }


package by.gsu.epamlab.model.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static by.gsu.epamlab.model.utils.Constants.DATE_RU_PATTERN;
import static by.gsu.epamlab.model.utils.Constants.DATE_SQL_PATTERN;

public class TimeUtils {
    public static final long ONE_DAY_MIL = 1000 * 60 * 60 * 24;
    public static final long TWO_DAY_MIL = ONE_DAY_MIL * 2;
    public static final long ONE_YEAR_MIL = ONE_DAY_MIL * 365;

    public static final Date datePlusDays(Date date, long days){
        return new Date(date.getTime() + days);
    }

    public static final String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_SQL_PATTERN);
        java.util.Date uDate = null;
        try{
            uDate = sdf.parse(date.toString());
        }catch (ParseException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        SimpleDateFormat sdfRu = new SimpleDateFormat(DATE_RU_PATTERN);
        return sdfRu.format(uDate);
    }
}

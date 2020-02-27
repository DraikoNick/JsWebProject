package by.gsu.epamlab.model.utils;

import by.gsu.epamlab.model.bin.Task;
import by.gsu.epamlab.model.bin.User;
import by.gsu.epamlab.model.fabrics.SectionFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.CDL;
import org.json.JSONArray;
import java.util.*;
import java.util.stream.Collectors;

import static by.gsu.epamlab.model.utils.Constants.DATE_SQL_PATTERN;
import static by.gsu.epamlab.model.utils.ConstantsJSP.*;

public class Utils {
    public static String[] getEnumNames(Class<? extends Enum<?>> en){
        return Arrays.stream(en.getEnumConstants())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    public static Map<String, String> getMapFromEnum(Class<? extends Enum<?>> en){
        String[] names = getEnumNames(en);
        Map <String, String> mapOfNames = new LinkedHashMap<>();
        for(String key:names){
            mapOfNames.put(
                    key,
                    SectionFactory.SectionKind.valueOf(key.toUpperCase()).getName());
        }
        return mapOfNames;
    }

    public static Map<String, String> getSectionsMap(){
        java.sql.Date today = new java.sql.Date(new Date().getTime());
        java.sql.Date tomorrow = TimeUtils.datePlusDays(today, TimeUtils.ONE_DAY_MIL);
        Map<String, String> map = getMapFromEnum(SectionFactory.SectionKind.class);
        String valueToday = map.get(SectionFactory.SectionKind.TODAY.toString()) + " " + TimeUtils.formatDate(today);
        String valueTomorrow = map.get(SectionFactory.SectionKind.TOMORROW.toString()) + " " + TimeUtils.formatDate(tomorrow);
        map.put(SectionFactory.SectionKind.TODAY.toString(), valueToday);
        map.put(SectionFactory.SectionKind.TOMORROW.toString(), valueTomorrow);
        return map;
    }

    public static String getJson2DMap(Map<String, String> map, String nameOfMap, String key, String value) {
        JSONArray ja = new JSONArray();
        ja.put(key);
        ja.put(value);
        String str = map.entrySet().stream()
                .map(entry -> entry.getKey() + ", " + entry.getValue())
                .collect(Collectors.joining(" \n"));
        JSONArray json = CDL.toJSONArray(ja, str);
        return "\"" + nameOfMap + "\" : " + json.toString();
    }

    public static String getJsonForMainPage(SectionFactory.SectionKind section, List<Task> sortedList, User user){
        Gson gson = new GsonBuilder().setDateFormat(DATE_SQL_PATTERN).create();
        return  OPEN + TASKS + gson.toJson(sortedList) +COMA +
                Utils.getJson2DMap(SectionFactory.getButtonsForEachTask(section), BTN_EACH, KEY, VAL) + COMA +
                Utils.getJson2DMap(SectionFactory.getButtonsForSection(section), BTN_SECT, KEY, VAL) + COMA +
                Utils.getJson2DMap(Utils.getSectionsMap(), SECTIONS, KEY, VAL) + COMA +
                USER + user.getName() + CLOSE;
    }

    public static String getJsonForActionPage(List<Task> tasksToChange){
        Gson gson = new GsonBuilder().setDateFormat(DATE_SQL_PATTERN).create();
        return TO_CHANGE + gson.toJson(tasksToChange) + COMA + Utils.getJson2DMap(Utils.getSectionsMap(), SECTIONS, KEY, VAL) + END;
    }
}

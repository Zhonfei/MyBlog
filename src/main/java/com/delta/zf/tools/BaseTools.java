package com.delta.zf.tools;


import com.delta.zf.sys.bean.EmBaseInfo;
import com.google.gson.Gson;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Administrator on 2021/5/25.
 */
public class BaseTools {
    private final static String STATIC_LOCATIONS = "/blog/";
    private final static String suffix = ".html";
    private final static String UPLOAD_PATH="D:\\my_java_pro\\MyBlog\\src\\main\\resources\\static\\blog\\image\\";


    /**
     * write sth to file
     * @param file
     * @param obj
     * @return
     */
    public static boolean writeInfoToTxt(File file,Object obj){
        boolean flag = true;
        ObjectOutputStream outputStream = null;
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(obj);
            outputStream.close();
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * read obj from file
     * @param file
     * @return
     */
    public static Object getObjectByTxt(File file){
        ObjectInputStream ois = null;
        Object res = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            res = ois.readObject();
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public static String uploadFile(MultipartFile file,String newFileName){
        boolean flag = true;
        String fileName = file.getOriginalFilename();
        String sx = fileName.substring(fileName.indexOf("."));
        String newFilePath = UPLOAD_PATH+newFileName+sx;
        System.out.println(newFilePath);
        File dest = new File(newFilePath);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
        }finally {
            System.out.println("flag:"+flag);
        }
        return "image/"+newFileName+sx;
    }

    public static <T> T getUserInfo(HttpSession session,Class<T> tClass){
        SecurityContextImpl securityContext =  (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        return ((T)securityContext.getAuthentication().getPrincipal());
    }

    public static String getUUIDKEY(HttpSession session){
        String res = session.getAttributeNames().nextElement();
        System.out.println("UUIDKEY:"+res);
        return res;
    }

    public static String getENUUID(){
        String uuidString = UUID.randomUUID().toString();
        String[] uuid = uuidString.split("-");
        System.out.println("加密前："+uuidString);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < uuid.length; i++) {
            StringBuffer temp = new StringBuffer(uuid[i]);
            sb.append(temp.reverse());
            sb.append("-");
        }
        System.out.println("加密后："+sb.deleteCharAt(sb.length()-1));
        return String.valueOf(sb);
    }

    public static String getDEUUID(String uuidString){
        System.out.println("解密前："+uuidString);
        String[] uuid = uuidString.split("-");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < uuid.length; i++) {
            StringBuffer temp = new StringBuffer(uuid[i]);
            sb.append(temp.reverse());
            sb.append("-");
        }
        System.out.println("解密后："+sb.deleteCharAt(sb.length()-1));
        return String.valueOf(sb);
    }

    public static String getNowDate(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        return formatter.format(date);
    }

    public static String getNowTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        return formatter.format(date);
    }

    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static boolean isWeek(String datetime){
        String six = "周六",seven="周日";
        String temp = dateToWeek(datetime);
        return six.equals(temp)||seven.equals(temp);
    }

    public static List<String> getDiffDateListByWeek(String minDate,String maxDate,String weekOnly){
        List<String> dateList = getDiffDateList(minDate,maxDate);
        List<String> resList = new ArrayList<>();
        for (String dates:dateList) {
            if(dateToWeek(dates).equals(weekOnly)){
                resList.add(dates);
            }
        }
        return resList;
    }

    public static List<String> getDiffDateList(String minDate,String maxDate){
        List<String> dateList = new ArrayList<>();
        DateFormat dateFormat;
        if(minDate.length()>10){
            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        }else{
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        try {
            Date start = dateFormat.parse(minDate);
            Date end = dateFormat.parse(maxDate);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            while (tempStart.before(tempEnd)) {
                dateList.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateList;
    }

    public static String getRandomTime(String minTime,String maxTime,int flag){
        StringBuilder sb = new StringBuilder();
        int mins = flag>0?8:Integer.parseInt(minTime.substring(0,2));
        int r_max = Integer.parseInt(maxTime.substring(0,2))-mins;
        sb.append(mins+new Random().nextInt(r_max));
        for (int i=0;i<2;i++){
            sb.append(":");
            int k = new Random().nextInt(60);
            sb.append(k<10?"0"+k:k);
        }
        return sb.toString();
    }

    public static <T> T mapToBean(Map map,Class<T> tClass){
        T t = null;
        if(!map.isEmpty() && map.size()>0){
            Gson gson = new Gson();
            String gsonStr = gson.toJson(map);
            System.out.println("gsonStr:"+gsonStr);
            t = gson.fromJson(gsonStr,tClass);
        }
        return t;
    }

    public static boolean isNull(Object obj){
        return obj==null || "".equals(obj) || "null".equals(obj);
    }

    public static String redict(String page){
        StringBuilder sb = new StringBuilder(STATIC_LOCATIONS);
        sb.append(page);
        sb.append(suffix);
        System.out.println(sb);
        return sb.toString();
    }


    /**
     * 当前时间往前或往后推迟多久，返回格式：yyyy-MM-dd HH:mm:ss
     * @param unit  单位(minute、day、)
     * @param val   值（正值表示加，负值表示减）
     * @return
     * @throws Exception
     */
    public static String getAfterTime(String dateStr,String unit,int val)  {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cale = Calendar.getInstance();  //获取实例化的日历对象
        try {
            cale.setTime(sdFormat.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //cale.add(Calendar.MINUTE, minute);  //cale.get(Calendar.MINUTE) 获取当前分钟数
        if("SECOND".equalsIgnoreCase(unit)){
            cale.add(Calendar.SECOND,val);
        }else if("MINUTE".equalsIgnoreCase(unit)){
            cale.add(Calendar.MINUTE, val);  //cale.get(Calendar.MINUTE) 获取当前分钟数
        }else if("HOUR".equalsIgnoreCase(unit)){
            cale.add(Calendar.HOUR, val);
        }else if("MONTH".equalsIgnoreCase(unit)){
            cale.add(Calendar.MONTH, val);
        }else if("YEAR".equalsIgnoreCase(unit)){
            cale.add(Calendar.YEAR, val);
        }else{
            cale.add(Calendar.DATE, val);
        }
        String format = sdFormat.format(cale.getTime());
        return format;
    }

}

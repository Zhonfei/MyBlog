package com.delta.zf.tools;

import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class DataUtils {

    //tomcat
    //public static DataSourceConnectionProvider dscp = new DataSourceConnectionProvider("java:comp/env/jdbc/ftp_ncb");
    //was
    //public final static DataSourceConnectionProvider dscp = new DataSourceConnectionProvider("jdbc/ftp_ncb");
    //public static GenericDao dao = new GenericDao();
    //public static DbSession session = new DbSession(dscp);

    public static List<Map<String, Object>> queryList(String sql) throws Exception {
        DataUtils.DBTools dscp = new DBTools();
        Connection con = dscp.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        ResultSet rs = null;
        try{
            rs = pstmt.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
        } catch(Exception ex){
            throw ex;
        }finally{
            close(con, pstmt, rs);
        }
        return list;
    }

    public static Map<String, Object> queryMap(String sql) {
        DataUtils.DBTools dscp = new DBTools();
        Connection con = dscp.getConnection();
        PreparedStatement pstmt = null;
        Map<String, Object> rowData = new HashMap<String, Object>();
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }finally{
            close(con, pstmt, rs);
        }

        return rowData;
    }

    public static String queryKey(String sql, String key) throws Exception {
        DataUtils.DBTools dscp = new DBTools();
        Connection con = dscp.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        String res = "";
        ResultSet rs = null;
        try{
            rs = pstmt.executeQuery();
            while (rs.next()) {
                res = rs.getString(key);
            }
        } catch(Exception ex){
            throw ex;
        }finally{
            close(con, pstmt, rs);
        }
        return res;
    }
    /**
     * 数值型的，如果没有查出值，默认为0； by jllu 20150831
     * @param sql
     * @param key
     * @return
     * @throws Exception
     */
    public static String queryKeyGetNumber(String sql, String key) throws Exception {
        String str = queryKey(sql,key);
        if(str != null && !str.equals(""))
            return str;
        else
            return "0";
    }

    public static void execute(String sql) throws Exception{
        DataUtils.DBTools dscp = new DBTools();
        Connection con = dscp.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        try{
            pstmt.executeUpdate();
        } catch(Exception ex){
            throw ex;
        }finally{
            close(con, pstmt, null);
        }

    }

    public static Connection getConnection(){
        DataUtils.DBTools dscp = new DBTools();
        return dscp.getConnection();
    }

    static class DBTools {

        public Connection connection;

        private final  String driver = "com.mysql.cj.jdbc.Driver";

        private final  String url = "jdbc:mysql://192.168.237.132:3306/em?characterEncoding=utf8&serverTimezone=UTC&allowMultiQueries=true&useSSL=false";

        private final  String user = "root";

        private final  String pwd = "1234";

        public DBTools() {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url,user,pwd);
                System.out.println("获取连接成功！");
                System.out.println("connection:"+connection);
            }catch (Exception e){
                System.out.println("获取连接失败！");
            }
        }

        public Connection getConnection(){
            return connection;
        }

        public String getStringByCol(String sql){
            String res = null;

            return res;
        }
    }






    //调用存储过程
    public static void execProduct(String sql) throws Exception {
        DataUtils.DBTools dscp = new DBTools();
        Connection con = dscp.getConnection();
        try{
            CallableStatement call = con.prepareCall("{ CALL " + sql + "}");
            call.execute();
        } catch(Exception ex){
            throw ex;
        }finally{
            con.close();
        }
    }

    //执行sql语句返回数据记录数
    public static int executeCount(String sql) throws Exception{
        DataUtils.DBTools dscp = new DBTools();
        int rownum = 0; //操作记录行数
        Connection con = dscp.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        try{
            rownum = pstmt.executeUpdate();
        } catch(Exception ex){
            throw ex;
        }finally{
            close(con, pstmt, null);
        }
        return rownum;
    }

    public static void addVo(Object object, String tableName) throws Exception {
        String sql = makeAddSql(object, tableName);
        execute(sql);
    }

    public static void addBatchVo(List vos, String tableName) throws Exception {
        List sqls = new ArrayList();
        for (int i=0; i<vos.size(); i++) {
            Object object = vos.get(i);
            sqls.add(makeAddSql(object, tableName));
        }
        handleBatchs(sqls, 500);
        sqls.clear();
    }

    public static String makeAddSql(Object object, String tableName) throws Exception {
        String commonSql = "insert into ";
        StringBuilder sb = new StringBuilder();
        Class c = object.getClass();
        sb.append(commonSql).append(tableName).append(" (");
        Field[] arrayFields = c.getDeclaredFields();
        for (int i=0; i<arrayFields.length; i++) {
            Field field = arrayFields[i];
            String fieldName = field.getName();
            if (i == arrayFields.length-1) {
                sb.append(fieldName).append(") values (");
            } else {
                sb.append(fieldName).append(", ");
            }
        }
        for (int j=0; j<arrayFields.length; j++) {
            Field field = arrayFields[j];
            Class fieldType = field.getType();
            String fieldName = field.getName();
            String methodName = "get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
            Method method = c.getMethod(methodName);
            Object fieldValue = method.invoke(object);
            if (j == arrayFields.length-1) {
                if (fieldType.isInstance(new BigDecimal(0))) {
                    sb.append(fieldValue).append(")");
                } else if (fieldType.isInstance(new Date())) {
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(fieldValue);
                    sb.append("'").append(dateStr).append("')");
                } else if (fieldValue == null) {
                    sb.append(fieldValue).append(")");
                } else {
                    sb.append("'").append(fieldValue).append("')");
                }
            } else {
                if (fieldType.isInstance(new BigDecimal(0))) {
                    sb.append(fieldValue).append(", ");
                } else if (fieldType.isInstance(new Date())) {
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(fieldValue);
                    sb.append("'").append(dateStr).append("'");
                } else if (fieldValue == null) {
                    sb.append(fieldValue).append(", ");
                } else {
                    sb.append("'").append(fieldValue).append("', ");
                }
            }
        }
        return sb.toString();
    }

    public static void handleBatchs(List results, int addCount) throws Exception {
        if (!results.isEmpty()) {
            int fromIndex = 0;
            int endIndex = fromIndex + addCount;
            if (endIndex > results.size()) {
                batchExe(results);
            } else {
                while (endIndex <= results.size()) {
                    List batchs = results.subList(fromIndex, endIndex);
                    batchExe(batchs);
                    fromIndex += addCount;
                    endIndex = fromIndex + addCount;
                }
                if (fromIndex < results.size()) {
                    List lastBatchs = results.subList(fromIndex, results.size());
                    batchExe(lastBatchs);
                }
            }
        }
    }

    public static void addBatchMap(List maps, String tableName) throws Exception {
        List sqls = new ArrayList();
        for (int i=0; i<maps.size(); i++) {
            Map dataMap = (Map) maps.get(i);
            sqls.add(getMapAddSql(dataMap, tableName));
        }
        handleBatchs(sqls, 500);
        sqls.clear();
    }

    public static String getMapAddSql(Map map, String tableName) {
        List keyList = new ArrayList();
        List valList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        String topSql = "insert into ";
        sb.append(topSql).append(tableName).append(" (");
        Set entitySet = map.entrySet();
        Iterator entityIt = entitySet.iterator();
        while (entityIt.hasNext()) {
            Map.Entry me = (Map.Entry) entityIt.next();
            keyList.add(me.getKey());
            valList.add(me.getValue());
        }
        for (int i=0; i<keyList.size(); i++) {
            if (i == keyList.size()-1) {
                sb.append(keyList.get(i)).append(") values (");
            } else {
                sb.append(keyList.get(i)).append(", ");
            }
        }
        for (int k=0; k<valList.size(); k++) {
            if (k == valList.size()-1) {
                if (valList.get(k) == null) {
                    sb.append(valList.get(k)).append(")");
                } else {
                    Class c = valList.get(k).getClass();
                    if (c.isInstance(new BigDecimal(0))) {
                        sb.append(valList.get(k)).append(")");
                    } else if (c.isInstance(new Date())) {
                        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(valList.get(k));
                        sb.append("'").append(dateStr).append("')");
                    } else {
                        sb.append("'").append(valList.get(k)).append("')");
                    }
                }
            } else {
                if (valList.get(k) == null) {
                    sb.append(valList.get(k)).append(", ");
                } else {
                    Class c = valList.get(k).getClass();
                    if (c.isInstance(new BigDecimal(0))) {
                        sb.append(valList.get(k)).append(", ");
                    } else if (c.isInstance(new Date())) {
                        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(valList.get(k));
                        sb.append("'").append(dateStr).append("', ");
                    } else {
                        sb.append("'").append(valList.get(k)).append("', ");
                    }
                }
            }
        }
        return sb.toString();
    }

    public static void batchExe(List sqls) throws Exception {
        Connection connection = null;
        Statement statemenet = null;
        ResultSet rs = null;
        try {
            DataUtils.DBTools dscp = new DBTools();
            connection = dscp.getConnection();
            statemenet = connection.createStatement();
            for (int i = 0; i < sqls.size(); i++) {
                String sql = (String) sqls.get(i);
                System.out.println("==="+sql);
                statemenet.addBatch(sql);
            }
            statemenet.executeBatch();

        } catch (Exception e) {
            throw e;
        } finally {
            close(connection, statemenet, rs);
        }
    }

    public static void close(Connection con, Statement pstmt, ResultSet rs) {
        try {
            if (con != null) {
                con.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

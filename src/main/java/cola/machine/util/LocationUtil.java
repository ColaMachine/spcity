package cola.machine.util;

import com.alibaba.fastjson.JSON;

import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LocationUtil {
	public static String path;
	public static String sql ="SELECT * FROM awifi_alf.center_pub_area where PARENT_ID =";

	public static void main(String args[]){
		Connection con;

		Statement stmt;
		try {
			Class.forName("com.mysql.jdbc.Driver") ;
			String url = "jdbc:mysql://192.168.10.88:3306/awifi_alf?user=DBcenter&password=dbcenter@2015";
			con = DriverManager.getConnection(url);
			HashMap totalMap =new HashMap();
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			LocationUtil.getListByParentId("",1l, totalMap,stmt);
			//System.out.println(JSON.toJSONString(totalMap).replaceAll(",",",\n"));

			Iterator it = totalMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				HashMap value = (HashMap) totalMap.get(key);
				System.out.println("'"+key+"':"+JSON.toJSONString(value)+",");
				//System.out.println(key + "→" + value);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void getListByParentId(String path,Long parentid,HashMap totalMap,Statement stmt)throws Exception{

		if(StringUtil.isBlank(path)){
			path+=parentid;
		}else
			path+=","+parentid;
		//System.out.println(path);
		ResultSet rs = stmt.executeQuery(LocationUtil.sql+parentid);
		/*ResultSetMetaData resultSetMD = rs.getMetaData();*/
	/*	for (int i = 1; i < resultSetMD.getColumnCount(); i++) {
			System.out.println("ColumnName:" + resultSetMD.getColumnName(i) + " " +
					"ColumnTypeName:" +
					resultSetMD.getColumnTypeName(i));
			System.out.println("isReadOnly:" + resultSetMD.isReadOnly(i)
					+ "  isWriteable:" + resultSetMD.isWritable(i)
					+ "  isNullable:" + resultSetMD.isNullable(i));
			System.out.println("tableName:" + resultSetMD.getTableName(i));
		}*/
		//get the id and the name
		//StringBuffer  jsonStr=new StringBuffer("'"+path+"':{");

		HashMap map =new HashMap();
		boolean hasData=false;
		while (rs.next()) {
			HashMap col = new HashMap();
			Long id = rs.getLong(1);
			String name = rs.getString(3);
			col.put("id", id);
			col.put("name", name);
			map.put(id,name);
			hasData=true;

		}
		if(hasData) {
			totalMap.put(path, map);
			rs.close();
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				Long key = (Long) it.next();
				String value = (String) map.get(key);
				getListByParentId(path, key, totalMap, stmt);
				//System.out.println(key + "→" + value);
			}
		}




	}
}

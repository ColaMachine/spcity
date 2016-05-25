package cola.machine.util;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


public class JsonUtil {
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
    /**
     * 遍历一个对象的所有属性
     * @param clazz
     * @param list
     */
    public static void getAllFields(Class clazz, List<Field> list) {
    	for(Field f : clazz.getDeclaredFields()){
    		list.add(f);
    	}
    	Class superClazz = clazz.getSuperclass();
    	if(superClazz != null){
    		getAllFields(superClazz, list);
    	}
    }
	/**
	 * object对象转json
	 * @param model
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static String getJson(Object model) 
			throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Field[] field = model.getClass().getDeclaredFields();//获取实体类的所有属性，返回Field数组  
        StringBuffer buffer = new StringBuffer("");
        for(int i=0 ; i<field.length ; i++){//遍历所有属性
        	field[i].setAccessible(true);
            String name = field[i].getName();//获取属性的名字
            if(buffer.toString().equals("")){
            	buffer.append("\""+name+"\":");
            }else{
            	buffer.append(",\""+name+"\":");
            }  
            name = name.substring(0,1).toUpperCase()+name.substring(1);//将属性的首字符大写，方便构造get，set方法
            String type = field[i].getGenericType().toString();//获取属性的类型
            
            Method m = null;
            try {
            	m = model.getClass().getMethod("get"+name);
			} catch (Exception e) {
				if(e instanceof NoSuchMethodException){
					continue;
				}
			}
            
            if(m != null){
            	m.setAccessible(true);
                if(type.equals("class java.lang.String")){//如果type是类类型，则前面包含"class "，后面跟类名
                    String value = (String) m.invoke(model);//调用getter方法获取属性值
                    if(value != null){
                    	buffer.append("\""+value+"\"");
                    }else{
                    	buffer.append("\"\"");
                    }
                }
                if(type.equals("class java.lang.Integer")){
                    Integer value = (Integer) m.invoke(model);
                    if(value != null){
                    	buffer.append("\""+value+"\"");
                    }else{
                    	buffer.append("\"\"");
                    }
                }
                if(type.equals("class java.lang.Long")){
                    Integer value = (Integer) m.invoke(model);
                    if(value != null){
                    	buffer.append("\""+value+"\"");
                    }else{
                    	buffer.append("\"\"");
                    }
                }
                if(type.equals("class java.lang.Short")){     
                    Short value = (Short) m.invoke(model);
                    if(value != null){
                    	buffer.append("\""+value+"\"");
                    }else{
                    	buffer.append("\"\"");
                    }
                }       
                if(type.equals("class java.lang.Double")){     
                    Double value = (Double) m.invoke(model);
                    if(value != null){                    
                    	buffer.append("\""+value+"\"");
                    }else{
                    	buffer.append("\"\"");
                    }
                }                  
                if(type.equals("class java.lang.Boolean")){
                    Boolean value = (Boolean) m.invoke(model);
                    if(value != null){                      
                    	buffer.append(""+value+"");
                    }else{
                    	buffer.append(Boolean.FALSE.toString());
                    }
                }
                if(type.equals("class java.util.Date")){
                    Date value = (Date) m.invoke(model);
                    if(value != null){
                    	buffer.append("\""+
                    			new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(value)
                    			+"\"");
                    }else{
                    	buffer.append("\"\"");
                    }
                }
            }
            
        }
        return buffer.toString();
    }
	
	/**
	 * parse object（include superclass）to json string
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	public static String objectToJsonStr(Object model) 
			throws Exception{
		//获取实体类的所有属性，返回Field数组 
        List<Field> fields = new ArrayList<Field>();
        getAllFields(model.getClass(),fields);
        StringBuffer buffer = new StringBuffer("{");
        Field field = null;
        for(int i=0 ; i<fields.size() ; i++){//遍历所有属性
        	field = fields.get(i);
        	field.setAccessible(true);
            String name = field.getName();//获取属性的名字
            if(i>0){
            	buffer.append(",");
            }
            buffer.append("\""+name+"\":");
            name = name.substring(0,1).toUpperCase()+name.substring(1);//将属性的首字符大写，方便构造get，set方法
            String type = field.getGenericType().toString();//获取属性的类型
            
            Method m = null;
            try {
            	m = model.getClass().getMethod("get"+name);
			} catch (Exception e) {
				if(e instanceof NoSuchMethodException){
					continue;
				}
			}
            if(m != null){
            	m.setAccessible(true);
                if(type.equals("class java.lang.String")
                 		|| type.equals("class java.lang.Integer")
                 		|| type.equals("class java.lang.Long")
                 		|| type.equals("class java.lang.Short")
                 		|| type.equals("class java.lang.Float")
                 		|| type.equals("class java.lang.Double")
                 		|| type.equals("class java.lang.Byte")
                		){   //如果type是类类型，则前面包含"class "，后面跟类名
                    Object value = m.invoke(model);//调用getter方法获取属性值
                    if(value != null){
                    	buffer.append("\""+String.valueOf(value)+"\"");
                    }else{
                    	buffer.append("\"\"");
                    }
                }else if(type.equals("class java.lang.Boolean")){
                    Boolean value = (Boolean) m.invoke(model);
                    if(value != null){                      
                    	buffer.append(value);
                    }else{
                    	buffer.append(false);
                    }
                }else if(type.equals("class java.util.Date")){
                    Date value = (Date) m.invoke(model);
                    if(value != null){
                    	buffer.append("\""+
                    			new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(value)
                    			+"\"");
                    }else{
                    	buffer.append("\"\"");
                    }
                }else if(type.equals("interface java.util.List")){
                    List<Object> value = (List<Object>) m.invoke(model);
                    buffer.append(listToJson(value));
                }else if(type.equals("class [Ljava.lang.String;")){//字符串数组类型
                    String[] value = (String[]) m.invoke(model);
                    StringBuffer valueStr = new StringBuffer("");
                    buffer.append("[");
                    if(value != null){
                        for(String str : value){
                        	if(valueStr.toString().equals("")){
                        		valueStr.append("\""+str+"\"");
                            }else{
                            	valueStr.append(",\""+str+"\"");
                            } 
                        }
                        buffer.append(valueStr.toString());
                    }
                    buffer.append("]");
                }
            }
            
        }
        buffer.append("}");
        return buffer.toString();
    }
	
	/**
	 * parse list to json string
	 * @param list
	 * @return
	 */
	public static String listToJsonStr(List<Object> list){
		StringBuffer buffer = new StringBuffer("[");
		try {
			if(list!=null && list.size()>0){
				for(Object o : list){
					if(buffer.toString().length()>1){
						buffer.append(",");
	                }
					buffer.append(objectToJson(o));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * parse object（include superclass）to json Object
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	public static Map<String,Object> objectToJson(Object model) 
			throws Exception{
		//获取实体类的所有属性，返回Field数组 
        List<Field> fields = new ArrayList<Field>();
        getAllFields(model.getClass(),fields);
        Map<String,Object> map = new HashMap<String,Object>();
        Field field = null;
        for(int i=0 ; i<fields.size() ; i++){//遍历所有属性
        	field = fields.get(i);
        	field.setAccessible(true);
            String name = field.getName();//获取属性的名字
            String methodName = "get"+name.substring(0,1).toUpperCase()+name.substring(1);//将属性的首字符大写，方便构造get，set方法
            String type = field.getGenericType().toString();//获取属性的类型
            Method m = null;
            try {
            	m = model.getClass().getMethod(methodName);
			} catch (Exception e) {
				if(e instanceof NoSuchMethodException){
					continue;
				}
			}
            if(m != null){
            	 m.setAccessible(true);
                 Object parseValue="";
                 if(m.invoke(model) != null){
                 	if(type.equals("class java.lang.String")
                     		|| type.equals("class java.lang.Integer")
                     		|| type.equals("class java.lang.Long")
                     		|| type.equals("class java.lang.Short")
                     		|| type.equals("class java.lang.Float")
                     		|| type.equals("class java.lang.Double")
                     		|| type.equals("class java.lang.Byte")
                     		){   //如果type是类类型，则前面包含"class "，后面跟类名
                         Object value = m.invoke(model);//调用getter方法获取属性值
                         parseValue = value==null?"":String.valueOf(value);
                     } else if (type.equals("class java.lang.Boolean")){
                         Boolean value = (Boolean) m.invoke(model);
                         parseValue = value==null?false:value;
                     } else if (type.equals("class java.util.Date")){
                         Date value = (Date) m.invoke(model);
                         parseValue = value==null?"":new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(value);
                     } else if (type.equals("interface java.util.List")){
                         List<Object> value = (List<Object>) m.invoke(model);
                         parseValue = listToJson(value);
                     } else if (type.equalsIgnoreCase("class [Ljava.lang.String;")){//字符串数组类型
                         String[] value = (String[]) m.invoke(model);
                         parseValue = value;
                     }
                 	map.put(name, parseValue);
                 }
            }
        }
        return map;
    }
	/**
	 * parse list to json Object
	 * @param list
	 * @return
	 */
	public static List<Map<String,Object>> listToJson(List<Object> list) throws Exception {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();

		if(list!=null && list.size()>0){
			for(Object o : list){
				resultList.add(objectToJson(o));
			}
		}

		return resultList;
	}
	
	public static List<Map<String, Object>> arrayToJson(Object[] array) throws Exception {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		if (array != null && array.length > 0) {
			for (Object o : array) {
				resultList.add(objectToJson(o));
			}
		}
		
		return resultList;
	}
	
	
	@SuppressWarnings("unchecked")
    public static void Reflect_Object(Object o,String classPath){
        try {            
            Class _class = Class.forName(classPath);// 加载类
            recursive(o,_class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 递归遍历类及父类的属性值
     * @param o
     * @param _class
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Class recursive(Object o,Class _class){
        if(_class==null){
         return null;
        }else{
            Method[] methods = _class.getDeclaredMethods();// 获得类的方法集合
            // 遍历方法集合
            for (int i = 0; i < methods.length; i++) {
                // 获取所有getXX()的返回值
                if (methods[i].getName().startsWith("get")) {// 方法返回方法名
                    methods[i].setAccessible(true);//允许private被访问(以避免private getXX())
                    Object object;
                    try {
                        object = methods[i].invoke(o, null);
                        System.out.println(" " + methods[i].getName() + "=" + object);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            return recursive(o,_class.getSuperclass());
         }
     }
    
    public static String convertDateToSafeString(Date dt) {
    	return new SimpleDateFormat(JsonUtil.YYYY_MM_DD_HH_MM_SS).format(dt);
    }
	    
    public static String convertToSafeString(String str) {
    	return str == null || str.equalsIgnoreCase("null") ? "" : str;
    }
/*
	    
	public static void main(String[] args) {
		AccountWithBLOBs account = new AccountWithBLOBs();
		String[] tags = {"123","456"};
		account.setTags(tags);
		account.setCreateDatetime(new Date());
		
		try {
			System.out.println(objectToJson(account));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}

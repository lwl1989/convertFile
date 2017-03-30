package example.web.src.lib.sql;


import lib.sql.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;


public class SqlOperate {
	private lib.sql.MySqlClass mySqlClass;
	private String sql;
	private String tableName;
	private String jointable="default";
	private String joinSql;
	private HashMap<String, String> options;
	private HashMap<String, String> conditions;
	
	public SqlOperate(){
		mySqlClass=new lib.sql.MySqlClass("xueba_documents", "root", "sa");
	}
	public int update(String table,HashMap<String,String> conditions,HashMap<String,String> values){
		tableName=table;
		if(conditions.size()>0){
			this.conditions=conditions;
		}
		if(values.size()>0){
			this.options=values;
		}
		return do_update();
	}
	public int add(String table,LinkedList<String> keys,LinkedList<String> values){
		tableName=table;
		return insert(keys,values);	
	}
	public int del(String table,HashMap<String,String> conditions){
		tableName=table;
		if(conditions.size()>0){
			this.conditions=conditions;
		}
		return delete();
	}
	public LinkedList<HashMap<String, String>> select(String table,HashMap<String,String> conditions,HashMap<String,String> options){
		tableName=table;
		if(conditions.size()>0){
			this.conditions=conditions;
		}
		if(options.size()>0){
			this.options=options;
		}
		return _select();
	}
	private int delete(){
		sql=new String("delete FROM "+tableName);
		int i=0;
		if(!(conditions==null)){
			int conditionSize=conditions.size();		
			Set<Entry<String, String>> ConditionEntry=conditions.entrySet();
			Iterator<Entry<String, String>> iterable=ConditionEntry.iterator();
			if(iterable.hasNext()){
				sql+=" where ";
			}
			while(iterable.hasNext()) {
				Entry<String,String> entry=iterable.next();
				String key=entry.getKey();
				String value=entry.getValue();
				sql+=key+"='";
				sql+=value+"'";
				if(i!=(conditionSize-1)){
					sql+=" and ";
				}else{
					sql+=" ";
				}
				i++;
			}
		}
		//System.out.println(sql);
		return mySqlClass.update(sql);
	}
	private LinkedList<HashMap<String, String>> _select(){
		
		int i=0;
		if(!jointable.equals("default")){
			sql=new String("select *,");
			sql += tableName+".id as tid from "+tableName;
			sql += joinSql;
		}else{
			sql=new String("select * from ");
			sql=sql+tableName;
		}
		if(!(conditions==null)){
			int conditionSize=conditions.size();		
			Set<Entry<String, String>> ConditionEntry=conditions.entrySet();
			Iterator<Entry<String, String>> iterable=ConditionEntry.iterator();
			if(iterable.hasNext()){
				sql+=" where ";
			}
			while(iterable.hasNext()) {
				Entry<String,String> entry=iterable.next();
				String key=entry.getKey();
				String value=entry.getValue();
				sql+=tableName+"."+key+"='";
				sql+=value+"'";
				if(i!=(conditionSize-1)){
					sql+=" and ";
				}else{
					sql+=" ";
				}
				i++;
			}
		}
		
		i=0;
		if(!(options==null)){
			Set<Entry<String, String>> optionEntry=options.entrySet();
			Iterator<Entry<String, String>> iterable1=optionEntry.iterator();
			while(iterable1.hasNext()) {
				Entry<String,String> entry=iterable1.next();
				String key=entry.getKey().toString();
				if(key.equals("limit")){
					//String[] arr;
					//arr=iterable.next().getValue().split(",");
					//if(arr.length==2){
					sql += " limit "+entry.getValue();
				}
				if(key.equals("order")){
					sql += "order by "+tableName+"."+entry.getValue();
				}
			}/**/
		}
		
		//System.out.println(sql);
		return mySqlClass.query(sql);
	}
	
	public void joinSearch(String jointable,String table,String joinCondition,String condition){
		this.jointable=jointable;
		joinSql="  join "+jointable+" on "+jointable+"."+joinCondition+"="+table+"."+condition;
	}
	
	public LinkedList<HashMap<String, String>> where_in(String sql){
		return mySqlClass.query(sql);
	}
	
	private int insert(LinkedList<String> keys,LinkedList<String> values){
		sql=new String("insert into ");
		sql=sql+tableName;
		sql+="(";
		int i=0;
		int keysSize=keys.size();
		for(;i<keysSize;i++){
			sql=sql+"";
			sql=sql+keys.get(i);
			if(i==(keysSize-1)){
				sql=sql+")";
			}else{
				sql=sql+",";
			}
		}
		sql=sql+" values(";
		i=0;
		int valuesSize=values.size();
		for(;i<valuesSize;i++){
			sql=sql+"'";
			sql=sql+values.get(i);
			
			if(i==(valuesSize-1)){
				sql=sql+"'";
				sql=sql+")";
			}else{
				sql=sql+"',";
			}
		}
//		System.out.println(sql);
		return mySqlClass.update(sql);
	}
	
	private int do_update(){
		sql=new String("update ");
		sql=sql+tableName;
		int i=0;
		if(!(options==null)){
			Set<Entry<String, String>> optionEntry=options.entrySet();
			Iterator<Entry<String, String>> iterable1=optionEntry.iterator();
			if(iterable1.hasNext()){
				sql+=" set ";
			}
			int optionsSize=options.size();
			while(iterable1.hasNext()) {
				Entry<String,String> entry=iterable1.next();
				String key=entry.getKey();
				String value=entry.getValue();
				sql+=key+"='";
				sql+=value+"'";
				if(i!=(optionsSize-1)){
					sql+=",";
				}else{
					sql+=" ";
				}
				i++;
			}
		}
		 i=0;
		if(!(conditions==null)){
			int conditionSize=conditions.size();		
			Set<Entry<String, String>> ConditionEntry=conditions.entrySet();
			Iterator<Entry<String, String>> iterable=ConditionEntry.iterator();
			if(iterable.hasNext()){
				sql+=" where ";
			}
			while(iterable.hasNext()) {
				Entry<String,String> entry=iterable.next();
				String key=entry.getKey();
				String value=entry.getValue();
				sql+=key+"='";
				sql+=value+"'";
				if(i!=(conditionSize-1)){
					sql+=" and ";
				}else{
					sql+=" ";
				}
				i++;
			}
		}
		
//		System.out.println(sql);
		return mySqlClass.update(sql);
	}
	
	

}

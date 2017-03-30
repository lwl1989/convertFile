package lib.sql;


import conf.Configuration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;

public class SqlOperate {
	private MySqlClass mySqlClass;
	private String sql;
	private String tableName;
	private HashMap<String, String> options;
	private HashMap<String, String> conditions;
	
	public SqlOperate(Configuration conf){
		mySqlClass=new MySqlClass(conf.getConfig("SQL_DATABASE"),conf.getConfig("SQL_USERNAME") , conf.getConfig("SQL_PASSWORD"));
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
		//System.out.println(sql);
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
		
		//System.out.println(sql);
		return mySqlClass.update(sql);
	}
	
	

}

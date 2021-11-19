package com.itstyle.seckill.common.dynamicquery;
import java.util.List;

public interface DynamicQuery {

	 void save(Object entity);

	 void update(Object entity);

	 <T> void delete(Class<T> entityClass, Object entityid);

	 <T> void delete(Class<T> entityClass, Object[] entityids);
	

	<T> List<T> nativeQueryList(String nativeSql, Object... params);
	

	<T> List<T> nativeQueryListMap(String nativeSql,Object... params);


	<T> List<T> nativeQueryListModel(Class<T> resultClass, String nativeSql, Object... params);

	Object nativeQueryObject(String nativeSql, Object... params);

	Object[] nativeQueryArray(String nativeSql, Object... params);

	int nativeExecuteUpdate(String nativeSql, Object... params);
}

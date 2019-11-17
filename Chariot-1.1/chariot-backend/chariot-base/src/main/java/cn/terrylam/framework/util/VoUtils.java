package cn.terrylam.framework.util;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class VoUtils {

	@SuppressWarnings( "unchecked" )
	public static <T> T toVo( Object entity, Class<T> clazz ) {
		try {
			Class<?> entityClass = entity.getClass();
			Class<?>[] paramTypes = { entityClass };
			Constructor<?> con = clazz.getConstructor( paramTypes );
			Object[] params = { entity };
			Object voObj = con.newInstance( params );
			return (T)voObj;
		}
		catch( Exception e ) {
			return null;
		}
	}

	@SuppressWarnings( "unchecked" )
	public static <T> List<T> toVoList( List<?> list, Class<T> clazz ) {
		List<T> voData = new ArrayList<T>();
		try {
			if( list.size() > 0 ) {
				Class<?> entityClass = list.get( 0 ).getClass();
				for( Object obj : list ) {
					Class<?>[] paramTypes = { entityClass };
					Constructor<?> con = clazz.getConstructor( paramTypes );
					Object[] params = { obj };
					Object voObj = con.newInstance( params );
					voData.add( (T)voObj );
				}
			}
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return voData;
	}

}
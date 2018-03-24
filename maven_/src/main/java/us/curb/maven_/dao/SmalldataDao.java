package us.curb.maven_.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import us.curb.maven_.pojo.Smalldata;

public interface SmalldataDao {

	/**
	 * 根据偏移量和时间戳查询smalldata表
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Smalldata> queryList5after(@Param("limit") int limit,
										@Param("timestamp") Date timestamp);
	
	/**
	 * 根据偏移量查询最新smalldata表的数据
	 * @param limit
	 * @return
	 */
	List<Smalldata> queryList5before(@Param("limit") int limit);
	
	/**
	 * 插入表到smalldata表中
	 * @param smalldata
	 * @return
	 */
	int insertSmalldata (Smalldata smalldata);
}

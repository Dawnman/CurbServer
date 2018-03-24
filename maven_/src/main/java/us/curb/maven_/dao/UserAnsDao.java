package us.curb.maven_.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import us.curb.maven_.pojo.UserAns;

public interface UserAnsDao {

	/**
	 * 往user_ans表里面插入数据
	 * @param userAns
	 * @return
	 */
	int insertUserAns(UserAns userAns);
	
	/**
	 * 查询出所有回答过sdId问卷的数据
	 * @param sdId
	 * @return
	 */
	List<UserAns> querryBySdid(@Param("sdId")String sdId);
	
	/**
	 * 查询回答过sdId问卷的人数
	 * @param sdId
	 * @return
	 */
	int querryBySdidNum(@Param("sdId") String sdId);
	
	/**
	 * 查询出回答过sdId问卷并且问题x答案是answer的人数
	 * @param sdId
	 * @param answer
	 * @return
	 */
	int querryBySdidAndAnsNum(@Param("sdId") String sdId,@Param("questionNum")String x,@Param("answer") String answer);
}

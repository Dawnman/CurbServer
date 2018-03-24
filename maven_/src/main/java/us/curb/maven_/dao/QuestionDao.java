package us.curb.maven_.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import us.curb.maven_.pojo.Question;

public interface QuestionDao {

	/**
	 * 从question表中根据smalldata的id抓取问题详情
	 * @param sdid
	 * @return
	 */
	List<Question> querryBySdid(@Param("sdid")String sdid);
	
	/**
	 * 往question表里面插入数据
	 * @param question
	 * @return
	 */
	int insertQuestion(Question question);
	
	
	
}

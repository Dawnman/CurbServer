package us.curb.maven_.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import us.curb.maven_.pojo.QuestionKey;

public interface QuestionKeyDao {

	List<QuestionKey> querryBySdid(@Param("sdid")int sdid);
}

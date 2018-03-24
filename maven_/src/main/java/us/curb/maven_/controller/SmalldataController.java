package us.curb.maven_.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSONArray;
import us.curb.maven_.dao.QuestionDao;
import us.curb.maven_.dao.SmalldataDao;
import us.curb.maven_.dao.UserAnsDao;
import us.curb.maven_.pojo.Smalldata;
import us.curb.maven_.pojo.Statistics;
import us.curb.maven_.pojo.UserAns;
import us.curb.maven_.pojo.Question;

@RequestMapping("curb")
@RestController
public class SmalldataController {

	@Autowired
	SmalldataDao smalldataDao;
	@Autowired
	QuestionDao questionDao;
	@Autowired
	UserAnsDao userAnsDao;
	
	@RequestMapping(value="/smalldata/createsd",method = RequestMethod.POST)
	public void insertCreateC(){
		
	}
	
	/**
	 * 前台请求问卷概要（约定数量5个）
	 * @return json
	 */
	@RequestMapping(value="/smalldata/summary",method = RequestMethod.POST)
	public @ResponseBody List<Smalldata> getSmalldata(HttpServletRequest request, HttpServletResponse response, @RequestParam("timestamp")String time,
													@RequestParam("direction")int direction){
		
		if(direction == 1){
			List<Smalldata> sdList = smalldataDao.queryList5before(5);
			if(sdList != null && sdList.size() > 0){
				return sdList;
			}else{
				return null;
			}
		}else if(direction == 2){
			if(time != null){
				SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
				Date date;
				try {
					date = sdf.parse(time);
					List<Smalldata> sdList = smalldataDao.queryList5after(5,date );
					if(sdList != null && sdList.size() > 0){
						return sdList;
					}else{
						return null;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
			}else{
				List<Smalldata> sdList = smalldataDao.queryList5before(5);
				if(sdList != null && sdList.size() > 0){
					return sdList;
				}else{
					return null;
				}
			}
			
		}else
			return null;
		
		
		
	}
	
//	/**
//	 *请求某个时间之后的5个问卷概要
//	 * @param tiemstamp
//	 * @return
//	 */
//	@RequestMapping(value="/smalldata/summary/{timestamp}",method = RequestMethod.GET)
//	public @ResponseBody List<Smalldata> getSmalldataAfterC(@PathVariable("timestamp")Date tiemstamp){
//		
//		List<Smalldata> sdList = smalldataDao.queryList5after(5,tiemstamp);
//		if(sdList != null && sdList .size() > 0){
//			return sdList;
//		}else{
//			return null;
//		}
//	
//	}
	
	/**
	 * 返回问卷详情
	 * @param sdId
	 * @return
	 */
	@RequestMapping(value="/smalldata/questiondetail",method = RequestMethod.POST)
	public @ResponseBody List<Question> getQuestionDetail(HttpServletRequest request, HttpServletResponse response,
															@RequestParam("sd_id")String sdId){
		List<Question> qus = questionDao.querryBySdid(sdId);
		if(qus != null && qus.size() > 0)
			return qus;
		return null;
	}
	
	/**
	 * 向数据库插入前端传过来的用户答案
	 * @param data
	 * @return 0代表插入成功，1代表传过来的data为空
	 */
	@RequestMapping(value="/smalldata/userans",method = RequestMethod.POST)
	public int insertUserans(@RequestBody Map<String,String> data){
		if(data != null && data.size() > 0){
			String userId = data.get("user_id");
			String sdId = data.get("sd_id");
			String numMap = data.get("result");
			
//			int numOfQues = Integer.parseInt(data.get("question_num"));
//			for(int i = 0;i < numOfQues;i++){
//				UserAns userAns = new UserAns();
//				userAns.setUserId(userId);
//				userAns.setSdId(sdId);
//				userAns.setQuestionNum(Integer.toString(i));
//				userAns.setAnswer(data.get(Integer.toString(i)));
//				userAnsDao.insertUserAns(userAns);
//			}
			System.out.println(numMap);
			return 0;
		}
		return 1;
	}
		
	
	@RequestMapping(value="/smalldata/questionresult",method = RequestMethod.POST)
	public @ResponseBody Object getStatistics(@RequestParam("sdid")String sdId){
		List<Question> quesList = questionDao.querryBySdid(sdId);
		JSONArray jsArr = new JSONArray();
		if(quesList != null && quesList.size() > 0){
//			Statistics sta = new Statistics(quesList);	
			
			for(int i = 0;i < quesList.size();i++){
				String num = quesList.get(i).getQuestionNum();				
				int numOfOP1 = userAnsDao.querryBySdidAndAnsNum(sdId, num, "1");
				int numOfop2 = userAnsDao.querryBySdidAndAnsNum(sdId, num, "2");
				int proportion1 = numOfOP1/(numOfOP1+numOfop2)*100;
				int proportion2 = 100-proportion1;
				JSONObject jo = new JSONObject();
				jo.put("question_num", num);
				jo.put("question", quesList.get(i).getQuestion());
				jo.put("option1", quesList.get(i).getOption1());
				jo.put("opton2", quesList.get(i).getOption2());
				jo.put("option1ans", proportion1);
				jo.put("option2ans", proportion2);
				if(jsArr != null)
					jsArr.add(jo);
				jo = null;
			}

		}
		return jsArr;
	}
}

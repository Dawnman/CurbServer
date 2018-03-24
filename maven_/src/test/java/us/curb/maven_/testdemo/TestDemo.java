package us.curb.maven_.testdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.utils.HttpConstant;
import us.curb.maven_.crawscholar.CrawScholar;
import us.curb.maven_.dao.CourseDao;
import us.curb.maven_.dao.HomeworkDao;
import us.curb.maven_.dao.QuestionDao;
import us.curb.maven_.dao.QuestionKeyDao;
import us.curb.maven_.dao.SmalldataDao;
import us.curb.maven_.dao.UserAnsDao;
import us.curb.maven_.dao.UserDao;
import us.curb.maven_.pojo.Homework;
import us.curb.maven_.pojo.UserAns;
import us.curb.maven_.serviceimpl.UploadService;
 
@RunWith(SpringRunner.class) // 不能是PowerMock等别的class，否则无法识别spring的配置文件  
@ContextConfiguration({"classpath:spring/applicationContext_dao.xml","classpath:spring/applicationContext_service.xml"})   // 读取spring配置文件  
public class TestDemo {

	@Autowired
	CourseDao courseDao;
	@Autowired
	SmalldataDao smalldatadao;
	
	@Autowired
	UserAnsDao userAnsDao;
	@Autowired
	QuestionDao questionDao;
	
	@Autowired 
	QuestionKeyDao questionKeyDao;
	
	@Autowired
	UserDao userDao;
	@Autowired
	HomeworkDao homeworkDao;
	@Autowired 
	UploadService uploadService;
	
	
	//@Test
	public void craw(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
		CrawScholar crawScholar = applicationContext.getBean(CrawScholar.class);
		crawScholar.init(2,"iri0jwj","Princess615");
		Request request = new Request("http://www.scholat.com/Auth.html");
		request.setMethod(HttpConstant.Method.POST);
		//request.setExtras(crawScholar.nameValuePair);
		request.addCookie("JSESSIONID",crawScholar.getLogin().cookie);
		request.setRequestBody(HttpRequestBody.form(crawScholar.getFormMap(), "utf-8"));
		Spider spider = Spider.create(crawScholar);
		spider.addRequest(request);
		
		spider.addPipeline(new ConsolePipeline()).addPipeline(crawScholar.getCrawScholarPipeline());
		spider.thread(1).run();
		if(crawScholar.getErrCode()==0){
			System.out.println("登陆信息错误！！！");
		}
		else{
			System.out.println("登陆信息正确！！！");
		}
	}
	
	@Test
	public void test(){
		//List<String> strlist = new ArrayList<String>();
		//System.out.println(strlist.size());
		
//		String str = "未提交";
//		if(str == "未提交"){
//			System.out.println(true);
//		}
//		System.out.println(Runtime.getRuntime().maxMemory());
//		List<Homework> homeworkList = homeworkDao.listUnfinish(1);
//		System.out.println(homeworkList.get(0));
		
		//20180318
//		List<Homework> homeworkList = homeworkDao.listHomework(568);
//		System.out.println(homeworkList.get(0));
		
		//20180322
		String uuid = UUID.randomUUID().toString();
		System.out.println(uuid);
		
	}
	
//	@Test
	public void test2(){
		UserAns u = new UserAns();
//		int sum = userAnsDao.querryBySdidNum(1);
//		int sdsum = userAnsDao.querryBySdidAndAnsNum(1, 2);
//		
//		System.out.println(sum);
//		System.out.println(sdsum);

//		Question qu = new Question();
//		qu.setSdId(3);
//		qu.setQuestionNum(1);
//		qu.setQuestion("test question");
//		qu.setOption1("test option1");
//		questionDao.insertQuestion(qu);  //成功
		
	}
	
	//@Test
	public void testUploadFile(){
		String id = "20152005072";
		File file1 = new File("C:\\testimg\\4430.png");
		if(!file1.exists()){
			System.out.println("不存在");
			return;
		}
			
		
		try {
			
			MultipartFile file2 = new MockMultipartFile("4430.png",new FileInputStream(file1));
			System.out.println(file2.getContentType());
			System.out.println(file2.getName());
			System.out.println(file2.getOriginalFilename());
			if(file2 != null){
				String path = uploadService.saveUploadImg(id, file2);
				System.out.println(path);
			}else
				System.out.println("file2 null");
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}
	
	
}

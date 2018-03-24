package us.curb.maven_.serviceimpl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("uploadService")
public class UploadService {

	private final String BASEPATH = "C:\\AccountImg\\";
	
	private String fileName = null;
	
	private String compPath = null;
	
	public String saveUploadImg(String accountId,MultipartFile img) throws IOException{
		
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//		String contentType = img.getContentType(); //null
//		String testName = img.getOriginalFilename(); //为空
//		System.out.println(testName);
		//System.out.println(contentType);//null
//		String suffixName = testName.substring(testName.indexOf("\\")+1);
		//System.out.println(suffixName);
		String suffixName = img.getName();
		String uploadRootPath = BASEPATH+accountId; //每个用户的头像文件夹
		
		fileName = uuid+suffixName;
		
		compPath = uploadRootPath + fileName;
		
		File uploadFileDir = new File(uploadRootPath);
		if(!uploadFileDir.exists()){
			uploadFileDir.mkdirs();
		}
		
		byte[] bytes = img.getBytes();
		
		File serverFile = new File(uploadFileDir.getAbsolutePath()+File.separator+fileName);
		
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		
		stream.write(bytes);
		stream.close();
		uploadFileDir = null;
		return serverFile.getAbsolutePath();
	}
}

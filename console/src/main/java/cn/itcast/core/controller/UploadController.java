package cn.itcast.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import cn.itcast.core.common.Constants;
import cn.itcast.core.interfaces.UploadService;

/**
 * 文件上传
 * @author 祝国龙
 *
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

	@Autowired
	private UploadService uploadService;
	
	@RequestMapping("/updatePic")
	public void uploadPic(MultipartFile pic, HttpServletResponse response) throws Exception {
//		//获取上传的文件名称
//		String fileName = pic.getOriginalFilename();
//		//为防止文件重名，修改文件名
//		String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + FilenameUtils.getExtension(fileName);
//		//将图片保存在文件服务器上
//		pic.transferTo(new File("D:\\WorkSpace\\WorkSpace_Project\\image\\" + newFileName));
		String path = uploadService.uploadFile(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
		//将图片保存的路径回显,即将图片服务器中的图片回显
		JSONObject jo = new JSONObject();
		String url = Constants.IMG_SERVER + path;
		jo.put("url", url);
		response.getWriter().write(jo.toString());
	}
	
	/**
	 * 添加商品页面：上传多个图片
	 * @param pics
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadPics")
	@ResponseBody
	public List<String> uploadPics(@RequestParam MultipartFile[] pics) throws Exception {
		//保存多个图片上传后返回的路径
		List<String> pathList = new ArrayList<>();
		
		if(pics != null){
			for (MultipartFile pic : pics) {
				//获取图片地址
				//参数一：获取图片内容，参数二：获取图片原来的名称，参数三：获取图片大小
				String path = uploadService.uploadFile(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
				//将图片路径放到集合中
				pathList.add(Constants.IMG_SERVER + path);
			}
		}
		
		return pathList;
	}
	
	//无敌版上传(不管是单张图片还是多张图片都可以上传)
	//即使不知道上传域的名称也能上传
	@RequestMapping("/uploadFck")
	public void uploadFck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//将原始的request转换成MultipartRequest对象，方便从里面提取上传的文件数据
		MultipartRequest multipartRequest = (MultipartRequest)request;
		//将上传的多个文件转换成map
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		//将map转换成set遍历
		Set<Entry<String,MultipartFile>> entrySet = fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			//遍历图片集合，获取内一个图片对象
			MultipartFile pic = entry.getValue();
			String path = uploadService.uploadFile(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
			String url = Constants.IMG_SERVER + path;
			JSONObject jo = new JSONObject();
			
			//前端使用kindeditor插件, 所以kindEditor插件规定, 返回的json中要包含,url图片路径, error错误信息
			//kinEditor规定error返回信息如果为0表示无错误信息
			jo.put("url", url);
			jo.put("error", 0);
			
			response.getWriter().write(jo.toString());
		}
	}
}



















































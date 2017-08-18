package cn.itcast.core.common;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

public class FastDFSUtils {
	/**
	 * 上传文件工具方法
	 * @param pic 上传文件的内容
	 * @param name 长传文件的名称
	 * @param size 上传文件的大小
	 * @return
	 * @throws Exception
	 */
	public static String uploadFile(byte[] pic, String name, long size) throws Exception {
		//让spring加载这个配置文件
		ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
		//再次加载这个配置文件
		ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
		//创建管理端
		TrackerClient trackerClient = new TrackerClient();
		//获得管理端连接
		TrackerServer connection = trackerClient.getConnection();
		//创建管理端
		StorageClient1 storageClient1 = new StorageClient1(connection, null);
		//获取文件扩展名
		String extension = FilenameUtils.getExtension(name);
		//写入文件的右键属性中的详细信息内容
		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("fileName", name);
		meta_list[1] = new NameValuePair("fileExt", extension);
		meta_list[2] = new NameValuePair("fileSize", String.valueOf(size));
		//上传文件并返回文件存储路径
		String path = storageClient1.upload_file1(pic, extension, meta_list);
		return path;
	}
}























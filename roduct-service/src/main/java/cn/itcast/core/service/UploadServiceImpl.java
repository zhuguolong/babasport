package cn.itcast.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.common.FastDFSUtils;
import cn.itcast.core.interfaces.UploadService;

@Service("uploadServiceImpl")
@Transactional
public class UploadServiceImpl implements UploadService {

	@Override
	public String uploadFile(byte[] pic, String name, long size) throws Exception {
		String path = FastDFSUtils.uploadFile(pic, name, size);
		return path;
	}
	
}

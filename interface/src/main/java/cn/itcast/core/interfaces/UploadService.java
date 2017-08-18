package cn.itcast.core.interfaces;

public interface UploadService {
	public String uploadFile(byte[] pic, String name, long size) throws Exception;
}

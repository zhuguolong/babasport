package cn.itcast.core.common;

import org.springframework.core.convert.converter.Converter;

/**
 * 第一个参数：源参数，即要转换的参数类型
 * 第二个参数：目标参数，即转换后的参数类型
 * @author 祝国龙
 *
 */
public class CustomerTrimConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		if(source != null){
			//去掉字符串首尾的空格
			source = source.trim();
			if(!"".equals(source)){
				return source;
			}
		}
		return null;
	}

}

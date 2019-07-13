package cn.terrylam.chariot.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements  WebMvcConfigurer {
	

//	/**
//	 * 利用fastjson替换掉jackson,且解决中文乱码问题
//	 * 
//	 * @param converters
//	 */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //1.构建了一个HttpMessageConverter  FastJson   消息转换器
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        //2.定义一个配置，设置编码方式，和格式化的形式
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        //3.设置成了PrettyFormat格式
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        SerializeConfig serializeConfig = new SerializeConfig();
//        fastJsonConfig.setSerializeConfig(serializeConfig);
//        //4.处理中文乱码问题
//        List<MediaType> fastMediaTypes =  new ArrayList<>();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        fastConverter.setSupportedMediaTypes(fastMediaTypes);
//
//        //5.将fastJsonConfig加到消息转换器中
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        converters.add(fastConverter);
//    }
	
	
	
}

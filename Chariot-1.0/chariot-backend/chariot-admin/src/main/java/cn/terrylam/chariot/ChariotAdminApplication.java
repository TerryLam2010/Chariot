package cn.terrylam.chariot;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import tk.mybatis.spring.annotation.MapperScan;


//@EnableTransactionManagement

@SpringBootApplication(scanBasePackages = {"cn.terrylam.chariot.*","com.alicp.jetcache.autoconfigure"})
@MapperScan(basePackages = {"cn.terrylam.chariot.base.dao","cn.terrylam.chariot.base.dao.system"})
@EnableMethodCache(basePackages = "cn.terrylam.chariot")
@EnableCreateCacheAnnotation
public class ChariotAdminApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ChariotAdminApplication.class, args);
	}

}

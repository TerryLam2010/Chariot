package cn.terrylam.chariot;

import cn.terrylam.chariot.base.cache.anno.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication(scanBasePackages = {"cn.terrylam.chariot.*"})
@MapperScan(basePackages = {"cn.terrylam.chariot.base.dao","cn.terrylam.chariot.base.dao.system"})
@EnableMethodCache(basePackages = {"cn.terrylam.chariot","cn.terrylam.chariot.*.service.*"})
public class ChariotAdminApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ChariotAdminApplication.class, args);
	}

}

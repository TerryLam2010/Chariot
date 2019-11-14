package cn.terrylam.chariot.api.controller;

import cn.terrylam.framework.enums.ResponseCodeEnum;
import cn.terrylam.framework.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {

	@GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> helloworld() {
		return ResponseResult.build(ResponseCodeEnum.SUCCESS, ResponseCodeEnum.SUCCESS.getDesc(), "HelloWorld!");
	}
}

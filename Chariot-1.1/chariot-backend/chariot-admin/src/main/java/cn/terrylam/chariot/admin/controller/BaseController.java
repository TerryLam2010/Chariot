package cn.terrylam.chariot.admin.controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseController {

    /**
     * 带参重定向
     *
     * @param path
     * @return
     */
    protected String redirect(String path) {
        return "redirect:" + path;
    }

    /**
     * 不带参重定向
     *
     * @param response
     * @param path
     * @return
     */
    protected String redirect(HttpServletResponse response, String path) {
        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

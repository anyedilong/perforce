package org.sdblt.modules.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/8/6.
 */
@Controller
public class FileController {

    /**
     * 文件上传
     * @param response
     * @param request
     */
    public void fileUpload(HttpServletResponse response, HttpServletRequest request){

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //获取文件流
        MultipartFile multipartFile = multipartRequest.getFile("Filedata");

        //request.getInputStream();

    }
}

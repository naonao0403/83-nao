package com.yc.springmvc.web;

import java.io.File;
import java.io.IOException;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadAction {

	@PostMapping(path="upload",produces="text/html;charset=UTF-8")
	public String upload(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		String diskpath="D:/software/workspace/photo/images/fulls/";
		String filename=file.getOriginalFilename();//文件名
		file.transferTo(new File(diskpath+filename));
		//上传文件名是 a.jpg 对应的web路径是 photo/a.jpg
		return "success:"+"photo/"+filename;
	}
}

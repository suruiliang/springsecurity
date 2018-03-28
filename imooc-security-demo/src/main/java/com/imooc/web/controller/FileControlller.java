package com.imooc.web.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.imooc.dto.FileInfo;

@RestController
@RequestMapping(value="/file")
public class FileControlller {
	private String folder=getClass().getResource("").getPath();

	@PostMapping
	public FileInfo upload(MultipartFile file) throws Exception {
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		
		File localFile=new File(folder, System.currentTimeMillis()+".txt"); 
		file.transferTo(localFile);
		return new FileInfo(localFile.getAbsolutePath());
	}
	@GetMapping(value="{id}")
	public void download(@PathVariable String id,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try(InputStream inputStream=new FileInputStream(new File(folder, id+".txt"));
				OutputStream outputStream=response.getOutputStream();) {
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment;filename=test.txt");
			IOUtils.copy(inputStream, outputStream);
			outputStream.flush();
		} 
	}
}

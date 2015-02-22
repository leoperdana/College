package com.dev.college.modules.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.college.modules.model.IndexModel;

@Controller
public class IndexController
{
	private IndexModel m_imIndexModel;
	
	public IndexController()
	{
		m_imIndexModel = new IndexModel();
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String Index()
	{
		return "/IndexView";
	}
	
	@RequestMapping(value = "/liststudentsvc", method = RequestMethod.GET)
	public @ResponseBody Map <String, Object> ListStudentService()
	{
		Map <String, Object> mResult = new HashMap <String, Object> ();
		mResult.put("status", 1);
		mResult.put("result", m_imIndexModel.ListStudent());
		
		return mResult;
	}
	
	@RequestMapping(value = "/listcoursessvc", method = RequestMethod.GET)
	public @ResponseBody Map <String, Object> ListCoursesService()
	{
		Map <String, Object> mResult = new HashMap <String, Object> ();
		mResult.put("status", 1);
		mResult.put("result", m_imIndexModel.ListCourses());
		
		return mResult;
	}
	
	@RequestMapping(value = "/listscoresvc", method = RequestMethod.GET)
	public @ResponseBody Map <String, Object> ListScoreService()
	{
		Map <String, Object> mResult = new HashMap <String, Object> ();
		mResult.put("status", 1);
		mResult.put("result", m_imIndexModel.ListScore());
		
		return mResult;
	}
}

package com.dev.college.modules.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.college.modules.model.CoursesModel;
import com.dev.college.skeleton.Courses;

@Controller
public class CoursesController
{
	private CoursesModel m_cmCoursesModel;
	
	public CoursesController()
	{
		m_cmCoursesModel = new CoursesModel();
	}
	
	@RequestMapping(value = "/addcourses", method = RequestMethod.GET)
	public String AddCourses()
	{
		return "/AddCoursesView";
	}
	
	@RequestMapping(value = "/doaddcoursessvc", method = RequestMethod.POST)
	public @ResponseBody Map <String, Object> DoAddCoursesService(@RequestBody Map <String, Object> mParam)
	{
		String strCoursesName = (String) mParam.get("coursesname");
		Integer iCoursesCredit = (Integer) mParam.get("coursescredit");
		
		Map <String, Object> mResult = new HashMap <String, Object> ();
		if(strCoursesName == null)
			mResult.put("status", 0);
		else if(iCoursesCredit == null)
			mResult.put("status", 0);
		else
		{
			boolean bResult = m_cmCoursesModel.InsertCourses(strCoursesName, iCoursesCredit);
			if(!bResult)
				mResult.put("status", 0);
			else
				mResult.put("status", 1);
		}
		
		return mResult;
	}
	
	@RequestMapping(value = "/editcourses/{coursesid}", method = RequestMethod.GET)
	public String EditCourses(ModelMap mmModelMap, @PathVariable("coursesid") int iCoursesId)
	{
		mmModelMap.addAttribute("coursesid", iCoursesId);
		
		return "/EditCoursesView";
	}
	
	@RequestMapping(value = "/editcoursessvc/{coursesid}", method = RequestMethod.GET)
	public @ResponseBody Map <String, Object> EditCoursesService(@PathVariable("coursesid") int iCoursesId)
	{
		Map <String, Object> mResult = new HashMap <String, Object> ();
		Courses cCourses = m_cmCoursesModel.GetCoursesById(iCoursesId);
		if(cCourses == null)
			mResult.put("status", 0);
		else
		{
			Map <String, Object> mCourses = new HashMap <String, Object> ();
			mCourses.put("coursesname", cCourses.getCoursesName());
			mCourses.put("coursescredit", cCourses.getCoursesCredit());
			
			mResult.put("status", 1);
			mResult.put("result", mCourses);
		}
		
		return mResult;
	}
	
	@RequestMapping(value = "/doeditcoursessvc/{coursesid}", method = RequestMethod.POST)
	public @ResponseBody Map <String, Object> DoEditCoursesService(@PathVariable("coursesid") int iCoursesId, @RequestBody Map <String, Object> mParam)
	{
		String strCoursesName = (String) mParam.get("coursesname");
		Integer iCoursesCredit = (Integer) mParam.get("coursescredit");
		
		Map <String, Object> mResult = new HashMap <String, Object> ();
		if(strCoursesName == null)
			mResult.put("status", 0);
		else if(iCoursesCredit == null)
			mResult.put("status", 0);
		else
		{
			boolean bResult = m_cmCoursesModel.UpdateCourses(iCoursesId, strCoursesName, iCoursesCredit);
			if(!bResult)
				mResult.put("status", 0);
			else
				mResult.put("status", 1);
		}
		
		return mResult;
	}
	
	@RequestMapping(value = "/dodeletecoursessvc/{coursesid}", method = RequestMethod.GET)
	public @ResponseBody Map <String, Object> DoDeleteCoursesService(@PathVariable("coursesid") int iCoursesId)
	{
		Map <String, Object> mResult = new HashMap <String, Object> ();
		
		boolean bResult = m_cmCoursesModel.DeleteCourses(iCoursesId);
		if(!bResult)
			mResult.put("status", 0);
		else
			mResult.put("status", 1);
		
		return mResult;
	}
}

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

import com.dev.college.modules.model.StudentModel;
import com.dev.college.skeleton.Student;

@Controller
public class StudentController
{
	private StudentModel m_smStudentModel;
	
	public StudentController()
	{
		m_smStudentModel = new StudentModel();
	}
	
	@RequestMapping(value = "/addstudent", method = RequestMethod.GET)
	public String AddStudent()
	{
		return "/AddStudentView";
	}
	
	@RequestMapping(value = "/doaddstudentsvc", method = RequestMethod.POST)
	public @ResponseBody Map <String, Object> DoAddStudentService(@RequestBody Map <String, Object> mParam)
	{
		String strStudentNumber = (String) mParam.get("studentnumber");
		String strStudentName = (String) mParam.get("studentname");
		
		Map <String, Object> mResult = new HashMap <String, Object> ();
		if(strStudentNumber == null)
			mResult.put("status", 0);
		else if(strStudentName == null)
			mResult.put("status", 0);
		else
		{
			boolean bResult = m_smStudentModel.InsertStudent(strStudentNumber, strStudentName);
			if(!bResult)
				mResult.put("status", 0);
			else
				mResult.put("status", 1);
		}
		
		return mResult;
	}
	
	@RequestMapping(value = "/editstudent/{studentid}", method = RequestMethod.GET)
	public String EditStudent(ModelMap mmModelMap, @PathVariable("studentid") int iStudentId)
	{
		mmModelMap.addAttribute("studentid", iStudentId);
		
		return "/EditStudentView";
	}
	
	@RequestMapping(value = "/editstudentsvc/{studentid}", method = RequestMethod.GET)
	public @ResponseBody Map <String, Object> EditStudentService(@PathVariable("studentid") int iStudentId)
	{
		Map <String, Object> mResult = new HashMap <String, Object> ();
		Student sStudent = m_smStudentModel.GetStudentById(iStudentId);
		if(sStudent == null)
			mResult.put("status", 0);
		else
		{
			Map <String, Object> mStudent = new HashMap <String, Object> ();
			mStudent.put("studentnumber", sStudent.getStudentNumber());
			mStudent.put("studentname", sStudent.getStudentName());
			
			mResult.put("status", 1);
			mResult.put("result", mStudent);
		}
		
		return mResult;
	}
	
	@RequestMapping(value = "/doeditstudentsvc/{studentid}", method = RequestMethod.POST)
	public @ResponseBody Map <String, Object> DoEditStudentService(@PathVariable("studentid") int iStudentId, @RequestBody Map <String, Object> mParam)
	{
		String strStudentName = (String) mParam.get("studentname");
		
		Map <String, Object> mResult = new HashMap <String, Object> ();
		if(strStudentName == null)
			mResult.put("status", 0);
		else
		{
			boolean bResult = m_smStudentModel.UpdateStudent(iStudentId, strStudentName);
			if(!bResult)
				mResult.put("status", 0);
			else
				mResult.put("status", 1);
		}
		
		return mResult;
	}
	
	@RequestMapping(value = "/dodeletestudentsvc/{studentid}", method = RequestMethod.GET)
	public @ResponseBody Map <String, Object> DoDeleteStudentService(@PathVariable("studentid") int iStudentId)
	{
		Map <String, Object> mResult = new HashMap <String, Object> ();
		
		boolean bResult = m_smStudentModel.DeleteStudent(iStudentId);
		if(!bResult)
			mResult.put("status", 0);
		else
			mResult.put("status", 1);
		
		return mResult;
	}
	
	@RequestMapping(value = "/checkstudentnumbersvc", method = RequestMethod.POST)
	public @ResponseBody Map <String, Object> CheckStudentNumberService(@RequestBody Map <String, Object> mParam)
	{
		String strStudentNumber = (String) mParam.get("studentnumber");
		
		Map <String, Object> mResult = new HashMap <String, Object> ();
		if(strStudentNumber == null)
			mResult.put("status", 0);
		else
		{
			int iCount = m_smStudentModel.CountStudentByNumber(strStudentNumber);
			if(iCount == -1)
				mResult.put("status", 0);
			else
			{
				mResult.put("status", 1);
				
				if(iCount == 0)
					mResult.put("result", 0);
				else
					mResult.put("result", 1);
			}
		}
		
		return mResult;
	}
}

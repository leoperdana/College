package com.dev.college.modules.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.college.modules.model.ScoreModel;
import com.dev.college.skeleton.Courses;
import com.dev.college.skeleton.Score;
import com.dev.college.skeleton.Student;

@Controller
public class ScoreController
{
	private ScoreModel m_smScoreModel;
	
	public ScoreController()
	{
		m_smScoreModel = new ScoreModel();
	}
	
	@RequestMapping(value = "/addscore", method = RequestMethod.GET)
	public String AddScore()
	{
		return "/AddScoreView";
	}
	
	@RequestMapping(value = "/doaddscoresvc", method = RequestMethod.POST)
	public @ResponseBody Map <String, Object> DoAddScoreService(@RequestBody Map <String, Object> mParam)
	{
		String strStudentId = (String) mParam.get("studentid");
		String strCoursesId = (String) mParam.get("coursesid");
		Integer iScoreValue = (Integer) mParam.get("scorevalue");
		String strScoreExamDate = (String) mParam.get("scoreexamdate");
		
		Map <String, Object> mResult = new HashMap <String, Object> ();
		if(strStudentId == null)
			mResult.put("status", 0);
		else if(strCoursesId == null)
			mResult.put("status", 0);
		else if(iScoreValue == null)
			mResult.put("status", 0);
		else if(strScoreExamDate == null)
			mResult.put("status", 0);
		else
		{
			try
			{
				Integer iStudentId = Integer.valueOf(strStudentId);
				Integer iCoursesId = Integer.valueOf(strCoursesId);
				
				SimpleDateFormat sdfFormatter = new SimpleDateFormat("yyyy-MM-dd");
				Date dScoreExamDate = sdfFormatter.parse(strScoreExamDate);
				
				boolean bResult = m_smScoreModel.InsertScore(iStudentId, iCoursesId, iScoreValue, dScoreExamDate);
				if(!bResult)
					mResult.put("status", 0);
				else
					mResult.put("status", 1);
			}
			catch(Exception eException)
			{
				mResult.put("status", 0);
			}
		}
		
		return mResult;
	}
	
	@RequestMapping(value = "/editscore/{studentid}/{coursesid}", method = RequestMethod.GET)
	public String EditScore(ModelMap mmModelMap, @PathVariable("studentid") int iStudentId, @PathVariable("coursesid") int iCoursesId)
	{
		mmModelMap.addAttribute("studentid", iStudentId);
		mmModelMap.addAttribute("coursesid", iCoursesId);
		
		return "/EditScoreView";
	}
	
	@RequestMapping(value = "/editscoresvc/{studentid}/{coursesid}", method = RequestMethod.GET)
	public @ResponseBody Map <String, Object> EditScoreService(@PathVariable("studentid") int iStudentId, @PathVariable("coursesid") int iCoursesId)
	{
		Map <String, Object> mResult = new HashMap <String, Object> ();
		Score sScore = m_smScoreModel.GetScoreByIds(iStudentId, iCoursesId);
		if(sScore == null)
			mResult.put("status", 0);
		else
		{
			try
			{
				Student sStudent = sScore.getStudent();
				Courses cCourses = sScore.getCourses();
				
				SimpleDateFormat sdfFormatter = new SimpleDateFormat("yyyy-MM-dd");
				String strScoreExamDate = sdfFormatter.format(sScore.getScoreExamDate());
				
				Map <String, Object> mScore = new HashMap <String, Object> ();
				mScore.put("scorestudent", sStudent.getStudentName());
				mScore.put("scorecourses", cCourses.getCoursesName());
				mScore.put("scorevalue", sScore.getScoreValue());
				mScore.put("scoreexamdate", strScoreExamDate);
				
				mResult.put("status", 1);
				mResult.put("result", mScore);
			}
			catch(Exception eException)
			{
				mResult.put("status", 0);
			}
		}
		
		return mResult;
	}
	
	@RequestMapping(value = "/doeditscoresvc/{studentid}/{coursesid}", method = RequestMethod.POST)
	public @ResponseBody Map <String, Object> DoEditScoreService(@PathVariable("studentid") int iStudentId, @PathVariable("coursesid") int iCoursesId, @RequestBody Map <String, Object> mParam)
	{
		Integer iScoreValue = (Integer) mParam.get("scorevalue");
		String strScoreExamDate = (String) mParam.get("scoreexamdate");
		
		Map <String, Object> mResult = new HashMap <String, Object> ();
		if(iScoreValue == null)
			mResult.put("status", 0);
		else if(strScoreExamDate == null)
			mResult.put("status", 0);
		else
		{
			try
			{
				SimpleDateFormat sdfFormatter = new SimpleDateFormat("yyyy-MM-dd");
				Date dScoreExamDate = sdfFormatter.parse(strScoreExamDate);
				
				boolean bResult = m_smScoreModel.UpdateScore(iStudentId, iCoursesId, iScoreValue, dScoreExamDate);
				if(!bResult)
					mResult.put("status", 0);
				else
					mResult.put("status", 1);
			}
			catch(Exception eException)
			{
				mResult.put("status", 0);
			}
		}
		
		return mResult;
	}
	
	@RequestMapping(value = "/dodeletescoresvc/{studentid}/{coursesid}", method = RequestMethod.GET)
	public @ResponseBody Map <String, Object> DoDeleteScoreService(@PathVariable("studentid") int iStudentId, @PathVariable("coursesid") int iCoursesId)
	{
		Map <String, Object> mResult = new HashMap <String, Object> ();
		
		boolean bResult = m_smScoreModel.DeleteScore(iStudentId, iCoursesId);
		if(!bResult)
			mResult.put("status", 0);
		else
			mResult.put("status", 1);
		
		return mResult;
	}
	
	@RequestMapping(value = "/getstudentsvc", method = RequestMethod.GET)
	public @ResponseBody Map <String, Object> GetStudentService()
	{
		Map <String, Object> mResult = new HashMap <String, Object> ();
		mResult.put("status", 1);
		mResult.put("result", m_smScoreModel.ListStudent());
		
		return mResult;
	}
	
	@RequestMapping(value = "/getcoursessvc", method = RequestMethod.POST)
	public @ResponseBody Map <String, Object> GetCoursesService(@RequestBody Map <String, Object> mParam)
	{
		String strStudentId = (String) mParam.get("studentid");
		
		Map <String, Object> mResult = new HashMap <String, Object> ();
		if(strStudentId == null)
			mResult.put("status", 0);
		else
		{
			try
			{
				Integer iStudentId = Integer.valueOf(strStudentId);
				
				mResult.put("status", 1);
				mResult.put("result", m_smScoreModel.ListCourses(iStudentId));
			}
			catch(Exception eException)
			{
				mResult.put("status", 0);
			}
		}
		
		return mResult;
	}
}

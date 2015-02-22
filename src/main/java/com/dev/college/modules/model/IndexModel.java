package com.dev.college.modules.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.dev.college.base.BaseModel;

public class IndexModel extends BaseModel
{
	@SuppressWarnings("unchecked")
	public List <Map <String, Object>> ListStudent()
	{
		List <Map <String, Object>> lResult = new ArrayList <Map <String, Object>> ();
		Session sSession = GetSession();
		
		try
		{
			String strSql = "SELECT	student_id, " + 
							"		student_name, " + 
							"		student_number " + 
							"FROM	student ";
			
			Query qQuery = sSession.createSQLQuery(strSql);
			qQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			
			lResult = qQuery.list();
		}
		catch(HibernateException heException)
		{
			System.out.println("Error : " + heException.getMessage());
		}
		finally
		{
			sSession.close();
		}
		
		return lResult;
	}
	
	@SuppressWarnings("unchecked")
	public List <Map <String, Object>> ListCourses()
	{
		List <Map <String, Object>> lResult = new ArrayList <Map <String, Object>> ();
		Session sSession = GetSession();
		
		try
		{
			String strSql = "SELECT	courses_id, " + 
							"		courses_credit, " + 
							"		courses_name " + 
							"FROM	courses ";
			
			Query qQuery = sSession.createSQLQuery(strSql);
			qQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			
			lResult = qQuery.list();
		}
		catch(HibernateException heException)
		{
			System.out.println("Error : " + heException.getMessage());
		}
		finally
		{
			sSession.close();
		}
		
		return lResult;
	}
	
	@SuppressWarnings("unchecked")
	public List <Map <String, Object>> ListScore()
	{
		List <Map <String, Object>> lResult = new ArrayList <Map <String, Object>> ();
		Session sSession = GetSession();
		
		try
		{
			String strSql = "SELECT	score.student_id, " + 
							"		score.courses_id, " + 
							"		score.score_value, " + 
							"		CONVERT(VARCHAR(10), score.score_exam_date, 103) AS score_exam_date, " + 
							"		student.student_name, " + 
							"		courses.courses_name " + 
							"FROM 	score INNER JOIN " + 
							"		student ON score.student_id = student.student_id INNER JOIN " + 
							"		courses ON score.courses_id = courses.courses_id ";
			
			Query qQuery = sSession.createSQLQuery(strSql);
			qQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			
			lResult = qQuery.list();
		}
		catch(HibernateException heException)
		{
			System.out.println("Error : " + heException.getMessage());
		}
		finally
		{
			sSession.close();
		}
		
		return lResult;
	}
}

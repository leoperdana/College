package com.dev.college.modules.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dev.college.base.BaseModel;
import com.dev.college.skeleton.Courses;
import com.dev.college.skeleton.Score;
import com.dev.college.skeleton.Student;

public class ScoreModel extends BaseModel
{
	public boolean InsertScore(int iStudentId, int iCoursesId, int iScoreValue, Date dScoreExamDate)
	{
		boolean bResult;
		Session sSession = GetSession();
		Transaction tTransaction = sSession.beginTransaction();
		
		try
		{
			Student sStudent = (Student) sSession.load(Student.class, iStudentId);
			Courses cCourses = (Courses) sSession.load(Courses.class, iCoursesId);
			
			Score sScore = new Score();
			sScore.setStudent(sStudent);
			sScore.setCourses(cCourses);
			sScore.setScoreValue(iScoreValue);
			sScore.setScoreExamDate(dScoreExamDate);
			
			sSession.save(sScore);
			tTransaction.commit();
			
			bResult = true;
		}
		catch(HibernateException heException)
		{
			System.out.println("Error : " + heException.getMessage());
			tTransaction.rollback();
			
			bResult = false;
		}
		finally
		{
			sSession.close();
		}
		
		return bResult;
	}
	
	public boolean UpdateScore(int iStudentId, int iCoursesId, int iScoreValue, Date dScoreExamDate)
	{
		boolean bResult;
		Session sSession = GetSession();
		Transaction tTransaction = sSession.beginTransaction();
		
		try
		{
			Student sStudent = (Student) sSession.load(Student.class, iStudentId);
			Courses cCourses = (Courses) sSession.load(Courses.class, iCoursesId);
			
			Score sScoreKey = new Score();
			sScoreKey.setStudent(sStudent);
			sScoreKey.setCourses(cCourses);			
			
			Score sScore = (Score) sSession.load(Score.class, sScoreKey);
			sScore.setScoreValue(iScoreValue);
			sScore.setScoreExamDate(dScoreExamDate);
			
			sSession.update(sScore);
			tTransaction.commit();
			
			bResult = true;
		}
		catch(HibernateException heException)
		{
			System.out.println("Error : " + heException.getMessage());
			tTransaction.rollback();
			
			bResult = false;
		}
		finally
		{
			sSession.close();
		}
		
		return bResult;
	}
	
	public boolean DeleteScore(int iStudentId, int iCoursesId)
	{
		boolean bResult;
		Session sSession = GetSession();
		Transaction tTransaction = sSession.beginTransaction();
		
		try
		{
			Student sStudent = (Student) sSession.load(Student.class, iStudentId);
			Courses cCourses = (Courses) sSession.load(Courses.class, iCoursesId);
			
			Score sScoreKey = new Score();
			sScoreKey.setStudent(sStudent);
			sScoreKey.setCourses(cCourses);			
			
			Score sScore = (Score) sSession.load(Score.class, sScoreKey);
			
			sSession.delete(sScore);
			tTransaction.commit();
			
			bResult = true;
		}
		catch(HibernateException heException)
		{
			System.out.println("Error : " + heException.getMessage());
			tTransaction.rollback();
			
			bResult = false;
		}
		finally
		{
			sSession.close();
		}
		
		return bResult;
	}
	
	public Score GetScoreByIds(int iStudentId, int iCoursesId)
	{
		Session sSession = GetSession();
		
		try
		{
			Student sStudent = (Student) sSession.get(Student.class, iStudentId);
			Courses cCourses = (Courses) sSession.get(Courses.class, iCoursesId);
			
			Score sScoreKey = new Score();
			sScoreKey.setStudent(sStudent);
			sScoreKey.setCourses(cCourses);			
			
			return (Score) sSession.get(Score.class, sScoreKey);
		}
		finally
		{
			sSession.close();
		}
	}
	
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
	public List <Map <String, Object>> ListCourses(int iStudentId)
	{
		List <Map <String, Object>> lResult = new ArrayList <Map <String, Object>> ();
		Session sSession = GetSession();
		
		try
		{
			String strSql = "SELECT	courses_id, " + 
							"		courses_name " + 
							"FROM 	courses " + 
							"WHERE	courses_id NOT IN " + 
							"		( " + 
							"			SELECT	courses_id " + 
							"			FROM	score " + 
							"			WHERE	student_id = ? " + 
							"		) ";
			
			Query qQuery = sSession.createSQLQuery(strSql);
			qQuery.setInteger(0, iStudentId);
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

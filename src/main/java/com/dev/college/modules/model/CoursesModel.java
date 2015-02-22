package com.dev.college.modules.model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dev.college.base.BaseModel;
import com.dev.college.skeleton.Courses;

public class CoursesModel extends BaseModel
{
	public boolean InsertCourses(String strCoursesName, int iCoursesCredit)
	{
		boolean bResult;
		Session sSession = GetSession();
		Transaction tTransaction = sSession.beginTransaction();
		
		try
		{
			Courses cCourses = new Courses();
			cCourses.setCoursesName(strCoursesName);
			cCourses.setCoursesCredit(iCoursesCredit);
			
			sSession.save(cCourses);
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
	
	public boolean UpdateCourses(int iCoursesId, String strCoursesName, int iCoursesCredit)
	{
		boolean bResult;
		Session sSession = GetSession();
		Transaction tTransaction = sSession.beginTransaction();
		
		try
		{
			Courses cCourses = (Courses) sSession.load(Courses.class, iCoursesId);
			cCourses.setCoursesName(strCoursesName);
			cCourses.setCoursesCredit(iCoursesCredit);
			
			sSession.update(cCourses);
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
	
	public boolean DeleteCourses(int iCoursesId)
	{
		boolean bResult;
		Session sSession = GetSession();
		Transaction tTransaction = sSession.beginTransaction();
		
		try
		{
			Courses cCourses = (Courses) sSession.load(Courses.class, iCoursesId);
			
			sSession.delete(cCourses);
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
	
	public Courses GetCoursesById(int iCoursesId)
	{
		Session sSession = GetSession();
		
		try
		{
			return (Courses) sSession.get(Courses.class, iCoursesId);
		}
		finally
		{
			sSession.close();
		}
	}
}

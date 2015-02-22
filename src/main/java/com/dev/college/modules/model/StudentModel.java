package com.dev.college.modules.model;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dev.college.base.BaseModel;
import com.dev.college.skeleton.Student;

public class StudentModel extends BaseModel
{
	public boolean InsertStudent(String strStudentNumber, String strStudentName)
	{
		boolean bResult;
		Session sSession = GetSession();
		Transaction tTransaction = sSession.beginTransaction();
		
		try
		{
			Student sStudent = new Student();
			sStudent.setStudentNumber(strStudentNumber);
			sStudent.setStudentName(strStudentName);
			
			sSession.save(sStudent);
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
	
	public boolean UpdateStudent(int iStudentId, String strStudentName)
	{
		boolean bResult;
		Session sSession = GetSession();
		Transaction tTransaction = sSession.beginTransaction();
		
		try
		{
			Student cStudent = (Student) sSession.load(Student.class, iStudentId);
			cStudent.setStudentName(strStudentName);
			
			sSession.update(cStudent);
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
	
	public boolean DeleteStudent(int iStudentId)
	{
		boolean bResult;
		Session sSession = GetSession();
		Transaction tTransaction = sSession.beginTransaction();
		
		try
		{
			Student sStudent = (Student) sSession.load(Student.class, iStudentId);
			
			sSession.delete(sStudent);
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
	
	public Student GetStudentById(int iStudentId)
	{
		Session sSession = GetSession();
		
		try
		{
			return (Student) sSession.get(Student.class, iStudentId);
		}
		finally
		{
			sSession.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public int CountStudentByNumber(String strStudentNumber)
	{
		int iCount;
		Session sSession = GetSession();
		
		try
		{
			String strSql = "SELECT	COUNT(student_id) AS count_student " + 
							"FROM 	student " + 
							"WHERE	student_number = ?";
			
			Query qQuery = sSession.createSQLQuery(strSql);
			qQuery.setString(0, strStudentNumber);
			qQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			
			List <Map <String, Integer>> lResult = qQuery.list();
			Map <String, Integer> mResult = lResult.get(0);
			iCount = mResult.get("count_student"); 
		}
		catch(HibernateException heException)
		{
			System.out.println("Error : " + heException.getMessage());
			
			iCount = -1;
		}
		finally
		{
			sSession.close();
		}
		
		return iCount;
	}
}

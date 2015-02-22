package com.dev.college.skeleton;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student
{
	@Id
	@GeneratedValue
	@Column(name = "student_id", nullable = false, unique = true)
	private int m_iStudentId;
	
	@Column(name = "student_number", nullable = false, unique = true, columnDefinition = "varchar(10)")
	private String m_strStudentNumber;
	
	@Column(name = "student_name", nullable = false)
	private String m_strStudentName;
	
	@OneToMany(targetEntity = Score.class, mappedBy = "m_sStudent", cascade = CascadeType.ALL)
	private Set <Score> m_sScore;

	public int getStudentId()
	{
		return m_iStudentId;
	}
	
	public void setStudentId(int iStudentId)
	{
		m_iStudentId = iStudentId;
	}
	
	public String getStudentNumber()
	{
		return m_strStudentNumber;
	}
	
	public void setStudentNumber(String strStudentNumber)
	{
		m_strStudentNumber = strStudentNumber;
	}
	
	public String getStudentName()
	{
		return m_strStudentName;
	}
	
	public void setStudentName(String strStudentName)
	{
		m_strStudentName = strStudentName;
	}

	public Set <Score> getScore()
	{
		return m_sScore;
	}

	public void setScore(Set <Score> sScore)
	{
		this.m_sScore = sScore;
	}
}

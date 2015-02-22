package com.dev.college.skeleton;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "score")
public class Score implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="student_id", nullable = false)
	private Student m_sStudent;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="courses_id", nullable = false)
	private Courses m_cCourses;
	
	@Column(name = "score_value", nullable = false)
	private int m_iScoreValue;
	
	@Column(name = "score_exam_date", nullable = false)
	private Date m_dScoreExamDate;
	
	public Student getStudent()
	{
		return m_sStudent;
	}
	
	public void setStudent(Student sStudent)
	{
		m_sStudent = sStudent;
	}
	
	public Courses getCourses()
	{
		return m_cCourses;
	}
	
	public void setCourses(Courses cCourses)
	{
		m_cCourses = cCourses;
	}
	
	public int getScoreValue()
	{
		return m_iScoreValue;
	}
	
	public void setScoreValue(int iScoreValue)
	{
		m_iScoreValue = iScoreValue;
	}
	
	public Date getScoreExamDate()
	{
		return m_dScoreExamDate;
	}
	
	public void setScoreExamDate(Date dScoreExamDate)
	{
		m_dScoreExamDate = dScoreExamDate;
	}
}

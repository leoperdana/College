
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
@Table(name = "courses")
public class Courses
{
	@Id
	@GeneratedValue
	@Column(name = "courses_id", nullable = false, unique = true)
	private int m_iCoursesId;
	
	@Column(name = "courses_name", nullable = false)
	private String m_strCoursesName;
	
	@Column(name = "courses_credit", nullable = false)
	private int m_iCoursesCredit;

	@OneToMany(targetEntity = Score.class, mappedBy = "m_cCourses", cascade = CascadeType.ALL)
	private Set <Score> m_sScore;
	
	public int getCoursesId()
	{
		return m_iCoursesId;
	}

	public void setCoursesId(int iCoursesId)
	{
		m_iCoursesId = iCoursesId;
	}

	public String getCoursesName()
	{
		return m_strCoursesName;
	}

	public void setCoursesName(String strCoursesName)
	{
		m_strCoursesName = strCoursesName;
	}

	public int getCoursesCredit()
	{
		return m_iCoursesCredit;
	}

	public void setCoursesCredit(int iCoursesCredit)
	{
		m_iCoursesCredit = iCoursesCredit;
	}
	
	public Set <Score> getScore()
	{
		return m_sScore;
	}

	public void setScore(Set <Score> sScore)
	{
		m_sScore = sScore;
	}
}

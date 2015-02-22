package com.dev.college.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class BaseModel
{
	protected static SessionFactory m_sfSessionFactory;
	
	protected BaseModel()
	{
		if(m_sfSessionFactory != null)
			return;
			
		Configuration cConfiguration = new Configuration();
		cConfiguration.configure("com/dev/college/configuration/hibernate.config.xml");
		
		StandardServiceRegistryBuilder ssrbStandardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
		ssrbStandardServiceRegistryBuilder = ssrbStandardServiceRegistryBuilder.applySettings(cConfiguration.getProperties());
		
		ServiceRegistry srServiceRegistry = ssrbStandardServiceRegistryBuilder.build();
		m_sfSessionFactory = cConfiguration.buildSessionFactory(srServiceRegistry);
	}
	
	protected Session GetSession()
	{
		return m_sfSessionFactory.openSession();
	}
}

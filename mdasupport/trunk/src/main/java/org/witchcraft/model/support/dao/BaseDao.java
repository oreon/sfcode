package org.witchcraft.model.support.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;
import org.witchcraft.model.support.BusinessEntity;
import org.witchcraft.model.support.audit.EntityAuditLogInterceptor;

@Repository
public class BaseDao<T> implements GenericDAO<T> {

	private Class<T> persistentClass;
	// private Session session;

	protected EntityManager entityManager;
	
	protected Interceptor entityAuditLogInterceptor;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		Session session = (Session) entityManager.getDelegate();
		session = session.getSessionFactory().openSession(entityAuditLogInterceptor);
	}

	@SuppressWarnings("unchecked")
	public BaseDao() {

		try {
			Type superclass = ((Class) getClass().getGenericSuperclass())
					.getGenericSuperclass();

			this.persistentClass = (Class<T>) ((ParameterizedType) superclass)
					.getActualTypeArguments()[0];
		} catch (ClassCastException cce) {
			this.persistentClass = (Class<T>) ((ParameterizedType) ((Class) getClass())
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	/**
	 * This method saves or updates the given entity based upon whether the id
	 * is null
	 */
	public T save(T entity) {

		/*
		 * FIXME : This code will make if (!entityManager.contains(entity))
		 * entityManager.persist(entity); else entityManager.merge(entity);
		 */
		
		//Session session = (Session) entityManager.getDelegate();
		//session = session.getSessionFactory().openSession(interceptor);

		BusinessEntity be = (BusinessEntity) entity;

		if (be.getId() != null){
			entity = entityManager.merge(entity);
			entityAuditLogInterceptor.onFlushDirty(entity, "TESTUSER",  null, null , null, null );
		}
		else{
			entityManager.persist(entity);
			//entityAuditLogInterceptor.onSave(entity, "TESTUSER",  null, null , null );
		}
		
		return entity;
	}

	public T load(Long id) {
		return entityManager.find(getPersistentClass(), id);
	}

	@SuppressWarnings("unchecked")
	public List<T> loadAll() {
		String qryString = "select e from "
				+ getPersistentClass().getSimpleName() + "  e ";
		Query query = entityManager.createQuery(qryString);
	
		return query.getResultList();
	}
	
	public long getCount(){
		return getCount(null, null);
	}

	public void delete(T entity) {
		entityManager.remove(entity);
	}

	private String excludedProperties[] = { "dateModified", "dateCreated", "id" };

	@SuppressWarnings("unchecked")
	public List<T> searchByExample(T exampleInstance) {

		Session session = (Session) entityManager.getDelegate();
		
		Example example = Example.create(exampleInstance).enableLike(
				MatchMode.START).ignoreCase().excludeZeroes();

		Criteria criteria = session.createCriteria(getPersistentClass()).add(
				example);

		for (String exclude : excludedProperties) {
			example.excludeProperty(exclude);
		}

		List list = criteria.list();

		return list;
	}

	public Interceptor getEntityAuditLogInterceptor() {
		return entityAuditLogInterceptor;
	}

	public void setEntityAuditLogInterceptor(Interceptor entityAuditLogInterceptor) {
		this.entityAuditLogInterceptor = entityAuditLogInterceptor;
	}

	public long getCount(Date fromDate, Date toDate) {
		String qryString = "select count(e) from "
			+ getPersistentClass().getSimpleName() + " e ";
		if (fromDate != null || toDate != null){
			qryString += " WHERE ";
			if(fromDate != null)
				qryString += " e.dateCreated >= '" + fromDate + "'";
			
			if(fromDate != null && toDate != null)
				qryString += " AND ";
			
			if(toDate != null)
				qryString += " e.dateCreated <= '" + toDate + "'";
		}
		
		Object result = entityManager.createQuery(qryString).getSingleResult();
		 
		return ((Long)entityManager.createQuery(qryString).getSingleResult());

	}
}

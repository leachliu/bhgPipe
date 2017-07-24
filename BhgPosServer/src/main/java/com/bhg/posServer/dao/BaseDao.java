package com.bhg.posServer.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class BaseDao<T> {

	@PersistenceContext
	protected EntityManager entityManager;

	private Class<T> entityClass;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseDao() {
		this.entityClass = null;
		Class c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}

	public void create(T t) {
		entityManager.persist(t);
	}

	public T get(long id) {
		return entityManager.find(entityClass, id);
	}

	public T get(String id) {
		return entityManager.find(entityClass, id);
	}

	public void update(T t) {
		entityManager.merge(t);
	}

	public Query buildQuery(String hsql, Map<String, Object> parameters) {
		Query query = entityManager.createQuery(hsql);
		for (String name : parameters.keySet()) {
			query.setParameter(name, parameters.get(name));
		}
		return query;
	}

	public Query buildNativeQuery(String hsql, Map<String, Object> parameters) {
		Query query = entityManager.createNativeQuery(hsql);
		for (String name : parameters.keySet()) {
			query.setParameter(name, parameters.get(name));
		}
		return query;
	}
}

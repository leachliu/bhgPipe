package com.bhg.pipeServer.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.bhg.pipeServer.exception.PipeException;
import com.bhg.pipeServer.zookeeper.ProgrammaticallyConfigYard;
import com.google.gson.Gson;

public abstract class ServiceBase<T extends IPipeEntity> {
	private static final String ZOOKEEPER_CLUSTER = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

	/**
	 * 数据文件
	 * 
	 * @return
	 */
	abstract String serviceKey();

	/**
	 * 实体类型
	 * 
	 * @return
	 */
	abstract Type getEntityType();

	Map<String, T> _datas;

	public Map<String, T> all() throws PipeException {
		return _datas;
	}

	public Set<String> ids() throws PipeException {
		return _datas.keySet();
	}

	public T add(T entity) throws PipeException {
		String id = generateId();
		System.out.println("new id: " + id);
		entity.setId(id);
		synchronized (this) {
			_datas.put(id, entity);
			save(_datas);
			_datas = load();
		}
		return get(id);
	}

	public T get(String id) throws PipeException {
		return _datas.get(id);
	}

	public T update(String id, T entity) throws PipeException {
		entity.setId(id);
		_datas.put(id, entity);
		save(_datas);
		return entity;
	}

	public void delete(String id) throws PipeException {
		synchronized (this) {
			_datas.remove(id);
			save(_datas);
			_datas = load();
		}
	}

	ProgrammaticallyConfigYard yard;

	@PostConstruct
	private void init() {
		yard = new ProgrammaticallyConfigYard(ZOOKEEPER_CLUSTER, null);

		String json = yard.get(this.serviceKey());
		if (null == json) {
			yard.add(this.serviceKey(), new Gson().toJson(new ArrayList()));
		}
		// 加载数据
		_datas = load();
	}

	private Map<String, T> load() throws PipeException {
		String json = yard.get(this.serviceKey());
		Gson gson = new Gson();
		Collection<T> list = gson.fromJson(json, collectionType);
		Map<String, T> map = new HashMap<String, T>();
		for (T entity : list) {
			map.put(entity.getId(), entity);
		}
		return map;
	}

	private void save(Map<String, T> datas) throws PipeException {
		String json = new Gson().toJson(datas.values());
		yard.update(this.serviceKey(), json);
	}

	private String generateId() {
		int maxId = 0;
		if (!_datas.isEmpty()) {
			String[] idsArray = new String[_datas.keySet().size()];
			_datas.keySet().toArray(idsArray);
			List<String> ids = java.util.Arrays.asList(idsArray);
			for (String id : ids) {
				int next = Integer.parseInt(id);
				maxId = next > maxId ? next : maxId;
			}
		}
		return maxId + 1 + "";
	}

	Type collectionType = new ParameterizedType() {
		@Override
		public Type[] getActualTypeArguments() {
			return new Type[] { getEntityType() };
		}

		@Override
		public Type getOwnerType() {
			return null;
		}

		@Override
		public Type getRawType() {
			return Collection.class;
		}
	};
}

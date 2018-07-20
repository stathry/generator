/*
 * ${copyright}
 */
package ${pkg}.dao.impl;

import ${pkg}.context.ApplicationContextUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import ${pkg}.dao.GenericDAO;


/**
 * 通用数据访问实现
 * @author ${author}
 * @date ${generateTime}
 */

public class GenericDAOImpl<T, ID extends Serializable> extends SqlSessionDaoSupport implements GenericDAO<T, ID> {

	protected String namespace;
	protected GenericDAO<T, ID> mapper;
	
	public GenericDAOImpl() {
		Class<?> interfaceClass = this.getClass().getInterfaces()[0];
		this.namespace = interfaceClass.getName();
		this.setSqlSessionFactory(ApplicationContextUtils.getBean(SqlSessionFactory.class));
		mapper = (GenericDAO<T, ID>) getSqlSession().getMapper(interfaceClass);
	}
	
	@Override
	public String wrapSQLID(String sqlID) {
		return getNamespace() + "." + sqlID;
	}

	@Override
	public String getNamespace() {
		return namespace;
	}

	@Override
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@Override
	public int insert(T t) {
		return mapper.insert(t);
	}

    @Override
    public int batchInsert(List<T> list) {
        return mapper.batchInsert(list);
    }

    @Override
	public int deleteById(ID id) {
		return mapper.deleteById(id);
	}

	@Override
	public int update(T t) {
		return mapper.update(t);
	}

	@Override
	public int updateList(List<T> list) {
		return mapper.updateList(list);
	}

	@Override
	public T queryById(ID id) {
		return (T) mapper.queryById(id);
	}

	@Override
	public List<T> queryAll() {
		 List<T> list = mapper.queryAll();
		 if(list == null) {
			 list = Collections.emptyList();
		 }
		 return list;
	}

	@Override
	public List<T> queryByCond(T t) {
		return mapper.queryByCond(t);
	}

	@Override
	public int count() {
		return mapper.count();
	}

	@Override
	public int countByCond(T t) {
		return mapper.countByCond(t);
	}

	@Override
	public GenericDAO<T, ID> getMapper() {
		return mapper;
	}

    @Override
    public <T1> T1 getMapper(Class<T1> clazz) {
        return getSqlSession().getMapper(clazz);
    }

    @Override
	public int updateStateById(T t) {
		return mapper.updateStateById(t);
	}

}

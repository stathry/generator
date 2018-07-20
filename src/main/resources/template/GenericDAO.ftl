/*
 * ${copyright}
 */
package ${pkg}.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 通用数据访问接口
 * @author ${author}
 * @date ${generateTime}
 */
public interface GenericDAO<T, ID extends Serializable> {

	/** 
	 * 包装SQLID
	 * @param SQLID
	 * @return SQLID
	 */
	public String wrapSQLID(String SQLID);
	
	/**
	 * 获取mapper操作对象
	 * @return mapper
	 */
	public GenericDAO<T, ID> getMapper();

	/**
	 * 根据DAOClass获取mapper操作对象
	 * @return mapper
	 */
    public <T> T getMapper(Class<T> clazz);
	
	/**
	 * 获取命名空间
	 * @return namespace
	 */
	public String getNamespace();
	
	/**
	 * 设置命名空间
	 */
	public void setNamespace(String namespace);

	/**
	 * 插入对象
	 * @param t 对象
	 * @return 影响记录数(返回ID置于T对象中)
	 */
	public int insert(T t);

    /**
     * 批量插入
     * @param list
     * @return
     */
	public int batchInsert(List<T> list);

	/**
	 * 根据ID删除对象
	 * @param id 对象唯一编码
	 * @return 影响记录数
	 */
	public int deleteById(ID id);

	/**
	 * 更新对象
	 * @param t 对象
	 * @return 影响记录数
	 */
	public int update(T t);
	/**
	 * 根据id更新状态
	 * @param t 对象
	 * @return 影响记录数
	 */
	public int updateStateById(T t);
	
	/**
	 * 更新对象列表
	 * @param list 对象列表
	 * @return 影响记录数
	 */
	public int updateList(List<T> list);

	/**
	 * 根据ID查询对象
	 * @param id 对象唯一编码
	 * @return 对象
	 */
	public T queryById(ID id);

	/**
	 * 查询所有对象列表
	 * @return 所有对象列表
	 */
	public List<T> queryAll();

	/**
	 * 分页查询  可参考 http://my.oschina.net/miemiedev/blog/135516
	 * @param page 分页对象(继承RowBounds,并支持排序,统计)
	 * @return 对象列表
	 */
//	public List<T> queryByPage(PageBounds page);

	/**
	 * 根据条件统计记录数
	 * @param t 条件对象
	 * @return 对象列表
	 */
	public List<T> queryByCond(T t);
	
	/**
	 * 根据条件分页查询对象列表
	 * @param t 对象
	 * @param page 分页对象(继承RowBounds,并支持排序,统计)
	 * @return
	 */
//	public List<T> queryByCondPage(T t, PageBounds page);
	
	/**
	 * 统计记录数
	 * @return 记录数
	 */
	public int count();

	/**
	 * 根据条件统计记录数
	 * @param t 条件对象
	 * @return 记录数
	 */
	public int countByCond(T t);
}

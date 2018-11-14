package com.github.hjx601496320.simpledao;

import com.github.hjx601496320.simpledao.jdbc.FunctionRowMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import com.github.hjx601496320.simpledao.jdbc.EntityMapperFactory;
import com.github.hjx601496320.simpledao.jdbc.EntityTableRowMapper;
import com.github.hjx601496320.simpledao.maker.Where;
import com.github.hjx601496320.simpledao.maker.Function;
import com.github.hjx601496320.simpledao.maker.delete.Delete;
import com.github.hjx601496320.simpledao.maker.insert.Insert;
import com.github.hjx601496320.simpledao.maker.query.DefaultQuery;
import com.github.hjx601496320.simpledao.maker.query.Query;
import com.github.hjx601496320.simpledao.maker.update.Update;

import java.util.Arrays;
import java.util.List;

/**
 * dao 的 基类
 *
 * @author hjx
 */
public abstract class AbstractDao {

    protected JdbcTemplate jdbcTemplate;

    protected Log logger = LogFactory.getLog(AbstractDao.class);

    /**
     * 添加一条数据
     *
     * @param insert
     * @return
     */
    final public Integer insertBy(final Insert insert) {
        String sql = insert.toSql();
        Object[] sqlValues = insert.getSqlValues();
        if (logger.isDebugEnabled()) {
            logger.debug(sql);
            logger.debug(Arrays.toString(sqlValues));
        }
        return jdbcTemplate.update(sql, sqlValues);
    }

    /**
     * 根据条件删除数据
     *
     * @param delete
     * @return
     */
    final public Integer deleteBy(final Delete delete) {
        String sql = delete.toSql();
        Object[] sqlValues = delete.getSqlValues();
        if (logger.isDebugEnabled()) {
            logger.debug(sql);
            logger.debug(Arrays.toString(sqlValues));
        }
        return jdbcTemplate.update(sql, sqlValues);
    }

    /**
     * 根据条件更新
     *
     * @param update
     * @return
     */
    final public Integer updateBy(final Update update) {
        String sql = update.toSql();
        Object[] sqlValues = update.getSqlValues();
        if (logger.isDebugEnabled()) {
            logger.debug(sql);
            logger.debug(Arrays.toString(sqlValues));
        }
        return jdbcTemplate.update(sql, sqlValues);
    }

    /**
     * 根据条件查询
     *
     * @param query
     * @return
     */
    final public List selectBy(final Query query) {
        EntityTableRowMapper mapper = EntityMapperFactory.getMapper(query.getEntity());
        String sql = query.toSql();
        Object[] sqlValues = query.getSqlValues();
        if (logger.isDebugEnabled()) {
            logger.debug(sql);
            logger.debug(Arrays.toString(sqlValues));
        }
        return jdbcTemplate.query(sql, sqlValues, mapper);

    }

    /**
     * 执行一条函数
     *
     * @param clz
     * @param function
     * @param wheres
     * @param <T>
     * @return
     */
    final public <T> List<T> function(final Class clz, final Function<T> function, final List<Where> wheres) {
        Query query = new DefaultQuery();
        query.target(clz);
        query.addSelection(false, function.getSql());
        for (Where where : wheres) {
            query.where(where);
        }
        String sql = query.toSql();
        Object[] sqlValues = query.getSqlValues();
        if (logger.isDebugEnabled()) {
            logger.debug(sql);
            logger.debug(Arrays.toString(sqlValues));
        }
        return jdbcTemplate.query(sql, new FunctionRowMapper(function), sqlValues);
    }

    /* Getter and Setter */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}

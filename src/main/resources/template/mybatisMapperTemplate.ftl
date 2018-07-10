<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${pkg}.dao.${clzz}DAO">
    <resultMap id="BaseResultMap" type="${clzz}">
     <#list fields as field>
        <#if field.name!='id'><result column="${field.column}" jdbcType="${field.jdbcType}" property="${field.name}" /><#else><id column="id" jdbcType="${field.jdbcType}" property="id"/></#if>
    </#list>
    </resultMap>

    <sql id="Base_Column_List">
      <#list fields as field><#if field_index!=0>, ${field.column}<#else>${field.column}</#if></#list>
    </sql>

    <select id="queryById" parameterType="${idType}" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from ${table}
        where id = <#if true>#</#if>{id, jdbcType=${idJdbcType}}
    </select>

    <delete id="deleteById" parameterType="${idType}">
        delete from ${table}
        where id = <#if true>#</#if>{id, jdbcType=${idJdbcType}}
    </delete>

    <insert id="insert" useGeneratedKeys="true" parameterType="${clzz}" keyProperty="id" keyColumn="id">
        insert into ${table} (<#list insertFields as field><#if field_index==0>${field.column}<#else>, ${field.column}</#if></#list>)
        values ( <#list insertFields as field><#if field_index==0><#if true>#</#if>{${field.name}, jdbcType=${field.jdbcType}}<#else>, <#if true>#</#if>{${field.name}, jdbcType=${field.jdbcType}}</#if></#list> )
    </insert>

    <update id="updateById" parameterType="${idType}">
        update ${table}
        set <#list insertFields as field><#if field_index==0>${field.column} = <#if true>#</#if>{${field.name}, jdbcType=${field.jdbcType}}<#else>, ${field.column} = <#if true>#</#if>{${field.name}, jdbcType=${field.jdbcType}}</#if></#list>
        where id = <#if true>#</#if>{id, jdbcType=${idJdbcType}}
    </update>

</mapper>
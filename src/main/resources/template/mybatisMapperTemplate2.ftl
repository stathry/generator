<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${pkg}.dao.${clzz}DAO">
    <resultMap id="BaseResultMap" type="${clzz}">
        <id column="id" jdbcType="BIGINT" property="id" />
          <#list fields as field>
        <result column="${field.column}" jdbcType="${field.type}" property="${field.name}" />
          </#list>
    </resultMap>

    <sql id="Base_Column_List">
          <#list fields as field>
              ${field.column},
          </#list>
    </sql>

    <select id="queryById" parameterType="long" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from ${table}
        where id = '#'{id, jdbcType=BIGINT}
    </select>

    <delete id="deleteById" parameterType="long">
        delete from ${table}
        where id = '#'{id,jdbcType=BIGINT}
    </delete>

</mapper>
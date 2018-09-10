<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.geely.devops.application.dao.extension.ApplicationMenuDomainExtensionMapper">
    <!-- 扩展自定义的SQl语句写在此文件中 -->
    <resultMap id="MenuResultMap" type="com.geely.devops.application.sdk.dto.MenuDto">
        <id column="id" property="id" jdbcType="INTEGER"/>
        <result column="name" property="title" jdbcType="VARCHAR"/>
        <result column="routeName" property="name" jdbcType="VARCHAR"/>
        <result column="childRoutes" property="child" jdbcType="VARCHAR"/>
        <result column="isLeaf" property="isLeaf" jdbcType="CHAR"/>
        <result column="parentId" property="parentId" jdbcType="INTEGER"/>
        <result column="icon" property="icon" jdbcType="VARCHAR"/>
        <!--此处递归需传参，在colum中体现-->
        <collection property="children" ofType="com.geely.devops.application.sdk.dto.MenuDto"
                    javaType="java.util.ArrayList" column="{id=id,userId=userId,appId=appId}" select="findMenuByParentId"/>
    </resultMap>

    <select id="selectMenusByUser" resultMap="MenuResultMap">
        SELECT *,#{userId} userId , #{appId} appId  from application_menu m WHERE 1 = 1
        and (m.parentId ='' or m.parentId is NULL)
        and m.id in ( select DISTINCT menuId FROM rel_application_user u left join rel_role_menu r on u.role = r.roleId
        WHERE u.userId = #{userId,jdbcType=INTEGER}
        <if test="appId != null">
            and u.appId = #{appId,jdbcType=VARCHAR}
        </if>
        )
        ORDER BY m.id ASC
    </select>



    <select id="findMenuByParentId" resultMap="MenuResultMap" parameterType="java.util.Map">
        SELECT *,#{userId} userId , #{appId} appId  FROM application_menu m WHERE m.parentId = #{id}
        and m.id in ( select DISTINCT menuId FROM rel_application_user u left join rel_role_menu r on u.role = r.roleId
        WHERE u.userId = #{userId,jdbcType=INTEGER}
        <if test="appId != null">
            and u.appId = #{appId,jdbcType=VARCHAR}
        </if>
        )
        ORDER BY m.id ASC
    </select>

</mapper>
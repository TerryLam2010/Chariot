package cn.terrylam.chariot.base.entity.system;

import java.io.Serializable;
import java.util.Date;

import cn.terrylam.framework.util.SpringCtxUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.terrylam.chariot.base.dao.system.ResourceDao;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
@Table(name="tb_role")
public class Role implements Serializable {

	/**
	 * 角色id
	 */
	@Id
	@KeySql(useGeneratedKeys = true, order = ORDER.AFTER)
	private Long id;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 角色key
	 */
	private String roleKey;

	/**
	 * 角色状态,0：正常；1：删除
	 */
	private Integer status;

	/**
	 * 角色描述
	 */
	private String description;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	@Transient
	private java.util.Set<Resource> resources;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@JsonIgnore
	public java.util.Set<Resource> getResources() {
		return SpringCtxUtils.getBean(ResourceDao.class).getAllResourceByRoleId(id);
	}

	public void setResources(java.util.Set<Resource> resources) {
		this.resources = resources;
	}

}

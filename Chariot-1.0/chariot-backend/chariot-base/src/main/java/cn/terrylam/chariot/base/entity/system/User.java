package cn.terrylam.chariot.base.entity.system;

import java.io.Serializable;
import java.util.Date;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import cn.terrylam.framework.util.SpringCtxUtils;
import cn.terrylam.chariot.base.dao.system.RoleDao;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <p>
 * 用户账户表
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
@Table(name="tb_user")
public class User implements Serializable {

	/**
	 * 用户id
	 */
	@Id
	@KeySql(useGeneratedKeys = true, order = ORDER.AFTER)
	private Long id;

	/**
	 * 账户名
	 */
	private String userName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 性别 0 女 1 男
	 */
	private Integer sex;

	/**
	 * 出生日期
	 */
	@JSONField(format = "yyyy-MM-dd")
	//@JsonSerialize(using= JsonDateSerializer.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	/**
	 * 电话
	 */
	private String telephone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 住址
	 */
	private String address;

	/**
	 * 逻辑删除状态 0 未删除 1 删除
	 */
	private Integer deleteStatus;

	/**
	 * 是否锁定
	 * 
	 * 0 未锁定 1 锁定
	 */
	private Integer locked;

	/**
	 * 用户描述
	 */
	private String description;

	/**
	 * 创建时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	@Transient
	private java.util.Set<Role> roles;

	@Transient
	private String rolesStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Integer deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Set<Role> getRoles() {
		roles =  SpringCtxUtils.getBean(RoleDao.class).getAllRoleByUserId(id);
		return roles;
	}

	public void setRoles(java.util.Set<Role> roles) {
		this.roles = roles;
	}

	public String getRolesStr() {
		return rolesStr;
	}

	public void setRolesStr(String rolesStr) {
		this.rolesStr = rolesStr;
	}
}

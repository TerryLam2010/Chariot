package cn.terrylam.chariot.base.entity.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


import cn.terrylam.chariot.base.dao.system.ResourceDao;
import cn.terrylam.framework.util.SpringCtxUtils;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
@Table(name="tb_resource")
public class Resource implements Serializable {

	/**
	 * 资源id
	 */
	@Id
	@KeySql(useGeneratedKeys = true, order = ORDER.AFTER)
	private Long id;

	/**
	 * 资源名称
	 */
	private String name;

	/**
	 * 资源唯一标识
	 */
	@Column(name = "source_key")
	private String sourceKey;

	/**
	 * 资源类型,0:目录;1:菜单;2:按钮
	 */
	private Integer type;

	/**
	 * 资源url
	 */
	private String sourceUrl;

	/**
	 * 层级
	 */
	private Integer level;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 是否隐藏
	 * 
	 * 0显示 1隐藏
	 */
	private Integer isHide;

	/**
	 * 描述
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

	/**
	 * 上级资源
	 */
	private Long parentId;

	@Transient
	private List<Resource> childResourses;

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

	public String getSourceKey() {
		return sourceKey;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@JsonIgnore
	public Resource getParent() {
		return SpringCtxUtils.getBean(ResourceDao.class).selectByPrimaryKey(parentId);
	}

	public List<Resource> getChildResourses() {
		try {
			if(SpringCtxUtils.getBean(ResourceDao.class)!=null){
				return SpringCtxUtils.getBean(ResourceDao.class).getResourceByParentId(id);
			}
		} catch (Exception e) {
		}
		return null;
	}

	public void setChildResourses(List<Resource> childResourses) {
		this.childResourses = childResourses;
	}
}

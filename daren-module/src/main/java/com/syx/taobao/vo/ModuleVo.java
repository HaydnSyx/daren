package com.syx.taobao.vo;

import java.util.Date;
import java.util.List;

import com.syx.taobao.model.authority.Func;
import com.syx.taobao.model.authority.Module;

public class ModuleVo {

	private int id;
	
	private Integer pId;
	
	private String name;
	
	private String url;
	
	private Date createTime;
	
	private List<Func> funcs;
	
	private List<ModuleVo> chlids;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Func> getFuncs() {
		return funcs;
	}

	public void setFuncs(List<Func> funcs) {
		this.funcs = funcs;
	}

	public List<ModuleVo> getChlids() {
		return chlids;
	}

	public void setChlids(List<ModuleVo> chlids) {
		this.chlids = chlids;
	}

	public ModuleVo() {
		super();
	}

	public ModuleVo(Module m, List<ModuleVo> chlids) {
		super();
		this.id = m.getId();
		this.pId = m.getpId();
		this.name = m.getName();
		this.url = m.getUrl();
		this.createTime = m.getCreateTime();
		this.funcs = m.getFuncs();
		this.chlids = chlids;
	}
	
	
}
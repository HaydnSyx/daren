package com.syx.taobao.service;

import java.util.List;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.MUser;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.vo.ResultVo;

public interface MUserService {

	public ResultVo login(String username, String password);

	public MUser queryMUser(int uid) throws BizException;

	public Pager<MUser> queryMUserPager(MUser user, int pageIndex, int pageSize) throws BizException;

	public void updateMUserStatus(int[] ids, String recStatus) throws BizException;

	public void addMUser(MUser user) throws BizException;

	public ResultVo editPwd(int id, String pwd, String newpwd) throws BizException;

	public void updateMUser(MUser user) throws BizException;

	public List<MUser> queryMUserListByRoleId(int roleId) throws BizException;
}
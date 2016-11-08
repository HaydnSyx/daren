package com.syx.taobao.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.dao.ImFriendGroupDao;
import com.syx.taobao.dao.ImFriendGroupMemDao;
import com.syx.taobao.dao.MUserDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.ImFriendGroup;
import com.syx.taobao.model.ImFriendGroupMem;
import com.syx.taobao.model.MUser;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.service.MUserService;
import com.syx.taobao.util.MD5;
import com.syx.taobao.util.StringUtil;
import com.syx.taobao.vo.ResultVo;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class MUserServiceImpl implements MUserService {
	private Logger logger = LoggerFactory.getLogger(MUserServiceImpl.class);
	@Autowired
	private MUserDao mUserDao;
	@Autowired
	private ImFriendGroupDao imFriendGroupDao;
	@Autowired
	private ImFriendGroupMemDao imFriendGroupMemDao;

	@Override
	@Transactional(readOnly = true)
	public ResultVo login(String username, String password) {
		try {
			MUser mUser = mUserDao.queryMUserByUsername(username);
			if (mUser != null) {
				if (mUser.getPassword().equals(password) && AppConstant.STATUS_AVAILABLE.equals(mUser.getRecStatus())) {
					// return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, ""
					// + mUser.getRole().getIndexUrl(), mUser.getId());
					return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, "" + "/index.htm", mUser.getId());
				} else if (mUser.getPassword().equals(password) && AppConstant.STATUS_UNAVAILABLE.equals(mUser.getRecStatus())) {
					return new ResultVo(AppConstant.RESULT_STATUS_ERROR, "您的账户已冻结，请联系管理员", null);
				}
			}
		} catch (DaoException e) {
			logger.error("muser login failure! username=[" + username + "]", e);
		}
		return new ResultVo(AppConstant.RESULT_STATUS_ERROR, "用户名或密码错误", null);
	}

	@Override
	@Transactional(readOnly = true)
	public MUser queryMUser(int uid) throws BizException {
		try {
			return mUserDao.queryMUser(uid);
		} catch (DaoException e) {
			logger.error("query MUser failure! uid=[" + uid + "]", e);
			throw new BizException("query MUser failure! uid=[" + uid + "]");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<MUser> queryMUserListByRoleId(int roleId) throws BizException {
		try {
			return mUserDao.queryMUserListByRoleId(roleId);
		} catch (DaoException e) {
			logger.error("query MUser List failure! roleId=[" + roleId + "]", e);
			throw new BizException("query MUser List failure! roleId=[" + roleId + "]");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Pager<MUser> queryMUserPager(MUser user, int pageIndex, int pageSize) throws BizException {
		try {
			return mUserDao.queryMUserPager(user, pageIndex, pageSize);
		} catch (DaoException e) {
			logger.error("query MUser pager failure!", e);
			throw new BizException("query MUser pager failure!");
		}
	}

	@Override
	public void updateMUserStatus(int[] ids, String recStatus) throws BizException {
		try {
			if (StringUtil.isNotNull(recStatus)) {
				if (ids != null && ids.length > 0) {
					for (int i = 0; i < ids.length; i++) {
						int id = ids[i];
						MUser muser = mUserDao.queryMUser(id);
						if (muser != null) {
							muser.setRecStatus(recStatus);
							mUserDao.updateMUser(muser);
						}
					}
				}
			}
		} catch (DaoException e) {
			logger.error("update MUser failure! ", e);
			throw new BizException("update MUser failure! ");
		}
	}

	@Override
	public void addMUser(MUser user) throws BizException {
		try {
			List<MUser> list = mUserDao.queryMUserByRoleId(new Integer[] { AppConstant.ROLE_ADMIN, AppConstant.ROLE_DAREN });

			MUser mUser = mUserDao.queryMUserByUsername(user.getUsername());
			if (mUser != null) {
				return;
			}
			user.setRecDate(new Date());
			user.setRecStatus(AppConstant.STATUS_AVAILABLE);
			mUserDao.addMUser(user);

			// 添加IM默认配置
			final int mId = user.getId();
			// 创建默认好友组 -> "我的好友"
			ImFriendGroup friendGroup = new ImFriendGroup(mId, "我的好友", list.size());
			imFriendGroupDao.addImFriendGroup(friendGroup);
			ImFriendGroupMem friendGroupMem = null;
			for (MUser o : list) {
				friendGroupMem = new ImFriendGroupMem(StringUtil.isNull(o.getNickname()) ? o.getUsername() : o.getNickname(),
						StringUtil.isNull(o.getAvatar()) ? AppConstant.USER_HEADER_IMG_DEFAULTURL : o.getAvatar(), StringUtil.isNull(o.getSign()) ? "" : o.getSign(), AppConstant.IM_FRIEND_STATUS_OK,
						friendGroup);
				friendGroupMem.setmId(o.getId());
				imFriendGroupMemDao.addImFriendGroupMem(friendGroupMem);

				// 互添好友
				List<ImFriendGroup> adminFriendGroupList = imFriendGroupDao.queryImFriendGroupByMId(o.getId());
				ImFriendGroup group = null;
				if (adminFriendGroupList.size() > 0) {
					group = adminFriendGroupList.get(0);
					// 好友数+1
					group.setOnline(group.getOnline() + 1);
					imFriendGroupDao.updateImFriendGroup(group);
				} else {
					group = new ImFriendGroup(o.getId(), "我的好友", 1);
					imFriendGroupDao.addImFriendGroup(group);
				}
				// 添加好友信息
				friendGroupMem = new ImFriendGroupMem(StringUtil.isNull(user.getNickname()) ? user.getUsername() : user.getNickname(),
						StringUtil.isNull(user.getAvatar()) ? AppConstant.USER_HEADER_IMG_DEFAULTURL : user.getAvatar(), StringUtil.isNull(user.getSign()) ? "" : user.getSign(),
						AppConstant.IM_FRIEND_STATUS_OK, group);
				friendGroupMem.setmId(user.getId());
				imFriendGroupMemDao.addImFriendGroupMem(friendGroupMem);
				// TODO 发送添加通知
			}
		} catch (DaoException e) {
			logger.error("add MUser failure! user = [" + user + "]", e);
			throw new BizException("add MUser failure! user = [" + user + "]");
		}
	}

	@Override
	public ResultVo editPwd(int id, String pwd, String newpwd) throws BizException {
		try {
			MUser muser = mUserDao.queryMUser(id);
			// 二次加密
			pwd = MD5.MD5Encode(pwd);
			newpwd = MD5.MD5Encode(newpwd);

			if (muser != null && muser.getPassword().equals(pwd)) {
				muser.setPassword(newpwd);
				mUserDao.updateMUser(muser);
				return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, "密码修改成功", "");
			} else {
				return new ResultVo(AppConstant.RESULT_STATUS_ERROR, "原密码错误", "");
			}
		} catch (DaoException e) {
			logger.error("edit Pwd failure! id = [" + id + "]", e);
			throw new BizException("edit Pwd failure! id = [" + id + "]");
		}
	}

	@Override
	public void updateMUser(MUser user) throws BizException {
		try {
			mUserDao.updateMUser(user);
		} catch (DaoException e) {
			logger.error("update MUser failure! user = [" + user + "]", e);
			throw new BizException("update MUserfailure! user = [" + user + "]");
		}
	}
}
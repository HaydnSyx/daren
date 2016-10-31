package com.syx.taobao.component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.MUser;
import com.syx.taobao.model.authority.Func;
import com.syx.taobao.model.authority.Module;
import com.syx.taobao.model.authority.Perm;
import com.syx.taobao.model.authority.Role;
import com.syx.taobao.service.FuncService;
import com.syx.taobao.service.ModuleService;
import com.syx.taobao.service.RoleService;
import com.syx.taobao.util.StringUtil;

@Component
public class PermComponent {

	public static Map<Integer, Set<String>> urlsMap = new HashMap<Integer, Set<String>>();
	public static Set<Integer> moduleSet = new HashSet<Integer>();

	@Autowired
	private RoleService roleService;
	@Autowired
	private FuncService funcService;
	@Autowired
	private ModuleService moduleService;

	public boolean validMUserPerm(MUser user, String url) throws BizException {
		if (StringUtil.isNotNull(url)) {
			if (user != null) {
				Role r = user.getRole();
				if (r != null) {
					Set<String> urls = urlsMap.get(r.getId());
					if (urls == null) {
						urls = getRoleAllUrls(r);
						moduleSet.clear();
						urlsMap.put(r.getId(), urls);
					}
					return checkUrlPerm(url, urls);
				}
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	private boolean checkUrlPerm(String url, Set<String> urls) {
		if (urls.contains(url)) {
			return true;
		}

		for (Iterator iterator = urls.iterator(); iterator.hasNext();) {
			String pu = (String) iterator.next();
			if (url.startsWith(pu)) {
				return true;
			}
		}

		return false;
	}

	public Set<String> getRoleAllUrls(Role r) throws BizException {
		Set<String> urls = new HashSet<String>();
		List<Perm> perms = roleService.queryAllPerm(r.getId());
		for (int i = 0; i < perms.size(); i++) {
			Perm p = perms.get(i);
			int funcId = p.getFuncId();
			getFuncUrls(urls, funcId);
		}
		return urls;
	}

	private void getFuncUrls(Set<String> set, int funcId) throws BizException {
		Func f = funcService.queryFunc(funcId);
		if (f != null) {
			String funcUrl = f.getUrl();
			if (StringUtil.isNotNull(funcUrl)) {
				String[] urls = funcUrl.split(",");
				for (int i = 0; i < urls.length; i++) {
					set.add(urls[i]);
				}
			}

			Module m = f.getModule();
			if (m != null) {
				getModuleUrls(set, m);
			}
		}
	}

	private void getModuleUrls(Set<String> set, Module m) throws BizException {
		if (moduleSet.contains(m.getId())) {
			return;
		}
		moduleSet.add(m.getId());
		String mUrl = m.getUrl();
		if (StringUtil.isNotNull(mUrl)) {
			String[] urls = mUrl.split(",");
			for (int i = 0; i < urls.length; i++) {
				set.add(urls[i]);
			}
		}

		Integer pId = m.getpId();
		if (pId != null && pId > 0) {
			Module parent = moduleService.queryModule(pId);
			if (parent != null) {
				getModuleUrls(set, parent);
			}

		}
	}

}

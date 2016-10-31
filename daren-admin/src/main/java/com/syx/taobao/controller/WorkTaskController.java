package com.syx.taobao.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.controller.common.AbstractController;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.Attachment;
import com.syx.taobao.model.MUser;
import com.syx.taobao.model.ProductCard;
import com.syx.taobao.model.WorkTask;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.service.AttachmentService;
import com.syx.taobao.service.AuditService;
import com.syx.taobao.service.MUserService;
import com.syx.taobao.service.ProductCardService;
import com.syx.taobao.service.WorkTaskService;
import com.syx.taobao.util.DateEditor;
import com.syx.taobao.vo.AuditVo;
import com.syx.taobao.vo.ResultVo;
import com.syx.taobao.vo.WorkTaskVo;

@Controller
@RequestMapping("work/task")
public class WorkTaskController extends AbstractController {
	@Autowired
	private WorkTaskService workTaskService;
	@Autowired
	private MUserService mUserService;
	@Autowired
	private ProductCardService productCardService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private AuditService auditService;

	@RequestMapping("list")
	public String list(WorkTaskVo vo, String orderby, String sort, HttpSession session, HttpServletRequest request,
			ModelMap map) {
		Pager<WorkTaskVo> pager = null;
		try {
			final Integer mId = (Integer) session.getAttribute(AppConstant.SESSION_MUSER_ID);
			if (mId == null || mId == 0) {
				return "redirect:/signin.htm";
			}
			MUser muser = mUserService.queryMUser(mId);
			if (muser.getRole().getId() == 3) {
				vo.setRoleId(muser.getRole().getId());
			}
			pager = workTaskService.queryWorkTaskPagerByCustom(vo, orderby, sort, getPageIndex(request), 50);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (pager == null) {
			pager = new Pager<WorkTaskVo>();
		}

		map.addAttribute("pager", pager);

		return "work/task/list";
	}

	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String view(@RequestParam("id") int id, HttpServletRequest request, HttpSession session, ModelMap map) {
		final Integer mId = (Integer) session.getAttribute(AppConstant.SESSION_MUSER_ID);
		if (mId == null || mId == 0) {
			return "redirect:/signin.htm";
		}
		WorkTask task = null;
		ProductCard pc = null;
		String product = null;
		MUser muser = null;
		try {
			task = workTaskService.queryWorkTask(id);
			if (task.getProductType() == AppConstant.PRODUCT_SINGLEPRODUCT) {
				product = "singleproduct";
			} else if (task.getProductType() == AppConstant.PRODUCT_DETAILLIST) {
				product = "detaillist";
			} else if (task.getProductType() == AppConstant.PRODUCT_CARD) {
				pc = productCardService.queryProductCardByTaskId(id);
				// 查询附件
				List<Attachment> attachmentList = attachmentService.queryAttachmentByTaskId(id);
				map.addAttribute("attachmentList", attachmentList);
				product = "card";
			}
			muser = mUserService.queryMUser(mId);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (task == null) {
			task = new WorkTask();
		}
		if (pc == null) {
			pc = new ProductCard();
		}
		map.addAttribute("task", task);
		map.addAttribute("pc", pc);
		map.addAttribute("muser", muser);
		return "work/task/view/" + product;
	}

	@RequestMapping(value = "add-{product}", method = RequestMethod.GET)
	public String addview(@PathVariable("product") String product, Integer webType, HttpServletRequest request,
			HttpSession session, ModelMap map) {
		final Integer mId = (Integer) session.getAttribute(AppConstant.SESSION_MUSER_ID);
		if (mId == null || mId == 0) {
			return "redirect:/signin.htm";
		}
		map.addAttribute("webType", webType == null ? 1 : webType);
		try {
			WorkTask o = new WorkTask();
			o.setmId(mId);
			o.setWebType(webType == null ? 1 : webType);
			if (product.equals("singleproduct")) {
				o.setProductType(AppConstant.PRODUCT_SINGLEPRODUCT);
			} else if (product.equals("detaillist")) {
				o.setProductType(AppConstant.PRODUCT_DETAILLIST);
			} else if (product.equals("card")) {
				o.setProductType(AppConstant.PRODUCT_CARD);
			}
			// 添加一个作业
			Map<String, Integer> re = workTaskService.addWorkTask(o);
			if (!re.isEmpty()) {
				map.addAttribute("taskId", re.get("taskId") == null ? 0 : re.get("taskId"));
			}
			return "redirect:/work/task/update.htm?id="
					+ String.valueOf(re.get("taskId") == null ? 0 : re.get("taskId"));
		} catch (BizException e) {
			e.printStackTrace();
		}
		return "redirect:/work/task/list.htm";
	}

	@RequestMapping(value = "add-{product}", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo add(@PathVariable("product") String product, WorkTask o, ProductCard pc, HttpServletRequest request,
			ModelMap map) {
		try {
			workTaskService.addWorkTask(o);
		} catch (BizException e) {
			return new ResultVo(AppConstant.RESULT_STATUS_ERROR, e.getMessage(), null);
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, null);
	}

	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateview(@RequestParam("id") int id, HttpServletRequest request, HttpSession session,
			ModelMap map) {
		final Integer mId = (Integer) session.getAttribute(AppConstant.SESSION_MUSER_ID);
		if (mId == null || mId == 0) {
			return "redirect:/signin.htm";
		}
		WorkTask task = null;
		ProductCard pc = null;
		String product = null;
		try {
			task = workTaskService.queryWorkTask(id);
			if (task.getProductType() == AppConstant.PRODUCT_SINGLEPRODUCT) {
				product = "singleproduct";
			} else if (task.getProductType() == AppConstant.PRODUCT_DETAILLIST) {
				product = "detaillist";
			} else if (task.getProductType() == AppConstant.PRODUCT_CARD) {
				pc = productCardService.queryProductCardByTaskId(id);
				// 查询附件
				List<Attachment> attachmentList = attachmentService.queryAttachmentByTaskId(id);
				map.addAttribute("attachmentList", attachmentList);
				product = "card";
			}
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (task == null) {
			task = new WorkTask();
		}
		if (pc == null) {
			pc = new ProductCard();
		}
		map.addAttribute("task", task);
		map.addAttribute("pc", pc);

		return "work/task/update/" + product;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo update(WorkTask wt, ProductCard pc, String[] url, String[] serverName, String[] originName,
			Long[] fileSize, Integer[] originAttachmentId, HttpServletRequest request, ModelMap map) {
		try {
			return workTaskService.updateWorkTaskAndProduct(wt, pc, url, serverName, originName, fileSize,
					originAttachmentId);
		} catch (BizException e) {
			return new ResultVo(AppConstant.RESULT_STATUS_ERROR, e.getMessage(), null);
		}
	}

	@RequestMapping("delete")
	public String delete(@RequestParam("id") int id, HttpServletRequest request, ModelMap map) {
		try {
			WorkTask task = workTaskService.queryWorkTask(id);
			if (task.getProductType() == AppConstant.PRODUCT_SINGLEPRODUCT) {
			} else if (task.getProductType() == AppConstant.PRODUCT_DETAILLIST) {
			} else if (task.getProductType() == AppConstant.PRODUCT_CARD) {
				ProductCard pc = productCardService.queryProductCardByTaskId(id);
				productCardService.deleteProductCard(pc.getId());
			}
			workTaskService.deleteWorkTask(id);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return "redirect:/work/task/list.htm";
	}

	@RequestMapping(value = "editStar", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo editStar(@RequestParam("id") int id, @RequestParam("level") int level, HttpServletRequest request,
			ModelMap map) {
		try {
			WorkTask o = workTaskService.queryWorkTask(id);
			o.setLevel(level);
			o.setUpdateTime(new Date());
			workTaskService.updateWorkTask(o);
		} catch (BizException e) {
			return new ResultVo(AppConstant.RESULT_STATUS_ERROR, e.getMessage(), null);
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, null);
	}

	@RequestMapping(value = "viewremark", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo viewremark(@RequestParam("id") int id, HttpServletRequest request, ModelMap map) {
		WorkTask o = null;
		try {
			o = workTaskService.queryWorkTask(id);
		} catch (BizException e) {
			return new ResultVo(AppConstant.RESULT_STATUS_ERROR, e.getMessage(), null);
		}
		if (o == null) {
			o = new WorkTask();
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, o.getRemark());
	}

	@RequestMapping(value = "audit", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo audit(@RequestParam("taskId") int taskId, @RequestParam("type") int type,
			@RequestParam("status") int status, String remark, HttpSession session, HttpServletRequest request,
			ModelMap map) {
		final Integer mId = (Integer) session.getAttribute(AppConstant.SESSION_MUSER_ID);
		if (mId == null || mId == 0) {
			return new ResultVo(AppConstant.RESULT_STATUS_ERROR, null, -1);
		}
		try {
			return workTaskService.auditWorkTask(mId, taskId, type, status, remark);
		} catch (BizException e) {
			return new ResultVo(AppConstant.RESULT_STATUS_ERROR, e.getMessage(), null);
		}
	}

	@RequestMapping(value = "audit", method = RequestMethod.GET)
	public String audit(HttpSession session, HttpServletRequest request, ModelMap map) {
		return "work/task/audit";
	}

	@RequestMapping(value = "auditRecord", method = RequestMethod.GET)
	public String auditRecord(@RequestParam("taskId") int taskId, HttpSession session, HttpServletRequest request,
			ModelMap map) {
		List<AuditVo> auditList = null;
		try {
			auditList = auditService.queryAuditBytaskId(taskId);
		} catch (BizException e) {
		}
		if (auditList == null) {
			auditList = new ArrayList<AuditVo>();
		}
		map.addAttribute("auditList", auditList);

		return "work/task/auditrecord";
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
}

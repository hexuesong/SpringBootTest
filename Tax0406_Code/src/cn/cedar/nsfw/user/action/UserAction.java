package cn.cedar.nsfw.user.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.cedar.nsfw.user.entity.User;
import cn.cedar.nsfw.user.service.UserService;

public class UserAction extends ActionSupport {

	@Resource
	private UserService userService;
	private List<User> userList;
	private User user;
	private String[] selectedRow;
	private File headImg;
	private String headImgContentType;
	private String headImgFileName;

	// 列表页面
	public String listUI() {
		userList = userService.findObjects();
		return "listUI";
	}

	// 跳转到新增页面
	public String addUI() {
		return "addUI";
	}

	// 保存新增
	public String add() {
		try {
			if (user != null) {
				// 处理头像
				if (headImg != null) {
					// 1.保存到upload/user
					// 获取保存路径的绝对地址
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName = UUID.randomUUID().toString().replace("-", "")
							+ headImgFileName.substring(headImgFileName.lastIndexOf("."));
					// 复制文件
					FileUtils.copyFile(headImg, new File(filePath, fileName));
					// 2.设置用户头像路径
					user.setHeadImg("user/" + fileName);//user/123.jpg
				}
				userService.save(user);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "list";
	}

	// 跳转到编辑页面
	public String editUI() {
		if (user != null && user.getId() != null) {
			user = userService.findByObjectById(user.getId());
		}
		return "editUI";
	}

	// 保存编辑
	public String edit() {
		try {
			if (user != null) {
				// 处理头像
				if (headImg != null) {
					// 1.保存到upload/user
					// 获取保存路径的绝对地址
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName = UUID.randomUUID().toString().replace("-", "")
							+ headImgFileName.substring(headImgFileName.lastIndexOf("."));
					// 复制文件
					FileUtils.copyFile(headImg, new File(filePath, fileName));
					// 2.设置用户头像路径
					user.setHeadImg("user/" + fileName);//user/123.jpg
				}
				userService.update(user);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "edit";
	}

	// 删除
	public String delete() {
		if (user != null && user.getId() != null) {
			userService.delete(user.getId());
		}
		return "list";
	}

	// 批量删除
	public String deleteSelected() {
		if (selectedRow != null) {
			for (String id : selectedRow) {
				userService.delete(id);
			}
		}
		return "list";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String[] getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}

	public File getHeadImg() {
		return headImg;
	}

	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}

	public String getHeadImgContentType() {
		return headImgContentType;
	}

	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}

	public String getHeadImgFileName() {
		return headImgFileName;
	}

	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}

}

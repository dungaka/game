package com.app.empire.protocol.data.mail;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SendOutboxMail</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_MAIL下子命令MAIL_SendOutboxMail(发送发件箱列表协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendOutboxMail extends AbstractData {
	
	private int[]		mailId;			//邮件id
	private String[]	theme;			//主题
	private String[]	receciverName;	//收件人姓名
	private String[]	time;			//发送时间
	private int[]		mailType;		//类型 0表示个人邮件，1表示系统邮件，2表示公会邮件，3GM工具邮件,8问题反馈，9充值咨询，10附近好友邮件
	private boolean[]	isRead;			//是否已读
	private int 		pageNumber; 	//当前第几页
	private int 		totalPage; 		//总页数


    public SendOutboxMail(int sessionId, int serial) {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_SendOutboxMail, sessionId, serial);
    }

    public SendOutboxMail() {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_SendOutboxMail);
    }

	public int[] getMailId() {
		return mailId;
	}

	public void setMailId(int[] mailId) {
		this.mailId = mailId;
	}

	public String[] getTheme() {
		return theme;
	}

	public void setTheme(String[] theme) {
		this.theme = theme;
	}


	public String[] getTime() {
		return time;
	}

	public void setTime(String[] time) {
		this.time = time;
	}

	public int[] getMailType() {
		return mailType;
	}

	public void setMailType(int[] mailType) {
		this.mailType = mailType;
	}

	public boolean[] getIsRead() {
		return isRead;
	}

	public void setIsRead(boolean[] isRead) {
		this.isRead = isRead;
	}

	public String[] getRececiverName() {
		return receciverName;
	}

	public void setRececiverName(String[] receciverName) {
		this.receciverName = receciverName;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}

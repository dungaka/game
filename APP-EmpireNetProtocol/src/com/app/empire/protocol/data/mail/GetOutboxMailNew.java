package com.app.empire.protocol.data.mail;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetOutboxMail</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_MAIL下子命令MAIL_GetOutboxMail(获得发件箱列表协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetOutboxMailNew extends AbstractData {
	private int 		pageNumber = 1; //要获得的页数

    public GetOutboxMailNew(int sessionId, int serial) {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_GetOutboxMailNew, sessionId, serial);
    }

    public GetOutboxMailNew() {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_GetOutboxMailNew);
    }

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}

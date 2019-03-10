package com.meijm.demo.vo;

//import lombok.Data;

/**
 * @author mjm
 * @createtime $date$ $time$
 **/
//@Data
public class Message {
	//发送者
	private String username;
	//发送者头像
	private String avatar;
	//消息体
	private String content;
	//发时间
	private String sendTime;
	// 接受者
	private String receiver;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}

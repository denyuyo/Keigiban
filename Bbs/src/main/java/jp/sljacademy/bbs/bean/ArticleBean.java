package jp.sljacademy.bbs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

// 記事

public class ArticleBean {
	
	private int articleId;
	private Date createDate;
	private String name;
	private String email;
	private String title;
	private String text;
	private String colorId;
	private String colorCode;
	
	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	
	public String getCreateDateView() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
		return sdf.format(createDate);
	}
	
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public ArticleBean()  {
		title = "";
		text = "";
	}
	
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}

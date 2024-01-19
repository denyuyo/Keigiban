package jp.sljacademy.bbs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.sljacademy.bbs.util.CommonFunction;

// 記事情報モデル(入力項目がある→newして紙を作る)

public class ArticleBean {
	
	private int articleId;
	private Date createDate;
	private String name;
	private String email;
	private String title;
	private String text;
	private String colorId;
	private String colorCode;
	
	public ArticleBean()  {
		// 初期値を空白
		name = "";
		email = "";
		title = "";
		text = "";
		// checkdを"青"
		colorId = "3";
	}
	
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
	
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	
	public String getNameView() {
		return CommonFunction.getDefault(name, "nobody");
	}
	
	public String getTitleView() {
		return CommonFunction.getDefault(title, "(no title)");
	}
	
	public String getTextView() {
		return CommonFunction.convertLineBreaksToHtml(text);
	}
	
	public String getCreateDateView() {
		// 指定されたフォーマットで日付と時刻を表示するためのフォーマッタを作成
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
		// createDate という日付情報を、上記で作成したフォーマッタ sdf を使用して指定されたフォーマットに整形し、文字列として返す
		return sdf.format(createDate);
	}
}

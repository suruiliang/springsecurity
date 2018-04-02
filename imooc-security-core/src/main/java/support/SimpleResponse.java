package support;

/**
 * @author suruiliang
 * @version 创建时间：2018年3月31日 上午10:30:30
 * @ClassName 类名称
 * @Description 类描述
 */
public class SimpleResponse {

	private Object content;

	public SimpleResponse() {
	}

	public SimpleResponse(Object content) {
		this.content=content;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}

}

package cn.junhui.blog_test.vo;

/**
 * 军辉
 * 2019-02-04 14:02
 * 是 REST 统一返回的值对象
 */
public class Response {

    private boolean success;
    private String message;
    private Object body;

    /*
    响应处理是否成功
     */
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /*
    相应处理的信息
     */
    public String getMessage() {
        return message;
    }

    public void setMessage() {
        this.message = message;
    }

    /*
    响应处理的返回内容
     */
    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response(boolean success, String message, Object body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }
}

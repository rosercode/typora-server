package cool.wangshuo.ts.replayModel;


import lombok.Data;

/**
 * @Classname CommonResult
 * @Description 通用返回接口
 * @Date 2021/8/8 上午1:55
 * @Created by minicode
 */
@Data
public class CommonResult<T> {


    private Integer code;  // 状态码


    private String msg;  // 消息


    private T data;   // 结果


    private String url;  // url

    public CommonResult() {
    }

    public CommonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    @Override
    public String toString() {
        return "CommonResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}

package com.hyf.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author：洪yf
 * @date：2022/3/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object obj;

    /**
     * create time: 2022/3/6
     * description:成功返回结果
     * params:message
     * return:
     */
    public static RespBean success(String message)
    {
        return new RespBean(200,message,null);
    }

    /**
     * create time: 2022/3/6
     * description:成功返回结果
     * params:
     * return:
     */
    public static RespBean success(String message,Object obj)
    {
        return new RespBean(200,message,obj);
    }

    /**
     * create time: 2022/3/6
     * description:失败返回结果
     * params:obj
     * return:
     */
    public static RespBean error(String message){
        return new RespBean(500,message,null);
    }

    /**
     * create time: 2022/3/6
     * description:失败返回结果
     * params:obj
     * return:
     */
    public static RespBean error(String message,Object obj)
    {
        return new RespBean(500,message,obj);
    }
}

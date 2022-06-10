package com.hyf.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author：洪yf
 * @date：2022/3/6
 * 用户登录实体类
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors
@ApiModel(value = "AdminLogin对象",description = "")
public class AdminLoginParam {
    @ApiModelProperty(value = "用户名",readOnly = true)
    private String username;
    @ApiModelProperty(value = "密码",readOnly = true)
    private String password;
    @ApiModelProperty(value = "验证码",readOnly = true)
    private String code;
}

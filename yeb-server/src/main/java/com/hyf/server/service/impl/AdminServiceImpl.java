package com.hyf.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.server.conf.security.JwtTokenUtils;
import com.hyf.server.mapper.AdminMapper;
import com.hyf.server.mapper.AdminRoleMapper;
import com.hyf.server.mapper.RoleMapper;
import com.hyf.server.pojo.Admin;
import com.hyf.server.pojo.AdminRole;
import com.hyf.server.pojo.RespBean;
import com.hyf.server.pojo.Role;
import com.hyf.server.service.IAdminService;
import com.hyf.server.utils.AdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyf
 * @since 2022-03-04
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private AdminMapper adminMapper;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    /**
     * create time: 2022/3/6
     * description:登录之后返回token
     * params:username
     * params:password
     * return:request
     */
    @Override
    public RespBean login(final String username, final String password, final String code, final HttpServletRequest request) {
        String captcha =(String) request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(code)|| !captcha.equalsIgnoreCase(code))
        {
            return RespBean.error("验证码输入错误，请重试");
        }
        //登录
//        System.out.println(jwtTokenUtils);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        authorities.stream().forEach(System.out::println);
        if(null == userDetails||!passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或密码不正确");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error(("账号已被禁用，请联系管理员"));
        }
        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token  = jwtTokenUtils.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登陆成功",tokenMap);
    }

    /**
     * create time: 2022/3/13
     * description:根据用户名获取用户
     * params:
     * return:
     */
    @Override
    public Admin getAdminByUserName(final String username) {
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));
        return admin;
    }

    /**
     * create time: 2022/3/21
     * description:根据用户id查询角色列表
     * params:
     * return:
     */
    @Override
    public List<Role> getRoles(final Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

    /**
     * create time: 2022/4/3
     * description:获取所有的操作员
     * params:adminId
     * return:
     */
    @Override
    public List<Admin> getAllAdmins(final String keywords) {
        return adminMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId(),keywords);
    }

    /**
     * create time: 2022/4/4
     * description:更新操作员角色
     * params:
     * return:
     */
    @Override
    @Transactional
    public RespBean updateAdminRole(final Integer adminId, final Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));
        Integer result = adminRoleMapper.updateAdminRole(adminId, rids);
        if(rids.length == result)
        {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }
}

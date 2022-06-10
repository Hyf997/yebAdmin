package com.hyf.server.service.impl;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.server.mapper.EmployeeMapper;
import com.hyf.server.mapper.MailLogMapper;
import com.hyf.server.pojo.*;
import com.hyf.server.service.IEmployeeService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MailLogMapper mailLogMapper;
    /**
     * create time: 2022/4/4
     * description:获取所有员工（分页）
     * params:
     * return:
     */
    @Override
    public RespPageBean getEmployeeByPage(final Integer currentPage, final Integer size, final Employee employee, final LocalDate[] beginDateScope) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage,size);
        IPage<Employee> employeeByPage = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        RespPageBean respPageBean = new RespPageBean(employeeByPage.getTotal(),employeeByPage.getRecords());
        return respPageBean;
    }

    /**
     * create time: 2022/4/4
     * description:获取工号
     * params:
     * return:
     */
    @Override
    public RespBean maxWorkID() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        return RespBean.success(null,String.format("%08d",Integer.parseInt(maps.get(0).get("max(workID)").toString())+1));
    }

    /**
     * create time: 2022/4/4
     * description:添加员工
     * params:
     * return:
     */
    @Override
    public RespBean addEmp(final Employee employee) {
        //处理合同期限，保留两位小数
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365.00)));
        if(1==employeeMapper.insert(employee))
        {
            Employee emp = employeeMapper.getEmployee(employee.getId()).get(0);
            //数据库记录发送的信息
//            String msg = UUID.randomUUID().toString();
            String msg = "123456";
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msg);
            mailLog.setEid(emp.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog);
            //发送信息
            rabbitTemplate.convertAndSend( MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,emp,
                    new CorrelationData(msg));
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    /**
     * create time: 2022/4/5
     * description:查询员工数据
     * params:
     * return:
     */
    @Override
    public List<Employee> getEmployee(final Integer id) {
        return employeeMapper.getEmployee(id);
    }

    /**
     * create time: 2022/6/9
     * description:获取所有工资账套
     * params:
     * return:
     * @return
     */
    @Override
    public RespPageBean getEmployeeWithSalary(final Integer currentPage, final Integer size) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage,size);
        IPage<Employee> employeeIPage = employeeMapper.getEmployeeWithSalary(page);
        RespPageBean respPageBean = new RespPageBean(employeeIPage.getTotal(), employeeIPage.getRecords());
        return respPageBean;
    }
}

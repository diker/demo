package com.diker.basic.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author diker
 * @since 2019/1/3
 */
public class FirstDemo {

    public static void main(String[] args) {
        initFlowRules();
        Random random = new SecureRandom();
        int time = random.nextInt();

        while (true) {
            Entry entry = null;
            try {
                time = random.nextInt();
                time = time<0?-time:time;
                time = time%100;
                Thread.sleep(time);
                /*流控逻辑处理 - 开始*/
                entry = SphU.entry("HelloWorld");
                /*您的业务逻辑 - 开始*/
                System.out.println("hello world");
                /*您的业务逻辑 - 结束*/
            } catch (BlockException e1) {
                System.out.println("block!");
                /*流控逻辑处理 - 结束*/
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }

    }

    private static void initFlowRules() {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("HelloWorld");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(20);

        List<FlowRule> list = new ArrayList();
        list.add(flowRule);
        FlowRuleManager.loadRules(list);
    }
}

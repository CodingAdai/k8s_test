package com.upupfarm.web.controller;

import com.google.common.base.Joiner;
import com.upupfarm.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@RestController
public class TestController {


    @DubboReference(version = "1.0.0", check = false)
    private DemoService demoService;


    @GetMapping("hello")
    public String hello() throws SocketException {
        List<String> hostAddresses = new ArrayList<>();

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
            for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                String hostAddress = interfaceAddress.getAddress().getHostAddress();
                hostAddresses.add(hostAddress);
            }
        }

        return demoService.sayHello(", web pod ip: " + Joiner.on(",").join(hostAddresses) + "    dubbo: ");
    }


}

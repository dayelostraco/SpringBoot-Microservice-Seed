package com.dayelostraco.microservice.controller;

import com.dayelostraco.microservice.model.dto.HeartBeat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.Calendar;
import java.util.TimeZone;

@RestController
@RequestMapping("api/v1/health")
@Api(value = "api/v1/health", description = "Heartbeat Controller API.")
public class HeartBeatController {

    @RequestMapping("")
    @ResponseBody
    @ApiOperation(value = "Server HeartBeat.",
            httpMethod = "GET", response = HeartBeat.class, nickname = "heartBeat")
    public HttpEntity<HeartBeat> heartBeat(HttpServletRequest request) {

        HeartBeat heartBeat = new HeartBeat(Calendar.getInstance(TimeZone.getTimeZone("UTC")), getIpAddress(request));

        return new ResponseEntity<>(heartBeat, HttpStatus.OK);
    }

    /**
     * Retrieves the IP address of the requester from the HttpServletRequest and checks to see if the original
     * request was forwarded from a Proxy or a Load Balancer.
     *
     * @param request HttpServletRequest from the Spring MVC request
     * @return String of the original requester's IP Address
     */
    private String getIpAddress(HttpServletRequest request) {
        return request.getHeader("X-FORWARDED-FOR") == null ? request.getRemoteAddr() : request.getHeader("X-FORWARDED-FOR");
    }
}

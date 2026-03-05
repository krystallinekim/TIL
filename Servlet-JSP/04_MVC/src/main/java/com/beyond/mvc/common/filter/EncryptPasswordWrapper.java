package com.beyond.mvc.common.filter;

import com.beyond.mvc.common.util.EncryptUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class EncryptPasswordWrapper extends HttpServletRequestWrapper {

    public EncryptPasswordWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {

        if (name.equals("userPwd")) {
            return EncryptUtil.oneWayEnc(super.getParameter(name), "SHA256");
        }

        return super.getParameter(name);
    }
}

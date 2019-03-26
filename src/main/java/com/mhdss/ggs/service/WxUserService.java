package com.mhdss.ggs.service;

import com.mhdss.ggs.dataobject.WxUserDO;
import com.mhdss.ggs.dto.WxCheckDTO;
import com.mhdss.ggs.para.UserInfo;

public interface WxUserService {

    WxCheckDTO getWxCheck(String code);

    UserInfo getUserInfo(String sessionKey, String encryptedData, String iv) throws Exception;

    void login(WxUserDO wxUserDO);

    WxUserDO queryUserBySession(String sessionKey);
}

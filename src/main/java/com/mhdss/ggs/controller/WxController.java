package com.mhdss.ggs.controller;


import com.mhdss.ggs.dataobject.WxUserDO;
import com.mhdss.ggs.dto.WxCheckDTO;
import com.mhdss.ggs.para.AddWxUserInfoPara;
import com.mhdss.ggs.para.UserInfo;
import com.mhdss.ggs.service.WxUserService;
import com.mhdss.ggs.utils.ErrorCode;
import com.mhdss.ggs.utils.ResponseData;
import com.mhdss.ggs.vo.WxLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/wx")
public class WxController {

    @Autowired
    private WxUserService wxUserService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData<?> login(@RequestBody AddWxUserInfoPara infoPara) {

        if (!infoPara.check()) {
            return ResponseData.error(ErrorCode.PARAMETER_ERROR);
        }

        String code = infoPara.getCode();
        WxCheckDTO wxCheckDTO = wxUserService.getWxCheck(code);
        if (null == wxCheckDTO) {
            return ResponseData.error(ErrorCode.NO_LOGIN);
        }

        if (0 != wxCheckDTO.getErrcode()) {
            return ResponseData.error(ErrorCode.PARAMETER_ERROR);
        }
        try {
            UserInfo userInfo = wxUserService.getUserInfo(wxCheckDTO.getSession_key(), infoPara.getEncryptedData(), infoPara.getIv());
            if (null != userInfo) {
                return ResponseData.error(ErrorCode.PARAMETER_ERROR);
            }
            WxUserDO wxUserDO = new WxUserDO();
            wxUserDO.setOpenId(wxCheckDTO.getOpenid());
            wxUserDO.setAvatarUrl(userInfo.getAvatarUrl());
            wxUserDO.setNickName(userInfo.getNickName());
            wxUserDO.setGender(userInfo.getGender());
            wxUserDO.setSessionKey(wxCheckDTO.getSession_key());
            wxUserService.login(wxUserDO);
        } catch (Exception e) {

        }
        WxLoginVO loginVo = new WxLoginVO();
        loginVo.setSessionKey(wxCheckDTO.getSession_key());

        return ResponseData.success(loginVo);
    }

}

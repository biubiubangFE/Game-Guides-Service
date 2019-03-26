package com.mhdss.ggs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.Base64;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhdss.ggs.constant.BaseStatus;
import com.mhdss.ggs.constant.SeretMessage;
import com.mhdss.ggs.constant.WxAPI;
import com.mhdss.ggs.dao.WxUserDAO;
import com.mhdss.ggs.dataobject.WxUserDO;
import com.mhdss.ggs.dto.WxCheckDTO;
import com.mhdss.ggs.para.UserInfo;
import com.mhdss.ggs.query.WxUserQuery;
import com.mhdss.ggs.service.WxUserService;
import com.mhdss.ggs.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;

@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WxUserDAO wxUserDAO;

    @Override
    public WxCheckDTO getWxCheck(String code) {

        String wxLoginCheck = WxAPI.wxLoginCheck + "?appid=" + SeretMessage.appId + "&secret=" + SeretMessage.appSecret + "&js_code=" + code + "&grant_type=authorization_code";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(wxLoginCheck, buildJsonRequestEntity(null), String.class);
        String body = responseEntity.getBody();
        JSONObject resultJson = JSONObject.parseObject(body);
        WxCheckDTO wxCheckDTO = new WxCheckDTO();

        String sessionKey = resultJson.getString("session_key");
        Integer errCode = resultJson.getInteger("errcode");

        String openid = resultJson.getString("openid");
        String unionid = resultJson.getString("unionid");
        String errmsg = resultJson.getString("errmsg");
        if (StringUtils.isNoneBlank(openid, sessionKey)) {
            wxCheckDTO.setSession_key(sessionKey);
            wxCheckDTO.setOpenid(openid);
            wxCheckDTO.setUnionid(unionid);
            wxCheckDTO.setErrcode(0);

        } else {
            wxCheckDTO.setErrcode(errCode);
            wxCheckDTO.setErrmsg(errmsg);
        }

        return wxCheckDTO;

    }

    private HttpEntity<String> buildJsonRequestEntity(Object obj) {
        if (obj == null) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            return new HttpEntity<>(new ObjectMapper().writeValueAsString(obj), headers);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public UserInfo getUserInfo(String sessionKey, String encryptedData, String iv) throws Exception {
        // 被加密的数据
        byte[] dataByte = Base64.decodeFast(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decodeFast(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decodeFast(iv);

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                JSONObject jsonObject = JSONObject.parseObject(result);
                JSONObject watermark = jsonObject.getJSONObject("watermark");
                String appid = watermark.getString("appid");
                if (SeretMessage.appId.equals(appid)) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setOpenId(jsonObject.getString("openId"));
                    userInfo.setNickName(jsonObject.getString("nickName"));
                    userInfo.setGender(jsonObject.getByte("gender"));
                    userInfo.setCity(jsonObject.getString("city"));
                    userInfo.setProvince(jsonObject.getString("province"));
                    userInfo.setCounty(jsonObject.getString("country"));
                    userInfo.setAvatarUrl(jsonObject.getString("avatarUrl"));
                    userInfo.setUnionId(jsonObject.getString("unionId"));
                    userInfo.setLanguage(jsonObject.getString("language"));
                    return userInfo;
                }
                return null;

            }
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void login(WxUserDO wxUserDO) {
        wxUserDO.setStatus(BaseStatus.NORMAL.getStatus());
        Long nowTime = DateUtil.getCurrentTimeStamp();
        wxUserDO.setCreateTime(nowTime);
        wxUserDO.setUpdateTime(nowTime);
        wxUserDAO.insertUpdateOnDuplicate(wxUserDO);
    }

    @Override
    public WxUserDO queryUserBySession(String sessionKey) {

        WxUserQuery query = new WxUserQuery();
        query.setSessionKey(sessionKey);

        return wxUserDAO.selectByQuery(query);
    }

}

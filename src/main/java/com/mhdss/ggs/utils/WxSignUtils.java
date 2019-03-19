package com.mhdss.ggs.utils;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 微信签名算法
 * 
 * @author yang.fan
 *
 */
public class WxSignUtils {

    /**
     * 生成签名算法</br>
     * 规则：
     * <a>https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=4_3
     * </a>
     * 
     * @param params
     * @param privateKey
     *            商户平台设置的密钥key
     * @return
     * @author yang.fan
     */
    public static String createSign(Map<String, String> params, String privateKey) {
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (String key : sortParams.keySet()) {
            String value = sortParams.get(key);
            if (StringUtils.isNotEmpty(value))
                sb.append("&").append(key).append("=").append(value);
        }
        String stringA = sb.toString().replaceFirst("&", "");
        String stringSignTemp = stringA + "&key=" + privateKey;
        return DigestUtils.md5Hex(stringSignTemp).toUpperCase();
    }

}

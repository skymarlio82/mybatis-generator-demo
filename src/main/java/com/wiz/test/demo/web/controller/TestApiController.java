
package com.wiz.test.demo.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSON;
import com.wiz.test.demo.data.dao.TUserMapper;
import com.wiz.test.demo.data.entity.TUser;
import com.wiz.test.demo.data.entity.TUserExample;
import com.wiz.test.demo.util.DateFormatUtil;
import com.wiz.test.demo.util.MyConfig;
import com.wiz.test.demo.util.WXPay;
import com.wiz.test.demo.util.WXPayConstants.SignType;
import com.wiz.test.demo.web.form.LoginForm;
import com.wiz.test.demo.web.model.LoginResult;
import com.wiz.test.demo.web.model.WxOpenIdToken;

@Controller
@RequestMapping("/test/api")
public class TestApiController {

	@RequestMapping(value="/login", method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public LoginResult login(@RequestBody LoginForm loginForm) {
		LoginResult result = new LoginResult();
		if (loginForm.getPassword().equals("333333")) {
			String token = UUID.randomUUID().toString() + "." + loginForm.getCompanyCode() + "." + loginForm.getPersonNumber();
			result.setToken(token);
			result.setStatus(true);
		} else {
			result.setStatus(false);
			result.setMessage("Password Verified Failed.");
		}
		return result;
	}

	@Autowired
	private TUserMapper tUserMapper = null;

	@RequestMapping(value="/hi", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TUser> hi() {
		List<TUser> users = tUserMapper.selectByExample((new TUserExample()).or().andUserNameBetween("test1", "test2").example().orderBy(TUser.Column.phone.asc()));
		return users;
	}

	@RequestMapping(value = "/callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(@RequestParam("code") String code, Model model) throws Exception {
		System.out.println("code = " + code);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxba3211abca2a188c&secret=71c65ffcd1bc9f803be323665d330797&code=" + code + "&grant_type=authorization_code");
		ResponseHandler<String> responseHandler = response -> {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity responseEntity = response.getEntity();
				return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		};
		String response = httpclient.execute(httpGet, responseHandler);
		System.out.println("response2 = " + response);

		WxOpenIdToken wxOpenIdToken = JSON.parseObject(response, WxOpenIdToken.class);

		MyConfig config = new MyConfig();
		WXPay wxpay = new WXPay(config);
		Map<String, String> data = new HashMap<String, String>();
		data.put("body", "Jitao微信支付测试");
		data.put("attach", "test payment");
		data.put("out_trade_no", "2020040213380000000002");
		data.put("fee_type", "CNY");
		data.put("total_fee", "1");
		Date d = new Date();
		data.put("time_start", DateFormatUtil.getSimpleDateTimeTxt1(d));
		data.put("time_expire", DateFormatUtil.getSimpleDateTimeTxt1(new Date(d.getTime() + 2 * 60 * 60 * 1000)));
		data.put("goods_tag", "goods");
		data.put("trade_type", "JSAPI");
		data.put("spbill_create_ip", "47.94.142.113");
		data.put("notify_url", "https://www.sky888.cn/api/wx/order/pay-notify");
		data.put("product_id", "10");
		data.put("openid", wxOpenIdToken.getOpenid());

		Map<String, String> resp = null;
		try {
			resp = wxpay.unifiedOrder(data);
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("appId", resp.get("appid"));
		model.addAttribute("nonceStr", resp.get("nonce_str"));
		model.addAttribute("package", "prepay_id=" + resp.get("prepay_id"));
		model.addAttribute("paySign", resp.get("sign"));
		model.addAttribute("signType", "MD5");
		model.addAttribute("timeStamp", String.valueOf((new Date()).getTime()));

//		model.addAttribute("appId", "wxba3211abca2a188c");
//		model.addAttribute("nonceStr", "600860054");
//		model.addAttribute("package", "prepay_id=wx02101151614429a7c442c7631949471500");
//		model.addAttribute("paySign", "34e8d7219ce8f5fd2531b79f49eff0924bbf3bbfbfefcc3f1f588e6257f37643");
//		model.addAttribute("signType", "MD5");
//		model.addAttribute("timeStamp", "1585793512");

		return "index";
	}

	@RequestMapping(value="/wxpay", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, String> wxpay() throws ClientProtocolException, IOException {
		Map<String, String> result = new HashMap<String, String>();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxba3211abca2a188c&redirect_uri=https://www.sky888.cn/test-api/test/api/callback&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		ResponseHandler<String> responseHandler = response -> {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity responseEntity = response.getEntity();
				return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		};
		String response = httpclient.execute(httpGet, responseHandler);
		System.out.println("response1 = " + response);
		result.put("code_response", response);
		return result;
	}
}
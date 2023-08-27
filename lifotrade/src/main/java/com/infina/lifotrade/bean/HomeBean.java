package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.infina.lifotrade.services.common.LifotradeApi;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ViewScoped
@ManagedBean
public class HomeBean implements Serializable {

	private static final long serialVersionUID = -20462409324104989L;
	
	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;
	
	public String exit() {
		LifotradeApi.setToken("");
		return "login?faces-redirect=true";
	}
	public String dayUpdate() {
		messageBean.showFailureMessage(Arrays.asList("Bu özellik yakında aktif olacaktır."));
		return "";
	}

}

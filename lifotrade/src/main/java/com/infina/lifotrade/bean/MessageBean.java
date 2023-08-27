package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ViewScoped
@ManagedBean
public class MessageBean implements Serializable {

	private static final long serialVersionUID = -338994678550963101L;

	private FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	public void showSuccessMessage(List<String> messages) {
		List<FacesMessage> facesMessages = new ArrayList<>();
		for (String message : messages) {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", message);
			facesMessages.add(facesMessage);
		}
		for (FacesMessage facesMessage : facesMessages) {
			getContext().addMessage(null, facesMessage);
		}
	}

	public void showFailureMessage(List<String> messages) {
		List<FacesMessage> facesMessages = new ArrayList<>();
		for (String message : messages) {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", message);
			facesMessages.add(facesMessage);
		}
		for (FacesMessage facesMessage : facesMessages) {
			getContext().addMessage(null, facesMessage);
		}
	}

}

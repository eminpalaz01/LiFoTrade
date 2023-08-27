package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infina.lifotrade.dto.AccountDto;
import com.infina.lifotrade.dto.AccountFundDto;
import com.infina.lifotrade.dto.FundDto;
import com.infina.lifotrade.dto.FundTransactionDto;
import com.infina.lifotrade.dto.request.AccountFundIdRequest;
import com.infina.lifotrade.dto.request.FundTransactionSaveRequestDto;
import com.infina.lifotrade.services.common.LifotradeApi;
import com.infina.lifotrade.services.common.MessagesUtility;
import com.infina.lifotrade.services.common.ObjectMapperSingleton;
import com.infina.lifotrade.services.common.Result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ViewScoped
@ManagedBean
public class FonAlimSatimTanimla implements Serializable {

	private static final long serialVersionUID = -5106813962081133701L;

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private Long accountNo;

	private Long selectedFundId;

	private BigDecimal balance = BigDecimal.ZERO;

	private Long quantity = 0L;

	private Long stock;

	private BigDecimal selectedFundPrice = BigDecimal.ZERO;

	private List<FundDto> allFunds;

	private LocalDate date = LocalDate.now();

	private String type;

	private BigDecimal calculatedTransactionAmount = BigDecimal.ZERO;

	@PostConstruct
	public void init() {
		Result result = lifotradeApi.get("/fund/");
		if (result.isSuccess()) {
			List<String> messages = new ArrayList<String>();
			messages.add("Fon getirme başarılı");
			messageBean.showSuccessMessage(messages);
			allFunds = convertToFundDto(result.getData());

		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}

	public List<FundDto> convertToFundDto(String response) {
		List<FundDto> fundListDto = null;
		try {
			fundListDto = objectMapper.readValue(response, new TypeReference<List<FundDto>>() {
			});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fundListDto;
	}

	public void onFundSelected() {
		FundDto selectedFund = allFunds
				.stream()
				.filter(fund -> fund.getId().equals(selectedFundId)).findFirst()
				.orElse(null);

		if (selectedFund != null) {
			selectedFundPrice = selectedFund.getCurrentPrice();
			if (selectedFund.getCurrentPrice() == null) {
				selectedFundPrice = BigDecimal.ZERO;
			}
			getAccountAndSetBalance();
			stock = getCustomerStock();
			calculateAmount();

		} else {
			selectedFundPrice = BigDecimal.ZERO;
			balance = BigDecimal.ZERO;
			stock = 0L;
		}
	}

	public void doTransaction() {
		FundTransactionSaveRequestDto request = new FundTransactionSaveRequestDto(accountNo, selectedFundId, quantity,
				type);
		Result result = lifotradeApi.post("/fund-transaction", request);
		if (result.isSuccess()) {
			List<String> messages = new ArrayList<String>();
			messages.add("İşlem başarılı");
			messageBean.showSuccessMessage(messages);
			FundTransactionDto fundTransactionDto = convertToFundTransactionDto(result);
			balance = fundTransactionDto.getAccount().getBalance();
			if (type.equals("SALE")) {
				stock -= quantity;
			} else {
				stock += quantity;
			}
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}

	public FundTransactionDto convertToFundTransactionDto(Result result) {
		FundTransactionDto fundTransactionDto = null;
		try {
			fundTransactionDto = objectMapper.readValue(result.getData(), FundTransactionDto.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fundTransactionDto;
	}

	public void getAccountAndSetBalance() {
		if (accountNo==null) {
			balance=BigDecimal.ZERO;
			stock=0L;
			return;
		}
		Result result = lifotradeApi.get("/account/" + accountNo);
		stock = getCustomerStock();
		if (result.isSuccess()) {
			List<String> messages = new ArrayList<String>();
			messages.add("Hesap getirme başarılı");
			messageBean.showSuccessMessage(messages);
			AccountDto accountDto = convertToAccountDto(result);
			balance = accountDto.getBalance();
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
			balance=BigDecimal.ZERO;
			stock=0L;
		}
	}

	public AccountDto convertToAccountDto(Result result) {
		AccountDto accountDto = null;
		try {
			accountDto = objectMapper.readValue(result.getData(), AccountDto.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountDto;
	}

	public Long getCustomerStock() {
		AccountFundIdRequest request = new AccountFundIdRequest(accountNo, selectedFundId);
		Result result = lifotradeApi.post("/account-fund", request);
		if (result.isSuccess()) {
			AccountFundDto accountFundDto = convertToAccountFundDto(result);
			return accountFundDto.getQuantity();
		} else {
			return 0L;
		}
	}

	public AccountFundDto convertToAccountFundDto(Result result) {
		AccountFundDto accountFundDto = null;
		try {
			accountFundDto = objectMapper.readValue(result.getData(), AccountFundDto.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountFundDto;
	}

	public void calculateAmount() {
		calculatedTransactionAmount = selectedFundPrice.multiply(BigDecimal.valueOf(quantity));
	}
}

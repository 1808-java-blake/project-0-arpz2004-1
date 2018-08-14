package com.revature.tests;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.revature.helpers.BigDecimalHelper;

public class BigDecimalHelperTest {
	@Test
	public void getMoneyStringTestZero() {
		Assert.assertEquals("$0.00", BigDecimalHelper.getMoneyString(new BigDecimal(0)));
	}
	
	@Test
	public void getMoneyStringTestPositiveInteger() {
		Assert.assertEquals("$42.00", BigDecimalHelper.getMoneyString(new BigDecimal(42)));
	}
	
	@Test
	public void getMoneyStringTestNegativeInteger() {
		Assert.assertEquals("-$3415.00", BigDecimalHelper.getMoneyString(new BigDecimal(-3415)));
	}
	
	@Test
	public void getMoneyStringTestPositiveNumberWithTwoDecimalPlaces() {
		Assert.assertEquals("$43.15", BigDecimalHelper.getMoneyString(new BigDecimal(43.15)));
	}
	
	@Test
	public void getMoneyStringTestNegativeNumberWithTwoDecimalPlaces() {
		Assert.assertEquals("-$732.43", BigDecimalHelper.getMoneyString(new BigDecimal(-732.43)));
	}
	
	@Test
	public void getMoneyStringTestPositiveNumberWithOneDecimalPlace() {
		Assert.assertEquals("$64.60", BigDecimalHelper.getMoneyString(new BigDecimal(64.6)));
	}
	
	@Test
	public void getMoneyStringTestNegativeNumberWithOneDecimalPlace() {
		Assert.assertEquals("-$7645.90", BigDecimalHelper.getMoneyString(new BigDecimal(-7645.9)));
	}
	
	@Test
	public void getMoneyStringTestPositiveNumberLessThanOne() {
		Assert.assertEquals("$0.43", BigDecimalHelper.getMoneyString(new BigDecimal(.43)));
	}
	
	@Test
	public void getMoneyStringTestNegativeNumberLessThanOne() {
		Assert.assertEquals("-$0.75", BigDecimalHelper.getMoneyString(new BigDecimal(-0.75)));
	}
	
	@Test
	public void getMoneyStringTestPositiveNumberWithMoreThanTwoDecimalPlaces() {
		Assert.assertEquals("-$5634.55", BigDecimalHelper.getMoneyString(new BigDecimal(5634.546)));
	}
	
	@Test
	public void getMoneyStringTestNegativeNumberWithMoreThanTwoDecimalPlaces() {
		Assert.assertEquals("-$876.56", BigDecimalHelper.getMoneyString(new BigDecimal(-876.56196763)));
	}
}

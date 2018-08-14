package com.revature.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatWidthException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.revature.helpers.StringHelper;

public class StringHelperTest {
	List<String> stringList;

	@Before
	public void initialize() {
		stringList = new ArrayList<>();
	}

	@Test
	public void maxLengthStringTestEmptyList() {
		Assert.assertEquals(0, StringHelper.maxLengthString(stringList));
	}

	@Test
	public void maxLengthStringTestOneStringLength0() {
		stringList.add("");
		Assert.assertEquals(0, StringHelper.maxLengthString(stringList));
	}

	@Test
	public void maxLengthStringTestOneStringLength1() {
		stringList.add("G");
		Assert.assertEquals(1, StringHelper.maxLengthString(stringList));
	}

	@Test
	public void maxLengthStringTestOneStringLength5() {
		stringList.add("Hello");
		Assert.assertEquals(5, StringHelper.maxLengthString(stringList));
	}

	@Test
	public void maxLengthStringTestMultipleStringSameLength0() {
		stringList.add("");
		stringList.add("");
		stringList.add("");
		Assert.assertEquals(0, StringHelper.maxLengthString(stringList));
	}

	@Test
	public void maxLengthStringTestMultipleStringSameLength4() {
		stringList.add("gerh");
		stringList.add("re e");
		stringList.add("    ");
		Assert.assertEquals(4, StringHelper.maxLengthString(stringList));
	}

	@Test
	public void maxLengthStringTestMultipleStringDifferentLength() {
		stringList.add("45");
		stringList.add("r 65 5");
		stringList.add("        ");
		stringList.add("");
		Assert.assertEquals(8, StringHelper.maxLengthString(stringList));
	}

	@Test(expected = MissingFormatWidthException.class)
	public void padRightTestZeroLength() {
		StringHelper.padRight("", 0);
	}
	
	@Test
	public void padRightTestLengthLessThanStringLength() {
		Assert.assertEquals("32432", StringHelper.padRight("32432", 2));
	}
	
	@Test
	public void padRightTestLengthEqualToStringLength() {
		Assert.assertEquals("j t tt", StringHelper.padRight("j t tt", 6));
	}
	
	@Test
	public void padRightTestLengthGreaterThanStringLength() {
		Assert.assertEquals("123 56   ", StringHelper.padRight("123 56", 9));
	}
}

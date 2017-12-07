package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;

import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

import com.model2.mvc.service.purchase.PurchaseService;

import com.model2.mvc.service.user.UserService;


/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = { "classpath:config/context-common.xml",
									"classpath:config/context-aspect.xml",
									"classpath:config/context-mybatis.xml",
									"classpath:config/context-transaction.xml"})
public class PurchaseServiceTest {

	// @RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	// @Test
	public void testAddPurchase() throws Exception {

		Purchase purchase = new Purchase();

		purchase.setPurchaseProd(productService.getProduct(10012));
		System.out.println(":: prodNo ::" + productService.getProduct(10012));

		purchase.setBuyer(userService.getUser("user02"));
		System.out.println("����������" + userService.getUser("user02"));

		purchase.setPaymentOption("1");
		purchase.setReceiverName("������â");
		purchase.setReceiverPhone("2222");
		purchase.setDivyAddr("�λ�");
		purchase.setDivyRequest("��ۿ䫊");
		purchase.setDivyDate("20171225");

		purchaseService.addPurchase(purchase);

		purchase = purchaseService.getPurchase(10012);

		System.out.println(purchase);

	}

	// @Test
	public void testGetPurchase() throws Exception {

		Purchase purchase = new Purchase();

		purchase = purchaseService.getPurchase(10012);

		System.out.println(purchase);
	}

	// @Test
	public void testGetPurchase2() throws Exception {

		Purchase purchase = new Purchase();

		purchase = purchaseService.getPurchase2(10021);

		System.out.println(purchase);
	}

	// @Test
	public void testUpdatePurchase() throws Exception {

		Purchase purchase = purchaseService.getPurchase2(10021);
		System.out.println(":: UpdatePurchase :: "+purchase);
		
		purchase.setPaymentOption("2");
		purchase.setReceiverName("Ȳ�ξ�");
		purchase.setReceiverPhone("0528");
		purchase.setDivyAddr("�λ�");
		purchase.setDivyRequest("ȣ�ѷѷ�");
		purchase.setDivyDate("20181231");
		
		purchaseService.updatePurchase(purchase);
		
		purchase =purchaseService.getPurchase2(10021);
		System.out.println(purchase);
	}
	// @Test
		public void testUpdateTranCode() throws Exception {

			Purchase purchase = purchaseService.getPurchase(10012);
			System.out.println(":: UpdatetranCode :: "+purchase);
			
			purchase.setTranCode("1");
			purchase.setPurchaseProd(productService.getProduct(10012));
			
			purchaseService.updateTranCode(purchase);
			
			
			purchase =purchaseService.getPurchase(10012);
			System.out.println("TranCode����� ====> "+purchase);
		} 
	 

	 @Test
	public void testGetPurchaseListAll() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String, Object> map = purchaseService.getPurchaseList(search);

		List<Object> list = (List<Object>) map.get("list");
		// Assert.assertEquals(3, list.size());
		System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("");
		map = purchaseService.getPurchaseList(search);

		list = (List<Object>) map.get("list");

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

	}
	 
	 
	 public void testGetUserListByProductNo() throws Exception{
		 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword("SCOTT");
		 	Map<String,Object> map = purchaseService.getPurchaseList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	//Assert.assertEquals(3, list.size());
		 	
			//==> console Ȯ��
		 	//System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = purchaseService.getPurchaseList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	//Assert.assertEquals(0, list.size());
		 	
			//==> console Ȯ��
		 	//System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }	 
}
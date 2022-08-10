package yile.model.dto.send;

import java.util.List;

import yile.model.po.Product;

/**
 * 列出所有資料的DTO。
 * */
public class AllProductsRes {

	private List<Product> proArr;

	public List<Product> getProArr() {
		return proArr;
	}

	public void setProArr(List<Product> proArr) {
		this.proArr = proArr;
	}
	
}

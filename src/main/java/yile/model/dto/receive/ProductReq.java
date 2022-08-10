package yile.model.dto.receive;

import yile.model.po.Product;

/**
 * 承接表單資料的DTO，與{@link Product} 解耦。
 * */
public class ProductReq {
	
	private Pro pro;
	
	public Pro getPro() {
		return pro;
	}

	public void setPro(Pro pro) {
		this.pro = pro;
	}



	public class Pro{
		
		private String chnName;
		
		private String spec;

		public String getChnName() {
			return chnName;
		}

		public void setChnName(String chnName) {
			this.chnName = chnName;
		}

		public String getSpec() {
			return spec;
		}

		public void setSpec(String spec) {
			this.spec = spec;
		}
		
		
	}

}

# YILE_Test
考試題目

## 環境、版本

開發與運行環境: JDK-8

SpringBoot版本: 2.7.2

## DEMO說明

預設RUN在8080 Port。

使用H2嵌入式的關聯資料庫，不用特別安裝，也會自己執行SQL去建置資料，所以一啟動程式就能DEMO

程式啟動時，會自動執行以下SQL建置DEMO用資料:

CREATE TABLE IF NOT EXISTS product(

  id IDENTITY PRIMARY KEY,

  chn_name NVARCHAR,

  spec NVARCHAR

);

<br>
TRUNCATE TABLE product RESTART IDENTITY;

INSERT INTO product (chn_name, spec) VALUES
	
  ('螺絲','5CM'),
	
  ('板手','0.5KG'),
	
  ('鋼釘','3CM');

<br><br>
H2-console訪問路徑：http://localhost:8080/h2-console

JDBC URL為 : jdbc:h2:./db/h2DB


<br><br>
## 我做了什麼

1. 新增時的資料傳遞型態指定兩層的json，且可為中文字，範例如下:

{pro : {chnName:"手套", spec:"3CM"}}

2. 只用bootStrap及javaScript實現前端功能(AES加密功能引入crypto-js套件、RSA加密功能引入jsencrypt套件)。
3. 加密實作1-將表格資料以AES加密後傳給後端，後端再AES解密，並新增進資料庫中。
4. 加密實作2-前端先跟後端要RSA公鑰，接著在前端將AES密鑰以RSA加密後，傳到後端以RSA私鑰解密出AES密鑰，之後兩端溝通還是以AES加密傳輸為主。
5. 後端以Spring MVC架構撰寫，前後端沒有分離。
6. spring-data-jpa實現CRUD。
7. 其他題目要求的一些行為，例如轉址、列表等等...


CREATE USER  GRR IDENTIFIED BY "GRR";
	
GRANT ALL PRIVILEGES TO GRR;

 CREATE TABLE "GRR"."URL_TABLE" 
   (	
    "ID" NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 50 ORDER  NOCYCLE , 
	"URL_LONG" VARCHAR2(250 CHAR), 
	"URL_SHORT" VARCHAR2(100 CHAR), 
	"DATE_CREATION" DATE, 
    "ENABLED" NUMBER(1,0)
   );

CREATE PUBLIC SYNONYM URL_TABLE FOR "GRR"."URL_TABLE";
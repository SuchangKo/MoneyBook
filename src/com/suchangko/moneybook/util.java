package com.suchangko.moneybook;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

public class util {
	KinofDB kinddb;
	DetailKindofDB detaildb;
	public final String Name="name";
	public final String Kindof="kindof";
	public final String Money="money";
	final ArrayList<HashMap<String, String>> headerData = new ArrayList<HashMap<String, String>>();
	final ArrayList<ArrayList<HashMap<String, Object>>> childData = new ArrayList<ArrayList<HashMap<String, Object>>>(); 
	public util(Context c){
		kinddb  = new KinofDB(c,kinddb.SQL_Create_kindofdb,kinddb.SQL_DBname);
		detaildb = new DetailKindofDB(c,detaildb.SQL_Create_detailkindofdb,detaildb.SQL_DBname);
		kinddb.open();
		detaildb.open();
	}
	/*
	public static CharSequence[] Middleitems = {"식비","교통비","교육비","건강,의료비","통신비","가구집기","주거비","품위유지비","교양,오락비","보험,저축","사업운영비","수수료,세금","기타"};
	public static CharSequence[] Middleitems1 = {"전체","식비","교통비","교육비","건강,의료비","통신비","가구집기","주거비","품위유지비","교양,오락비","보험,저축","사업운영비","수수료,세금","기타"};
	public static CharSequence[] detailitems1={"외식비","간식비","급식비","주부식비","주류비"};
	public static CharSequence[] detailitems2={"교통비","주유비","유지비","통행료","주차비","자동차세","자동차보험","세차비"};
	public static CharSequence[] detailitems3={"학교","학원","교재비","학용품","체험활동"};
	public static CharSequence[] detailitems4={"건강식품","의약품","진료비","헬스"};
	public static CharSequence[] detailitems5={"핸드폰","인터넷전화","우편요금"};
	public static CharSequence[] detailitems6={"생활용품","가전제품","주방용품","생필품"};
	public static CharSequence[] detailitems7={"상하수도","난방비","전기요금","가스요금","일반관리비","임대료","렌탈비"};
	public static CharSequence[] detailitems8={"뷰티,미용비","의류비","세탁비","액세서리","목욕비"};
	public static CharSequence[] detailitems9={"신문구독료","여행","도서","영화","대여비","공연"};
	public static CharSequence[] detailitems10={"국민연금","건강보험","종신보험","기타보험","저축"};
	public static CharSequence[] detailitems11={"본사입금","문구류","교육비","상금","컨텐츠"};
	public static CharSequence[] detailitems12={"카드수수료","이체수수료","세금","기타수수료"};
	public static CharSequence[] detailitems13={"기부금","용돈","접대비","회비","축의금","부의금"};
	*/
	public static CharSequence[] Middleitems;
	public static CharSequence[] Middleitems1;
	public static CharSequence[] detailitems1;
	public static CharSequence[] detailitems2;
	public static CharSequence[] detailitems3;
	public static CharSequence[] detailitems4;
	public static CharSequence[] detailitems5;
	public static CharSequence[] detailitems6;
	public static CharSequence[] detailitems7;
	public static CharSequence[] detailitems8;
	public static CharSequence[] detailitems9;
	public static CharSequence[] detailitems10;
	public static CharSequence[] detailitems11;
	public static CharSequence[] detailitems12;
	public static CharSequence[] detailitems13;
	
	public static CharSequence[] spendhow={"현금","카드"};
	public static CharSequence[] spendhow1={"전체","현금","카드"};
	public static CharSequence[] spendkindof={"신한","현대","국민","우리","외환","신한","롯데"};
	
	
	public static CharSequence[] Middleitems_input = {"없음","월급","이자소득","기타"};
	public static CharSequence[] Middleitems_input1 = {"전체","없음","월급","이자소득","기타"};
	public static CharSequence[] fixdel = {"수정","삭제"};
	public static CharSequence[] fixdel1 = {"예산 수정","예산 삭제"};
	
	
	public static CharSequence[] yeosan_category = {"전체 예산","카테고리 기준","세부 카테고리 기준"};
	
	public static CharSequence[]  statistics1= {"지출/전체","수입/전체","합산 통계"};
	public static CharSequence[]  statistics2= {"지출/전체","수입/전체","지출/분류","수입/분류"};
	public static CharSequence[]  monthyear= {"월 단위","년 단위"};
	public static CharSequence[]  searchdate= {"전체 기간","기간 설정"};
	public static CharSequence[]  searchcard= {"전체","현금","카드"};
	public static CharSequence[]  allspend={"전 체","없음","식비","교통비","교육비","건강,의료비","통신비","가구집기","주거비","품위유지비","교양,오락비","보험,저축","사업운영비","수수료,세금","기타"};
	//public static CharSequence[] Middleitems_input = {"없음","월급","이자소득","기타"};
	
}

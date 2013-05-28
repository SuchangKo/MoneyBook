package com.suchangko.moneybook;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

public class util {
	public final String Name="name";
	public final String Kindof="kindof";
	public final String Money="money";
	final ArrayList<HashMap<String, String>> headerData = new ArrayList<HashMap<String, String>>();
	final ArrayList<ArrayList<HashMap<String, Object>>> childData = new ArrayList<ArrayList<HashMap<String, Object>>>();
	
	public static final CharSequence[] Middleitems = {"식비","교통비","교육비","건강,의료비","통신비","가구집기","주거비","품위유지비","교양,오락비","보험,저축","사업운영비","수수료,세금","기타"};
	public static final CharSequence[] Middleitems1 = {"전체","식비","교통비","교육비","건강,의료비","통신비","가구집기","주거비","품위유지비","교양,오락비","보험,저축","사업운영비","수수료,세금","기타"};
	public static final CharSequence[] detailitems1={"외식비","간식비","급식비","주부식비","주류비"};
	public static final CharSequence[] detailitems2={"교통비","주유비","유지비","통행료","주차비","자동차세","자동차보험","세차비"};
	public static final CharSequence[] detailitems3={"학교","학원","교재비","학용품","체험활동"};
	public static final CharSequence[] detailitems4={"건강식품","의약품","진료비","헬스"};
	public static final CharSequence[] detailitems5={"핸드폰","인터넷전화","우편요금"};
	public static final CharSequence[] detailitems6={"생활용품","가전제품","주방용품","생필품"};
	public static final CharSequence[] detailitems7={"상하수도","난방비","전기요금","가스요금","일반관리비","임대료","렌탈비"};
	public static final CharSequence[] detailitems8={"뷰티,미용비","의류비","세탁비","액세서리","목욕비"};
	public static final CharSequence[] detailitems9={"신문구독료","여행","도서","영화","대여비","공연"};
	public static final CharSequence[] detailitems10={"국민연금","건강보험","종신보험","기타보험","저축"};
	public static final CharSequence[] detailitems11={"본사입금","문구류","교육비","상금","컨텐츠"};
	public static final CharSequence[] detailitems12={"카드수수료","이체수수료","세금","기타수수료"};
	public static final CharSequence[] detailitems13={"기부금","용돈","접대비","회비","축의금","부의금"};
	
	public static final CharSequence[] spendhow={"현금","카드"};
	public static final CharSequence[] spendhow1={"전체","현금","카드"};
	public static final CharSequence[] spendkindof={"신한","현대","국민","우리","외환","신한","롯데"};
	
	
	public static final CharSequence[] Middleitems_input = {"없음","월급","이자소득","기타"};
	public static final CharSequence[] fixdel = {"수정","삭제"};
	
	
	public static final CharSequence[] yeosan_category = {"전체 예산","카테고리 기준","세부 카테고리 기준"};
	
	public static final CharSequence[]  statistics1= {"지출/전체","지출/분류","수입/전체","수입/분류","합산 통계"};
	public static final CharSequence[]  monthyear= {"월 단위","주 단위"};
	public static final CharSequence[]  searchdate= {"전체 기간","기간 설정"};
	public static final CharSequence[]  searchcard= {"전 체","현금"};
	public static final CharSequence[]  allspend={"전 체","없음","식비","교통비","교육비","건강,의료비","통신비","가구집기","주거비","품위유지비","교양,오락비","보험,저축","사업운영비","수수료,세금","기타"};
	//public static final CharSequence[] Middleitems_input = {"없음","월급","이자소득","기타"};
	
}

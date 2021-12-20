package kr.or.fineapple.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DietServ {

	String UserId;
	double dailyTrgtIntakeKcal;
	double dailyTrgtIntakeCarb;
	double dailyTrgtIntakeProtein;
	double dailyTrgtIntakeFat;
	String dietServiceTrgt;
	double bodyFat;
	double trgtBodyFat;
	
	
	public DietServ() {
	}

}
